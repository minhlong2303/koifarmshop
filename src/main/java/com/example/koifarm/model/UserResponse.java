package com.example.koifarm.model;

import com.example.koifarm.enums.Role;
import lombok.Data;

@Data
public class UserResponse {
     long id;
     //String userCode;
     String email;
     String phone;
     String token;
     Role role;
}