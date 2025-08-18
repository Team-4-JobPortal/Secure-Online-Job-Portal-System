package com.example.controller;

import com.example.entity.Application;
import com.example.entity.Job;
import com.example.entity.User;
import com.example.service.ApplicationService;
import com.example.service.JobService;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApplicationService applicationService;

 // Employer: Create Job
    @PostMapping("/postJob")
    public ResponseEntity<?> createJob(@RequestBody Job job, Authentication authentication) {
        // Get logged-in username from JWT authentication
        String email = authentication.getName();

        // Fetch user from DB
        User dbUser = userService.findByemail(email);

        if (dbUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not found!");
        }

        // Check role
        if (!"employer".equalsIgnoreCase(dbUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only Employer can post jobs!");
        }

        // Attach employer (user) to the job
        job.setUser(dbUser);

        jobService.createJob(job);

        return ResponseEntity.ok("Job created successfully!");
    }

    // Employer: Update Job
    @PutMapping("/{id}")
    public String updateJob(@PathVariable int id, @RequestBody Job updatedJob) {
        Job existingJob = jobService.getJobById(id);
        if (existingJob == null) {
            return "Job not found!";
        }
        updatedJob.setJob_id(id); // keep the same ID
        jobService.updateJob(updatedJob);
        return "Job updated successfully!";
    }

    // Employer: Delete Job
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
        return "Job deleted successfully!";
    }

    // Candidate: View All Jobs
    @GetMapping("/list")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

 // Candidate: Search Jobs
    @GetMapping("/search")
    public List<Job> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String minSalary,
            @RequestParam(required = false) String maxSalary) {
  				 
        return jobService.searchJobs(keyword, location, Integer.parseInt(minSalary), Integer.parseInt(maxSalary));
    }

    // Common: Get Job by ID
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable int id) {
        return jobService.getJobById(id);
    }
    
    @GetMapping("/currentLoginEmployerJobPosted/list")
    public List<Job> getMyJobs(Authentication authentication) {
        String email = authentication.getName(); // logged-in employer’s email
        return jobService.getJobsByEmployer(email);
    }
    
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<?> applyForJob(
            @PathVariable int jobId,
            @RequestBody Application request,
            Authentication authentication) {

        String email = authentication.getName();
        User dbUser = userService.findByemail(email);

        if (dbUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not found!");
        }

        // Only candidates can apply
        if (!"candidate".equalsIgnoreCase(dbUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only Candidates can apply for jobs!");
        }

        Job job = jobService.getJobById(jobId);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Job not found!");
        }

        // CHECK IF USER HAS ALREADY APPLIED TO THIS JOB
        boolean hasAlreadyApplied = applicationService.hasUserAppliedToJob(dbUser.getUser_id(), jobId);
        if (hasAlreadyApplied) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("You have already applied to this job!");
        }

        // Validate cover letter
        if (request.getCoverLetter() == null || request.getCoverLetter().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cover letter is required!");
        }

        Application application = new Application();
        application.setJob(job);
        application.setUser(dbUser);
        application.setCoverLetter(request.getCoverLetter().trim());
        application.setStatus("Pending"); // default status

        try {
            applicationService.saveApp(application);
            System.out.println("Application saved successfully for Job ID: " + jobId + ", User ID: " + dbUser.getUser_id());
            return ResponseEntity.ok("Application submitted successfully with status Pending!");
        } catch (Exception e) {
            System.err.println("Error saving application: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to submit application. Please try again.");
        }
    }

}
