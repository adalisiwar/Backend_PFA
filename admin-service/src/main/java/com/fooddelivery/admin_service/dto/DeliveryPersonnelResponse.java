package com.fooddelivery.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPersonnelResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String vehicleType;

    private String licenseNumber;

    private String status;
}
