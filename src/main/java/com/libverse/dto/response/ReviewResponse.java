package com.libverse.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("commnet")
    private String comment;
}
