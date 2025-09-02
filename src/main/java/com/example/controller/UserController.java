package com.example.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.dto.UpdateCandidateDto;
import com.example.entity.User;
import com.example.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /* 
     * Retrieves a list of all registered users in the system.
     * Simple delegation to service layer without additional business logic.
     * @return List<User> containing all user entities
     */
    @GetMapping
    public List<User> listUsers() {
        // Simple delegation - no business logic needed
        return userService.findAllUsers();
    }

    /* 
     * Updates the profile information for the authenticated user.
     * Supports updating basic user details and candidate profile information.
     * @param authentication Spring Security authentication context containing user details
     * @param updateDto UpdateCandidateDto containing updated profile data with validation
     * @return ResponseEntity indicating successful profile update or validation errors
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(Authentication authentication,
                                         @Valid @RequestBody UpdateCandidateDto updateDto) {
        return userService.updateUserProfile(updateDto, authentication);
    }
}
