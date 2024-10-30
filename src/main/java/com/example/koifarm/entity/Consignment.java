package com.example.koifarm.entity;

import com.example.koifarm.enums.ConsignmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Consignment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

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
    int careDuration;
    double careFee;
    String specialRequirements;

    @Enumerated(EnumType.STRING)
    ConsignmentType consignmentType;

    LocalDateTime createdDate = LocalDateTime.now(); // Khởi tạo mặc định

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    User customer;

    // Other fields (e.g., address, inspectionMethod, etc.)
    String address;

    LocalDateTime inspectionDate;

    String inspectionMethod;
}