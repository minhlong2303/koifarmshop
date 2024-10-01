package com.example.koifarm.repository;

import com.example.koifarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //lay account bang dien thoai
    User findUserByPhone(String phone);

    User findUserById(long id);

}
