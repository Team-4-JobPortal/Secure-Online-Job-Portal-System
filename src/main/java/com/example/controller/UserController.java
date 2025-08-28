package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.entity.CandidateProfile;
import com.example.entity.User;
import com.example.enums.ExperienceLevel;
import com.example.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listUsers() {
        return userService.findAllUsers();
    }
    // update candidate profile
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(Authentication authentication, @RequestBody Map<String, String> body) {
        User user = userService.findByemail(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        // Basic fields
        if (body.containsKey("firstName")) {
            user.setFirstName(body.get("firstName"));
        }
        if (body.containsKey("lastName")) {
            user.setLastName(body.get("lastName"));
        }
        if (body.containsKey("phone")) {
            user.setPhoneNumber(body.get("phone"));
        }

        // Candidate profile fields
        CandidateProfile candidateProfile = user.getCandidateProfile();
        if (candidateProfile == null) {
            candidateProfile = new CandidateProfile();
            candidateProfile.setUser(user);
            user.setCandidateProfile(candidateProfile);
        }

        if (body.containsKey("location")) {
            candidateProfile.setCurrentLocation(body.get("location"));
        }
        if (body.containsKey("skills")) {
            candidateProfile.setSkills(body.get("skills"));
        }
        if (body.containsKey("experience")) {
            candidateProfile.setExperienceLevel(mapUiToExperience(body.get("experience")));
        }

        userService.updateUser(user);
        return ResponseEntity.ok().build();
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
