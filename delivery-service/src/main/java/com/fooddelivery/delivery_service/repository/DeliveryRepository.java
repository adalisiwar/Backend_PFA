package com.fooddelivery.delivery_service.repository;

import com.fooddelivery.delivery_service.model.Delivery;
import com.fooddelivery.delivery_service.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByStatus(DeliveryStatus status);
}