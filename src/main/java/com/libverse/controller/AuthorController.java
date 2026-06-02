package com.libverse.controller;

import com.libverse.dto.request.AuthorRequest;
import com.libverse.dto.response.ApiResponse;
import com.libverse.dto.response.AuthorResponse;
import com.libverse.service.AuthorService;
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
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/paginate")
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> paginate(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String name) {
        Page<AuthorResponse> response = authorService.paginate(page, size, name);

        return ResponseEntity.ok(ApiResponse.paginate("Successfully get data", response));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> list() {
        List<AuthorResponse> authorResponse = authorService.list();

        return ResponseEntity.ok(ApiResponse.success("Successfully get data", authorResponse));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody AuthorRequest request) {
        authorService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Successfully create data"));
    }

    @PatchMapping("/{authorId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable UUID authorId, @Valid @RequestBody AuthorRequest request) {
        authorService.update(authorId, request);

        return ResponseEntity.ok(ApiResponse.success("Successfully update data"));
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID authorId) {
        authorService.delete(authorId);

        return ResponseEntity.ok(ApiResponse.success("Successfully delete data"));
    }
}
