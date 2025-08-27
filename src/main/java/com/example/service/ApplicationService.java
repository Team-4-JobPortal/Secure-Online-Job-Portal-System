package com.example.service;
 
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.dto.ApplicationDto;
import com.example.entity.Application;
 
public interface ApplicationService {
    List<Application> findAllApp();
    Application findAppById(int id);
    Application saveApp(int jobId,ApplicationDto request,Authentication authentication);
    void updateApp(Application application);
    void deleteApp(int id);
    // NEW METHODS
    List<Application> findApplicationsByUser(int userId);
    
 // ADD THIS METHOD
    List<Application> findApplicationsByEmployer(int employerId);
    
    boolean hasUserAppliedToJob(int userId, int jobId);
}