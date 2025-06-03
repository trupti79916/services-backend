package com.eServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.eServices.entity.Order;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderNotificationToVendor(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            // Set vendor email (assuming you have vendor email in ServiceOffering)
            message.setTo(order.getServiceOffering().getContact());

            message.setSubject("New Order Received - " + order.getServiceOffering().getServiceName());

            String emailBody = String.format(
                    "Dear %s,\n\n" +
                            "You have received a new order!\n\n" +
                            "Order Details:\n" +
                            "Order ID: %s\n" +
                            "Service: %s\n" +
                            "Customer: %s\n" +
                            "Scheduled Date: %s\n" +
                            "Order Date: %s\n" +
                            "Cost: $%.2f\n\n" +
                            "Please login to your dashboard to view more details and manage this order.\n\n" +
                            "Best regards,\n" +
                            "eServices Team",
                    order.getServiceOffering().getProviderName(),
                    order.getOrderId(),
                    order.getServiceOffering().getServiceName(),
                    order.getUser().getUsername(),
                    order.getScheduledDate(),
                    order.getOrderDate(),
                    order.getServiceOffering().getCost()
            );

            message.setText(emailBody);
            message.setFrom("noreply@eservices.com"); // Configure this in application.properties

            mailSender.send(message);

        } catch (Exception e) {
            // Log the error but don't fail the order creation
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
    }
}