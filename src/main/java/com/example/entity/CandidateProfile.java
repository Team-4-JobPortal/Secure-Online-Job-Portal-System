package com.example.entity;

import com.example.enums.ExperienceLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "CandidateProfiles")
public class CandidateProfile {

    @Id
    private int userId;

    private String currentLocation;
    private String skills;

    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel; // updated field

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore // prevent infinite recursion
    private User user;

    // getters and setters
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getCurrentLocation() {
        return currentLocation;
    }
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }
    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
