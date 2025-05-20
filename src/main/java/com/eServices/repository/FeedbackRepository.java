package com.eServices.repository;

import com.eServices.entity.Feedback;
import com.eServices.entity.ServiceOffering;
import com.eServices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUser(User user);
    List<Feedback> findByServiceOffering(ServiceOffering serviceOffering);
    List<Feedback> findByServiceOfferingOrderByFeedbackDateDesc(ServiceOffering serviceOffering);
}
