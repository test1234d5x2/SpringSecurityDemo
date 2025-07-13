package com.example.securityDemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.securityDemo.models.User;
import com.example.securityDemo.repositories.UserRepo;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/{username}")
    public User getUserByUsername(Authentication authentication, @PathVariable String username) {
        if (authentication == null) {
            System.out.println("No authentication present");
            return null;
        }

        if (!authentication.getName().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        
        System.out.println("Authenticated username: " + authentication.getName());
        return userRepo.findByUsername(username).orElse(null);
    }
}
