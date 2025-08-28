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
    
    @GetMapping("/list")
    public List<Application> listApplications() {
        return appService.findAllApp();
    }
    
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable int id) {
        return appService.findAppById(id);
    }
    
    @GetMapping("/my-applications")
    public ResponseEntity<?> getMyApplications(Authentication authentication) {
        return appService.getMyApplications(authentication);
    }
    
    @GetMapping("/my-job-applications")
    public ResponseEntity<?> getApplicationsForMyJobs(Authentication authentication) {
        return appService.getApplicationsForMyJobs(authentication);
    }

    @GetMapping("/candidate/stats")
    public ResponseEntity<?> getDashboardStats(Authentication authentication) {
        return appService.getCandidateStats(authentication);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable int id, 
                                                   @RequestParam String status, 
                                                   Authentication authentication) {
        return appService.updateApplicationStatus(id, status, authentication);
    }
    
    @GetMapping("/employer/stats")
    public ResponseEntity<?> getEmployerDashboardStats(Authentication authentication) {
        return appService.getEmployerStats(authentication);
    }

    @DeleteMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawApplication(@PathVariable int id, Authentication authentication) {
        return appService.withdrawApplication(id, authentication);
    }
}
