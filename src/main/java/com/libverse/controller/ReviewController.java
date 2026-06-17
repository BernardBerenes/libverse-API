package com.libverse.controller;

import com.libverse.dto.request.ReviewRequest;
import com.libverse.dto.response.ApiResponse;
import com.libverse.dto.response.BorrowingResponse;
import com.libverse.dto.response.ReviewResponse;
import com.libverse.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/paginate/{bookId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> paginate(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @PathVariable UUID bookId) {
        Page<ReviewResponse> response = reviewService.paginate(page, size, bookId);

        return ResponseEntity.ok(ApiResponse.paginate("Successfully get data", response));
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> create(@PathVariable UUID bookId, @Valid @RequestBody ReviewRequest request) {
        reviewService.create(bookId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Successfully create data"));
    }
}
