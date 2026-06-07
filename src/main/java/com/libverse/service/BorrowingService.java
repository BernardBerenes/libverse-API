package com.libverse.service;

import com.libverse.dto.request.BorrowingRequest;
import com.libverse.dto.response.*;
import com.libverse.entity.Book;
import com.libverse.entity.Borrowing;
import com.libverse.entity.User;
import com.libverse.entity.enums.BorrowStatus;
import com.libverse.kafka.producer.MailProducer;
import com.libverse.repository.BookRepository;
import com.libverse.repository.BorrowingRepository;
import com.libverse.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    private final BookRepository bookRepository;

    private final AuthenticationUtil authenticationUtil;

    private final MailProducer mailProducer;

    private BorrowingResponse toResponse(Borrowing borrowing) {
        return BorrowingResponse.builder()
                .id(borrowing.getId())
                .book(BookResponse.builder()
                        .id(borrowing.getBook().getId())
                        .author(AuthorResponse.builder()
                                .id(borrowing.getBook().getAuthor().getId())
                                .nationality(CountryResponse.builder()
                                        .id(borrowing.getBook().getAuthor().getNationality().getId())
                                        .code(borrowing.getBook().getAuthor().getNationality().getCode())
                                        .name(borrowing.getBook().getAuthor().getNationality().getName())
                                        .build())
                                .name(borrowing.getBook().getAuthor().getName())
                                .biography(borrowing.getBook().getAuthor().getBiography())
                                .birthDate(borrowing.getBook().getAuthor().getBirthDate())
                                .build())
                        .category(CategoryResponse.builder()
                                .id(borrowing.getBook().getCategory().getId())
                                .name(borrowing.getBook().getCategory().getName())
                                .build())
                        .isbn(borrowing.getBook().getIsbn())
                        .title(borrowing.getBook().getTitle())
                        .coverUrl(borrowing.getBook().getCoverUrl())
                        .publishedYear(borrowing.getBook().getPublishedYear())
                        .totalPages(borrowing.getBook().getTotalPages())
                        .synopsis(borrowing.getBook().getSynopsis())
                        .build())
                .borrowDate(borrowing.getBorrowDate())
                .dueDate(borrowing.getDueDate())
                .status(borrowing.getStatus())
                .build();
    }

    public Page<BorrowingResponse> paginate(int page, int size) {
        User authenticatedUser = authenticationUtil.getAuthenticatedUser();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("borrowDate").descending());

        return borrowingRepository.findByUserId(authenticatedUser.getId(), pageable)
                .map(this::toResponse);
    }

    public void create(BorrowingRequest request) {
        if (request.getDueDate().isBefore(LocalDate.now().plusWeeks(1))) throw new IllegalArgumentException("Due date must be at least 1 weeks from today");

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        User authenticatedUser = authenticationUtil.getAuthenticatedUser();

        Borrowing borrowing = new Borrowing();
        borrowing.setUser(authenticatedUser);
        borrowing.setBook(book);
        borrowing.setDueDate(request.getDueDate());
        borrowingRepository.save(borrowing);

        mailProducer.send(authenticatedUser.getEmail(), "Confirmation Borrowing - " + book.getTitle(), "borrowing-created",
                Map.of(
                    "name", authenticatedUser.getName(),
                    "book_title", book.getTitle(),
                    "author_name", book.getAuthor().getName(),
                    "isbn", book.getIsbn(),
                    "borrow_date", LocalDate.now().toString(),
                    "due_date", request.getDueDate().toString(),
                    "status", borrowing.getStatus().name()
                )
        );
    }

    public void confirm(UUID borrowingId, UUID userId) {
        Borrowing borrowing = borrowingRepository.findByIdAndUserIdAndStatus(borrowingId, userId, BorrowStatus.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));

        borrowing.setStatus(BorrowStatus.BORROWED);
        borrowingRepository.save(borrowing);
    }

    public void returning(UUID borrowingId) {
        User authenticatedUser = authenticationUtil.getAuthenticatedUser();

        Borrowing borrowing = borrowingRepository.findByIdAndUserIdAndStatus(borrowingId, authenticatedUser.getId(), BorrowStatus.BORROWED)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));

        borrowing.setStatus(BorrowStatus.RETURNED);
        borrowingRepository.save(borrowing);
    }

    public void complete(UUID borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));

        if (borrowing.getStatus() != BorrowStatus.RETURNED) throw new IllegalArgumentException("Borrowing not found");

        borrowing.setStatus(BorrowStatus.COMPLETED);
        borrowingRepository.save(borrowing);
    }
}
