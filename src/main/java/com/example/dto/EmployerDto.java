package com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EmployerDto {
	/*
	 * @NotNull(message = "User ID is required") private Integer userId;
	 */

    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name must not exceed 150 characters")
    private String companyName;

    @NotBlank(message = "Company description is required")
    @Size(max = 1000, message = "Company description must not exceed 1000 characters")
    private String companyDescription;

    @NotBlank(message = "Current profile is required")
    @Size(max = 255, message = "Current profile must not exceed 255 characters")
    private String currentProfile;

    
    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(String currentProfile) {
        this.currentProfile = currentProfile;
    }
}
