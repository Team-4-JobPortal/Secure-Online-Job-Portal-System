package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.dto.LoginRequest;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /* 
     * Authenticates user credentials and generates JWT token on successful login.
     * Validates email, password, and role combination.
     * @param user LoginRequest DTO containing email, password, and role
     * @return ResponseEntity with JWT token and user role or error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest user) {
        // Delegate all logic to service layer
        return userService.loginUser(user);
    }
    
    /* 
     * Changes the password for an authenticated user.
     * Requires old password verification before setting new password.
     * @param req User entity containing email, role, oldPassword, and newPassword
     * @return ResponseEntity indicating success or failure of password change
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody User req) {
        // Delegate all logic to service layer
        return userService.changeUserPassword(req);
    }

    /* 
     * Retrieves basic information about the currently authenticated user.
     * @param authentication Spring Security authentication context containing user details
     * @return ResponseEntity with user's email, firstName, and lastName
     */
    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        // Delegate all logic to service layer
        return userService.getCurrentUserInfo(authentication);
    }
    
    /* 
     * Registers a new user account with validation.
     * Creates user profile based on role (candidate or employer).
     * @param userDto UserDto containing user registration data and profile information
     * @return ResponseEntity indicating successful registration or validation errors
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }
}
