package com.libverse.service;

import com.libverse.dto.request.CategoryRequest;
import com.libverse.dto.response.CategoryResponse;
import com.libverse.entity.Category;
import com.libverse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public void create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
    }

    public void update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (categoryRepository.existsByNameAndIdNot(request.getName(), id)) throw new IllegalArgumentException("Category name already exists");

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
    }
}
