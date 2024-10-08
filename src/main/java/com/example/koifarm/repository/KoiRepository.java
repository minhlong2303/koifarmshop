package com.example.koifarm.repository;

import com.example.koifarm.entity.Koi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface KoiRepository extends JpaRepository<Koi, UUID> {
    //DAT TEN FUNCTION THEO DINH DANG JPA CUNG CAP
    //findKoiByKoiID(long koiID)
    Koi findKoiByKoiID(UUID koiID);

    //lay danh sach koi chua bi xoa
    //findKoiByIsDeletedFalse();
    List<Koi> findKoiByIsDeletedFalse();

    //Page<Koi> findAll(Pageable pageable); lay danh sach tat ca koi trong db

    Page<Koi> findKoiByIsDeletedFalse(Pageable pageable);  //lay danh sach koi da bi xoa, display len shop page
}
