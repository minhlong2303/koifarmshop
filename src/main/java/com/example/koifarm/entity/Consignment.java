package com.example.koifarm.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Consignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate consignmentDate;
    private String inspectionMethod;
    private String status;

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConsignmentDetails> consignmentDetails;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getConsignmentDate() {
        return consignmentDate;
    }

    public void setConsignmentDate(LocalDate consignmentDate) {
        this.consignmentDate = consignmentDate;
    }

    public String getInspectionMethod() {
        return inspectionMethod;
    }

    public void setInspectionMethod(String inspectionMethod) {
        this.inspectionMethod = inspectionMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ConsignmentDetails> getConsignmentDetails() {
        return consignmentDetails;
    }

    public void setConsignmentDetails(List<ConsignmentDetails> consignmentDetails) {
        this.consignmentDetails = consignmentDetails;
    }
}
