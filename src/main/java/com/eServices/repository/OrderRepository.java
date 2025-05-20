package com.eServices.repository;

import com.eServices.entity.Order;
import com.eServices.entity.ServiceOffering;
import com.eServices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByServiceOffering(ServiceOffering serviceOffering);
    List<Order> findByStatus(Order.OrderStatus status);
}