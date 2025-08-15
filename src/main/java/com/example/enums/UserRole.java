package com.example.enums;

public enum UserRole {
    CANDIDATE("candidate", "Job Seeker (Candidate)"),
    EMPLOYER("employer", "Employer");
    
    private final String value;
    private final String displayName;
    
    UserRole(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }
    
    public String getValue() {
        return value;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}