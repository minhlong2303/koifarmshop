package com.example.koifarm.model.response;

import com.example.koifarm.entity.Role;
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