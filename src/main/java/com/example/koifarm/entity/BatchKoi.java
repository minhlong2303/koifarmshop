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
    private int quantity;

    float  price;

    @NotBlank(message = "Breed is required")
    private String breed;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relationship with KoiSpecies
    @ManyToOne
    @JoinColumn(name = "species_id")
    private KoiSpecies species;

    // Relationship with BatchKoiOrderDetail
    @OneToMany(mappedBy = "batchKoi")
    private List<BatchKoiOrderDetail> orderDetails;
}
