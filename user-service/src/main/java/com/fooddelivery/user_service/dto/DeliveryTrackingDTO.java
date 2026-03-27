package com.fooddelivery.user_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryTrackingDTO {
    private Long orderId;
    private String status;
    private String delivererName;
}