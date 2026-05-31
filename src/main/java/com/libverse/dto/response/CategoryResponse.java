package com.libverse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}
