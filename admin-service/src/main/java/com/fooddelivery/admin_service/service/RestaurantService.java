package com.fooddelivery.admin_service.service;

import com.fooddelivery.admin_service.client.RestaurantClient;
import com.fooddelivery.admin_service.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantClient restaurantClient;

    public RestaurantService(RestaurantClient restaurantClient) {
        this.restaurantClient = restaurantClient;
    }

    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO, String token) {
        return restaurantClient.createRestaurant(restaurantDTO, "Bearer " + token);
    }

    public RestaurantDTO getRestaurantById(Long id, String token) {
        return restaurantClient.getRestaurantById(id, "Bearer " + token);
    }

    public List<RestaurantDTO> getAllRestaurants(String token) {
        return restaurantClient.getAllRestaurants("Bearer " + token);
    }

public void deleteRestaurant(Long id, String token) {
        restaurantClient.deleteRestaurant(id, "Bearer " + token);
    }

    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO, String token) {
        return restaurantClient.updateRestaurant(id, restaurantDTO, "Bearer " + token);
    }
}