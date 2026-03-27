package com.fooddelivery.delivery_service.controller;

import com.fooddelivery.delivery_service.dto.ErrorResponse;
import com.fooddelivery.delivery_service.dto.LoginRequest;
import com.fooddelivery.delivery_service.dto.LoginResponse;
import com.fooddelivery.delivery_service.model.Deliverer;
import com.fooddelivery.delivery_service.repository.DelivererRepository;
import com.fooddelivery.delivery_service.util.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/delivery/auth")
public class AuthController {

    private final DelivererRepository delivererRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(DelivererRepository delivererRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.delivererRepository = delivererRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Deliverer> optional =
                delivererRepository.findByEmail(loginRequest.getEmail());

        if (optional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid credentials"));
        }

        Deliverer deliverer = optional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), deliverer.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid credentials"));
        }

        String token = jwtTokenProvider.generateToken(
                deliverer.getEmail(),
                List.of("ROLE_DELIVERY")
        );

        return ResponseEntity.ok(new LoginResponse(token, deliverer.getEmail()));
    }
}