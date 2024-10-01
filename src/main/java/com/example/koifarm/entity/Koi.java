package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Koi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long koiID;

    @JsonIgnore
    private boolean isDeleted = false;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Origin is required")
    @Size(max = 100, message = "Origin must not exceed 100 characters")
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
    @Size(max = 100, message = "Breed must not exceed 100 characters")
    private String breed;

    private String location;
    private String owner;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price; // Giá cá Koi

    @Override
    public String toString() {
        return "Koi{" +
                "koiID=" + koiID +
                ", name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", size=" + size +
                ", breed='" + breed + '\'' +
                ", location='" + location + '\'' +
                ", owner='" + owner + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    // Optional: You can add other utility methods here, e.g. for calculating discounts
}
