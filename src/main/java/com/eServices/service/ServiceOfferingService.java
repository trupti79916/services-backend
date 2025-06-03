package com.eServices.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eServices.dto.request.ServiceRequest;
import com.eServices.dto.response.ServiceResponse;
import com.eServices.entity.ServiceOffering;
import com.eServices.repository.ServiceOfferingRepository;

@Service
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Autowired
    public ServiceOfferingService(ServiceOfferingRepository serviceOfferingRepository) {
        this.serviceOfferingRepository = serviceOfferingRepository;
    }

    private ServiceResponse convertToResponse(ServiceOffering serviceOffering) {
        ServiceResponse.ProviderResponse provider = new ServiceResponse.ProviderResponse(
            serviceOffering.getServiceId().toString(),
            serviceOffering.getProviderName(),
            serviceOffering.getProviderAvatar()
        );

        return new ServiceResponse(
            serviceOffering.getServiceId(),
            serviceOffering.getServiceName(),
            serviceOffering.getDescription(),
            serviceOffering.getCost(),
            serviceOffering.getRating(),
            serviceOffering.getReviewCount(),
            serviceOffering.getLocation(),
            serviceOffering.getCategory(),
            serviceOffering.getImage(),
            serviceOffering.getPhone(),
            serviceOffering.getContact(),
            serviceOffering.getFeatures(),
            provider
        );
    }

    private ServiceOffering convertToEntity(ServiceRequest request) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setServiceName(request.getServiceName());
        serviceOffering.setDescription(request.getDescription());
        serviceOffering.setCategory(request.getCategory());
        serviceOffering.setCost(request.getCost());
        serviceOffering.setLocation(request.getLocation());
        serviceOffering.setContact(request.getContact());
        serviceOffering.setImage("https://images.unsplash.com/photo-1581578731548-c64695cc6952?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80");
        serviceOffering.setFeatures(request.getFeatures());
        serviceOffering.setProviderName(request.getProviderName());
        serviceOffering.setProviderAvatar("https://randomuser.me/api/portraits/lego/5.jpg");
        serviceOffering.setPhone(request.getPhone());
        return serviceOffering;
    }

    public List<ServiceResponse> getAllServices() {
        return serviceOfferingRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Optional<ServiceResponse> getServiceById(Long id) {
        return serviceOfferingRepository.findById(id)
                .map(this::convertToResponse);
    }

    public ServiceResponse createService(ServiceRequest request) {
        ServiceOffering serviceOffering = convertToEntity(request);
        ServiceOffering saved = serviceOfferingRepository.save(serviceOffering);
        return convertToResponse(saved);
    }

    public Optional<ServiceResponse> updateService(Long id, ServiceRequest request) {
        return serviceOfferingRepository.findById(id).map(existingService -> {
            existingService.setServiceName(request.getServiceName());
            existingService.setDescription(request.getDescription());
            existingService.setCategory(request.getCategory());
            existingService.setCost(request.getCost());
            existingService.setLocation(request.getLocation());
            existingService.setContact(request.getContact());
            existingService.setImage(request.getImage());
            existingService.setFeatures(request.getFeatures());
            existingService.setProviderName(request.getProviderName());
            existingService.setProviderAvatar(request.getProviderAvatar());
            existingService.setPhone(request.getPhone());
            
            ServiceOffering updated = serviceOfferingRepository.save(existingService);
            return convertToResponse(updated);
        });
    }

    public boolean deleteService(Long id) {
        if (serviceOfferingRepository.existsById(id)) {
            serviceOfferingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ServiceResponse> searchServices(String location, String category, String name) {
        List<ServiceOffering> services;
        
        if (name != null && !name.trim().isEmpty()) {
            services = serviceOfferingRepository.findByServiceNameContainingIgnoreCase(name);
        } else if (category != null && location != null) {
            services = serviceOfferingRepository.findByCategoryAndLocation(category, location);
        } else if (category != null) {
            services = serviceOfferingRepository.findByCategory(category);
        } else if (location != null) {
            services = serviceOfferingRepository.findByLocation(location);
        } else {
            services = serviceOfferingRepository.findAll();
        }
        
        return services.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceResponse> findByCategory(String category) {
        return serviceOfferingRepository.findByCategory(category).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceResponse> findByLocation(String location) {
        return serviceOfferingRepository.findByLocation(location).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Legacy methods for backwards compatibility
    public List<ServiceOffering> getAllServiceOfferings() {
        return serviceOfferingRepository.findAll();
    }

    public Optional<ServiceOffering> getServiceOfferingById(Long id) {
        return serviceOfferingRepository.findById(id);
    }

    public ServiceOffering saveServiceOffering(ServiceOffering serviceOffering) {
        return serviceOfferingRepository.save(serviceOffering);
    }

    public void deleteServiceOffering(Long id) {
        serviceOfferingRepository.deleteById(id);
    }

    public List<ServiceOffering> findByCostLessThanEqual(BigDecimal maxCost) {
        return serviceOfferingRepository.findByCostLessThanEqual(maxCost);
    }

    public List<ServiceOffering> findByFilters(String category, String location, BigDecimal maxCost) {
        if (category != null && location != null && maxCost != null) {
            return serviceOfferingRepository.findByCategoryAndLocationAndCostLessThanEqual(category, location, maxCost);
        }

        if (category != null) {
            return serviceOfferingRepository.findByCategory(category);
        }

        if (location != null) {
            return serviceOfferingRepository.findByLocation(location);
        }

        if (maxCost != null) {
            return serviceOfferingRepository.findByCostLessThanEqual(maxCost);
        }

        return serviceOfferingRepository.findAll();
    }
}