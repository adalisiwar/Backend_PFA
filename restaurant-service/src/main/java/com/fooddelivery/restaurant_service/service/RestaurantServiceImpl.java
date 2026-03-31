package com.fooddelivery.restaurant_service.service;

import com.fooddelivery.restaurant_service.dto.RestaurantDTO;
import com.fooddelivery.restaurant_service.model.Restaurant;
import com.fooddelivery.restaurant_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RestaurantDTO addRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = Restaurant.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        Restaurant saved = restaurantRepository.save(restaurant);
        return mapToDTO(saved);
    }

@Override
@PreAuthorize("hasRole('RESTAURANT') or hasRole('ADMIN')")
public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO, Authentication authentication) {
    // Admin bypass
    if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
        return this.updateRestaurantData(id, restaurantDTO);
    }

    String loggedInUsername = authentication.getName();
    RestaurantDTO existing = this.getRestaurantById(id);
    if (!existing.getEmail().equals(loggedInUsername)) {
        throw new AccessDeniedException("You can only update your own profile");
    }

    return this.updateRestaurantData(id, restaurantDTO);
}

private RestaurantDTO updateRestaurantData(Long id, RestaurantDTO restaurantDTO) {
    Restaurant restaurant = restaurantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    
    if (restaurantDTO.getName() != null) {
        restaurant.setName(restaurantDTO.getName());
    }
    if (restaurantDTO.getEmail() != null) {
        restaurant.setEmail(restaurantDTO.getEmail());
    }
    if (restaurantDTO.getAddress() != null) {
        restaurant.setAddress(restaurantDTO.getAddress());
    }
    if (restaurantDTO.getPhone() != null) {
        restaurant.setPhone(restaurantDTO.getPhone());
    }
    if (restaurantDTO.getPassword() != null) {
        restaurant.setPassword(passwordEncoder.encode(restaurantDTO.getPassword()));
    }
    
    Restaurant updated = restaurantRepository.save(restaurant);
    return mapToDTO(updated);
}


    @Override
    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
        return mapToDTO(restaurant);
    }

    private RestaurantDTO mapToDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .email(restaurant.getEmail())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                // password intentionally omitted for security
                .build();
    }
}