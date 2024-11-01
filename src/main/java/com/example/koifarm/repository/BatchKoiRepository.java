package com.example.koifarm.repository;

import com.example.koifarm.entity.BatchKoi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BatchKoiRepository extends JpaRepository<BatchKoi, UUID> {

    BatchKoi findBatchKoiByBatchKoiID(UUID batchKoiID);

    List<BatchKoi> findBatchKoiByIsDeletedFalse();

    Page<BatchKoi> findBatchKoiByIsDeletedFalse(Pageable pageable);
}
