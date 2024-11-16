package com.example.koifarm.api;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.service.ConsignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/consignments")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")// URL gốc của API
public class ConsignmentAPI {

    @Autowired
    private ConsignmentService consignmentService;

    // Tạo mới một đơn ký gửi
    @PostMapping
    public ResponseEntity<Consignment> createConsignment(@RequestBody ConsignmentRequest consignmentRequest) {
        Consignment newConsignment = consignmentService.createConsignment(consignmentRequest);
        return new ResponseEntity<>(newConsignment, HttpStatus.CREATED);
    }

    // Lấy thông tin đơn ký gửi theo ID
    @GetMapping("/{consignmentId}")
    public ResponseEntity<Consignment> getConsignmentById(@PathVariable UUID consignmentId) {
        Consignment consignment = consignmentService.getConsignmentById(consignmentId);
        return new ResponseEntity<>(consignment, HttpStatus.OK);
    }

    // Cập nhật đơn ký gửi
    @PutMapping("/{consignmentId}")
    public ResponseEntity<Consignment> updateConsignment(@PathVariable UUID consignmentId,
                                                         @RequestBody ConsignmentRequest consignmentRequest) {
        Consignment updatedConsignment = consignmentService.updateConsignment(consignmentId, consignmentRequest);
        return new ResponseEntity<>(updatedConsignment, HttpStatus.OK);
    }

    // Xóa đơn ký gửi
    @DeleteMapping("/{consignmentId}")
    public ResponseEntity<Void> deleteConsignment(@PathVariable UUID consignmentId) {
        consignmentService.deleteConsignment(consignmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Duyệt đơn ký gửi và tạo Koi mới
    @PostMapping("/{consignmentId}/approve")
    public ResponseEntity<Consignment> approveConsignment(@PathVariable UUID consignmentId) {
        Consignment approvedConsignment = consignmentService.approveConsignment(consignmentId);
        return new ResponseEntity<>(approvedConsignment, HttpStatus.OK);
    }
}

