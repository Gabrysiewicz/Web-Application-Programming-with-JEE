package com.example.memstagram.controller;

import com.example.memstagram.dto.LoginRequestDto;
import com.example.memstagram.dto.UserRegistrationDto;
import com.example.memstagram.model.User;
import com.example.memstagram.service.UserService;
import com.example.memstagram.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public String home() {
        return "Welcome to the API!";
    }

    @Operation(summary = "Create a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/auth/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            User registeredUser = userService.registerUser(userRegistrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @Operation(summary = "User login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        try {
            String usernameOrEmail = loginRequestDto.getUsernameOrEmail();
            String password = loginRequestDto.getPassword();

            // Check if the usernameOrEmail is an email or username
            Optional<User> user = userService.authenticateByUsernameOrEmail(usernameOrEmail, password);

            if (user.isPresent()) {
                // Get necessary fields to generate token
                User authenticatedUser = user.get();
                String token = jwtUtil.generateToken(authenticatedUser.getUsername(), authenticatedUser.getEmail(), authenticatedUser.getId());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
