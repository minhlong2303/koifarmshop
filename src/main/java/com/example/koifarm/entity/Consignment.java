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
    private String koiName;

    @Column(length = 50)
    private String breed;

    @Column(length = 20)
    private String size;

    private int age;
    private String gender;
    private double expectedPrice;
    private int quantity;
    private int careDuration;
    private double careFee;
    private String specialRequirements;

    @Enumerated(EnumType.STRING)
    private ConsignmentType consignmentType;

    private LocalDateTime createdDate = LocalDateTime.now(); // Khởi tạo mặc định

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private User customer;

    // Other fields (e.g., address, inspectionMethod, etc.)
    private String address;

    private LocalDateTime inspectionDate;

    private String inspectionMethod;
}