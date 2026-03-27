package com.fooddelivery.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long customerId;
    private Long menuItemId;
    private int quantity;
    private BigDecimal price;
}