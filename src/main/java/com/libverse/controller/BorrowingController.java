package com.libverse.controller;

import com.libverse.dto.request.BorrowingRequest;
import com.libverse.dto.response.ApiResponse;
import com.libverse.dto.response.BorrowingResponse;
import com.libverse.service.BorrowingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrowing")
public class BorrowingController {
    private final BorrowingService borrowingService;

    @GetMapping("/paginate")
    public ResponseEntity<ApiResponse<List<BorrowingResponse>>> paginate(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<BorrowingResponse> response = borrowingService.paginate(page, size);

        return ResponseEntity.ok(ApiResponse.paginate("Successfully get data", response));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody BorrowingRequest request) {
        borrowingService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Successfully create data"));
    }
}
