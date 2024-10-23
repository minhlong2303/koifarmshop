package com.example.koifarm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedBackResponse {
    Long id;
    String content;
    int rating;
    String email;
}
