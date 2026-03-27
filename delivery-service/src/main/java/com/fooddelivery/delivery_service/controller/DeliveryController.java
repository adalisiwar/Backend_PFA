package com.fooddelivery.delivery_service.controller;

import com.fooddelivery.delivery_service.dto.DeliveryDTO;
import com.fooddelivery.delivery_service.dto.DeliveryTrackingDTO;
import com.fooddelivery.delivery_service.dto.UpdateStatusRequest;
import com.fooddelivery.delivery_service.model.Delivery;
import com.fooddelivery.delivery_service.service.DeliveryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    private String extractJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    // badlneha lezm tytrma f user
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryDTO dto) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(dto.getOrderId());
        delivery.setUserId(dto.getUserId());
        delivery.setDeliveryAddress(dto.getDeliveryAddress());
        return new ResponseEntity<>(deliveryService.createDelivery(delivery), HttpStatus.CREATED);
    }

    // User tracks their delivery
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/track")
    public ResponseEntity<DeliveryTrackingDTO> trackDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.trackDelivery(id));
    }

    // Admin gets all deliveries
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    // Admin or Delivery gets delivery by id
    @PreAuthorize("hasRole('ADMIN') or hasRole('DELIVERY')")
    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    // Delivery sees available deliveries
    @PreAuthorize("hasRole('DELIVERY')")
    @GetMapping("/available")
    public ResponseEntity<List<Delivery>> getAvailableDeliveries() {
        return ResponseEntity.ok(deliveryService.getAvailableDeliveries());
    }

    // Delivery accepts a delivery
    @PreAuthorize("hasRole('DELIVERY')")
    @PutMapping("/{id}/accept")
    public ResponseEntity<Delivery> acceptDelivery(
            @PathVariable Long id,
            @RequestParam Long livreurId,
            HttpServletRequest request) {
        String token = extractJwt(request);
        return ResponseEntity.ok(deliveryService.acceptDelivery(id, livreurId, token));
    }

    // Delivery updates delivery status
    @PreAuthorize("hasRole('DELIVERY')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Delivery> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request,
            HttpServletRequest servletRequest) {
        String token = extractJwt(servletRequest);
        return ResponseEntity.ok(deliveryService.updateStatus(id, request.getStatus(), token));
    }

}