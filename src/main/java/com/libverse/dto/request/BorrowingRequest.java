package com.libverse.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRequest {
    @NotNull(message = "Book is required")
    @JsonProperty("book_id")
    private UUID bookId;

    @NotNull(message = "Due date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("due_date")
    private LocalDate dueDate;
}
