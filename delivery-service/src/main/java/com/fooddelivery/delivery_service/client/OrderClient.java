package com.fooddelivery.delivery_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "http://localhost:8085")
public interface OrderClient {

    @PutMapping("/api/orders/{id}/status")
    void updateOrderStatus(@RequestHeader("Authorization") String token,
                           @PathVariable("id") Long orderId,
                           @RequestParam("status") String status);

    @PutMapping("/api/orders/{id}/delivery")
    void updateDeliveryId(@RequestHeader("Authorization") String token,
                          @PathVariable("id") Long orderId,
                          @RequestParam("deliveryId") Long deliveryId);
}
