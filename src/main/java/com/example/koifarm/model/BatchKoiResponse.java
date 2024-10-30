package com.example.koifarm.model;

import com.example.koifarm.entity.BatchKoi;
import lombok.Data;

import java.util.List;

@Data
public class BatchKoiResponse {
    private List<BatchKoi> content;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
}