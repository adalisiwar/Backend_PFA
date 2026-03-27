package com.fooddelivery.admin_service.service;

import com.fooddelivery.admin_service.client.DelivererClient;
import com.fooddelivery.admin_service.dto.DelivererDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelivererService {

    private final DelivererClient delivererClient;

    public DelivererService(DelivererClient delivererClient) {
        this.delivererClient = delivererClient;
    }

    public DelivererDTO createDeliverer(DelivererDTO delivererDTO, String token) {
        return delivererClient.createDeliverer(delivererDTO, "Bearer " + token);
    }

    public DelivererDTO getDelivererById(Long id, String token) {
        return delivererClient.getDelivererById(id, "Bearer " + token);
    }

    public List<DelivererDTO> getAllDeliverers(String token) {
        return delivererClient.getAllDeliverers("Bearer " + token);
    }

    public void deleteDeliverer(Long id, String token) {
        delivererClient.deleteDeliverer(id, "Bearer " + token);
    }

    public DelivererDTO updateDeliverer(Long id, DelivererDTO delivererDTO, String token) {
        return delivererClient.updateDeliverer(id, delivererDTO, "Bearer " + token);
    }
}
