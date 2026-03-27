package com.fooddelivery.user_service.controller;

import com.fooddelivery.user_service.dto.ErrorResponse;
import com.fooddelivery.user_service.dto.LoginRequest;
import com.fooddelivery.user_service.dto.LoginResponse;
import com.fooddelivery.user_service.model.User;
import com.fooddelivery.user_service.repository.UserRepository;
import com.fooddelivery.user_service.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/auth")  // Separate path for auth
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
        }

        // ✅ Créer la liste de rôles correctement
        List<String> roles = List.of("ROLE_" + user.getRole().toUpperCase());

        // Générer le token avec la liste de rôles
        String token = tokenProvider.generateToken(user.getEmail(), roles);

        return ResponseEntity.ok(new LoginResponse(token, user.getUsername()));
    }
}