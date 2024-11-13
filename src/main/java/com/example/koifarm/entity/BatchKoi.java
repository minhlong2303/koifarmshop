package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class BatchKoi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID batchKoiID;

    @JsonIgnore
    private boolean isDeleted = false;

    @NotBlank(message = "Batch name is required")
    @Column(unique = true)
    private String batchName;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantityInBatch; // Số lượng cá trong lô

    boolean isAvailable = true;

    @Positive(message = "Price must be greater than zero")
    float batchKoiPrice;

    @NotBlank(message = "Breed is required")
    String breed;

    String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id")
    private KoiSpecies species;
}
