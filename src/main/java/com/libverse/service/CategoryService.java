package com.libverse.service;

import com.libverse.dto.request.CategoryRequest;
import com.libverse.dto.response.CategoryResponse;
import com.libverse.entity.Category;
import com.libverse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Page<CategoryResponse> paginate(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        return (name != null && !name.isBlank()) ? categoryRepository.findByNameContainingIgnoreCase(name, pageable).map(this::toResponse) : categoryRepository.findAll(pageable).map(this::toResponse);
    }

    public List<CategoryResponse> list() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse detail(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        return toResponse(category);
    }

    public void create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) throw new IllegalArgumentException("Category name already exists");

        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    public void update(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (categoryRepository.existsByNameAndIdNot(request.getName(), categoryId)) throw new IllegalArgumentException("Category name already exists");

        category.setName(request.getName());
        categoryRepository.save(category);
    }

    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        category.setDeletedAt(Instant.now());
        categoryRepository.save(category);
    }
}
