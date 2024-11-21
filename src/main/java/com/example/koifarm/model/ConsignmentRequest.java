package com.example.koifarm.model;

import com.example.koifarm.enums.ConsignmentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsignmentRequest {

    // Thông tin khách hàng
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;

    // Thông tin cá Koi
    private String koiName;
    private String breed;
    private float size;
    private int age;
    private String gender;

    // Hình thức và loại ký gửi
    @Enumerated(EnumType.STRING)
    private ConsignmentType consignmentType;  // "SALE"
    private String inspectionMethod;    //online

    // Thông tin hình ảnh
    private String koiImageUrl;  // URL của ảnh cá Koi
    private String certificateImageUrl;  // URL của ảnh giấy chứng nhận
    private float expectedPrice;
}
