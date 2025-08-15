package com.example.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final  JwtUtil jwtUtil = new JwtUtil();
    @Autowired
    private AuthenticationManager authenticationManager;

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    		String email = body.get("email");
        String password = body.get("password");
        System.out.println("Email "+email+" ,"+password+", role : "+body.get("role"));
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            String token = jwtUtil.generateToken(userDetails.getUsername());
            Map<String, Object> response = new HashMap<>();
            
            response.put("status", "success");
            response.put("token", token);
            
//            return ResponseEntity.ok(Collections.singletonMap("token", token));
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }
    }
}
 