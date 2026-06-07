package com.libverse.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineRequest {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Integer amount;
}
