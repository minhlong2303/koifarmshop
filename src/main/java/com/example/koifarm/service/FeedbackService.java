package com.example.koifarm.service;

import com.example.koifarm.entity.Feedback;
import com.example.koifarm.entity.Orders;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.FeedBackResponse;
import com.example.koifarm.model.FeedbackRequest;
import com.example.koifarm.repository.FeedbackRepository;
import com.example.koifarm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderRepository orderRepository;

    public Feedback createFeedback(FeedbackRequest feedbackRequest) {
        Orders orders = orderRepository.findById(feedbackRequest.getOrderID())
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));
        Feedback feedback = new Feedback();
        feedback.setContent(feedbackRequest.getContent());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setCustomer(authenticationService.getCurrentUser());  //lay thong tin khach hang
        return feedbackRepository.save(feedback);
    }

    public List<FeedBackResponse> getFeedback() {
        // Get feedback based on the current logged-in user
        return feedbackRepository.findFeedbackByUserId(authenticationService.getCurrentUser().getId());
    }


}
