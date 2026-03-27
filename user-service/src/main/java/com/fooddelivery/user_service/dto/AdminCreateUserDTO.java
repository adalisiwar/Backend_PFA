package com.fooddelivery.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Used exclusively by the admin POST /api/users endpoint.
 * Password is optional — the service generates a secure default if omitted.
 * Role is required since admin must explicitly assign it.
 */
@Data
public class AdminCreateUserDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * Optional. If not provided, a secure random password is generated.
     * The user should reset it via a password-reset flow.
     */
    private String password;

    @NotBlank(message = "Role is required")
    private String role;
}