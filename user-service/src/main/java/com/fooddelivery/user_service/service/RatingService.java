package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.dto.RatingDTO;
import com.fooddelivery.user_service.model.Rating;

import java.util.List;

public interface RatingService {
    Rating addRatingFromDTO(RatingDTO dto);
    RatingDTO toDTO(Rating rating);
    List<RatingDTO> getRatingsByUser(Long userId);

    // New methods
    List<Rating> getRatingsForRestaurant(Long restaurantId);
    List<Rating> getRatingsForDeliveryPerson(Long deliveryPersonId);
}
