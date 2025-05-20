package com.eServices.service;
import com.eServices.entity.ServiceOffering;
import com.eServices.repository.ServiceOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Autowired
    public ServiceOfferingService(ServiceOfferingRepository serviceOfferingRepository) {
        this.serviceOfferingRepository = serviceOfferingRepository;
    }

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

    public List<ServiceOffering> findByCategory(String category) {
        return serviceOfferingRepository.findByCategory(category);
    }

    public List<ServiceOffering> findByLocation(String location) {
        return serviceOfferingRepository.findByLocation(location);
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