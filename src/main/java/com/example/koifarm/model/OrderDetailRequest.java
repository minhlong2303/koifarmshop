package com.example.koifarm.model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailRequest {
    UUID itemId;  // Có thể là ID của Koi hoặc BatchKoi
    boolean isBatch; // true nếu là lô cá, false nếu là cá thể
    int quantity; // Số lượng cho lô cá; với cá thể thì là 1
    String itemType;
}
