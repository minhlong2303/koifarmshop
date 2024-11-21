package com.example.koifarm.repository;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.enums.ConsignmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsignmentRepository extends JpaRepository<Consignment, UUID> {

    List<Consignment> findByConsignmentType(ConsignmentType type);

    // Phương thức tìm theo tên Koi
    List<Consignment> findByKoiNameContainingIgnoreCase(String koiName);

    // Phương thức tìm theo user
    List<Consignment> findByCustomer_Id(Long customerId);

    Consignment findConsignmentById(UUID id);
    // Phương thức phân trang
    Page<Consignment> findAll(Pageable pageable);
}