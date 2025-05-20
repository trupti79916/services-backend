package com.eServices.repository;

import com.eServices.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    List<ServiceOffering> findByCategory(String category);
    List<ServiceOffering> findByLocation(String location);
    List<ServiceOffering> findByCostLessThanEqual(BigDecimal maxCost);
    List<ServiceOffering> findByCategoryAndLocationAndCostLessThanEqual(
            String category, String location, BigDecimal maxCost);
}
