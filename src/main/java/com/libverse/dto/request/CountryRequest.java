package com.libverse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryRequest {
    @NotBlank(message = "Code is required")
    @Size(min = 3, max = 3)
    private String code;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;
}
