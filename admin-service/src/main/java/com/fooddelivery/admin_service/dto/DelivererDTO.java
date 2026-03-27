package com.fooddelivery.admin_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DelivererDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String licenseNumber;
    private String vehiculeType;
    private String password;
}