package com.fooddelivery.delivery_service.service;

import com.fooddelivery.delivery_service.client.OrderClient;
import com.fooddelivery.delivery_service.dto.DeliveryTrackingDTO;
import com.fooddelivery.delivery_service.model.Delivery;
import com.fooddelivery.delivery_service.model.DeliveryStatus;
import com.fooddelivery.delivery_service.repository.DelivererRepository;
import com.fooddelivery.delivery_service.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository repository;
    private final DelivererRepository delivererRepository;  // ← add this
    private final OrderClient orderClient;

    public Delivery createDelivery(Delivery delivery) {
        return repository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
    }

    public List<Delivery> getAvailableDeliveries() {
        return repository.findByStatus(DeliveryStatus.AVAILABLE);
    }

    public Delivery acceptDelivery(Long id, Long livreurId, String token) {
        Delivery delivery = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        delivery.setLivreurId(livreurId);
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        repository.save(delivery);

        try {
            orderClient.updateOrderStatus("Bearer " + token, delivery.getOrderId(), "IN_DELIVERY");
            orderClient.updateDeliveryId("Bearer " + token, delivery.getOrderId(), delivery.getId());
        } catch (Exception e) {
            System.err.println("Failed to update order: " + e.getMessage());
        }

        return delivery;
    }

    public Delivery updateStatus(Long id, DeliveryStatus status, String token) {
        Delivery delivery = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        delivery.setStatus(status);
        repository.save(delivery);

        try {
            String orderStatus = switch (status) {
                case IN_PROGRESS -> "IN_DELIVERY";
                case DELIVERED -> "DELIVERED";
                case CANCELED -> "CANCELLED";
                default -> null;
            };

            if (orderStatus != null) {
                orderClient.updateOrderStatus("Bearer " + token, delivery.getOrderId(), orderStatus);
            }
        } catch (Exception e) {
            System.err.println("Failed to update order status: " + e.getMessage());
        }

        return delivery;
    }

    public DeliveryTrackingDTO trackDelivery(Long id) {
        Delivery delivery = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));

        String delivererName = "Not assigned yet";
        if (delivery.getLivreurId() != null) {
            delivererName = delivererRepository.findById(delivery.getLivreurId())
                    .map(d -> d.getName() + " (" + d.getPhone() + ")")
                    .orElse("Deliverer #" + delivery.getLivreurId());
        }

        return DeliveryTrackingDTO.builder()
                .orderId(delivery.getOrderId())
                .status(delivery.getStatus().name())
                .delivererName(delivererName)
                .build();
    }
}
