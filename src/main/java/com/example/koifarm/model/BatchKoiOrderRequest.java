package com.example.koifarm.model;

import lombok.Data;
import java.util.List;

@Data
public class BatchKoiOrderRequest extends BatchKoiOrderDetailRequest {
    List<BatchKoiOrderRequest> detail;
}
