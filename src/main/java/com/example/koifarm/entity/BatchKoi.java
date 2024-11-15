package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class BatchKoi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID batchKoiID;

    @NotBlank(message = "Name is required")
    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float price;

    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "species_id")
        @JsonIgnore
    private KoiSpecies species;

    @JsonIgnore
    boolean isDeleted = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    boolean isAvailable = true;

    String status = "available"; // Default status

}
