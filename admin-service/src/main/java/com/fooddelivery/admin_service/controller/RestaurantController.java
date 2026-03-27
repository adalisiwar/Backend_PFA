package com.fooddelivery.admin_service.controller;

import com.fooddelivery.admin_service.dto.RestaurantDTO;
import com.fooddelivery.admin_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurants")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(
            @RequestBody RestaurantDTO restaurantDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurantDTO, token), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(
            @PathVariable Long id,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(restaurantService.getRestaurantById(id, token));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants(HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(restaurantService.getAllRestaurants(token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(
            @PathVariable Long id,
            HttpServletRequest request) {
        String token = extractJwt(request);
        restaurantService.deleteRestaurant(id, token);
        return ResponseEntity.ok("Restaurant deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantDTO restaurantDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDTO, token));
    }
}

