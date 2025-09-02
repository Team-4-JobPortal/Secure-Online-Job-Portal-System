package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;


import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="Applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private int application_id;

    private String status;
    
    @Column(columnDefinition = "TEXT") // longer cover letters allowed
    private String coverLetter;

    @ManyToOne
    @JoinColumn(name = "candidate_id") // foreign key column in Applications table
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")       // foreign key column in Applications table
    private Job job;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "application_date")
    private LocalDateTime applicationDate;
    
 // Auto-set application date when creating
    @PrePersist
    protected void onCreate() {
        this.applicationDate = LocalDateTime.now();
    }

    // --- Getters & Setters ---
    public int getApplication_id() {
        return application_id;
    }

    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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
