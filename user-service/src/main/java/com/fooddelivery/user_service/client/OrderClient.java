package com.fooddelivery.user_service.client;

import com.fooddelivery.user_service.dto.CartItemDTO;
import com.fooddelivery.user_service.dto.CartResponseDTO;
import com.fooddelivery.user_service.dto.OrderRequestDTO;
import com.fooddelivery.user_service.dto.OrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "http://localhost:8085")
public interface OrderClient {

    // CART
    @PostMapping("/api/cart/add")
    CartResponseDTO addToCart(@RequestHeader("Authorization") String token,
                              @RequestBody CartItemDTO cartItem,
                              @RequestParam("customerId") Long customerId);

    @GetMapping("/api/cart/{customerId}")
    List<CartResponseDTO> getCart(@RequestHeader("Authorization") String token,
                                  @PathVariable("customerId") Long customerId);

    @DeleteMapping("/api/cart/{customerId}")
    void clearCart(@RequestHeader("Authorization") String token,
                   @PathVariable("customerId") Long customerId);

    // ORDERS
    @PostMapping("/api/orders")
    OrderResponseDTO createOrder(@RequestHeader("Authorization") String token,
                                 @RequestBody OrderRequestDTO orderRequestDTO);

    @GetMapping("/api/orders/{orderId}")
    OrderResponseDTO getOrderById(@RequestHeader("Authorization") String token,
                                  @PathVariable("orderId") Long orderId);

    @GetMapping("/api/orders/customer/{customerId}")
    List<OrderResponseDTO> getOrdersByCustomer(@RequestHeader("Authorization") String token,
                                               @PathVariable("customerId") Long customerId);

    // FROM CART
    @PostMapping("/api/orders/from-cart")
    OrderResponseDTO createOrderFromCart(@RequestHeader("Authorization") String token,
                                         @RequestParam("customerId") Long customerId,
                                         @RequestBody OrderRequestDTO orderRequestDTO);
}