package com.example.koifarm.repository;

import com.example.koifarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //lay account bang dien thoai
    //User findUserByPhone(String phone);

    //lay account bang username
    User findUserByUsername(String username);

    User findUserById(long id);

    User findUserByEmail(String email);

    Optional<User> findByPhone(String phone);

}
