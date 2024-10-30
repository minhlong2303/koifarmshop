package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class BatchKoi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID batchKoiID;

    @NotBlank(message = "Name is required")
    @Column(unique = true)
    String name;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    float price;

    String description;
    String image;

    @ManyToOne
    @JoinColumn(name = "species_id")
    @JsonIgnore
    KoiSpecies species;

    @JsonIgnore
    boolean isDeleted = false;

}
