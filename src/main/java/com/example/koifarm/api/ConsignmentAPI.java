package com.example.koifarm.api;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.service.ConsignmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consignment")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class ConsignmentAPI {

    @Autowired
    ConsignmentService consignmentService;

    @PostMapping
    public ResponseEntity create(@RequestBody ConsignmentRequest consignmentRequest) {
        Consignment consignment = consignmentService.create(consignmentRequest);
        return ResponseEntity.ok(consignment);
    }
}
