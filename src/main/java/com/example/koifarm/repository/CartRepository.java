package com.example.koifarm.repository;

import com.example.koifarm.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Tìm giỏ hàng theo userId
    Optional<Cart> findByUserId(Long userId);
}
