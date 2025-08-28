package com.example.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.example.dto.LoginRequest;
import com.example.dto.UpdateCandidateDto;
import com.example.dto.UserDto;
import com.example.entity.User;

public interface UserService {
    // Existing methods
    List<User> findAllUsers();
    User findByUserId(int id);
    List<User> findByUserRole(String role);
    User findByemail(String email);
    void saveUser(User user);
    void updateUser(User user);
    void updatePassword(User dbUser, String newPassword);
    void deleteUser(int id);
    
    // Auth business logic methods
    ResponseEntity<?> loginUser(LoginRequest loginRequest);
    ResponseEntity<?> changeUserPassword(User request);
    ResponseEntity<?> getCurrentUserInfo(Authentication authentication);
    ResponseEntity<?> registerUser(UserDto userDto);

    
    // NEW: Profile business logic methods
    ResponseEntity<?> getUserProfile(Authentication authentication);
   
    ResponseEntity<?> updateUserProfile(UpdateCandidateDto updateDto, Authentication authentication);

    
}
