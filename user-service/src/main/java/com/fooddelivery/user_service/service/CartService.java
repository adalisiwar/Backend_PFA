package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.client.OrderClient;
import com.fooddelivery.user_service.dto.CartItemDTO;
import com.fooddelivery.user_service.dto.CartResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final OrderClient orderClient;

    public CartResponseDTO addToCart(String token, Long customerId, CartItemDTO cartItem) {
        return orderClient.addToCart("Bearer " + token, cartItem, customerId);
    }

    public List<CartResponseDTO> getCart(String token, Long customerId) {
        return orderClient.getCart("Bearer " + token, customerId);
    }

    public void clearCart(String token, Long userId) {
        orderClient.clearCart("Bearer " + token, userId);
    }
}