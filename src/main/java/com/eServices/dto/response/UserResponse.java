package com.eServices.dto.response;

import com.eServices.entity.User.UserRole;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private AddressResponse address;

    public UserResponse() {}

    public UserResponse(Long id, String name, String email, UserRole role, AddressResponse address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public static class AddressResponse {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;

        public AddressResponse() {}

        public AddressResponse(String street, String city, String state, String zipCode, String country) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
            this.country = country;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
} 