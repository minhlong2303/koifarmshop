package com.example.koifarm.repository;

import com.example.koifarm.entity.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment, Long> {
}
