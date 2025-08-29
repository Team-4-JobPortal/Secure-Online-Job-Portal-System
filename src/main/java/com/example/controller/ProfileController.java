package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.UserService;

@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
    return userService.getUserProfile(authentication);
    }
    
    
}
