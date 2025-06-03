package com.eServices.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "service_offerings")
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(length = 100, nullable = false)
    private String serviceName;

    @Column(length = 500)
    private String description;

    @Column(length = 100)
    private String category;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(length = 100)
    private String location;

    @Column(length = 100)
    private String contact;

    @Column(length = 500)
    private String image;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.valueOf(0.0);

    @Column
    private Integer reviewCount = 0;

    @ElementCollection
    @CollectionTable(name = "service_features", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "feature")
    private List<String> features;

    @Column(length = 100)
    private String providerName;

    @Column(length = 500)
    private String providerAvatar;

    @Column(length = 15)
    private String phone;

    public ServiceOffering() {
    }
}