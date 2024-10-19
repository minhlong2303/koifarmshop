package com.example.koifarm.model;

import lombok.Data;

import java.util.List;

@Data
public class ConsignmentRequest {
    List<ConsignmentDetailRequest> detail;
}
