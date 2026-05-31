package com.libverse.service;

import com.libverse.dto.request.LoginRequest;
import com.libverse.dto.request.RegisterRequest;
import com.libverse.dto.response.LoginResponse;
import com.libverse.entity.User;
import com.libverse.repository.UserRepository;
import com.libverse.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) throw new IllegalArgumentException("Email already registered");

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = Objects.requireNonNull((User) authentication.getPrincipal());

            if (user.getDeletedAt() != null) {
                throw new BadCredentialsException("Account has been deactivated");
            }

            String token = jwtUtil.generateToken(user);

            return LoginResponse.builder()
                    .token(token)
                    .build();

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
