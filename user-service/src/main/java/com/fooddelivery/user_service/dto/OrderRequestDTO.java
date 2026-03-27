package com.fooddelivery.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Long restaurantId;
    private Long customerId;      // ← renamed from userId
    private List<OrderItemDTO> orderItems;  // ← renamed from items
}