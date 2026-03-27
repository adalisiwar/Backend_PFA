package com.fooddelivery.user_service.controller;

import com.fooddelivery.user_service.client.DeliveryClient;
import com.fooddelivery.user_service.dto.AdminCreateUserDTO;
import com.fooddelivery.user_service.dto.DeliveryTrackingDTO;
import com.fooddelivery.user_service.dto.RatingDTO;
import com.fooddelivery.user_service.dto.UserDTO;
import com.fooddelivery.user_service.dto.UserRegisterDTO;
import jakarta.validation.Valid;
import com.fooddelivery.user_service.model.Rating;
import com.fooddelivery.user_service.service.RatingService;
import com.fooddelivery.user_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RatingService ratingService;
    private final DeliveryClient deliveryClient;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    // ─── Public registration ───────────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserRegisterDTO registerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(registerDTO));
    }

    // ─── Admin CRUD ────────────────────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * POST /api/users
     * Admin creates a user. Password is optional — a secure random one is
     * generated server-side if omitted. Role must be explicitly provided.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody @Valid AdminCreateUserDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String email,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // ─── Ratings ───────────────────────────────────────────────────────────────

    @PostMapping("/ratings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO dto) {
        Rating saved = ratingService.addRatingFromDTO(dto);
        return ResponseEntity.ok(ratingService.toDTO(saved));
    }

    @GetMapping("/{userId}/ratings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RatingDTO>> getRatingsByUser(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ratingService.getRatingsByUser(userId));
    }

    // ─── Delivery tracking ─────────────────────────────────────────────────────

    @GetMapping("/delivery/{id}/track")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DeliveryTrackingDTO> trackDelivery(
            @PathVariable Long id,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(deliveryClient.trackDelivery("Bearer " + token, id));
    }
}