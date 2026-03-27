package com.fooddelivery.user_service.controller;

import com.fooddelivery.user_service.dto.RatingDTO;
import com.fooddelivery.user_service.model.Rating;
import com.fooddelivery.user_service.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    // Add a rating (restaurant or delivery)
    @PostMapping
    public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO ratingDTO) {
        Rating savedRating = ratingService.addRatingFromDTO(ratingDTO);
        return ResponseEntity.ok(ratingService.toDTO(savedRating));
    }

    // Get ratings by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByUser(@PathVariable Long userId) {
        List<RatingDTO> ratings = ratingService.getRatingsByUser(userId);
        return ResponseEntity.ok(ratings);
    }

    // Get ratings for a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<RatingDTO>> getRatingsForRestaurant(@PathVariable Long restaurantId) {
        List<RatingDTO> ratings = ratingService.getRatingsForRestaurant(restaurantId)
                .stream()
                .map(ratingService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ratings);
    }

    // Get ratings for a delivery person
    @GetMapping("/delivery/{deliveryPersonId}")
    public ResponseEntity<List<RatingDTO>> getRatingsForDeliveryPerson(@PathVariable Long deliveryPersonId) {
        List<RatingDTO> ratings = ratingService.getRatingsForDeliveryPerson(deliveryPersonId)
                .stream()
                .map(ratingService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ratings);
    }
}
