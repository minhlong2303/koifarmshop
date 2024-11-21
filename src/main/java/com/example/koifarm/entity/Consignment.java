package com.example.koifarm.entity;

import com.example.koifarm.enums.ConsignmentStatus;
import com.example.koifarm.enums.ConsignmentType;
import com.example.koifarm.enums.OrderStatusEnum;
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
    private UUID id;

    // Thông tin khách hàng

    private String firstName;


    private String lastName;


    private String phone;


    private String email;

    private String address;

    // Thông tin cá Koi
    private String koiName;

    private String breed;

    private String size;

    private int age;
    private String gender;

    // Hình thức và loại ký gửi
    @Enumerated(EnumType.STRING)
    private ConsignmentType consignmentType;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status = OrderStatusEnum.PENDING;

    @Enumerated(EnumType.STRING)
    private ConsignmentStatus consignmentStatus = ConsignmentStatus.PENDING;


    // Thông tin hình ảnh
    @Column
    private String koiImageUrl;


    private LocalDateTime createdDate = LocalDateTime.now(); // Khởi tạo mặc định

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private User customer;

    // Relationship with KoiSpecies
    @ManyToOne
    @JoinColumn(name = "species_id")
    @JsonIgnore
    KoiSpecies species;
}
