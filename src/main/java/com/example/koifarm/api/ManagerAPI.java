package com.example.koifarm.api;

import com.example.koifarm.service.ManagerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class ManagerAPI {
    @Autowired
    ManagerService managerService;

    @GetMapping("/stats")
    public ResponseEntity getDashboardStats(){
        Map<String, Object> stats = managerService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity getMonthlyRev(){
        Map<String, Object> stats = managerService.getMonthlyRevenue();
        return ResponseEntity.ok(stats);
    }
}
