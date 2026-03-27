package com.fooddelivery.delivery_service.dto;

import jakarta.validation.constraints.NotNull;
import com.fooddelivery.delivery_service.model.DeliveryStatus;

public class UpdateStatusRequest {
    @NotNull(message = "Status is required")
    private DeliveryStatus status;

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
