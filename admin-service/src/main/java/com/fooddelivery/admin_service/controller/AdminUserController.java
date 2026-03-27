package com.fooddelivery.admin_service.controller;

import com.fooddelivery.admin_service.dto.UserDTO;
import com.fooddelivery.admin_service.dto.RatingDTO;
import com.fooddelivery.admin_service.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(adminUserService.getAllUsers(token));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        String token = extractJwt(request);
        return new ResponseEntity<>(adminUserService.createUser(userDTO, token), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id, HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(adminUserService.getUserById(id, token));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email, HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(adminUserService.getUserByEmail(email, token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO, HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(adminUserService.updateUser(id, userDTO, token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        String token = extractJwt(request);
        adminUserService.deleteUser(id, token);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{userId}/ratings")
    public ResponseEntity<List<RatingDTO>> getRatingsByUser(@PathVariable Long userId, HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(adminUserService.getRatingsByUser(userId, token));
    }
}

