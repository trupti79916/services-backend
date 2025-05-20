package com.eServices.controller;

import com.eServices.entity.ServiceOffering;
import com.eServices.service.ServiceOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @Autowired
    public ServiceOfferingController(ServiceOfferingService serviceOfferingService) {
        this.serviceOfferingService = serviceOfferingService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceOffering>> getAllServiceOfferings() {
        return ResponseEntity.ok(serviceOfferingService.getAllServiceOfferings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceOfferingById(@PathVariable Long id) {
        Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(id);
        return serviceOffering.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceOffering> createServiceOffering(@RequestBody ServiceOffering serviceOffering) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceOfferingService.saveServiceOffering(serviceOffering));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateServiceOffering(
            @PathVariable Long id, @RequestBody ServiceOffering serviceOffering) {
        Optional<ServiceOffering> existingServiceOffering = serviceOfferingService.getServiceOfferingById(id);
        if (existingServiceOffering.isPresent()) {
            serviceOffering.setServiceId(id);
            return ResponseEntity.ok(serviceOfferingService.saveServiceOffering(serviceOffering));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceOffering(@PathVariable Long id) {
        Optional<ServiceOffering> existingServiceOffering = serviceOfferingService.getServiceOfferingById(id);
        if (existingServiceOffering.isPresent()) {
            serviceOfferingService.deleteServiceOffering(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceOffering>> searchServiceOfferings(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) BigDecimal maxCost) {
        return ResponseEntity.ok(serviceOfferingService.findByFilters(category, location, maxCost));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ServiceOffering>> getServiceOfferingsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(serviceOfferingService.findByCategory(category));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<ServiceOffering>> getServiceOfferingsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(serviceOfferingService.findByLocation(location));
    }
}
