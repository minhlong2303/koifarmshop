package com.example.koifarm.api;

import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consignments")
public class ConsignmentAPI {

    @Autowired
    private ConsignmentService consignmentService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitConsignment(@RequestBody ConsignmentRequest request) {
        consignmentService.createConsignment(request);
        return ResponseEntity.ok("Yêu cầu ký gửi của bạn đã được tiếp nhận.");
    }
}
