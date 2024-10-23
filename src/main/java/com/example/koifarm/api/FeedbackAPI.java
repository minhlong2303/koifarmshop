package com.example.koifarm.api;

import com.example.koifarm.entity.Feedback;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.model.FeedBackResponse;
import com.example.koifarm.model.FeedbackRequest;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.service.FeedbackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@SecurityRequirement(name = "api")
public class FeedbackAPI {
    @Autowired
    FeedbackService feedbackService;
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody FeedbackRequest feedbackRequest){
        Feedback feedback = feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity getFeedback(){
        List<FeedBackResponse> feedback = feedbackService.getFeedback();
        return ResponseEntity.ok(feedback);
    }
}
