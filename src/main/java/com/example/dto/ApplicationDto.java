package com.example.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApplicationDto {

    private String status;
    
    @NotBlank(message = "cv is required")
    @Size(max=500,message="cover letter content can't be greater than 500 characters")
    private String coverLetter;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "candidate_id") // foreign key column in Applications
	 * table private User user;
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "job_id") // foreign key column in Applications table
	 * private Job job;
	 */
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime applicationDate;
    
 // Auto-set application date when creating
    @PrePersist
    protected void onCreate() {
        this.applicationDate = LocalDateTime.now();
    }

    // --- Getters & Setters ---
	/*
	 * public int getApplication_id() { return application_id; }
	 * 
	 * public void setApplication_id(int application_id) { this.application_id =
	 * application_id; }
	 */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	/*
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 * 
	 * public Job getJob() { return job; }
	 * 
	 * public void setJob(Job job) { this.job = job; }
	 */
    
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
