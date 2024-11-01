package com.example.koifarm.service;

import com.example.koifarm.entity.User;
import com.example.koifarm.exception.DuplicateEntity;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.*;
import com.example.koifarm.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    EmailService emailService;

    public UserResponse register(RegisterRequest registerRequest){
        //map RegisterRequest -> user
        User user = modelMapper.map(registerRequest, User.class);

        try {
            String originPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(originPassword));
            User newUser = userRepository.save(user);

            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setUser(newUser);
            emailDetail.setSubject("Welcome to Koi Farm Shop");
            emailDetail.setLink("http://localhost:5173/");   //link project

            emailService.sentEmail(emailDetail);

            return modelMapper.map(newUser, UserResponse.class);
        } catch (Exception e){
            if (e.getMessage().contains(user.getUsername())){
                throw new DuplicateEntity("Duplicate Name!");
            } else if (e.getMessage().contains(user.getEmail())){
                throw new DuplicateEntity("Duplicate Email!");
            } else {
                throw new DuplicateEntity("Duplicate Phone!");
            }
        }
    }

    public List<User> getAllUser(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public UserResponse login(LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
            User user = (User) authentication.getPrincipal();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            userResponse.setToken(tokenService.generateToken(user));
            return userResponse;
        } catch (Exception e){
            throw new EntityNotFoundException("Invalid username or password!");
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
//        return userRepository.findUserByPhone(phone);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    //ai dang goi request
    public User getCurrentUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserById(user.getId());
    }

    public User delete(long id){
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) throw new EntityNotFoundException("User not found!");
        oldUser.setDeleted(true);
        return userRepository.save(oldUser);
    }

    public UserResponse getUserById(long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User with ID " + id + " not found!");
        }
        return modelMapper.map(user, UserResponse.class);
    }

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest){
        User user = userRepository.findUserByEmail(forgotPasswordRequest.getEmail());

        if (user == null){
            throw new EntityNotFoundException("User not found!");
        } else {
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setUser(user);
            emailDetail.setSubject("Reset Password");
            emailDetail.setLink("https://www.google.com/?token=" + tokenService.generateToken(user));  //link forgot password

            emailService.sentEmail(emailDetail);
        }

    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest){
        User user = getCurrentUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userRepository.save(user);
    }
}
