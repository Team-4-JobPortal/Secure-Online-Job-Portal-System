package com.example.controller;

import com.example.dto.ApplicationDto;
import com.example.dto.JobDto;
import com.example.entity.Application;
import com.example.entity.Job;
import com.example.entity.JobHistory;
import com.example.entity.User;
import com.example.service.ApplicationService;
import com.example.service.JobHistoryService;
import com.example.service.JobService;
import com.example.service.UserService;
import com.example.service.impl.JobHistoryServiceImpl;
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

	private final JobHistoryServiceImpl jobHistoryServiceImpl;

	@Autowired
	private JobService jobService;

	@Autowired
	private JobHistoryService jobHistoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService applicationService;

	
	JobController(JobHistoryServiceImpl jobHistoryServiceImpl) {
		
	this.jobHistoryServiceImpl = jobHistoryServiceImpl; 
	}
	 

	// Employer: Post Job
	@PostMapping("/postJob")
	@ResponseBody
	public ResponseEntity<?> createJob(@Valid @RequestBody JobDto job, Authentication authentication) {
		String email = authentication.getName();
		User dbUser = userService.findByemail(email);
		if (dbUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
		}

		// Check role
		if (!"employer".equalsIgnoreCase(dbUser.getRole())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Employer can post jobs!");
		}
		Job j=new Job();
		j.setTitle(job.getTitle());
		j.setDescription(job.getDescription());
		j.setLocation(job.getLocation());
		j.setMin_salary(job.getMin_salary());
		j.setMax_salary(job.getMax_salary());
		j.setDeadline(job.getDeadline());
		j.setUser(dbUser);
		j.setCreatedAt(LocalDateTime.now());
		// save job
		jobService.createJob(j);
		System.out.println("Job posted successfully");
		return ResponseEntity.ok("Job posted successfully");
	}

	// Employer: Delete Job
	@DeleteMapping("/{id}")
	public String deleteJob(@PathVariable int id) {
		jobService.deleteJob(id);

		return "Job deleted successfully!";
	}

	@GetMapping("/history")
	public List<JobHistory> getAllJobHistories() {
		return jobHistoryService.list();
	}

	// Candidate: View All Jobs
	@GetMapping("/list")
	public List<Job> getAllJobs() {
		return jobService.getAllJobs();
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
	public Job getJobById(@PathVariable int id) {
		return jobService.getJobById(id);
	}
	// list all Employer job posts
	@GetMapping("/currentLoginEmployerJobPosted/list")
	public List<Job> getMyJobs(Authentication authentication) {
		String email = authentication.getName(); // logged-in employer’s email
		return jobService.getJobsByEmployer(email);
	}
	// candidate job apply
	@PostMapping("/{jobId}/apply")
	public ResponseEntity<?> applyForJob(@PathVariable int jobId, @Valid @RequestBody ApplicationDto request,
			Authentication authentication) {
		try {
			Application application = applicationService.saveApp(jobId, request, authentication);
			return ResponseEntity.ok("Application submitted successfully with status " + application.getStatus());
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
