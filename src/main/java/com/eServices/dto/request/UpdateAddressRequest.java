package com.eServices.dto.request;

import jakarta.validation.constraints.Size;

public class UpdateAddressRequest {
    @Size(max = 300, message = "Address must not exceed 300 characters")
    private String address;

    public UpdateAddressRequest() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
} 