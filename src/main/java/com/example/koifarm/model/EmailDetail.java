package com.example.koifarm.model;

import com.example.koifarm.entity.User;
import lombok.Data;

@Data
public class EmailDetail {
    User user;
    String subject;
    String link;
}
