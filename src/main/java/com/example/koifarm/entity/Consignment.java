package com.example.koifarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Consignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID consignmentID;

    @OneToOne
    @JoinColumn(name = "koi_id")
    @JsonIgnore
    Koi koi;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Thêm quan hệ này để User có thể quản lý nhiều Consignment
    @JsonIgnore
    User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsignmentStatus consignmentStatus = ConsignmentStatus.PROCESSING; // Trạng thái mặc định là PROCESSING

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Thời gian tạo

    private LocalDateTime approvedAt; // Thời gian phê duyệt (nếu có)

    private LocalDateTime rejectedAt; // Thời gian từ chối (nếu có)

    private String rejectionReason; // Lý do từ chối (nếu rejected)

    public enum ConsignmentStatus {
        PROCESSING, // Đơn đang được xử lý
        APPROVED,   // Đơn đã được phê duyệt
        REJECTED    // Đơn đã bị từ chối
    }
}
