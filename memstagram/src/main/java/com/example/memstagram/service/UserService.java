package com.example.memstagram.service;

import com.example.memstagram.dto.LoginRequestDto;
import com.example.memstagram.dto.UserRegistrationDto;
import com.example.memstagram.exception.ResourceAlreadyExistsException;
import com.example.memstagram.model.User;
import com.example.memstagram.repository.UserRepository;
import com.example.memstagram.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Autowired
    private JwtUtil jwtUtil;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        // Check if username or email already exists
        if (userRepository.findByUsername(userRegistrationDto.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists.");
        }

        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists.");
        }

        // Create and save new user
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRole(User.Role.USER); // Set role as USER by default
        user.setProfileImageUrl(null); // Set profile image URL to null during registration
        user.setBio(null); // Set bio to null during registration

        return userRepository.save(user);
    }


    // Authenticate user and return JWT token
    public Optional<User> authenticateByUsernameOrEmail(String usernameOrEmail, String password) {
        Optional<User> user = userRepository.findByUsername(usernameOrEmail);

        if (!user.isPresent()) {
            user = userRepository.findByEmail(usernameOrEmail);
        }

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getUsername(), user.get().getEmail(), user.get().getId());
            return Optional.of(user.get()); // Return the user with the token
        }
        return Optional.empty();
    }



    // Update user
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOpt = getUserById(id);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();  // Extract the User object from Optional

            // Update fields
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            existingUser.setProfileImageUrl(updatedUser.getProfileImageUrl());
            existingUser.setBio(updatedUser.getBio());

            return userRepository.save(existingUser);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }


    // Delete user
    public boolean deleteUser(Long id, String requestingUsername) {
        User requestingUser = userRepository.findByUsername(requestingUsername)
                .orElseThrow(() -> new RuntimeException("Requesting user not found"));

        if (requestingUser.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Only users with ADMIN role can delete other users.");
        }

        Optional<User> userOpt = getUserById(id);

        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());  // Delete the User object if present
            return true; // Return true if deletion was successful
        } else {
            // Handle user not found scenario
            return false;
        }
    }

}