package com.example.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UpdateCandidateDto {
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    @JsonAlias({ "phone", "phone_number", "mobile" })
    private String phoneNumber;
    
    @Valid   // important! ensures validation on nested CandidateDto
    private CandidateDto candidateProfile;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }


    public CandidateDto getCandidateProfile() { return candidateProfile; }
    public void setCandidateProfile(CandidateDto candidateProfile) { this.candidateProfile = candidateProfile; }

}