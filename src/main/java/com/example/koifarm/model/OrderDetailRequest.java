package com.example.koifarm.model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailRequest {
    UUID koiId;
    int quantity;
}
