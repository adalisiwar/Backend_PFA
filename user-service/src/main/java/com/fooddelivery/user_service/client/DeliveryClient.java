package com.fooddelivery.user_service.client;

import com.fooddelivery.user_service.dto.DeliveryTrackingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "delivery-service", url = "http://localhost:8083")
public interface DeliveryClient {

    @GetMapping("/api/delivery/{id}/track")
    DeliveryTrackingDTO trackDelivery(@RequestHeader("Authorization") String token,
                                      @PathVariable("id") Long deliveryId);
}