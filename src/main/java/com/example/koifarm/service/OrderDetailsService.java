package com.example.koifarm.service;

import com.example.koifarm.entity.OrderDetails;
import com.example.koifarm.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    // Lấy danh sách OrderDetails theo orderId
    public List<OrderDetails> getOrderDetailsByOrderId(UUID orderId) {
        return orderDetailsRepository.findByOrders_Id(orderId);
    }
}
