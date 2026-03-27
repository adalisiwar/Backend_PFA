package com.fooddelivery.user_service.controller;

import com.fooddelivery.user_service.dto.MenuDTO;
import com.fooddelivery.user_service.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/menu")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class MenuController {

    private final MenuService menuService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuDTO>> getMenuByRestaurant(
            @PathVariable Long restaurantId,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(menuService.getMenuForRestaurant(restaurantId, token));
    }
}