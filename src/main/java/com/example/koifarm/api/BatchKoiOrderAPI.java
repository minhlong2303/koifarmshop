package com.example.koifarm.api;

import com.example.koifarm.entity.Orders;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.model.BatchKoiOrderRequest;
import com.example.koifarm.entity.BatchKoiOrder;
import com.example.koifarm.model.OrderRequest;
import com.example.koifarm.service.BatchKoiOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/batchKoiOrders")
@CrossOrigin("*") //de cho fe co the truy cap
@SecurityRequirement(name = "api")
public class BatchKoiOrderAPI {

    @Autowired
    BatchKoiOrderService batchKoiOrderService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody BatchKoiOrderRequest batchKoiOrderRequest) throws Exception {
        String vnPayURL = batchKoiOrderService.createUrl(batchKoiOrderRequest);
        return ResponseEntity.ok(vnPayURL);  // Hoặc ResponseEntity.status(HttpStatus.CREATED).body(vnPayURL);
    }

    @PostMapping("pay")
    public ResponseEntity<String> pay(@RequestParam UUID orderID) throws Exception {
        batchKoiOrderService.createTransaction(orderID);
        return ResponseEntity.ok("Transaction completed successfully for order ID: " + orderID);
    }

    @GetMapping
    public ResponseEntity<List<BatchKoiOrder>> get() {
        List<BatchKoiOrder> orders = batchKoiOrderService.get();
        return ResponseEntity.ok(orders);
    }


    //update orders status
    @PreAuthorize("hasAnyAuthority('MANAGER', 'STAFF')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<BatchKoiOrder> updateOrderStatus(@PathVariable UUID orderId, @RequestParam OrderStatusEnum status) {
        BatchKoiOrder updatedOrder = batchKoiOrderService.updateOrderStatus(orderId, status);
        if (updatedOrder == null) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy đơn hàng
        }
        return ResponseEntity.ok(updatedOrder);
    }
}
