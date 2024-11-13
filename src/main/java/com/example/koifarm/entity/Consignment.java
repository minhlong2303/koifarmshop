package com.example.koifarm.entity;

import com.example.koifarm.enums.ConsignmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Consignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @NotBlank(message = "Koi name is required")
    @Column(length = 100, nullable = false)
    String koiName;

    @Column(length = 50)
    String breed;

    @Column(length = 20)
    String size;

    int age;
    String gender;
    double expectedPrice;
    int quantity;

    @Min(value = 1, message = "Care duration must be at least 1")
    int careDuration;

    @Positive(message = "Care fee must be greater than zero")
    double careFee;

    String specialRequirements;

    @Enumerated(EnumType.STRING)
    ConsignmentType consignmentType;

    LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    User customer;

    String address;

    LocalDateTime inspectionDate;

    String inspectionMethod;
}
