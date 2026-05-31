package com.libverse.controller;

import com.libverse.dto.request.CategoryRequest;
import com.libverse.dto.response.ApiResponse;
import com.libverse.dto.response.CategoryResponse;
import com.libverse.service.CategoryService;
import org.springframework.data.domain.Page;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/paginate")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> paginate(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String name) {
        Page<CategoryResponse> response = categoryService.paginate(page, size, name);

        return ResponseEntity.ok(ApiResponse.paginate("Successfully get data", response));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> list(@RequestParam(required = false) String name) {
        List<CategoryResponse> categoryResponse = categoryService.list();

        return ResponseEntity.ok(ApiResponse.success("Successfully get data", categoryResponse));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> detail(@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = categoryService.detail(categoryId);

        return ResponseEntity.ok(ApiResponse.success("Successfully get data", categoryResponse));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CategoryRequest request) {
        categoryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Successfully create data"));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequest request) {
        categoryService.update(categoryId, request);

        return ResponseEntity.ok(ApiResponse.success("Successfully update data"));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);

        return ResponseEntity.ok(ApiResponse.success("Successfully delete data"));
    }
}
