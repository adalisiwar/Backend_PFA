package com.fooddelivery.user_service.service;

import com.fooddelivery.user_service.dto.RatingDTO;
import com.fooddelivery.user_service.dto.UserDTO;
import com.fooddelivery.user_service.model.Rating;
import com.fooddelivery.user_service.model.User;
import com.fooddelivery.user_service.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserService userService;

    @Override

    public Rating addRatingFromDTO(RatingDTO dto) {
        UserDTO userDTO = userService.getUserById(dto.getUserId());

        User user = new User();
        user.setId(userDTO.getId());

        Rating rating = new Rating();
        rating.setUser(user); 
        rating.setRestaurantId(dto.getRestaurantId());
        rating.setDeliveryPersonId(dto.getDeliveryPersonId());
        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());
        rating.setType(dto.getType());

        return ratingRepository.save(rating);
    }

    @Override
    public RatingDTO toDTO(Rating rating) {
        return new RatingDTO(
                rating.getId(),
                rating.getUser().getId(),
                rating.getRestaurantId(),
                rating.getDeliveryPersonId(),
                rating.getScore(),
                rating.getComment(),
                rating.getType()
        );
    }

    @Override
    public List<RatingDTO> getRatingsByUser(Long userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);
        return ratings.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Rating> getRatingsForRestaurant(Long restaurantId) {
        return ratingRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Rating> getRatingsForDeliveryPerson(Long deliveryPersonId) {
        return ratingRepository.findByDeliveryPersonId(deliveryPersonId);
    }
}
