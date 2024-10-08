package com.example.koifarm.model;

import com.example.koifarm.entity.Koi;
import lombok.Data;

import java.util.List;

@Data
public class KoiResponse {
    private List<Koi> content;
    private int pageNumber;
    private int totalPages;
    private long totalElements;

}
