package com.example.koifarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsignmentRepository extends JpaRepository<Consignment, Long> {

    List<Consignment> findByConsignmentType(ConsignmentType type);

    // Phương thức tìm theo tên Koi
    List<Consignment> findByKoiNameContainingIgnoreCase(String koiName);

    // Phương thức tìm theo user
    List<Consignment> findByCustomer_Id(Long customerId);


    // Phương thức phân trang
    Page<Consignment> findAll(Pageable pageable);
}