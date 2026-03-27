package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.client.RestaurantClient;
import com.fooddelivery.user_service.dto.MenuDTO;
import com.fooddelivery.user_service.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantClient restaurantClient;

    public RestaurantService(RestaurantClient restaurantClient) {
        this.restaurantClient = restaurantClient;
    }

    public List<RestaurantDTO> getAllRestaurants(String token) {
        return restaurantClient.getAllRestaurants("Bearer " + token);
    }

    public RestaurantDTO getRestaurantById(Long id, String token) {
        return restaurantClient.getRestaurantById(id, "Bearer " + token);
    }

    public List<MenuDTO> getMenuByRestaurant(Long restaurantId, String token) {
        return restaurantClient.getMenuByRestaurant(restaurantId, "Bearer " + token);
    }
}