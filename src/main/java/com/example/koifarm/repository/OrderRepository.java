package com.example.koifarm.repository;

import com.example.koifarm.entity.Orders;
import com.example.koifarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
    List<Orders> findOrdersByCustomer(User customer);

    Orders findOrdersById(UUID id);
}
