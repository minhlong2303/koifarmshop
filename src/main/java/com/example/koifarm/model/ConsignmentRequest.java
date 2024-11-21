package com.example.koifarm.model;

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
    private String size;
    private int age;
    private String gender;

    // Hình thức và loại ký gửi
    private String consignmentType;  // "SALE"
    private String inspectionMethod;    //online

    // Thông tin hình ảnh
    private String koiImageUrl;  // URL của ảnh cá Koi
    private String certificateImageUrl;  // URL của ảnh giấy chứng nhận
}
