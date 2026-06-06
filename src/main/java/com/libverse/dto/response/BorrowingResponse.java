package com.libverse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libverse.entity.enums.BorrowStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class BorrowingResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("book")
    private BookResponse book;

    @JsonProperty("borrow_date")
    private LocalDate borrowDate;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("status")
    private BorrowStatus status;
}
