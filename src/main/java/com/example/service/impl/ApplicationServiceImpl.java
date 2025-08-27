package com.example.service.impl;
 
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dao.ApplicationDAO;
import com.example.dto.ApplicationDto;
import com.example.entity.Application;
import com.example.entity.Job;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.service.ApplicationService;
import com.example.service.JobService;
 
@Service
public class ApplicationServiceImpl implements ApplicationService{
@Autowired
private ApplicationDAO applicationDAO;

@Autowired
private UserService userService;

@Autowired
private JobService jobService;
 
@Override
public List<Application> findAllApp() {
	// TODO Auto-generated method stub
	return applicationDAO.findAll();
}
 
@Override
public Application findAppById(int id) {
	// TODO Auto-generated method stub
	return applicationDAO.findById(id);
}

// used by candidate to apply for a job with cv
//@Override
//public ResponseEntity<?> saveApp(int jobId,
//        ApplicationDto request,
//        Authentication authentication) {
//	// TODO Auto-generated method stub
//	String email = authentication.getName();
//    User dbUser = userService.findByemail(email);
//
//    if (dbUser == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body("User not found!");
//    }
//
//    // Only candidates can apply
//    if (!"candidate".equalsIgnoreCase(dbUser.getRole())) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body("Only Candidates can apply for jobs!");
//    }
//
//    Job job = jobService.getJobById(jobId);
//    if (job == null) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("Job not found!");
//    }
//
//    // CHECK IF USER HAS ALREADY APPLIED TO THIS JOB
//    boolean hasAlreadyApplied = hasUserAppliedToJob(dbUser.getUser_id(), jobId);
//    if (hasAlreadyApplied) {
//        return ResponseEntity.status(HttpStatus.CONFLICT)
//                .body("You have already applied to this job!");
//    }
//
//    Application application = new Application();
//    application.setJob(job);
//    application.setUser(dbUser);
//    application.setCoverLetter(request.getCoverLetter().trim());
//    application.setStatus("Pending"); // default status
//
//    try {
//    	applicationDAO.save(application);
//        System.out.println("Application saved successfully for Job ID: " + jobId + ", User ID: " + dbUser.getUser_id());
//        return ResponseEntity.ok("Application submitted successfully with status Pending!");
//    } catch (Exception e) {
//        System.err.println("Error saving application: " + e.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Failed to submit application. Please try again.");
//    }
//	
//}

@Override
public Application saveApp(int jobId, ApplicationDto request, Authentication authentication) {
    String email = authentication.getName();
    User dbUser = userService.findByemail(email);

    if (dbUser == null) {
        throw new RuntimeException("User not found!");
    }

    if (!"candidate".equalsIgnoreCase(dbUser.getRole())) {
        throw new RuntimeException("Only Candidates can apply for jobs!");
    }

    Job job = jobService.getJobById(jobId);
    if (job == null) {
        throw new RuntimeException("Job not found!");
    }

    if (hasUserAppliedToJob(dbUser.getUser_id(), jobId)) {
        throw new RuntimeException("Already applied to this job!");
    }

    Application application = new Application();
    application.setJob(job);
    application.setUser(dbUser);
    application.setCoverLetter(request.getCoverLetter().trim());
    application.setStatus("Pending");

    applicationDAO.save(application);
    return application;
}

 
@Override
public void updateApp(Application application) {
	// TODO Auto-generated method stub
	applicationDAO.update(application);
}
 
@Override
public void deleteApp(int id) {
	// TODO Auto-generated method stub
	applicationDAO.delete(id);
}

@Override
public List<Application> findApplicationsByUser(int userId) {
    return applicationDAO.findByUserId(userId);
}

// NEW: Check if user has already applied to a job
@Override
public boolean hasUserAppliedToJob(int userId, int jobId) {
    return applicationDAO.existsByUserIdAndJobId(userId, jobId);
}
 
@Override
public List<Application> findApplicationsByEmployer(int employerId) {
    return applicationDAO.findApplicationsByEmployer(employerId);
}
}