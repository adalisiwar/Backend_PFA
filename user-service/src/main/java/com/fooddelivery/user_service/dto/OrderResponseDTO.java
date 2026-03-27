package com.fooddelivery.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private Long deliveryId;
    private String status;
    private List<OrderItemDTO> orderItems;
    private BigDecimal totalPrice;
}