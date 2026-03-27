package com.fooddelivery.admin_service.controller;

import com.fooddelivery.admin_service.dto.DelivererDTO;
import com.fooddelivery.admin_service.service.DelivererService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/deliverers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DelivererController {

    private final DelivererService delivererService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<DelivererDTO> createDeliverer(
            @RequestBody DelivererDTO delivererDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return new ResponseEntity<>(delivererService.createDeliverer(delivererDTO, token), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DelivererDTO> getDelivererById(
            @PathVariable Long id,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(delivererService.getDelivererById(id, token));
    }

    @GetMapping
    public ResponseEntity<List<DelivererDTO>> getAllDeliverers(HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(delivererService.getAllDeliverers(token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeliverer(
            @PathVariable Long id,
            HttpServletRequest request) {
        String token = extractJwt(request);
        delivererService.deleteDeliverer(id, token);
        return ResponseEntity.ok("Deliverer deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<DelivererDTO> updateDeliverer(
            @PathVariable Long id,
            @RequestBody DelivererDTO delivererDTO,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(delivererService.updateDeliverer(id, delivererDTO, token));
    }
}
