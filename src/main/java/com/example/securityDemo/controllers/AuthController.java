package com.example.securityDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securityDemo.security.JWTUtil;

import com.example.securityDemo.models.User;
import com.example.securityDemo.repositories.UserRepo;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;
    
    @PostMapping("/login")
    public String login(@RequestBody LoginDetailsRequest request) {
        String username = request.username;
        String password = request.password;

        var userOpt = userRepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            return null; // Or throw exception / return error response
        }
        var user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null; // Or throw exception / return error response
        }

        return jwtUtil.generateToken(username);
    }

    @PostMapping("/register")
    public boolean register(@RequestBody LoginDetailsRequest request) {
        String username = request.username;
        String password = request.password;

        if (userRepo.findByUsername(username).isPresent()) {
            return false; // User already exists
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt the password
        userRepo.save(user); // Save the new user to the database

        return true;
    }
}


class LoginDetailsRequest {
    public String password;
    public String username;
}