package com.example.securityDemo.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.securityDemo.models.User;
import com.example.securityDemo.repositories.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    public boolean userExists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public User getUserByUsername(String username) throws NoSuchElementException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchElementException("User not found with username: " + username);
        }
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
