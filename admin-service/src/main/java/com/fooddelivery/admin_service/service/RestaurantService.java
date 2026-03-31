package com.fooddelivery.admin_service.service;

import com.fooddelivery.admin_service.client.RestaurantClient;
import com.fooddelivery.admin_service.dto.RestaurantDTO;
import com.fooddelivery.admin_service.util.JwtTokenProvider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantClient restaurantClient;
    private final JwtTokenProvider jwtTokenProvider;

    public RestaurantService(RestaurantClient restaurantClient, JwtTokenProvider jwtTokenProvider) {
        this.restaurantClient = restaurantClient;
        this.jwtTokenProvider = jwtTokenProvider;
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
        String cleanToken = token.replace("Bearer ", "");
        
        // Admin bypass
        if (jwtTokenProvider.getRolesFromToken(cleanToken).stream()
                .anyMatch(role -> role.equals("ROLE_ADMIN"))) {
            return updateRestaurantData(id, restaurantDTO, token);
        }

        String loggedInUsername = jwtTokenProvider.getUsernameFromToken(cleanToken);
        RestaurantDTO existing = getRestaurantById(id, token);
        if (!existing.getEmail().equals(loggedInUsername)) {
            throw new AccessDeniedException("You can only update your own profile");
        }

        return updateRestaurantData(id, restaurantDTO, token);
    }

    private RestaurantDTO updateRestaurantData(Long id, RestaurantDTO restaurantDTO, String token) {
        return restaurantClient.updateRestaurant(id, restaurantDTO, "Bearer " + token);
    }
}