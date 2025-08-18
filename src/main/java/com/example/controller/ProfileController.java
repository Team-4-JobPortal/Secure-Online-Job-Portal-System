package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.CandidateProfile;
import com.example.entity.User;
import com.example.enums.ExperienceLevel;
import com.example.service.UserService;

@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        User user = userService.findByemail(authentication.getName());
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
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

    @PutMapping("/users/update")
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

    private String mapExperienceToUi(ExperienceLevel experienceLevel) {
        if (experienceLevel == null) return null;
        switch (experienceLevel) {
            case JUNIOR:
                return "1-3"; // representative value for JUNIOR range
            case SENIOR:
                return "5-10";
            case LEAD:
                return "10+";
            default:
                return null;
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


