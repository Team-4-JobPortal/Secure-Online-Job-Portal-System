package com.example.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.entity.User;
import com.example.security.JwtUtil;
import com.example.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final  JwtUtil jwtUtil = new JwtUtil();
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;

    

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String role = body.get("role");

        // 1. Authenticate the user by email and password
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Collections.singletonMap("message", "Invalid email or password"));
        }

        // 2. Fetch user from DB
        User dbUser = userService.findByemail(email);
        if (dbUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Collections.singletonMap("message", "User not found"));
        }

        // 3. Compare roles, assuming dbUser.getRole() returns "employer" or "candidate"
        if (!dbUser.getRole().equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Collections.singletonMap("message", "Role does not match for this account"));
        }

        // 4. Generate JWT token
        String token = jwtUtil.generateToken(dbUser.getEmail()); // or username if different

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("token", token);
        response.put("role", dbUser.getRole());

        return ResponseEntity.ok(response);
    }
    
    
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody User req) {
        User dbUser = userService.findByemail(req.getEmail());
        if (dbUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Account not found with this email and role."));
        }

        // Role check
        if (!dbUser.getRole().equalsIgnoreCase(req.getRole())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Role does not match for this account."));
        }

        // Verify old password
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getOldPassword())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Current password is incorrect."));
        }

        // Update password
        userService.updatePassword(dbUser, req.getNewPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Password changed successfully");
        return ResponseEntity.ok(response);
    }

    
    

    @GetMapping("/user/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(Authentication authentication) {
        User user = userService.findByemail(authentication.getName());

        Map<String, String> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public void addUser(@RequestBody User user) {
        
        userService.saveUser(user);
    }
    
}
 