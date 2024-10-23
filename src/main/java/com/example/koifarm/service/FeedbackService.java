package com.example.koifarm.service;

import com.example.koifarm.entity.Feedback;
import com.example.koifarm.entity.User;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.FeedBackResponse;
import com.example.koifarm.model.FeedbackRequest;
import com.example.koifarm.repository.FeedbackRepository;
import com.example.koifarm.repository.UserRepository;
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
    UserRepository userRepository;

    public Feedback createFeedback(FeedbackRequest feedbackRequest) {
        User shop = userRepository.findUserById(feedbackRequest.getShopID())
                .orElseThrow(() -> new EntityNotFoundException("Shop not found!"));
        Feedback feedback = new Feedback();
        feedback.setContent(feedbackRequest.getContent());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setCustomer(authenticationService.getCurrentUser());
        feedback.setShop(shop);

        return feedbackRepository.save(feedback);
    }

    public List<FeedBackResponse> getFeedback() {
        return feedbackRepository.findFeedbackByShopId(authenticationService.getCurrentUser().getId());
    }

}
