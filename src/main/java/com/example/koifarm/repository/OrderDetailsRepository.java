package com.example.koifarm.repository;

import com.example.koifarm.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {
    // Tìm OrderDetails theo order_id
    List<OrderDetails> findByOrders_Id(UUID orderId);
}
