package com.example.koifarm.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class KoiRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female", message = "Gender must be either 'Male' or 'Female'")
    private String gender;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be a positive number")
    private Integer age;

    @NotNull(message = "Size is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Size must be greater than 0")
    private BigDecimal size;

    @NotBlank(message = "Breed is required")
    private String breed;

    private String location;
    private String owner;
    private String description;
}
