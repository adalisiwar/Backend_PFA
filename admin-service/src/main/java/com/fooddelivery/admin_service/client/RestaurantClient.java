package com.fooddelivery.admin_service.client;

import com.fooddelivery.admin_service.dto.RestaurantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "restaurant-service", url = "http://localhost:8082")
public interface RestaurantClient {

    @PostMapping("/restaurants")
    RestaurantDTO createRestaurant(@RequestBody RestaurantDTO restaurantDTO,
                                   @RequestHeader("Authorization") String token);

    @GetMapping("/restaurants/{id}")
    RestaurantDTO getRestaurantById(@PathVariable("id") Long id,
                                    @RequestHeader("Authorization") String token);

    @GetMapping("/restaurants")
    List<RestaurantDTO> getAllRestaurants(@RequestHeader("Authorization") String token);

    @DeleteMapping("/restaurants/{id}")
    void deleteRestaurant(@PathVariable("id") Long id,
                          @RequestHeader("Authorization") String token);

    @PutMapping("/restaurants/{id}")
    RestaurantDTO updateRestaurant(@PathVariable("id") Long id, @RequestBody RestaurantDTO restaurantDTO,
                                   @RequestHeader("Authorization") String token);
}
