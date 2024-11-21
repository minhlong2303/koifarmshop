package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Koi {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID koiID;

    @JsonIgnore
    boolean isDeleted = false;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female", message = "Gender must be either 'Male' or 'Female'")
    private String gender;

    @NotNull(message = "Size is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Size must be greater than 0")
    private float size;

    private float  price;

    @NotBlank(message = "Breed is required")
    private String breed;

    private String location;
    private String owner;
    private String description;
    private String image;

    String status = "available"; // Default status

    boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "consignment_id")
    @JsonIgnore
    Consignment consignment;  // Liên kết với Consignment (Ký gửi)

    @ManyToOne
        @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    // Relationship with KoiSpecies
    @ManyToOne
    @JoinColumn(name = "species_id")
            @JsonIgnore
    KoiSpecies species;

    @OneToMany(mappedBy = "koi", cascade = CascadeType.ALL)
    List<OrderDetails> orderDetails;

}
