package com.example.koifarm.api;

import com.example.koifarm.entity.Orders;
import com.example.koifarm.model.OrderRequest;
import com.example.koifarm.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class OrderAPI {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity create(@RequestBody OrderRequest orderRequest){
        Orders order = orderService.create(orderRequest);
        return ResponseEntity.ok(order);
    }


}
