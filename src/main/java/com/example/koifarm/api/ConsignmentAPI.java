package com.example.koifarm.api;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consignments")
public class ConsignmentAPI {

    @Autowired
    private ConsignmentService consignmentService;

    // Create a new consignment
    @PostMapping
    public ResponseEntity<Consignment> createConsignment(@RequestBody ConsignmentRequest consignmentRequest) {
        Consignment createdConsignment = consignmentService.createConsignment(consignmentRequest);
        return ResponseEntity.ok(createdConsignment);
    }

    // Get all consignments
    @GetMapping
    public ResponseEntity<List<Consignment>> getAllConsignments() {
        List<Consignment> consignments = consignmentService.getAllConsignments();
        return ResponseEntity.ok(consignments);
    }


}
