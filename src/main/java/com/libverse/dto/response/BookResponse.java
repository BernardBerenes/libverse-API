package com.libverse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("author")
    private AuthorResponse author;

    @JsonProperty("category")
    private CategoryResponse category;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("title")
    private String title;

    @JsonProperty("cover_url")
    private String coverUrl;

    @JsonProperty("published_year")
    private Integer publishedYear;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("synopsis")
    private String synopsis;
}
