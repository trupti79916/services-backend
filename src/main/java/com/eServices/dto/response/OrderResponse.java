package com.eServices.dto.response;

import java.time.LocalDateTime;

import com.eServices.entity.Order.OrderStatus;

public class OrderResponse {
    private Long id;
    private Long userId;
    private Long serviceId;
    private Integer rating;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private LocalDateTime scheduledDate;
    private ServiceResponse service;

    // Constructors
    public OrderResponse() {}

    public OrderResponse(Long id, Long userId, Long serviceId, Integer rating, OrderStatus status,
                        LocalDateTime orderDate, LocalDateTime scheduledDate, ServiceResponse service) {
        this.id = id;
        this.userId = userId;
        this.serviceId = serviceId;
        this.rating = rating;
        this.status = status;
        this.orderDate = orderDate;
        this.scheduledDate = scheduledDate;
        this.service = service;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public ServiceResponse getService() {
        return service;
    }

    public void setService(ServiceResponse service) {
        this.service = service;
    }
} 