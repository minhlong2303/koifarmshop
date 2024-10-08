package com.example.koifarm.repository;

import com.example.koifarm.entity.User;
import com.example.koifarm.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //lay account bang dien thoai
    //User findUserByPhone(String phone);

    //lay account bang username
    User findUserByUsername(String username);

    User findUserById(long id);

    User findUserByEmail(String email);

    User findUserByRole(Role role);

}
