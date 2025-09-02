package com.example.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.LoginRequest;
import com.example.dto.UpdateCandidateDto;
import com.example.dto.UserDto;
import com.example.entity.CandidateProfile;
import com.example.entity.EmployerProfile;
import com.example.entity.User;
import com.example.enums.ExperienceLevel;
import com.example.exception.UserEmailExistException;
import com.example.exception.UserEmailNotFoundException;
import com.example.repository.CandidateProfileRepository;
import com.example.repository.EmployerProfileRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;
import com.example.service.UserService;

/**
 * Service implementation for User management using Spring Data JPA repositories.
 * Handles user registration, authentication, profile management, and business logic.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CandidateProfileRepository candidateProfileRepository;
    
    @Autowired
    private EmployerProfileRepository employerProfileRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;

    // Basic CRUD operations
    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUserId(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByUserRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public void saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserEmailExistException("Email already exists.");
        }
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Handle profile associations
        if ("candidate".equalsIgnoreCase(user.getRole()) && user.getCandidateProfile() != null) {
            user.getCandidateProfile().setUser(user);
        }
        if ("employer".equalsIgnoreCase(user.getRole()) && user.getEmployerProfile() != null) {
            user.getEmployerProfile().setUser(user);
        }
        
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // Handle profile associations
        if (user.getCandidateProfile() != null) {
            user.getCandidateProfile().setUser(user);
        }
        if (user.getEmployerProfile() != null) {
            user.getEmployerProfile().setUser(user);
        }
        
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByemail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException("No user found by the email: " + email));
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String encoded = passwordEncoder.encode(newPassword);
        user.setPassword(encoded);
        userRepository.save(user);
    }

    // Authentication and business logic methods
    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String role = loginRequest.getRole();
        
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
        User dbUser = findByemail(email);
        
        // 3. Compare roles
        if (!dbUser.getRole().equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Collections.singletonMap("message", "Role does not match for this account"));
        }

        // 4. Generate JWT token
        String token = jwtUtil.generateToken(dbUser.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("token", token);
        response.put("role", dbUser.getRole());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> changeUserPassword(User request) {
        User dbUser = findByemail(request.getEmail());
        
        // Role validation
        if (!dbUser.getRole().equalsIgnoreCase(request.getRole())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Role does not match for this account."));
        }

        // Verify old password
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getOldPassword())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Invalid old password."));
        }

        // Update password
        updatePassword(dbUser, request.getNewPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Password changed successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
        User user = findByemail(authentication.getName());

        Map<String, String> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> registerUser(UserDto userDto) {
        User user = new User();
        user.setRole(userDto.getRole());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        if ("candidate".equalsIgnoreCase(userDto.getRole()) && userDto.getCandidateProfile() != null) {
            CandidateProfile cp = new CandidateProfile();
            cp.setCurrentLocation(userDto.getCandidateProfile().getCurrentLocation());
            cp.setSkills(userDto.getCandidateProfile().getSkills());
            cp.setExperienceLevel(userDto.getCandidateProfile().getExperienceLevel());
            cp.setUser(user);
            user.setCandidateProfile(cp);
        }

        if ("employer".equalsIgnoreCase(userDto.getRole()) && userDto.getEmployerProfile() != null) {
            EmployerProfile ep = new EmployerProfile();
            ep.setCompanyName(userDto.getEmployerProfile().getCompanyName());
            ep.setCompanyDescription(userDto.getEmployerProfile().getCompanyDescription());
            ep.setCurrentProfile(userDto.getEmployerProfile().getCurrentProfile());
            ep.setUser(user);
            user.setEmployerProfile(ep);
        }

        saveUser(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        User user = findByemail(authentication.getName());

        CandidateProfile candidateProfile = user.getCandidateProfile();

        Map<String, Object> dto = new HashMap<>();
        dto.put("firstName", user.getFirstName());
        dto.put("lastName", user.getLastName());
        dto.put("email", user.getEmail());
        dto.put("phone", user.getPhoneNumber());
        
        if (candidateProfile != null) {
            dto.put("location", candidateProfile.getCurrentLocation());
            dto.put("skills", candidateProfile.getSkills());
            dto.put("experience", mapExperienceToUi(candidateProfile.getExperienceLevel()));
        }

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> updateUserProfile(UpdateCandidateDto updateDto, Authentication authentication) {
        User user = findByemail(authentication.getName());

        // Basic updates
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setPhoneNumber(updateDto.getPhoneNumber());

        // Candidate profile updates
        if (updateDto.getCandidateProfile() != null) {
            CandidateProfile cp = user.getCandidateProfile();
            if (cp == null) {
                cp = new CandidateProfile();
                cp.setUser(user);
                user.setCandidateProfile(cp);
            }
            cp.setCurrentLocation(updateDto.getCandidateProfile().getCurrentLocation());
            cp.setSkills(updateDto.getCandidateProfile().getSkills());
            cp.setExperienceLevel(updateDto.getCandidateProfile().getExperienceLevel());
        }

        updateUser(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "Profile updated successfully"));
    }

    // Private utility methods
    private String mapExperienceToUi(ExperienceLevel experienceLevel) {
        if (experienceLevel == null) return null;
        
        switch (experienceLevel) {
            case JUNIOR: return "1-3";
            case SENIOR: return "5-10";
            case LEAD: return "10+";
            default: return null;
        }
    }

    private ExperienceLevel mapUiToExperience(String uiValue) {
        if (uiValue == null) return null;
        
        uiValue = uiValue.trim();
        if ("10+".equals(uiValue)) return ExperienceLevel.LEAD;
        if ("5-10".equals(uiValue)) return ExperienceLevel.SENIOR;
        return ExperienceLevel.JUNIOR;
    }
}