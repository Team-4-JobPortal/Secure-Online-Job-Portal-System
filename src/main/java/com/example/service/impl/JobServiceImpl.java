package com.example.service.impl;

import com.example.dto.JobDto;
import com.example.entity.Job;
import com.example.entity.User;
import com.example.exception.JobNotFoundException;
import com.example.exception.NegativeSalaryException;
import com.example.repository.JobRepository;
import com.example.service.JobService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for Job management using Spring Data JPA repositories.
 * Handles job creation, retrieval, updating, deletion, and search operations.
 */
@Service
@Transactional
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Job getJobById(int id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> getAllJobs() {
        return jobRepository.findActiveJobs();
    }

    @Override
    public void updateJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public void deleteJob(int id) {
        jobRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary) {
        if (minSalary != null && minSalary < 0) {
            throw new NegativeSalaryException("MinSalary should not be negative.");
        }
        if (maxSalary != null && maxSalary < 0) {
            throw new NegativeSalaryException("MaxSalary should not be negative.");
        }
        
        List<Job> jobs = jobRepository.searchJobs(keyword, location, minSalary, maxSalary);
        
        if (jobs.isEmpty()) {
            throw new JobNotFoundException("No jobs found matching your criteria.");
        }
        return jobs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> getJobsByEmployer(String email) {
        return jobRepository.findByEmployerEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> findJobsByEmployer(int userId) {
        return jobRepository.findByEmployerUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> getDeletedJobsByEmployer(int employerId) {
        return jobRepository.findDeletedJobsByEmployerUserId(employerId);
    }

    @Override
    public ResponseEntity<?> createJobWithValidation(JobDto jobDto, Authentication authentication) {
        String email = authentication.getName();
        User employer = userService.findByemail(email);
        
        if (!"employer".equalsIgnoreCase(employer.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Employer can post jobs!");
        }

        Job job = new Job();
        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setLocation(jobDto.getLocation());
        job.setMin_salary(jobDto.getMin_salary());
        job.setMax_salary(jobDto.getMax_salary());
        job.setDeadline(jobDto.getDeadline());
        job.setDeleted(jobDto.getIsDeleted() != null ? jobDto.getIsDeleted() : false);
        job.setUser(employer);
        job.setCreatedAt(LocalDateTime.now());

        createJob(job);
        return ResponseEntity.ok("Job posted successfully");
    }

    @Override
    public ResponseEntity<?> deleteJobWithValidation(int id, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);

        if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only employers can delete jobs!");
        }

        Optional<Job> jobOpt = jobRepository.findById(id);
        if (jobOpt.isEmpty()) {  // ✅ Correct logic
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found!");
        }

        
        Job job = jobOpt.get();
        if (job.getUser().getUser_id() != currentUser.getUser_id()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own jobs!");
        }

        deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully!");
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getJobByIdWithValidation(int id) {
        Optional<Job> jobOpt = jobRepository.findById(id);
        if (jobOpt.isEmpty()) {  // ✅ Correct logic
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found!");
        }
        return ResponseEntity.ok(jobOpt.get());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getDeletedJobsWithValidation(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);

        if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only employers can view job history!");
        }

        List<Job> deletedJobs = getDeletedJobsByEmployer(currentUser.getUser_id());
        return ResponseEntity.ok(deletedJobs);
    }
}