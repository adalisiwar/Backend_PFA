package com.fooddelivery.user_service.controller;


import com.fooddelivery.user_service.dto.RestaurantDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fooddelivery.user_service.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/users/restaurants")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(restaurantService.getAllRestaurants(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(
            @PathVariable Long id,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(restaurantService.getRestaurantById(id, token));
    }


}