package com.libverse.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    private String password;
}
