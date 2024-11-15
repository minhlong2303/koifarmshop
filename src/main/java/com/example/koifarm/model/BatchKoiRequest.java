package com.example.koifarm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class BatchKoiRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID batchKoiID;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float price;

    private String description;
    private String image;
    long speciesId;
}