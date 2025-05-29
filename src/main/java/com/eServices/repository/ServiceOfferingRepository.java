package com.eServices.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eServices.entity.ServiceOffering;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    List<ServiceOffering> findByCategory(String category);
    List<ServiceOffering> findByLocation(String location);
    List<ServiceOffering> findByCostLessThanEqual(BigDecimal maxCost);
    List<ServiceOffering> findByCategoryAndLocationAndCostLessThanEqual(
            String category, String location, BigDecimal maxCost);
    
    List<ServiceOffering> findByServiceNameContainingIgnoreCase(String serviceName);
    List<ServiceOffering> findByCategoryAndLocation(String category, String location);
}
