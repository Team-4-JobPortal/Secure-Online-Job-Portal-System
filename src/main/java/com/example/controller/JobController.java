package com.example.controller;

import com.example.dto.ApplicationDto;
import com.example.dto.JobDto;
import com.example.entity.Job;
import com.example.service.ApplicationService;
import com.example.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/postJob")
    public ResponseEntity<?> createJob(@Valid @RequestBody JobDto jobDto, Authentication authentication) {
        return jobService.createJobWithValidation(jobDto, authentication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable int id, Authentication authentication) {
        return jobService.deleteJobWithValidation(id, authentication);
    }

    @GetMapping("/list")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam(required = false) String keyword,
                               @RequestParam(required = false) String location, 
                               @RequestParam(required = false) String minSalary,
                               @RequestParam(required = false) String maxSalary) {
        int min = (minSalary == null || minSalary.isEmpty()) ? 0 : Integer.parseInt(minSalary);
        int max = (maxSalary == null || maxSalary.isEmpty()) ? 0 : Integer.parseInt(maxSalary);
        return jobService.searchJobs(keyword, location, min, max);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable int id) {
        return jobService.getJobByIdWithValidation(id);
    }

    @GetMapping("/currentLoginEmployerJobPosted/list")
    public List<Job> getMyJobs(Authentication authentication) {
        String email = authentication.getName();
        return jobService.getJobsByEmployer(email);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getDeletedJobs(Authentication authentication) {
        return jobService.getDeletedJobsWithValidation(authentication);
    }

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
