package com.example.koifarm.model;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackRequest {
    private String content;
    private int rating;
    private UUID orderID ;
}
