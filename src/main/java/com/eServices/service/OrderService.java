package com.eServices.service;
import com.eServices.entity.Order;
import com.eServices.entity.ServiceOffering;
import com.eServices.entity.User;
import com.eServices.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        boolean isNewOrder = (order.getOrderId() == null);
        Order savedOrder = orderRepository.save(order);

        // Send email notification to vendor only for new orders
        if (isNewOrder) {
            System.out.println("Sending message to vendor "+ order.getServiceOffering().getContact());
//            emailService.sendOrderNotificationToVendor(savedOrder);
            System.out.println("Message sent");
        }
        return savedOrder;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getOrdersByServiceOffering(ServiceOffering serviceOffering) {
        return orderRepository.findByServiceOffering(serviceOffering);
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Order placeOrder(User user, ServiceOffering serviceOffering) {
        Order order = new Order();
        order.setUser(user);
        order.setServiceOffering(serviceOffering);

        return saveOrder(order);
    }

    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Optional<Order> optionalOrder = getOrderById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return saveOrder(order);
        }
        throw new RuntimeException("Order not found with id: " + orderId);
    }
}
