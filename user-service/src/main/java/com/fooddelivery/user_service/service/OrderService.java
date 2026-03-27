package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.client.OrderClient;
import com.fooddelivery.user_service.dto.CartItemDTO;
import com.fooddelivery.user_service.dto.OrderRequestDTO;
import com.fooddelivery.user_service.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderClient orderClient;

    // ORDER METHODS
    public OrderResponseDTO placeOrder(String token, OrderRequestDTO orderRequestDTO) {
        return orderClient.createOrder("Bearer " + token, orderRequestDTO);
    }

    public OrderResponseDTO getOrderById(String token, Long orderId) {
        return orderClient.getOrderById("Bearer " + token, orderId);
    }

    public OrderResponseDTO createOrderFromCart(String token, Long userId, OrderRequestDTO orderRequestDTO) {
        return orderClient.createOrderFromCart("Bearer " + token, userId, orderRequestDTO);
    }






}