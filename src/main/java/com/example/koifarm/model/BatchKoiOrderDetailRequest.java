package com.example.koifarm.model;

import lombok.Data;

import java.util.UUID;

@Data
public class BatchKoiOrderDetailRequest {
    UUID batchKoiId;
    int quantity;
}
