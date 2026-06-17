package com.libverse.service;

import com.libverse.dto.request.ReviewRequest;
import com.libverse.dto.response.CategoryResponse;
import com.libverse.dto.response.ReviewResponse;
import com.libverse.entity.Book;
import com.libverse.entity.Review;
import com.libverse.entity.User;
import com.libverse.repository.BookRepository;
import com.libverse.repository.ReviewRepository;
import com.libverse.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final AuthenticationUtil authenticationUtil;

    private final BookRepository bookRepository;

    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }

    public Page<ReviewResponse> paginate(int page, int size, UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        return reviewRepository.findByBook(book, pageable).map(this::toResponse);
    }

    public void create(UUID bookId, ReviewRequest request) {
        User authenticatedUser = authenticationUtil.getAuthenticatedUser();

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Review review = new Review();
        review.setUser(authenticatedUser);
        review.setBook(book);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        reviewRepository.save(review);
    }
}
