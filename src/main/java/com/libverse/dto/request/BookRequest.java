package com.libverse.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    @NotNull(message = "Author is required")
    private UUID authorId;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:\\d{9}[\\dXx]|\\d{13})$", message = "ISBN must be a valid ISBN-10 or ISBN-13")
    private String isbn;

    @NotBlank(message = "Title is required")
    private String title;

    private MultipartFile cover;

    @NotNull(message = "Published year is required")
    private Integer publishedYear;

    @NotNull(message = "Total pages is required")
    @Min(1)
    private Integer totalPages;

    @Size(min = 50, max = 5000)
    private String synopsis;
}
