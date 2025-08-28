package com.example.service;

import com.example.dto.ApplicationDto;
import com.example.entity.Application;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ApplicationService {
    List<Application> findAllApp();
    Application findAppById(int id);
    Application saveApp(int jobId, ApplicationDto request, Authentication authentication);
    void updateApp(Application application);
    void deleteApp(int id);
    List<Application> findApplicationsByUser(int userId);
    boolean hasUserAppliedToJob(int userId, int jobId);
    List<Application> findApplicationsByEmployer(int employerId);
    
    // New business logic methods
    ResponseEntity<?> getMyApplications(Authentication authentication);
    ResponseEntity<?> getApplicationsForMyJobs(Authentication authentication);
    ResponseEntity<?> getCandidateStats(Authentication authentication);
    ResponseEntity<?> getEmployerStats(Authentication authentication);
    ResponseEntity<?> updateApplicationStatus(int id, String status, Authentication authentication);
    ResponseEntity<?> withdrawApplication(int id, Authentication authentication);
}
