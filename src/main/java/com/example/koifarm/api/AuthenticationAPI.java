package com.example.koifarm.api;

import com.example.koifarm.entity.User;
import com.example.koifarm.model.response.UserResponse;
import com.example.koifarm.model.request.LoginRequest;
import com.example.koifarm.model.request.RegisterRequest;
import com.example.koifarm.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class AuthenticationAPI {
    //DI: Dependency Injection
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest){
        UserResponse newUser = authenticationService.register(registerRequest);
        return ResponseEntity.ok(newUser);
    }
    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        UserResponse newUser = authenticationService.login(loginRequest);
        return ResponseEntity.ok(newUser);
    }
    @GetMapping("user")
    public ResponseEntity getAllUser(){
        List<User> users = authenticationService.getAllUser();
        return ResponseEntity.ok(users);
    }
}
