package com.fooddelivery.order_service.client;

import com.fooddelivery.order_service.dto.MenuItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "restaurant-service", url = "http://localhost:8082")
public interface RestaurantClient {

    @GetMapping("/menu-items/{id}")
    MenuItemDTO getMenuItemById(@RequestHeader("Authorization") String token,
                                @PathVariable("id") Long menuItemId);
}