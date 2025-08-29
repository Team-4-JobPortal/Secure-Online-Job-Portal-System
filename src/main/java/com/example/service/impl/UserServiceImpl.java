package com.example.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.UserDAO;
import com.example.dto.LoginRequest;
import com.example.dto.UpdateCandidateDto;
import com.example.dto.UserDto;
import com.example.entity.CandidateProfile;
import com.example.entity.EmployerProfile;
import com.example.entity.User;
import com.example.enums.ExperienceLevel;
import com.example.security.JwtUtil;
import com.example.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    private final JwtUtil jwtUtil = new JwtUtil();

    // Existing methods
    @Override
    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User findByUserId(int id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> findByUserRole(String role) {
        return userDAO.findByRole(role);
    }

    @Override
    public void saveUser(User user) {
        userDAO.save(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.delete(id);
    }

    @Override
    public User findByemail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String encoded = passwordEncoder.encode(newPassword);
        user.setPassword(encoded);
        userDAO.update(user);
    }

    // Business logic methods with ResponseEntity for role validation
    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String role = loginRequest.getRole();
        
        // 1. Authenticate the user by email and password (let exceptions bubble up)
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException ex) {
        	System.out.println("{\"message\": \"Invalid email or password!\"}");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Collections.singletonMap("message", "Invalid email or password"));
        }
        
        // 2. Fetch user from DB
        User dbUser = findByemail(email);
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

    @Override
    public ResponseEntity<?> changeUserPassword(User request) {
        User dbUser = findByemail(request.getEmail());
        
        if (dbUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Account not found with this email and role."));
        }
        
        // Role validation - RETURN RESPONSE instead of throwing exception
        if (!dbUser.getRole().equalsIgnoreCase(request.getRole())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Role does not match for this account."));
        }

        // Verify old password (let authentication exceptions bubble up)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getOldPassword())
        );

        // Update password
        updatePassword(dbUser, request.getNewPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Password changed successfully");
        return ResponseEntity.ok(response);
    }

    @Override
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
        user.setPassword(userDto.getPassword()); // encode in DAO/save layer

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
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        User user = findByemail(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

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
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

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


    // MOVED: Private utility methods from controllers
    private String mapExperienceToUi(ExperienceLevel experienceLevel) {
        if (experienceLevel == null) return null;
        
        switch (experienceLevel) {
            case JUNIOR: return "1-3"; // representative value for JUNIOR range
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
        // Map 0-1, 1-3, 3-5 all to JUNIOR
        return ExperienceLevel.JUNIOR;
    }
}
