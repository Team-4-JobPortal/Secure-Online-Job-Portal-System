package com.example.dto;

import java.time.LocalDateTime;


import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApplicationDto {

    private String status;
    
    @NotBlank(message = "cv is required")
    @Size(max=500,message="cover letter content can't be greater than 500 characters")
    private String coverLetter;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime applicationDate;
    
 // Auto-set application date when creating
    @PrePersist
    protected void onCreate() {
        this.applicationDate = LocalDateTime.now();
    }

    // --- Getters & Setters ---
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCoverLetter() {
        return coverLetter;
    }
    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
    
    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }
}
