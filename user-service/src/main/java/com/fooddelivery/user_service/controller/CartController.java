package com.fooddelivery.user_service.controller;

import com.fooddelivery.user_service.dto.CartItemDTO;
import com.fooddelivery.user_service.dto.CartResponseDTO;
import com.fooddelivery.user_service.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(
            @RequestBody CartItemDTO cartItem,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(cartService.addToCart(token, cartItem.getCustomerId(), cartItem));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartResponseDTO>> getCart(
            @PathVariable Long customerId,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(cartService.getCart(token, customerId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(
            @PathVariable Long userId,
            HttpServletRequest request) {
        String token = extractJwt(request);
        cartService.clearCart(token, userId);
        return ResponseEntity.noContent().build();
    }
}