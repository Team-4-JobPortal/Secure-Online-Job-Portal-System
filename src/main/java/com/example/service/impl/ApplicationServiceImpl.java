package com.example.service.impl;

import com.example.dto.ApplicationDto;
import com.example.entity.Application;
import com.example.entity.Job;
import com.example.entity.User;
import com.example.repository.ApplicationRepository;
import com.example.service.ApplicationService;
import com.example.service.JobService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service implementation for Application management using Spring Data JPA repositories.
 * Handles job application creation, retrieval, updating, and status management.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JobService jobService;

    @Override
    @Transactional(readOnly = true)
    public List<Application> findAllApp() {
        return applicationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Application findAppById(int id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public Application saveApp(int jobId, ApplicationDto request, Authentication authentication) {
        String email = authentication.getName();
        User dbUser = userService.findByemail(email);
        
        if (!"candidate".equalsIgnoreCase(dbUser.getRole())) {
            throw new RuntimeException("Only Candidates can apply for jobs!");
        }
        
        Job job = jobService.getJobById(jobId);
        if (job == null) {
            throw new RuntimeException("Job not found!");
        }
        
        if (hasUserAppliedToJob(dbUser.getUser_id(), jobId)) {
            throw new RuntimeException("Already applied to this job!");
        }
        
        Application application = new Application();
        application.setJob(job);
        application.setUser(dbUser);
        application.setCoverLetter(request.getCoverLetter().trim());
        application.setStatus("Pending");
        
        return applicationRepository.save(application);
    }

    @Override
    public void updateApp(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public void deleteApp(int id) {
        applicationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> findApplicationsByUser(int userId) {
        return applicationRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserAppliedToJob(int userId, int jobId) {
        return applicationRepository.existsByUserIdAndJobId(userId, jobId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> findApplicationsByEmployer(int employerId) {
        return applicationRepository.findApplicationsByEmployer(employerId);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getMyApplications(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);
        
        if (!"candidate".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only candidates can view applications!");
        }
        
        List<Application> userApplications = findApplicationsByUser(currentUser.getUser_id());
        return ResponseEntity.ok(userApplications);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getApplicationsForMyJobs(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);
        
        if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only employers can view applications for their jobs!");
        }
        
        List<Application> jobApplications = findApplicationsByEmployer(currentUser.getUser_id());
        return ResponseEntity.ok(jobApplications);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCandidateStats(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);
        
        if (!"candidate".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only candidates can view application stats!");
        }
        
        List<Application> userApplications = findApplicationsByUser(currentUser.getUser_id());
        
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalApplications", userApplications.size());
        stats.put("pendingApplications", (int) userApplications.stream()
                .filter(app -> "Pending".equalsIgnoreCase(app.getStatus())).count());
        stats.put("shortlistedApplications", (int) userApplications.stream()
                .filter(app -> "Accepted".equalsIgnoreCase(app.getStatus())).count());
        stats.put("rejectedApplications", (int) userApplications.stream()
                .filter(app -> "Rejected".equalsIgnoreCase(app.getStatus())).count());
        
        return ResponseEntity.ok(stats);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getEmployerStats(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);
        
        if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only employers can view these stats!");
        }
        
        List<Job> jobsPosted = jobService.findJobsByEmployer(currentUser.getUser_id());
        int totalJobs = jobsPosted.size();
        
        List<Application> allApplications = findApplicationsByEmployer(currentUser.getUser_id());
        int totalApplications = allApplications.size();
        
        int pendingApplications = (int) allApplications.stream()
                .filter(app -> "Pending".equalsIgnoreCase(app.getStatus()))
                .count();
        
        int hiredCandidates = (int) allApplications.stream()
                .filter(app -> "Accepted".equalsIgnoreCase(app.getStatus()))
                .count();
        
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalJobs", totalJobs);
        stats.put("totalApplications", totalApplications);
        stats.put("pendingApplications", pendingApplications);
        stats.put("hiredCandidates", hiredCandidates);
        
        return ResponseEntity.ok(stats);
    }

    @Override
    public ResponseEntity<?> updateApplicationStatus(int id, String status, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);
        
        if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only employers can update application status!");
        }
        
        Optional<Application> applicationOpt = applicationRepository.findById(id);
        if (applicationOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Application not found!");
        }
        
        Application application = applicationOpt.get();
        if (application.getJob().getUser().getUser_id() != currentUser.getUser_id()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can only update applications for your own jobs!");
        }
        
        if (!"Pending".equalsIgnoreCase(application.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You can only update pending applications!");
        }
        
        if (!"accepted".equalsIgnoreCase(status) && !"rejected".equalsIgnoreCase(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Status must be either 'accepted' or 'rejected'!");
        }
        
        String newStatus = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        application.setStatus(newStatus);
        
        updateApp(application);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Application status updated successfully!");
        response.put("newStatus", newStatus);
        
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> withdrawApplication(int id, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);
        
        Optional<Application> applicationOpt = applicationRepository.findById(id);
        if (applicationOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Application not found!");
        }
        
        Application application = applicationOpt.get();
        if (application.getUser().getUser_id() != currentUser.getUser_id()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can only withdraw your own applications!");
        }
        
        if (!"Pending".equalsIgnoreCase(application.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You can only withdraw pending applications!");
        }
        
        deleteApp(id);
        return ResponseEntity.ok("Application withdrawn successfully!");
    }
}