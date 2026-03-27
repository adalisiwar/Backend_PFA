package com.fooddelivery.delivery_service.repository;

import com.fooddelivery.delivery_service.model.Deliverer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DelivererRepository extends JpaRepository<Deliverer, Long> {
    Optional<Deliverer> findByEmail(String email);
}
