package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    List<MenuDTO> getMenuForRestaurant(Long restaurantId, String token);
}
