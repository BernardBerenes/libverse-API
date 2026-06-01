package com.libverse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libverse.entity.Country;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class AuthorResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("nationality")
    private CountryResponse nationality;

    @JsonProperty("name")
    private String name;

    @JsonProperty("biography")
    private String biography;

    @JsonProperty("birth_date")
    private LocalDate birthDate;
}
