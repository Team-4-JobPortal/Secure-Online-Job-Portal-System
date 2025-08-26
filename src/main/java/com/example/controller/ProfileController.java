package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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

	private String mapExperienceToUi(ExperienceLevel experienceLevel) {
		if (experienceLevel == null)
			return null;
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

}
