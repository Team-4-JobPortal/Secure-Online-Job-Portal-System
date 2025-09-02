package com.example.repository;

import com.example.entity.Application;
import com.example.entity.Job;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Application entity.
 * Provides CRUD operations and custom query methods for job application management.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    
    /**
     * Find all applications by user (candidate)
     * @param user the candidate user
     * @return List of applications submitted by the candidate
     */
    List<Application> findByUser(User user);
    
    /**
     * Find all applications by user ID (candidate)
     * @param userId the candidate user ID
     * @return List of applications submitted by the candidate
     */
    @Query("SELECT a FROM Application a WHERE a.user.user_id = :userId")
    List<Application> findByUserId(@Param("userId") int userId);
    
    /**
     * Find all applications for a specific job
     * @param job the job
     * @return List of applications for the job
     */
    List<Application> findByJob(Job job);
    
    /**
     * Find all applications for a specific job ID
     * @param jobId the job ID
     * @return List of applications for the job
     */
    @Query("SELECT a FROM Application a WHERE a.job.job_id = :jobId")
    List<Application> findByJobId(@Param("jobId") int jobId);
    
    /**
     * Check if an application exists for a specific user and job
     * @param userId the user ID
     * @param jobId the job ID
     * @return true if application exists, false otherwise
     */
    @Query("SELECT COUNT(a) > 0 FROM Application a WHERE a.user.user_id = :userId AND a.job.job_id = :jobId")
    boolean existsByUserIdAndJobId(@Param("userId") int userId, @Param("jobId") int jobId);
    
    /**
     * Find all applications for jobs posted by a specific employer
     * @param employerId the employer user ID
     * @return List of applications for jobs posted by the employer
     */
    @Query("SELECT a FROM Application a WHERE a.job.user.user_id = :employerId")
    List<Application> findApplicationsByEmployer(@Param("employerId") int employerId);
    
    /**
     * Find applications by status
     * @param status the application status
     * @return List of applications with the specified status
     */
    List<Application> findByStatus(String status);
    
    /**
     * Find applications by user and status
     * @param user the candidate user
     * @param status the application status
     * @return List of applications with the specified status for the user
     */
    List<Application> findByUserAndStatus(User user, String status);
    
    /**
     * Find applications by job and status
     * @param job the job
     * @param status the application status
     * @return List of applications with the specified status for the job
     */
    List<Application> findByJobAndStatus(Job job, String status);
    
    /**
     * Count applications for a specific job
     * @param jobId the job ID
     * @return number of applications for the job
     */
    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.job_id = :jobId")
    long countByJobId(@Param("jobId") int jobId);
    
    /**
     * Count applications by status for a specific job
     * @param jobId the job ID
     * @param status the application status
     * @return number of applications with the specified status for the job
     */
    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.job_id = :jobId AND a.status = :status")
    long countByJobIdAndStatus(@Param("jobId") int jobId, @Param("status") String status);
}