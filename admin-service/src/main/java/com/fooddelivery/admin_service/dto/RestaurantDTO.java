package com.fooddelivery.admin_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;
    private String email;
    private String name;
    private String address;
    private String phone;
    private String password;
}