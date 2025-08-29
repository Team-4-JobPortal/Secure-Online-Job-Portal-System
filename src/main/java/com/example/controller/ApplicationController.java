package com.example.controller;

import com.example.entity.Application;
import com.example.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    
    @Autowired
    private ApplicationService appService;
    
    /* 
     * Retrieves a complete list of all applications in the system.
     * @return List<Application> containing all application entities
     */
    @GetMapping("/list")
    public List<Application> listApplications() {
        return appService.findAllApp();
    }
    
    /* 
     * Fetches a specific application by its unique identifier.
     * @param id The unique application ID to retrieve
     * @return Application entity matching the provided ID
     */
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable int id) {
        return appService.findAppById(id);
    }
    
    /* 
     * Retrieves all applications submitted by the currently authenticated candidate.
     * @param authentication Spring Security authentication context containing user details
     * @return ResponseEntity with list of candidate's applications or error message
     */
    @GetMapping("/my-applications")
    public ResponseEntity<?> getMyApplications(Authentication authentication) {
        return appService.getMyApplications(authentication);
    }
    
    /* 
     * Gets all applications submitted to jobs posted by the current employer.
     * @param authentication Spring Security authentication context containing employer details
     * @return ResponseEntity with applications for employer's jobs or error message
     */
    @GetMapping("/my-job-applications")
    public ResponseEntity<?> getApplicationsForMyJobs(Authentication authentication) {
        return appService.getApplicationsForMyJobs(authentication);
    }

    /* 
     * Provides dashboard statistics for candidates including application counts by status.
     * @param authentication Spring Security authentication context containing candidate details
     * @return ResponseEntity with statistical data for candidate dashboard
     */
    @GetMapping("/candidate/stats")
    public ResponseEntity<?> getDashboardStats(Authentication authentication) {
        return appService.getCandidateStats(authentication);
    }
    
    /* 
     * Updates the status of a specific application (e.g., accepted, rejected).
     * @param id The unique identifier of the application to update
     * @param status The new status to assign to the application
     * @param authentication Spring Security authentication context containing employer details
     * @return ResponseEntity indicating success/failure of status update
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable int id, 
                                                   @RequestParam String status, 
                                                   Authentication authentication) {
        return appService.updateApplicationStatus(id, status, authentication);
    }
    
    /* 
     * Provides dashboard statistics for employers including job and application metrics.
     * @param authentication Spring Security authentication context containing employer details
     * @return ResponseEntity with statistical data for employer dashboard
     */
    @GetMapping("/employer/stats")
    public ResponseEntity<?> getEmployerDashboardStats(Authentication authentication) {
        return appService.getEmployerStats(authentication);
    }

    /* 
     * Allows candidates to withdraw their submitted applications.
     * @param id The unique identifier of the application to withdraw
     * @param authentication Spring Security authentication context containing candidate details
     * @return ResponseEntity indicating success/failure of withdrawal operation
     */
    @DeleteMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawApplication(@PathVariable int id, Authentication authentication) {
        return appService.withdrawApplication(id, authentication);
    }
}
