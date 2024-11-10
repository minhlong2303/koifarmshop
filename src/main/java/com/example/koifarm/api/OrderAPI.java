package com.example.koifarm.api;

import com.example.koifarm.entity.Orders;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.OrderRequest;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity create(@RequestBody OrderRequest orderRequest) throws Exception{
        String vnPayURL = orderService.createUrl(orderRequest);
        return ResponseEntity.ok(vnPayURL);

    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable UUID orderId) {
        try {
            Orders cancelledOrder = orderService.cancelOrder(orderId);
            return ResponseEntity.ok(cancelledOrder); // Trả về đơn hàng đã hủy
        } catch (EntityNotFoundException e) {
            // Nếu không tìm thấy đơn hàng
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn hàng với ID: " + orderId);
        } catch (IllegalStateException e) {
            // Nếu không thể hủy đơn hàng (trạng thái đã hoàn thành hoặc đã hủy)
            return ResponseEntity.badRequest().body("Không thể hủy đơn hàng: " + e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
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

    @GetMapping("{orderId}")
    public ResponseEntity getOrder(@PathVariable UUID orderId){
        Orders orders = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity delete(@PathVariable UUID orderId){
        Orders deletedOrders = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(deletedOrders);
    }


    //update orders status
    @PreAuthorize("hasAnyAuthority('MANAGER', 'STAFF')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity updateOrderStatus(@PathVariable UUID orderId, @RequestParam OrderStatusEnum status) {
        try {
            // Cập nhật trạng thái đơn hàng
            Orders updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(updatedOrder); // Trả về đối tượng đơn hàng đã cập nhật và mã 200 OK
        } catch (IllegalStateException e) {
            // Nếu chuyển trạng thái không hợp lệ (ví dụ: từ COMPLETED quay lại PENDING), trả về 400 Bad Request
            return ResponseEntity.badRequest().body("Chuyển trạng thái không hợp lệ: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            // Nếu không tìm thấy đơn hàng, trả về 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn hàng với ID: " + orderId);
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác (nếu có)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

}
