package com.libverse.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {
    @NotNull(message = "Nationality is required")
    @JsonProperty("country_id")
    private Long countryId;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255)
    @JsonProperty("name")
    private String name;

    @JsonProperty("biography")
    private String biography;

    @Past(message = "Birth date must be in the past")
    @JsonProperty("birth_date")
    private LocalDate birthDate;
}
