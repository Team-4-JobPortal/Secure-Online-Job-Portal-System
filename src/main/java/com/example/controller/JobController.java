package com.example.controller;

import com.example.dto.ApplicationDto;
import com.example.dto.JobDto;
import com.example.entity.Job;
import com.example.service.ApplicationService;
import com.example.service.JobService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ApplicationService applicationService;

    /* 
     * Creates a new job posting with validation.
     * Only authenticated employers can post jobs.
     * @param jobDto JobDto containing job details like title, description, salary, deadline
     * @param authentication Spring Security authentication context containing employer details
     * @return ResponseEntity indicating successful job creation or validation errors
     */
    @PostMapping("/postJob")
    public ResponseEntity<?> createJob(@Valid @RequestBody JobDto jobDto, Authentication authentication) {
        return jobService.createJobWithValidation(jobDto, authentication);
    }

    /* 
     * Soft deletes a job posting by marking it as deleted.
     * Only the job owner can delete their own jobs.
     * @param id The unique identifier of the job to delete
     * @param authentication Spring Security authentication context containing employer details
     * @return ResponseEntity indicating success or failure of deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable int id, Authentication authentication) {
        return jobService.deleteJobWithValidation(id, authentication);
    }

    /* 
     * Retrieves all active (non-deleted) job postings.
     * Job descriptions are hidden for expired jobs (past deadline).
     * @return List<Job> containing all active job postings
     */
    @GetMapping("/list")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    /* 
     * Searches for jobs based on keyword, location, and salary range criteria.
     * All parameters are optional for flexible searching.
     * @param keyword Optional search term for job title or description
     * @param location Optional location filter
     * @param minSalary Optional minimum salary filter (converted to int)
     * @param maxSalary Optional maximum salary filter (converted to int)
     * @return List<Job> matching the search criteria
     */
    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String location, 
                               @RequestParam(required = false) String minSalary,
                               @RequestParam(required = false) String maxSalary) {
        int min = (minSalary == null || minSalary.isEmpty()) ? 0 : Integer.parseInt(minSalary);
        int max = (maxSalary == null || maxSalary.isEmpty()) ? 0 : Integer.parseInt(maxSalary);
        return jobService.searchJobs(keyword, location, min, max);
    }

    /* 
     * Retrieves a specific job by its ID with validation.
     * Returns 404 if job not found or has been deleted.
     * @param id The unique identifier of the job to retrieve
     * @return ResponseEntity with job details or error message
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable int id) {
        return jobService.getJobByIdWithValidation(id);
    }

    /* 
     * Retrieves all jobs posted by the currently authenticated employer.
     * @param authentication Spring Security authentication context containing employer details
     * @return List<Job> containing employer's posted jobs
     */
    @GetMapping("/currentLoginEmployerJobPosted/list")
    public List<Job> getMyJobs(Authentication authentication) {
        String email = authentication.getName();
        return jobService.getJobsByEmployer(email);
    }

    /* 
     * Retrieves deleted jobs history for the authenticated employer.
     * Shows previously posted jobs that have been soft-deleted.
     * @param authentication Spring Security authentication context containing employer details
     * @return ResponseEntity with deleted jobs list or error message
     */
    @GetMapping("/history")
    public ResponseEntity<?> getDeletedJobs(Authentication authentication) {
        return jobService.getDeletedJobsWithValidation(authentication);
    }

    /* 
     * Submits a job application for a specific job posting.
     * Validates job availability and prevents duplicate applications.
     * @param jobId The unique identifier of the job to apply for
     * @param request ApplicationDto containing cover letter and application details
     * @param authentication Spring Security authentication context containing candidate details
     * @return ResponseEntity indicating successful application submission or error
     */
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<?> applyForJob(@PathVariable int jobId, 
                                       @Valid @RequestBody ApplicationDto request,
                                       Authentication authentication) {
        try {
            Job job = jobService.getJobById(jobId);
            if (job == null || job.isDeleted()) {
                return ResponseEntity.status(404).body("Job not found or no longer available!");
            }

            applicationService.saveApp(jobId, request, authentication);
            return ResponseEntity.ok("Application submitted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
