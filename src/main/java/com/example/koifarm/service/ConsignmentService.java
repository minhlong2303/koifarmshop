package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.repository.ConsignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConsignmentService {

    @Autowired
    private ConsignmentRepository consignmentRepository;

    @Autowired
    private KoiService koiService;  // Service để tạo mới cá koi khi trạng thái là COMPLETED

    // Tạo mới Consignment
    public Consignment createConsignment(Consignment consignment) {
        consignment.setStatus(OrderStatusEnum.PENDING);  // Đặt trạng thái ban đầu là PENDING
        return consignmentRepository.save(consignment);
    }

    // Cập nhật trạng thái của Consignment
    public Consignment updateConsignmentStatus(UUID consignmentId, OrderStatusEnum status) {
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new RuntimeException("Consignment not found"));

        consignment.setStatus(status);

        // Nếu trạng thái là COMPLETED, tự động tạo cá koi mới từ consignment
        if (status == OrderStatusEnum.COMPLETED) {
            createKoiFromConsignment(consignment);
        }

        return consignmentRepository.save(consignment);
    }

    // Lấy danh sách tất cả Consignments
    public List<Consignment> getAllConsignments() {
        return consignmentRepository.findAll();
    }

    // Lấy Consignment theo ID
    public Consignment getConsignmentById(UUID consignmentId) {
        return consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new RuntimeException("Consignment not found"));
    }

    // Tạo mới Koi từ Consignment khi trạng thái là COMPLETED
    private void createKoiFromConsignment(Consignment consignment) {
        for (Koi koi : consignment.getKois()) {
            koi.setConsignment(null);  // Tạo một cá koi mới mà không liên kết với consignment cũ
            koiService.createKoi(koi);  // Gọi service để tạo Koi mới trong hệ thống
        }
    }
}

