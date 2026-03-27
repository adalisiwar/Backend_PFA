package com.fooddelivery.user_service.client;

import com.fooddelivery.user_service.dto.MenuDTO;
import com.fooddelivery.user_service.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "restaurant-service", url = "http://localhost:8082")
public interface RestaurantClient {

    @GetMapping("/restaurants")
    List<RestaurantDTO> getAllRestaurants(@RequestHeader("Authorization") String token);

    @GetMapping("/restaurants/{id}")
    RestaurantDTO getRestaurantById(@PathVariable("id") Long id,
                                    @RequestHeader("Authorization") String token);

    @GetMapping("/menu-items/restaurant/{restaurantId}")
    List<MenuDTO> getMenuByRestaurant(@PathVariable("restaurantId") Long restaurantId,
                                      @RequestHeader("Authorization") String token);
}