package com.example.koifarm.repository;

import com.example.koifarm.entity.Orders;
import com.example.koifarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT o FROM Orders o WHERE o.customer = :customer ORDER BY o.date ASC")
    List<Orders> findOrdersByCustomer(@Param("customer") User customer);

    Orders findOrdersById(UUID id);
}
