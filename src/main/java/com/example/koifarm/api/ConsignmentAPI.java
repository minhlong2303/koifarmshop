package com.example.koifarm.api;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.service.ConsignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consignments")
@CrossOrigin("*") // cho phép truy cập từ FE
@SecurityRequirement(name = "api")
public class ConsignmentAPI {

    @Autowired
    private ConsignmentService consignmentService;

    // Tạo mới Consignment
    @PostMapping
    public ResponseEntity<Consignment> createConsignment(@Valid @RequestBody Consignment consignment) {
        Consignment createdConsignment = consignmentService.createConsignment(consignment);
        return ResponseEntity.ok(createdConsignment);
    }

    // Cập nhật trạng thái Consignment
    @PutMapping("/{consignmentId}/status")
    public ResponseEntity<Consignment> updateConsignmentStatus(
            @PathVariable UUID consignmentId,
            @RequestParam OrderStatusEnum status) {
        Consignment updatedConsignment = consignmentService.updateConsignmentStatus(consignmentId, status);
        return ResponseEntity.ok(updatedConsignment);
    }

    // Lấy danh sách tất cả Consignments
    @GetMapping
    public ResponseEntity<List<Consignment>> getAllConsignments() {
        List<Consignment> consignments = consignmentService.getAllConsignments();
        return ResponseEntity.ok(consignments);
    }

    // Lấy chi tiết một Consignment
    @GetMapping("/{consignmentId}")
    public ResponseEntity<Consignment> getConsignmentById(@PathVariable UUID consignmentId) {
        Consignment consignment = consignmentService.getConsignmentById(consignmentId);
        return ResponseEntity.ok(consignment);
    }
}


