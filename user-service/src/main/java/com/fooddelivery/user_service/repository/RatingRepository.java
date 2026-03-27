package com.fooddelivery.user_service.repository;

import com.fooddelivery.user_service.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUserId(Long userId);
    List<Rating> findByRestaurantId(Long restaurantId);
    List<Rating> findByDeliveryPersonId(Long deliveryPersonId);
}
