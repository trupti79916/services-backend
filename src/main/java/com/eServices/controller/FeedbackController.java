package com.eServices.controller;

import com.eServices.entity.Feedback;
import com.eServices.entity.ServiceOffering;
import com.eServices.entity.User;
import com.eServices.service.FeedbackService;
import com.eServices.service.ServiceOfferingService;
import com.eServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;
    private final ServiceOfferingService serviceOfferingService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService, UserService userService, ServiceOfferingService serviceOfferingService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.serviceOfferingService = serviceOfferingService;
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        return feedback.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.saveFeedback(feedback));
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitFeedback(
            @RequestParam Long userId,
            @RequestParam Long serviceId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment) {

        Optional<User> user = userService.getUserById(userId);
        Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(serviceId);

        if (user.isPresent() && serviceOffering.isPresent()) {
            Feedback feedback = feedbackService.submitFeedback(user.get(), serviceOffering.get(), rating, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
        } else {
            return ResponseEntity.badRequest().body("User or service not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        Optional<Feedback> existingFeedback = feedbackService.getFeedbackById(id);
        if (existingFeedback.isPresent()) {
            feedback.setFeedbackId(id);
            return ResponseEntity.ok(feedbackService.saveFeedback(feedback));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        Optional<Feedback> existingFeedback = feedbackService.getFeedbackById(id);
        if (existingFeedback.isPresent()) {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getFeedbacksByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(feedbackService.getFeedbacksByUser(user.get()));
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<?> getFeedbacksByService(@PathVariable Long serviceId) {
        Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(serviceId);
        if (serviceOffering.isPresent()) {
            return ResponseEntity.ok(feedbackService.getFeedbacksByServiceOffering(serviceOffering.get()));
        } else {
            return ResponseEntity.badRequest().body("Service not found");
        }
    }

    @GetMapping("/service/{serviceId}/recent")
    public ResponseEntity<?> getRecentFeedbacksByService(@PathVariable Long serviceId) {
        Optional<ServiceOffering> serviceOffering = serviceOfferingService.getServiceOfferingById(serviceId);
        if (serviceOffering.isPresent()) {
            return ResponseEntity.ok(feedbackService.getFeedbacksByServiceOfferingOrderByDate(serviceOffering.get()));
        } else {
            return ResponseEntity.badRequest().body("Service not found");
        }
    }
}
