package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Koi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long koiID;

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

//    @DecimalMin(value = "0.01", inclusive = false, message = "Price must be greater than 0")
//    @DecimalMax(value = "99999999.99", message = "Price must be less than or equal to 99999999.99")
    private float  price;

    @NotBlank(message = "Breed is required")
    private String breed;

    private String location;
    private String owner;
    private String description;
    private String image;

    @ManyToOne
        @JoinColumn(name = "user_id")
    User user;

    // Relationship with KoiSpecies
    @ManyToOne
    @JoinColumn(name = "species_id")
            @JsonIgnore
    KoiSpecies species;

    @OneToMany(mappedBy = "koi")
    List<OrderDetails> orderDetails;
}
