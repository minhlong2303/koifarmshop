package com.example.koifarm.model.request;

import com.example.koifarm.entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
//    @Pattern(regexp = "KH\\d{6}", message = "Invalid code!")
//    @Column(unique = true)
//    String userCode;

    @Column(unique = true)
    String username;

//    String firstName;
//
//    String lastName;

    @Column(unique = true)
    @Email(message = "Invalid Email!")
    String email;

    @Column(unique = true)
    @Pattern(regexp = "^((\\+84)|0)[3|5|7|8|9][0-9]{8}$", message = "Invalid phone!")
    String phone;

    @Size(min = 6, message = "Password must be at least 6 character!")
    String password;

    Role role;
}
