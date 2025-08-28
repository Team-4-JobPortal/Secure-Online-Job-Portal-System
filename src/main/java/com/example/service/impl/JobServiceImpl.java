package com.example.service.impl;

import com.example.dao.JobDao;
import com.example.dto.JobDto;
import com.example.entity.Job;
import com.example.entity.User;
import com.example.exception.JobNotFoundException;
import com.example.service.JobService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;
    
    @Autowired
    private UserService userService;

    @Override
    public void createJob(Job job) {
        jobDao.save(job);
    }

    @Override
    public Job getJobById(int id) {
        return jobDao.get(id);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobDao.list();
    }

    @Override
    public void updateJob(Job job) {
        jobDao.update(job);
    }

    @Override
    public void deleteJob(int id) {
        jobDao.delete(id);
    }

    @Override
    public List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary) {
        List<Job> jobs = jobDao.searchJobs(keyword, location, minSalary, maxSalary);
        if (jobs.isEmpty()) {
            throw new JobNotFoundException("No jobs found matching your criteria.");
        }
        return jobs;
    }

    @Override
    public List<Job> getJobsByEmployer(String email) {
        return jobDao.findByEmployerEmail(email);
    }

    @Override
    public List<Job> findJobsByEmployer(int userId) {
        return jobDao.findByEmployerUserid(userId);
    }

    @Override
    public List<Job> getDeletedJobsByEmployer(int employerId) {
        return jobDao.findDeletedJobsByEmployerUserid(employerId);
    }

    @Override
    public ResponseEntity<?> createJobWithValidation(JobDto jobDto, Authentication authentication) {
        String email = authentication.getName();
        User employer = userService.findByemail(email);
        if (employer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
        }
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
        
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
        }
        
        if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only employers can delete jobs!");
        }
        
        Job job = getJobById(id);
        if (job == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found!");
        }
        
        if (job.getUser().getUser_id() != currentUser.getUser_id()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own jobs!");
        }
        
        deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully!");
    }

    @Override
    public ResponseEntity<?> getJobByIdWithValidation(int id) {
        Job job = getJobById(id);
        if (job == null || job.isDeleted()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found!");
        }
        return ResponseEntity.ok(job);
    }

    @Override
    public ResponseEntity<?> getDeletedJobsWithValidation(Authentication authentication) {
        String email = authentication.getName();
        User currentUser = userService.findByemail(email);
        
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
        }
        
        if (!"employer".equalsIgnoreCase(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only employers can view job history!");
        }
        
        List<Job> deletedJobs = getDeletedJobsByEmployer(currentUser.getUser_id());
        return ResponseEntity.ok(deletedJobs);
    }
}
