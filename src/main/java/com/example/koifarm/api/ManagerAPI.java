package com.example.koifarm.api;

import com.example.koifarm.entity.User;
import com.example.koifarm.service.ManagerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class ManagerAPI {
    @Autowired
    ManagerService managerService;

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/stats")
    public ResponseEntity getDashboardStats(){
        Map<String, Object> stats = managerService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/revenue/monthly")
    public ResponseEntity getMonthlyRev(){
        Map<String, Object> stats = managerService.getMonthlyRevenue();
        return ResponseEntity.ok(stats);
    }

}
