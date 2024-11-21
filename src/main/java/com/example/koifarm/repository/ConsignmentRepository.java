package com.example.koifarm.repository;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.enums.ConsignmentStatusEnum;
import com.example.koifarm.enums.ConsignmentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment, Long> {

    List<Consignment> findByConsignmentType(ConsignmentType type);

    // Phương thức tìm theo tên Koi
    List<Consignment> findByKoiNameContainingIgnoreCase(String koiName);

    // Phương thức tìm theo user
    List<Consignment> findByCustomer_Id(Long customerId);

    Optional<Consignment> findById(UUID id);
    // Phương thức phân trang
    Page<Consignment> findAll(Pageable pageable);

    List<Consignment> findByStatus(ConsignmentStatusEnum status);
}