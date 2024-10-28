package com.example.koifarm.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsignmentRequest {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    private LocalDateTime date; // Có thể cần thêm validation nếu cần thiết

    @NotBlank(message = "Inspection method cannot be blank")
    private String inspectionMethod;

    @NotBlank(message = "Consignment type cannot be blank")
    private String consignmentType; // Xem xét chuyển sang kiểu enum nếu có thể

    @NotBlank(message = "Koi name cannot be blank")
    private String koiName;

    @NotBlank(message = "Breed cannot be blank")
    private String breed;

    @NotBlank(message = "Size cannot be blank")
    private String size;

    @Positive(message = "Age must be a positive number")
    private int age;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @Positive(message = "Expected price must be a positive number")
    private double expectedPrice;

    @Positive(message = "Quantity must be a positive number")
    private int quantity;

    @Positive(message = "Care duration must be a positive number")
    private int careDuration;

    @Positive(message = "Care fee must be a positive number")
    private double careFee;

    private String specialRequirements;
}