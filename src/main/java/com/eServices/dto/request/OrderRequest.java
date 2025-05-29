package com.eServices.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull(message = "Service ID is required")
    private Long serviceId;

    private LocalDateTime scheduledDate;

    public OrderRequest() {}

    public OrderRequest(Long serviceId, LocalDateTime scheduledDate) {
        this.serviceId = serviceId;
        this.scheduledDate = scheduledDate;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
} 