package com.example.koifarm.entity;

import com.example.koifarm.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Data
public class Consignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Sử dụng UUID nếu cần
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID consignmentID;

    @NotBlank(message = "Customer Name is required")
    String customerName;

    @NotBlank(message = "Koi Description is required")
    String koiDescription;  // Mô tả về cá koi (bao gồm giống cá, kích thước, giới tính, v.v.)

    @Positive(message = "Requested Price must be greater than zero")
    BigDecimal requestedPrice;  // Giá trị mong muốn bán của khách hàng

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusEnum status = OrderStatusEnum.PENDING; // Trạng thái ban đầu là PENDING

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;  // Liên kết với người dùng (khách hàng)

    @OneToMany(mappedBy = "consignment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Koi> kois;  // Liên kết với danh sách cá koi đã ký gửi

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonIgnore
    private User manager;  // Người quản lý (manager) sẽ xử lý việc duyệt ký gửi


}

