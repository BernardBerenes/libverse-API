package com.libverse.controller;

import com.libverse.dto.request.BookRequest;
import com.libverse.dto.response.ApiResponse;
import com.libverse.dto.response.BookResponse;
import com.libverse.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/paginate")
    public ResponseEntity<ApiResponse<List<BookResponse>>> paginate(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<BookResponse> response = bookService.paginate(page, size);

        return ResponseEntity.ok(ApiResponse.paginate("Successfully get data", response));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @ModelAttribute BookRequest request) throws IOException {
        bookService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Successfully create data"));
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable UUID bookId, @Valid @ModelAttribute BookRequest request) {
        bookService.update(bookId, request);

        return ResponseEntity.ok(ApiResponse.success("Successfully update data"));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID bookId) {
        bookService.delete(bookId);

        return ResponseEntity.ok(ApiResponse.success("Successfully delete data"));
    }
}
