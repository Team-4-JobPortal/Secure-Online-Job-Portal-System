package com.example.controller;


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

    /* 
     * Retrieves the complete profile information for the authenticated user.
     * Returns user details along with candidate/employer profile data.
     * @param authentication Spring Security authentication context containing user details
     * @return ResponseEntity with user profile data including personal info and role-specific details
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        return userService.getUserProfile(authentication);
    }
}
