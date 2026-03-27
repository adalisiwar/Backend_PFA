package com.fooddelivery.delivery_service.controller;

import com.fooddelivery.delivery_service.dto.DelivererDTO;
import com.fooddelivery.delivery_service.model.Deliverer;
import com.fooddelivery.delivery_service.repository.DelivererRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliverers")
public class DelivererController {

    private final DelivererRepository delivererRepository;
    private final PasswordEncoder passwordEncoder;

    public DelivererController(DelivererRepository delivererRepository,
                               PasswordEncoder passwordEncoder) {
        this.delivererRepository = delivererRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DelivererDTO> createDeliverer(@RequestBody DelivererDTO dto) {
        Deliverer deliverer = new Deliverer();
        deliverer.setName(dto.getName());
        deliverer.setEmail(dto.getEmail());
        deliverer.setPhone(dto.getPhone());
        deliverer.setLicenseNumber(dto.getLicenseNumber());
        deliverer.setVehiculeType(dto.getVehiculeType());
        deliverer.setPassword(passwordEncoder.encode(dto.getPassword()));

        Deliverer saved = delivererRepository.save(deliverer);
        return new ResponseEntity<>(mapToDTO(saved), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<DelivererDTO>> getAllDeliverers() {
        return ResponseEntity.ok(delivererRepository.findAll()
                .stream().map(this::mapToDTO).toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DelivererDTO> getDelivererById(@PathVariable Long id) {
        Deliverer deliverer = delivererRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deliverer not found with id: " + id));
        return ResponseEntity.ok(mapToDTO(deliverer));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDeliverer(@PathVariable Long id) {
        if (!delivererRepository.existsById(id)) {
            throw new RuntimeException("Deliverer not found with id: " + id);
        }
        delivererRepository.deleteById(id);
        return ResponseEntity.ok("Deliverer deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DelivererDTO> updateDeliverer(@PathVariable Long id, @RequestBody DelivererDTO dto) {
        Deliverer deliverer = delivererRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deliverer not found with id: " + id));
        
        deliverer.setName(dto.getName());
        deliverer.setEmail(dto.getEmail());
        deliverer.setPhone(dto.getPhone());
        deliverer.setLicenseNumber(dto.getLicenseNumber());
        deliverer.setVehiculeType(dto.getVehiculeType());
        
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            deliverer.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        Deliverer updated = delivererRepository.save(deliverer);
        return ResponseEntity.ok(mapToDTO(updated));
    }

    private DelivererDTO mapToDTO(Deliverer deliverer) {
        return DelivererDTO.builder()
                .id(deliverer.getId())
                .name(deliverer.getName())
                .email(deliverer.getEmail())
                .phone(deliverer.getPhone())
                .licenseNumber(deliverer.getLicenseNumber())
                .vehiculeType(deliverer.getVehiculeType())
                .build();
    }
}