package com.libverse.util;

import com.libverse.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationUtil {
    public User getAuthenticatedUser() {
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getPrincipal();

        if (!(principal instanceof User)) throw new IllegalStateException("No authenticated user found");

        return (User) principal;
    }
}
