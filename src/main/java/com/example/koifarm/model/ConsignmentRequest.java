package com.example.koifarm.model;

import com.example.koifarm.entity.User;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ConsignmentRequest {
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
    private float expectedPrice;

    @NotBlank(message = "Breed is required")
    private String  breed;

    private String location;
    private String owner;
    private String description;
    private String image;

    private int quantity;

    private String specialRequirements;

    private LocalDateTime createdDate;

    @NotNull(message = "Customer information is required")
    private User customer;

    private String address;
    private LocalDateTime inspectionDate;
    private String inspectionMethod;
    @NotNull(message = "Koi species ID is required")
    private Long koiSpeciesId;
}
