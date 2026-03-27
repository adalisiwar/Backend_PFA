package com.fooddelivery.admin_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {

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

    @Enumerated(EnumType.STRING)
    private Role role;


    public enum Role {
        ADMIN, CUSTOMER, RESTAURANT_OWNER, DELIVERY_PERSONNEL
    }
}
