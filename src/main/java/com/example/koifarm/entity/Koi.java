package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Koi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Consider AUTO if UUID strategy causes issues
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID koiID;

    @JsonIgnore
    boolean isDeleted = false;

    @NotBlank(message = "Name is required")
    @Column(unique = true)
    String name;

    @NotBlank(message = "Origin is required")
    String origin;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female", message = "Gender must be either 'Male' or 'Female'")
    String gender;

    @NotNull(message = "Size is required")
    @DecimalMin(value = "0.1", inclusive = true, message = "Size must be greater than 0")
    float size;

    @Positive(message = "Price must be greater than zero")
    float price;

    @NotBlank(message = "Breed is required")
    String breed;

    String location;
    String owner;
    String description;
    String image;

    String status = "available"; // Default status

    boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    @ManyToOne
    @JoinColumn(name = "species_id")
    @JsonIgnore
    KoiSpecies species;

    @OneToMany(mappedBy = "koi", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;

}
