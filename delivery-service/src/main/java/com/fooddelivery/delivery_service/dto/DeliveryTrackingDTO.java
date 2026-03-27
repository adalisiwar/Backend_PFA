package com.fooddelivery.delivery_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryTrackingDTO {
    private Long orderId;
    private String status;        // PENDING, ACCEPTED, IN_PROGRESS, DELIVERED
    private String delivererName; // just the name, not full details
}