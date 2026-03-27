package com.fooddelivery.restaurant_service.service;

import com.fooddelivery.restaurant_service.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {

    // Accept DTO for registration (so we can include password)
    RestaurantDTO addRestaurant(RestaurantDTO dto);

    // Update existing restaurant
    RestaurantDTO updateRestaurant(Long id, RestaurantDTO dto);

    // Delete restaurant
    void deleteRestaurant(Long id);

    // Get all restaurants
    List<RestaurantDTO> getAllRestaurants();

    // Get restaurant by id
    RestaurantDTO getRestaurantById(Long id);



}
