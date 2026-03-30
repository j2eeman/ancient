package com.ancient.game.service;

import com.ancient.game.entity.User;
import com.ancient.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User register(String username, String password, String email) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setScore(0);
        
        return userRepository.save(user);
    }
    
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return user;
    }
    
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public void updateScore(Long userId, int score) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setScore(user.getScore() + score);
            userRepository.save(user);
        }
    }
}