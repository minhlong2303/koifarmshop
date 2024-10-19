package com.example.koifarm.model;

import lombok.Data;

@Data
public class ConsignmentDetailRequest {
    long koiId;  // ID của Koi
    int quantity; // Số lượng Koi
}
