package com.fooddelivery.delivery_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryDTO {
    private Long orderId;      // which order
    private Long userId;       // which user
    private String deliveryAddress;  // where to deliver
}