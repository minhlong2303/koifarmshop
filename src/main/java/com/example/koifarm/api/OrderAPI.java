package com.example.koifarm.api;

import com.example.koifarm.entity.Orders;
import com.example.koifarm.model.OrderRequest;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/order")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class OrderAPI {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity create(@RequestBody OrderRequest orderRequest) throws Exception{
        String vnPayURL = orderService.createUrl(orderRequest);
        return ResponseEntity.ok(vnPayURL);

    }

    @PostMapping("pay")
    public ResponseEntity create(@RequestParam UUID orderID) throws Exception{
        orderService.createTransaction(orderID);
        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping
    public ResponseEntity get(){
        List<Orders> orders = orderService.get();
        return ResponseEntity.ok(orders);
    }


}