package com.fooddelivery.order_service.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Long customerId;
    private Long menuItemId;
    private int quantity;
    private BigDecimal price;
}
