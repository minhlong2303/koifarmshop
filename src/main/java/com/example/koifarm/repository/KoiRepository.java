package com.example.koifarm.repository;

import com.example.koifarm.entity.Koi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KoiRepository extends JpaRepository<Koi, Long> {
    //DAT TEN FUNCTION THEO DINH DANG JPA CUNG CAP
    //findKoiByKoiID(long koiID)
    Optional<Koi> findKoiByKoiID(UUID koiID);

    //lay danh sach koi chua bi xoa
    //findKoiByIsDeletedFalse();
    List<Koi> findKoiByIsDeletedFalse();

    //Page<Koi> findAll(Pageable pageable); lay danh sach tat ca koi trong db

    //Page<Koi> findKoiByIsDeletedFalse(Pageable pageable);  //lay danh sach koi da bi xoa, display len shop page
    Page<Koi> findKoiByIsDeletedFalse(Pageable pageable);  //lay danh sach koi da bi xoa, display len shop page
    //lay ra 5 ca koi duoc mua nhieu nhat
    @Query("select p.name, sum(od.quantity) from OrderDetails od join od.koi p " +
            "group by p.id " +
            "order by sum(od.quantity) desc")
    List<Object[]> findTop5BestSellerProducts();

}
