package com.eServices.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eServices.dto.request.ServiceRequest;
import com.eServices.dto.response.ServiceResponse;
import com.eServices.service.ServiceOfferingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceOfferingController {

    @Autowired
    private ServiceOfferingService serviceOfferingService;

    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        List<ServiceResponse> services = serviceOfferingService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long id) {
        Optional<ServiceResponse> service = serviceOfferingService.getServiceById(id);
        return service.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/services")
    public ResponseEntity<ServiceResponse> createService(@Valid @RequestBody ServiceRequest serviceRequest) {
        ServiceResponse createdService = serviceOfferingService.createService(serviceRequest);
        return ResponseEntity.ok(createdService);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable Long id, 
                                                        @Valid @RequestBody ServiceRequest serviceRequest) {
        Optional<ServiceResponse> updatedService = serviceOfferingService.updateService(id, serviceRequest);
        return updatedService.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        boolean deleted = serviceOfferingService.deleteService(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
