package com.example.koifarm.model;

import lombok.Data;

@Data
public class OrderDetailRequest {
    long koiId;
    int quantity;
}
