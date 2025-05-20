package com.eServices.service;

import com.eServices.entity.Feedback;
import com.eServices.entity.ServiceOffering;
import com.eServices.entity.User;
import com.eServices.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> getFeedbacksByUser(User user) {
        return feedbackRepository.findByUser(user);
    }

    public List<Feedback> getFeedbacksByServiceOffering(ServiceOffering serviceOffering) {
        return feedbackRepository.findByServiceOffering(serviceOffering);
    }

    public List<Feedback> getFeedbacksByServiceOfferingOrderByDate(ServiceOffering serviceOffering) {
        return feedbackRepository.findByServiceOfferingOrderByFeedbackDateDesc(serviceOffering);
    }

    public Feedback submitFeedback(User user, ServiceOffering serviceOffering, Integer rating, String comment) {
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setServiceOffering(serviceOffering);
        feedback.setRating(rating);
        feedback.setComment(comment);

        return saveFeedback(feedback);
    }
}