package com.eServices.controller;

import com.eServices.entity.Order;
import com.eServices.entity.ServiceOffering;
import com.eServices.entity.User;
import com.eServices.service.OrderService;
import com.eServices.service.ServiceOfferingService;
import com.eServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ServiceOfferingService serviceOfferingService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, ServiceOfferingService serviceOfferingService) {
        this.orderService = orderService;
        this.userService = userService;
        this.serviceOfferingService = serviceOfferingService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(order));
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestParam Long userId, @RequestParam Long serviceId) {
        Optional<User> user = userService.getUserById(userId);
        Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(serviceId);

        if (user.isPresent() && serviceOffering.isPresent()) {
            Order order = orderService.placeOrder(user.get(), serviceOffering.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } else {
            return ResponseEntity.badRequest().body("User or service not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Optional<Order> existingOrder = orderService.getOrderById(id);
        if (existingOrder.isPresent()) {
            order.setOrderId(id);
            return ResponseEntity.ok(orderService.saveOrder(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> existingOrder = orderService.getOrderById(id);
        if (existingOrder.isPresent()) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(orderService.getOrdersByUser(user.get()));
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<?> getOrdersByService(@PathVariable Long serviceId) {
        Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(serviceId);
        if (serviceOffering.isPresent()) {
            return ResponseEntity.ok(orderService.getOrdersByServiceOffering(serviceOffering.get()));
        } else {
            return ResponseEntity.badRequest().body("Service not found");
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
}
