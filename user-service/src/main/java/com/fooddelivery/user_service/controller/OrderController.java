package com.fooddelivery.user_service.controller;

import com.fooddelivery.user_service.dto.CartItemDTO;
import com.fooddelivery.user_service.dto.OrderRequestDTO;
import com.fooddelivery.user_service.dto.OrderResponseDTO;
import com.fooddelivery.user_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class OrderController {

    private final OrderService orderService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    // -------------------------
    // ORDER ENDPOINTS
    // -------------------------

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(orderService.placeOrder(token, orderRequestDTO));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(orderService.getOrderById(token, orderId));
    }

    @PostMapping("/from-cart")
    public ResponseEntity<OrderResponseDTO> createOrderFromCart(
            @RequestParam Long userId,
            @RequestBody OrderRequestDTO orderRequestDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(orderService.createOrderFromCart(token, userId, orderRequestDTO));
    }






}