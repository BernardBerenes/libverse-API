package com.libverse.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookResponse {
    private UUID id;

    private AuthorResponse author;

    private CategoryResponse category;

    private String isbn;

    private String title;

    private String coverUrl;

    private Integer publishedYear;

    private Integer totalPages;

    private String synopsis;
}
