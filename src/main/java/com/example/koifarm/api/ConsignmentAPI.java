package com.example.koifarm.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consignments")
@CrossOrigin("*") // cho phép truy cập từ FE
@SecurityRequirement(name = "api")
public class ConsignmentAPI {
    @Autowired
    private ConsignmentService consignmentService;

    @PostMapping
    public ResponseEntity<Consignment> createConsignment(@RequestBody ConsignmentRequest consignmentRequest) throws Exception {
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



}
