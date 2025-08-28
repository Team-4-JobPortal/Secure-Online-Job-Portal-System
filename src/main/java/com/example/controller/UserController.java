package com.example.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.dto.UpdateCandidateDto;
import com.example.entity.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listUsers() {
        // Simple delegation - no business logic needed
        return userService.findAllUsers();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(Authentication authentication,
    @Valid @RequestBody UpdateCandidateDto updateDto) {
    return userService.updateUserProfile(updateDto, authentication);
    }
}
