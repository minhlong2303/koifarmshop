package com.example.koifarm.model;

import java.time.LocalDate;
import java.util.List;

public class ConsignmentRequest {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate consignmentDate;
    private String inspectionMethod;
    private List<ConsignmentDetailsRequest> consignmentDetails;

    // Getters and Setters

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

    public List<ConsignmentDetailsRequest> getConsignmentDetails() {
        return consignmentDetails;
    }

    public void setConsignmentDetails(List<ConsignmentDetailsRequest> consignmentDetails) {
        this.consignmentDetails = consignmentDetails;
    }
}
