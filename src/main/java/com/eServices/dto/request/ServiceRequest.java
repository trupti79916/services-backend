package com.eServices.dto.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ServiceRequest {
    @NotBlank(message = "Service name is required")
    private String serviceName;

    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Cost is required")
    @Positive(message = "Cost must be positive")
    private BigDecimal cost;

    @NotBlank(message = "Location is required")
    private String location;

    private String contact;
    private String image;
    private List<String> features;

    @NotBlank(message = "Provider name is required")
    private String providerName;

    private String providerAvatar;

    @NotBlank(message = "Phone is required")
    private String phone;

    public ServiceRequest() {}

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderAvatar() {
        return providerAvatar;
    }

    public void setProviderAvatar(String providerAvatar) {
        this.providerAvatar = providerAvatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
} 