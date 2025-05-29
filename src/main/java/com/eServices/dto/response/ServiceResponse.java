package com.eServices.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class ServiceResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal rating;
    private Integer reviews;
    private String location;
    private String type;
    private String image;
    private String phone;
    private List<String> features;
    private ProviderResponse provider;

    // Constructors
    public ServiceResponse() {}

    public ServiceResponse(Long id, String name, String description, BigDecimal price, 
                          BigDecimal rating, Integer reviews, String location, String type, 
                          String image, String phone, List<String> features, ProviderResponse provider) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.reviews = reviews;
        this.location = location;
        this.type = type;
        this.image = image;
        this.phone = phone;
        this.features = features;
        this.provider = provider;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public ProviderResponse getProvider() {
        return provider;
    }

    public void setProvider(ProviderResponse provider) {
        this.provider = provider;
    }

    // Inner class for Provider
    public static class ProviderResponse {
        private String id;
        private String name;
        private String avatar;

        public ProviderResponse() {}

        public ProviderResponse(String id, String name, String avatar) {
            this.id = id;
            this.name = name;
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
} 