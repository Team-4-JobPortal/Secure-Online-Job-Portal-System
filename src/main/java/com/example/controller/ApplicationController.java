package com.example.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Application;
import com.example.entity.User;
import com.example.service.ApplicationService;
import com.example.service.UserService;
 
@RestController
@RequestMapping("/applications")
public class ApplicationController {
    
    @Autowired
    private ApplicationService appService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    public List<Application> listUsers() {
        return appService.findAllApp();
    }
    
    @GetMapping("/{id}")
    public Application GetByAppId(@PathVariable int id) {
    	return appService.findAppById(id);
    }
 
    @PostMapping("/apply")
    public void saveApp(@RequestBody Application app) {
    	
    	appService.saveApp(app);
    }
    
    
    @PutMapping("/{id}")
    public void updateApp(@PathVariable int id, @RequestBody Application app) {
    	app.setApplication_id(id);
        appService.updateApp(app);
    }
    
    
    @DeleteMapping("/{id}")
    public void deleteApp(@PathVariable int id) {
    	appService.deleteApp(id);
    }
    
    // Get current user's applications (for candidate dashboard)
    @GetMapping("/my-applications")
    public ResponseEntity<?> getMyApplications(Authentication authentication) {
        try {
            String email = authentication.getName();
            User currentUser = userService.findByemail(email);
            
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User not found!");
            }
            
            // Only candidates should access this endpoint
            if (!"candidate".equalsIgnoreCase(currentUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only candidates can view applications!");
            }
            
            List<Application> userApplications = appService.findApplicationsByUser(currentUser.getUser_id());
            return ResponseEntity.ok(userApplications);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching applications: " + e.getMessage());
        }
    }

    // Get dashboard statistics for current user
//    @GetMapping("/stats")
//    public ResponseEntity<?> getDashboardStats(Authentication authentication) {
//        try {
//            String email = authentication.getName();
//            User currentUser = userService.findByemail(email);
//            
//            if (currentUser == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body("User not found!");
//            }
//            
//            if (!"candidate".equalsIgnoreCase(currentUser.getRole())) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body("Only candidates can view application stats!");
//            }
//            
//     //       List<Application> userApplications = appService.findApplicationsByUser(currentUser.getUser_id());
//            
//            Map<String, Integer> stats = new HashMap<>();
//            stats.put("totalApplications", userApplications.size());
//            stats.put("pendingApplications", (int) userApplications.stream()
//                    .filter(app -> "Pending".equalsIgnoreCase(app.getStatus())).count());
//            stats.put("shortlistedApplications", (int) userApplications.stream()
//                    .filter(app -> "Shortlisted".equalsIgnoreCase(app.getStatus())).count());
//            stats.put("jobsViewed", 0); // You can implement job view tracking separately
//            
//            return ResponseEntity.ok(stats);
//            
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error fetching stats: " + e.getMessage());
//        }
//    }

    // Check if user has already applied to a specific job
    @GetMapping("/check-applied/{jobId}")
    public ResponseEntity<?> checkIfApplied(@PathVariable int jobId, Authentication authentication) {
        try {
            String email = authentication.getName();
            User currentUser = userService.findByemail(email);
            
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User not found!");
            }
            
           boolean hasApplied = appService.hasUserAppliedToJob(currentUser.getUser_id(), jobId);
            
            Map<String, Boolean> response = new HashMap<>();
            response.put("hasApplied", hasApplied);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking application status: " + e.getMessage());
        }
    }

    // Withdraw application
    @DeleteMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawApplication(@PathVariable int id, Authentication authentication) {
        try {
            String email = authentication.getName();
            User currentUser = userService.findByemail(email);
            
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User not found!");
            }
            
            Application application = appService.findAppById(id);
            if (application == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Application not found!");
            }
            
            // Check if this application belongs to the current user
            if (application.getUser().getUser_id() != currentUser.getUser_id()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You can only withdraw your own applications!");
            }
            
            // Only allow withdrawal of pending applications
            if (!"Pending".equalsIgnoreCase(application.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You can only withdraw pending applications!");
            }
            
            appService.deleteApp(id);
            return ResponseEntity.ok("Application withdrawn successfully!");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error withdrawing application: " + e.getMessage());
        }
    }

}