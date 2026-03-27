package com.fooddelivery.admin_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "delivery_personnels")
@Data
public class DeliveryPersonnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank
    private String password;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    private String vehicleType;

    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private Status status;
    //role

    public enum Status {
        AVAILABLE, BUSY, OFFLINE
    }
}
