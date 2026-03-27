package com.fooddelivery.admin_service.client;

import com.fooddelivery.admin_service.dto.DelivererDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "delivery-service", url = "http://localhost:8083")
public interface DelivererClient {

    @PostMapping("/api/deliverers")
    DelivererDTO createDeliverer(@RequestBody DelivererDTO delivererDTO,
                                 @RequestHeader("Authorization") String token);

    @GetMapping("/api/deliverers/{id}")
    DelivererDTO getDelivererById(@PathVariable("id") Long id,
                                  @RequestHeader("Authorization") String token);

    @GetMapping("/api/deliverers")
    List<DelivererDTO> getAllDeliverers(@RequestHeader("Authorization") String token);

    @DeleteMapping("/api/deliverers/{id}")
    void deleteDeliverer(@PathVariable("id") Long id,
                         @RequestHeader("Authorization") String token);

    @PutMapping("/api/deliverers/{id}")
    DelivererDTO updateDeliverer(@PathVariable("id") Long id, @RequestBody DelivererDTO delivererDTO,
                                 @RequestHeader("Authorization") String token);
}
