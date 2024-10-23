package com.example.koifarm.model;

import lombok.Data;

@Data
public class FeedbackRequest {
    private String content;
    private int rating;
    //private long shopID ;
}
