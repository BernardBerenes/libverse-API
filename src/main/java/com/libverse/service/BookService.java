package com.libverse.service;

import com.libverse.dto.request.BookRequest;
import com.libverse.dto.response.AuthorResponse;
import com.libverse.dto.response.BookResponse;
import com.libverse.dto.response.CategoryResponse;
import com.libverse.dto.response.CountryResponse;
import com.libverse.entity.Author;
import com.libverse.entity.Book;
import com.libverse.entity.Category;
import com.libverse.repository.AuthorRepository;
import com.libverse.repository.BookRepository;
import com.libverse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    private final S3Service s3Service;

    private BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .author(AuthorResponse.builder()
                        .id(book.getAuthor().getId())
                        .nationality(CountryResponse.builder()
                                .id(book.getAuthor().getNationality().getId())
                                .code(book.getAuthor().getNationality().getCode())
                                .name(book.getAuthor().getNationality().getName())
                                .build())
                        .name(book.getAuthor().getName())
                        .biography(book.getAuthor().getBiography())
                        .birthDate(book.getAuthor().getBirthDate())
                        .build())
                .category(CategoryResponse.builder()
                        .id(book.getCategory().getId())
                        .name(book.getCategory().getName())
                        .build())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .coverUrl(book.getCoverUrl())
                .publishedYear(book.getPublishedYear())
                .totalPages(book.getTotalPages())
                .synopsis(book.getSynopsis())
                .build();
    }

    private void upsert(BookRequest request, Book book) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        String coverUrl = null;
        if (request.getCover() != null && !request.getCover().isEmpty()) {
            if (book.getCoverUrl() != null) s3Service.delete(book.getCoverUrl());

            coverUrl = s3Service.upload(request.getCover());
        }

        book.setAuthor(author);
        book.setCategory(category);
        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());
        book.setCoverUrl(coverUrl);
        book.setPublishedYear(request.getPublishedYear());
        book.setTotalPages(request.getTotalPages());
        book.setSynopsis(request.getSynopsis());
        bookRepository.save(book);
    }

    public Page<BookResponse> paginate(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        return bookRepository.findAll(pageable).map(this::toResponse);
    }

    public void create(BookRequest request) throws IOException {
        if (bookRepository.existsByIsbn(request.getIsbn())) throw new IllegalArgumentException("ISBN already exists");

        Book book = new Book();
        upsert(request, book);
    }

    public void update(UUID bookId, BookRequest request) {
        if (bookRepository.existsByIsbnAndIdNot(request.getIsbn(), bookId)) throw new IllegalArgumentException("ISBN already exists");

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        upsert(request, book);
    }

    public void delete(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        s3Service.delete(book.getCoverUrl());

        book.setDeletedAt(Instant.now());
        bookRepository.save(book);
    }
}
