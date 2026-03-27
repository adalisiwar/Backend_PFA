package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.client.RestaurantClient;
import com.fooddelivery.user_service.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final RestaurantClient restaurantClient;

    @Override
    public List<MenuDTO> getMenuForRestaurant(Long restaurantId, String token) {
        return restaurantClient.getMenuByRestaurant(restaurantId, "Bearer " + token);
    }
}