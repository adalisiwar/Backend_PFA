package com.fooddelivery.user_service.dto;

import com.fooddelivery.user_service.model.RatingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private Long deliveryPersonId;
    private Integer score;
    private String comment;
    private RatingType type;
}
