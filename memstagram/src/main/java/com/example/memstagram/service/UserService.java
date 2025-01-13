package com.example.memstagram.service;

import com.example.memstagram.model.User;
import com.example.memstagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    // Get a user by Username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    // Create a new user
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        Optional<User> existingUsername = userRepository.findByUsername(user.getUsername());
        if (existingUsername.isPresent()) {
            throw new IllegalArgumentException("Username is already in use.");
        }

        return userRepository.save(user);
    }

    // Update user
    public Optional<User> updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            user.setId(id);
            user.setCreatedAt(existingUser.getCreatedAt()); // Preserve the created date
            return userRepository.save(user);
        });
    }

    // Soft delete user
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get()); // Delete the user from the database
            return true;
        }
        return false; // Return false if the user was not found
    }

}
