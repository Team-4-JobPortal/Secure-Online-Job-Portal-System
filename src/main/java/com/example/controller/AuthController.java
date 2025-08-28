package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.dto.LoginRequest;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest user) {
        // Delegate all logic to service layer
        return userService.loginUser(user);
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody User req) {
        // Delegate all logic to service layer
        return userService.changeUserPassword(req);
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        // Delegate all logic to service layer
        return userService.getCurrentUserInfo(authentication);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }
}
