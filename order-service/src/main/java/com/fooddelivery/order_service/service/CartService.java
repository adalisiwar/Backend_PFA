package com.fooddelivery.order_service.service;

import com.fooddelivery.order_service.dto.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    // Simple in-memory cart: Map<customerId, List<CartItemDTO>>
    private final Map<Long, List<CartItemDTO>> carts = new HashMap<>();

    public CartItemDTO addToCart(Long customerId, CartItemDTO item) {
        // customerId comes from either @RequestParam or item.getCustomerId()
        Long id = customerId != null ? customerId : item.getCustomerId();

        List<CartItemDTO> cart = carts.getOrDefault(id, new ArrayList<>());

        Optional<CartItemDTO> existing = cart.stream()
                .filter(i -> i.getMenuItemId().equals(item.getMenuItemId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            cart.add(item);
        }

        carts.put(id, cart);
        return item;
    }

    public List<CartItemDTO> getCart(Long customerId) {
        return carts.getOrDefault(customerId, new ArrayList<>());
    }

    public void clearCart(Long customerId) {
        carts.remove(customerId);
    }
}
