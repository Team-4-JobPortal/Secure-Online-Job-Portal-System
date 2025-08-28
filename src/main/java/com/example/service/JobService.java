package com.example.service;

import com.example.dto.JobDto;
import com.example.entity.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface JobService {
    void createJob(Job job);
    Job getJobById(int id);
    List<Job> getAllJobs();
    void updateJob(Job job);
    void deleteJob(int id);
    List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary);
    List<Job> getJobsByEmployer(String email);
    List<Job> findJobsByEmployer(int userId);
    List<Job> getDeletedJobsByEmployer(int employerId);
    
    // New business logic methods
    ResponseEntity<?> createJobWithValidation(JobDto jobDto, Authentication authentication);
    ResponseEntity<?> deleteJobWithValidation(int id, Authentication authentication);
    ResponseEntity<?> getJobByIdWithValidation(int id);
    ResponseEntity<?> getDeletedJobsWithValidation(Authentication authentication);
}
