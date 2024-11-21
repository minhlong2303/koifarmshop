package com.example.koifarm.api;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.enums.ConsignmentType;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.service.ConsignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    ConsignmentService consignmentService;

    @PostMapping
    public ResponseEntity<Consignment> createConsignment(@RequestBody @Valid ConsignmentRequest consignmentRequest) throws Exception {
        Consignment consignment = consignmentService.createConsignment(consignmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(consignment);
    }

    @GetMapping
    public ResponseEntity<List<Consignment>> getAllConsignments() {
        return ResponseEntity.ok(consignmentService.getAllConsignments());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getConsignmentsByType(@PathVariable String type) {
        try {
            ConsignmentType consignmentType = ConsignmentType.valueOf(type.toUpperCase());
            List<Consignment> consignments = consignmentService.getConsignmentsByType(consignmentType);

            if (consignments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No consignments found for type: " + consignmentType);
            }

            return ResponseEntity.ok(consignments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid consignment type: " + type);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Consignment>> getConsignmentsByUserId(@PathVariable Long userId) {
        List<Consignment> consignments = consignmentService.getConsignmentsByUserId(userId);
        return ResponseEntity.ok(consignments);
    }

    @PostMapping("/{consignmentId}/create-koi")
    public ResponseEntity<Koi> createKoiFromConsignment(@PathVariable UUID consignmentId) {
        Koi newKoi = consignmentService.createKoiFromConsignment(consignmentId);
        return ResponseEntity.ok(newKoi);
    }

    @DeleteMapping("/{consignmentId}")
    public ResponseEntity<Void> deleteConsignment(@PathVariable UUID consignmentId) {
        consignmentService.deleteConsignmentById(consignmentId);
        return ResponseEntity.noContent().build();
    }

}
