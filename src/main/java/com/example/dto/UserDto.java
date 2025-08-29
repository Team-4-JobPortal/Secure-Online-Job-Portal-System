package com.example.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserDto {
	@NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

	
    
    @NotBlank(message = "Phone number is required")
    @Size(max = 10, message = "Phone number must not exceed 10 characters")

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain only digits")
    @JsonAlias({ "phone", "phone_number", "mobile" })
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!?.]).{8,}$",
        message = "Min 8 characters, must contain uppercase, lowercase, number, and special character"
    )
    private String password;

    private String oldPassword;

    private String newPassword;
    
    @Valid   // 🔑 important! ensures validation on nested CandidateDto
    private CandidateDto candidateProfile;


    @Valid   // 🔑 important! ensures validation on nested CandidateDto
    private EmployerDto employerProfile;

    
    // Getters and Setters

    public CandidateDto getCandidateProfile() {
		return candidateProfile;
	}

	public void setCandidateProfile(CandidateDto candidateProfile) {
		this.candidateProfile = candidateProfile;
	}

	public EmployerDto getEmployerProfile() {
		return employerProfile;
	}

	public void setEmployerProfile(EmployerDto employerProfile) {
		this.employerProfile = employerProfile;
	}

	public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}