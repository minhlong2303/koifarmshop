package com.example.koifarm.repository;

import com.example.koifarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BatchKoiOrderRepository extends JpaRepository<BatchKoiOrder, UUID> {
    List<BatchKoiOrder> findBatchKoiOrderByCustomer(User customer);
}
