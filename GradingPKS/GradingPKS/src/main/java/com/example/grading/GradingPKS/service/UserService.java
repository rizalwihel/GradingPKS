package com.example.grading.GradingPKS.service;

import com.example.grading.GradingPKS.model.User;
import com.example.grading.GradingPKS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public User register(User user) {
        // Cek apakah username sudah ada
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username sudah digunakan");
        }
        return userRepository.save(user);
    }
} 