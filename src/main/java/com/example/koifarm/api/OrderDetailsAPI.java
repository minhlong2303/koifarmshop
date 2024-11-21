package com.example.koifarm.api;

import com.example.koifarm.entity.OrderDetails;
import com.example.koifarm.service.OrderDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orderDetails")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class OrderDetailsAPI {

    @Autowired
    OrderDetailsService orderDetailsService;

    // API để lấy danh sách OrderDetails theo orderId
    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrderId(@PathVariable UUID orderId) {
        List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByOrderId(orderId);
        if (orderDetailsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Không tìm thấy chi tiết đơn hàng
        }
        return new ResponseEntity<>(orderDetailsList, HttpStatus.OK); // Trả về chi tiết đơn hàng
    }

}
