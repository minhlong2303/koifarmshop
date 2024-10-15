package com.example.koifarm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class KoiRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long koiID;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female", message = "Gender must be either 'Male' or 'Female'")
    private String gender;

    @NotNull(message = "Size is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Size must be greater than 0")
    private BigDecimal size;

    @DecimalMin(value = "0.01", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "99999999.99", message = "Price must be less than or equal to 99999999.99")
    private float price;

    @NotBlank(message = "Breed is required")
    private String breed;

    private String location;
    private String owner;
    private String description;
    private String image;

    long speciesId;
}
