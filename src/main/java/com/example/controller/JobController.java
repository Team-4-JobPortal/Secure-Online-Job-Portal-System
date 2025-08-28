package com.example.controller;

import com.example.dto.ApplicationDto;
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

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/jobs")
public class JobController {

	@Autowired
	private JobService jobService;

	

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService applicationService;


	 

	// Employer: Post Job
	@PostMapping("/postJob")
	@ResponseBody
	public ResponseEntity<?> createJob(@Valid @RequestBody Job job, Authentication authentication) {
		String email = authentication.getName();
		User dbUser = userService.findByemail(email);
		if (dbUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
		}

		// Check role
		if (!"employer".equalsIgnoreCase(dbUser.getRole())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Employer can post jobs!");
		}

		job.setUser(dbUser);
		job.setCreatedAt(LocalDateTime.now());
		// save job
		jobService.createJob(job);
		System.out.println("Job posted successfully");
		return ResponseEntity.ok("Job posted successfully");
	}

	 // Employer: Soft Delete Job
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable int id, Authentication authentication) {
        try {
            String email = authentication.getName();
            User currentUser = userService.findByemail(email);
            
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
            }

            if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only employers can delete jobs!");
            }

            // Verify job ownership before deletion
            Job job = jobService.getJobById(id);
            if (job == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found!");
            }

            if (job.getUser().getUser_id() != currentUser.getUser_id()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own jobs!");
            }

            jobService.deleteJob(id); // This will soft delete
            return ResponseEntity.ok("Job deleted successfully!");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting job: " + e.getMessage());
        }
    }

	

 // Candidate: View All Jobs
    @GetMapping("/list")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs(); 
        // Only returns non-deleted jobs
    }

	// Candidate: Search Jobs
	@GetMapping("/search")
	public List<Job> searchJobs(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String location, @RequestParam(required = false) String minSalary,
			@RequestParam(required = false) String maxSalary) {

		// fixing the default value if the user send data
		// input is string is emtpy then initailing with zero
		int min = (minSalary.isEmpty()) ? 0 : Integer.parseInt(minSalary);
		int max = (maxSalary.isEmpty()) ? 0 : Integer.parseInt(maxSalary);

		return jobService.searchJobs(keyword, location, min, max);
	}

	// Common: Get Job by ID
	 @GetMapping("/{id}")
	    public ResponseEntity<?> getJobById(@PathVariable int id) {
	        Job job = jobService.getJobById(id);
	        if (job == null || job.isDeleted()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found!");
	        }
	        return ResponseEntity.ok(job);
	    }
	 
	 
	 
	// list all Employer job posts
	@GetMapping("/currentLoginEmployerJobPosted/list")
	public List<Job> getMyJobs(Authentication authentication) {
		String email = authentication.getName(); // logged-in employer’s email
		return jobService.getJobsByEmployer(email);
	}
	
	// Get deleted jobs (history) for employer
    @GetMapping("/history")
    public ResponseEntity<?> getDeletedJobs(Authentication authentication) {
        try {
            String email = authentication.getName();
            User currentUser = userService.findByemail(email);
            
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
            }

            if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only employers can view job history!");
            }

            // Get deleted jobs for this employer
            List<Job> deletedJobs = jobService.getDeletedJobsByEmployer(currentUser.getUser_id());
            return ResponseEntity.ok(deletedJobs);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching job history: " + e.getMessage());
        }
    }
    

 // Candidate: Apply for job
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<?> applyForJob(@PathVariable int jobId, @Valid @RequestBody ApplicationDto request,
            Authentication authentication) {
        try {
            // Check if job exists and is not deleted
            Job job = jobService.getJobById(jobId);
            if (job == null || job.isDeleted()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found or no longer available!");
            }

            Application application = applicationService.saveApp(jobId, request, authentication);
            return ResponseEntity.ok("Application submitted successfully with status " + application.getStatus());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
