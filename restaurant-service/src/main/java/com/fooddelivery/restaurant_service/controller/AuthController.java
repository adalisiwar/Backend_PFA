package com.fooddelivery.restaurant_service.controller;

import com.fooddelivery.restaurant_service.dto.ErrorResponse;
import com.fooddelivery.restaurant_service.dto.LoginRequest;
import com.fooddelivery.restaurant_service.dto.LoginResponse;
import com.fooddelivery.restaurant_service.model.Restaurant;
import com.fooddelivery.restaurant_service.repository.RestaurantRepository;
import com.fooddelivery.restaurant_service.util.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants/auth")
public class AuthController {

    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(RestaurantRepository restaurantRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Restaurant> optionalRestaurant =
                restaurantRepository.findByEmail(loginRequest.getEmail());

        if (optionalRestaurant.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid credentials"));
        }

        Restaurant restaurant = optionalRestaurant.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), restaurant.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Invalid credentials"));
        }

        String token = jwtTokenProvider.generateToken(
                restaurant.getEmail(),
                List.of("ROLE_RESTAURANT")
        );

        return ResponseEntity.ok(new LoginResponse(token, restaurant.getEmail()));
    }
}