package com.example.koifarm.entity;

import com.example.koifarm.enums.ConsignmentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    private UUID id;

    // Thông tin khách hàng
    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 15, nullable = false)
    @Pattern(regexp = "^((\\+84)|0)[3|5|7|8|9][0-9]{8}$", message = "Invalid phone!")
    private String phone;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String address;

    // Thông tin cá Koi
    @Column(length = 100, nullable = false)
    private String koiName;

    @Column(length = 50)
    private String breed;

    @Column(length = 20)
    private String size;

    private int age;

    @Column(length = 10)
    private String gender;

    // Hình thức và loại ký gửi
    @Enumerated(EnumType.STRING)
    private ConsignmentType consignmentType;

    @Column(length = 20)
    private String inspectionMethod;

    // Thông tin hình ảnh
    @Column
    private String koiImageUrl;

    @Column
    private String certificateImageUrl;

    private LocalDateTime createdDate = LocalDateTime.now(); // Khởi tạo mặc định

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private User customer;
}