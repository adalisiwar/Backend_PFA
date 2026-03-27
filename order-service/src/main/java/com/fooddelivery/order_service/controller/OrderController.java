package com.fooddelivery.order_service.controller;

import com.fooddelivery.order_service.dto.CartItemDTO;
import com.fooddelivery.order_service.dto.OrderDTO;
import com.fooddelivery.order_service.dto.OrderItemDTO;
import com.fooddelivery.order_service.service.CartService;
import com.fooddelivery.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody OrderDTO orderDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(orderService.createOrder(orderDTO, token));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('DELIVERY')")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/orders/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DELIVERY')")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    @PutMapping("/orders/{id}/status")
    @PreAuthorize("hasRole('DELIVERY')")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @PutMapping("/orders/{id}/delivery")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDTO> updateDeliveryId(
            @PathVariable Long id,
            @RequestParam Long deliveryId) {
        return ResponseEntity.ok(orderService.updateDeliveryId(id, deliveryId));
    }

    @PostMapping("/cart/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartItemDTO> addToCart(
            @RequestBody CartItemDTO cartItem,
            @RequestParam Long customerId) {
        CartItemDTO addedItem = cartService.addToCart(customerId, cartItem);
        return ResponseEntity.ok(addedItem);
    }

    @GetMapping("/cart/{customerId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }

    @DeleteMapping("/cart/{customerId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/from-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderDTO> createOrderFromCart(
            @RequestParam Long customerId,
            @RequestBody OrderDTO orderDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        List<CartItemDTO> cartItems = cartService.getCart(customerId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<OrderItemDTO> orderItems = cartItems.stream()
                .map(ci -> OrderItemDTO.builder()
                        .menuItemId(ci.getMenuItemId())
                        .quantity(ci.getQuantity())
                        .price(ci.getPrice())
                        .build())
                .collect(Collectors.toList());
        orderDTO.setOrderItems(orderItems);
        orderDTO.setCustomerId(customerId);
        OrderDTO createdOrder = orderService.createOrder(orderDTO, token);
        cartService.clearCart(customerId);
        return ResponseEntity.ok(createdOrder);
    }
}