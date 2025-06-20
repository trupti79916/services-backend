package com.eServices.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eServices.dto.request.OrderRequest;
import com.eServices.dto.request.RatingRequest;
import com.eServices.dto.response.OrderResponse;
import com.eServices.dto.response.ServiceResponse;
import com.eServices.entity.Order;
import com.eServices.entity.ServiceOffering;
import com.eServices.entity.User;
import com.eServices.service.OrderService;
import com.eServices.service.ServiceOfferingService;
import com.eServices.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ServiceOfferingService serviceOfferingService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> orderResponses = orders.stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(o -> ResponseEntity.ok(convertToOrderResponse(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            
            User user = (User) authentication.getPrincipal();
            Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(orderRequest.getServiceId());

            if (serviceOffering.isPresent()) {
                Order order = new Order();
                order.setUser(user);
                order.setServiceOffering(serviceOffering.get());
                order.setScheduledDate(orderRequest.getScheduledDate());
                
                Order savedOrder = orderService.saveOrder(order);
                OrderResponse response = convertToOrderResponse(savedOrder);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.badRequest().body("Service not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + e.getMessage());
        }
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest orderRequest) {
        Optional<Order> existingOrder = orderService.getOrderById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setScheduledDate(orderRequest.getScheduledDate());
            
            Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(orderRequest.getServiceId());
            if (serviceOffering.isPresent()) {
                order.setServiceOffering(serviceOffering.get());
            }
            
            Order updatedOrder = orderService.saveOrder(order);
            OrderResponse response = convertToOrderResponse(updatedOrder);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase().replace("-", "_"));
            Order updatedOrder = orderService.updateOrderStatus(id, orderStatus);
            OrderResponse response = convertToOrderResponse(updatedOrder);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status: " + status);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> existingOrder = orderService.getOrderById(id);
        if (existingOrder.isPresent()) {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            List<Order> orders = orderService.getOrdersByUser(user.get());
            List<OrderResponse> orderResponses = orders.stream()
                    .map(this::convertToOrderResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orderResponses);
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/orders/service/{serviceId}")
    public ResponseEntity<?> getOrdersByService(@PathVariable Long serviceId) {
        Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(serviceId);
        if (serviceOffering.isPresent()) {
            List<Order> orders = orderService.getOrdersByServiceOffering(serviceOffering.get());
            List<OrderResponse> orderResponses = orders.stream()
                    .map(this::convertToOrderResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orderResponses);
        } else {
            return ResponseEntity.badRequest().body("Service not found");
        }
    }

    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable String status) {
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase().replace("-", "_"));
            List<Order> orders = orderService.getOrdersByStatus(orderStatus);
            List<OrderResponse> orderResponses = orders.stream()
                    .map(this::convertToOrderResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(orderResponses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/orders/{id}/rating")
    public ResponseEntity<?> rateOrder(@PathVariable Long id, @Valid @RequestBody RatingRequest ratingRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            
            User user = (User) authentication.getPrincipal();
            Optional<Order> orderOpt = orderService.getOrderById(id);
            
            if (!orderOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            Order order = orderOpt.get();
            
            if (!order.getUser().getUserId().equals(user.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only rate your own orders");
            }
            
            if (order.getIsReviewed()) {
                return ResponseEntity.badRequest().body("Order has already been reviewed");
            }
            
            if (order.getStatus() != Order.OrderStatus.COMPLETED) {
                return ResponseEntity.badRequest().body("You can only rate completed orders");
            }
            
            order.setRating(ratingRequest.getRating());
            order.setIsReviewed(true);
            
            ServiceOffering service = order.getServiceOffering();
            updateServiceRating(service, ratingRequest.getRating());
            
            Order updatedOrder = orderService.saveOrder(order);
            OrderResponse response = convertToOrderResponse(updatedOrder);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rating order: " + e.getMessage());
        }
    }
    
    private void updateServiceRating(ServiceOffering service, Integer newRating) {
        java.math.BigDecimal currentRating = service.getRating();
        Integer currentReviewCount = service.getReviewCount();
        
        java.math.BigDecimal totalRating = currentRating.multiply(java.math.BigDecimal.valueOf(currentReviewCount))
                .add(java.math.BigDecimal.valueOf(newRating));
        Integer newReviewCount = currentReviewCount + 1;
        java.math.BigDecimal newAverageRating = totalRating.divide(java.math.BigDecimal.valueOf(newReviewCount), 2, java.math.RoundingMode.HALF_UP);
        
        service.setRating(newAverageRating);
        service.setReviewCount(newReviewCount);
        serviceOfferingService.saveServiceOffering(service);
    }

    private OrderResponse convertToOrderResponse(Order order) {
        ServiceResponse.ProviderResponse provider = new ServiceResponse.ProviderResponse(
            order.getServiceOffering().getServiceId().toString(),
            order.getServiceOffering().getProviderName(),
            order.getServiceOffering().getProviderAvatar()
        );

        ServiceResponse serviceResponse = new ServiceResponse(
            order.getServiceOffering().getServiceId(),
            order.getServiceOffering().getServiceName(),
            order.getServiceOffering().getDescription(),
            order.getServiceOffering().getCost(),
            order.getServiceOffering().getRating(),
            order.getServiceOffering().getReviewCount(),
            order.getServiceOffering().getLocation(),
            order.getServiceOffering().getCategory(),
            order.getServiceOffering().getImage(),
            order.getServiceOffering().getPhone(),
            order.getServiceOffering().getContact(),
            order.getServiceOffering().getFeatures(),
            provider
        );

        return new OrderResponse(
            order.getOrderId(),
            order.getUser().getUserId(),
            order.getServiceOffering().getServiceId(),
            order.getRating(), 
            order.getStatus(),
            order.getOrderDate(),
            order.getScheduledDate(),
            serviceResponse,
            order.getIsReviewed()
        );
    }
}
