package com.example.koifarm.repository;

import com.example.koifarm.entity.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ConsignmentRepository extends JpaRepository<Consignment, UUID> {
}