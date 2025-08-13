package com.example.controller;

import com.example.entity.Job;
import com.example.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Employer: Create Job
    @PostMapping
    public String createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return "Job created successfully!";
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
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // Candidate: Search Jobs
    @GetMapping("/search")
    public List<Job> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer maxSalary) {
        return jobService.searchJobs(keyword, location, minSalary, maxSalary);
    }

    // Common: Get Job by ID
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable int id) {
        return jobService.getJobById(id);
    }
}
