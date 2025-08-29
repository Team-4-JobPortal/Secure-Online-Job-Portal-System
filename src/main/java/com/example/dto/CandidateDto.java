package com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.example.enums.ExperienceLevel;

public class CandidateDto {
    @NotBlank(message = "Current location is required")
    @Size(max = 150, message = "Location must not exceed 150 characters")
    @Pattern(
    	    regexp = "^[A-Za-z]+$",
    	    message = "Location must be a single word without spaces"
    	)
    private String currentLocation;

    @NotBlank(message = "Skills are required")
    @Size(max = 1000, message = "Skills must not exceed 1000 characters")
    private String skills;

    @NotNull(message = "Experience level is required")
    private ExperienceLevel experienceLevel;

    // Getters and setters
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    
    public ExperienceLevel getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(ExperienceLevel experienceLevel) { this.experienceLevel = experienceLevel; }
}
