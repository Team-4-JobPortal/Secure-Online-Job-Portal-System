package com.example.repository;

import com.example.entity.Job;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for Job entity.
 * Provides CRUD operations and custom query methods for job management.
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    
    /**
     * Find all jobs that are not deleted
     * @return List of active jobs
     */
    @Query("SELECT j FROM Job j WHERE j.isDeleted = false")
    List<Job> findActiveJobs();
    
    /**
     * Find jobs by employer user
     * @param user the employer user
     * @return List of jobs posted by the employer
     */
    List<Job> findByUser(User user);
    
    /**
     * Find jobs by employer user ID
     * @param userId the employer user ID
     * @return List of jobs posted by the employer
     */
    @Query("SELECT j FROM Job j WHERE j.user.user_id = :userId AND j.isDeleted = false")
    List<Job> findByEmployerUserId(@Param("userId") int userId);
    
    /**
     * Find deleted jobs by employer user ID
     * @param userId the employer user ID
     * @return List of deleted jobs posted by the employer
     */
    @Query("SELECT j FROM Job j WHERE j.user.user_id = :userId AND j.isDeleted = true")
    List<Job> findDeletedJobsByEmployerUserId(@Param("userId") int userId);
    
    /**
     * Find jobs by employer email
     * @param email the employer email
     * @return List of jobs posted by the employer
     */
    @Query("SELECT j FROM Job j WHERE j.user.email = :email AND j.isDeleted = false")
    List<Job> findByEmployerEmail(@Param("email") String email);
    
    /**
     * Search jobs by multiple criteria
     * @param keyword search term for title or description
     * @param location job location
     * @param minSalary minimum salary
     * @param maxSalary maximum salary
     * @return List of jobs matching the search criteria
     */
    @Query("SELECT j FROM Job j WHERE j.isDeleted = false " +
           "AND (:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (:minSalary IS NULL OR j.min_salary >= :minSalary) " +
           "AND (:maxSalary IS NULL OR j.max_salary <= :maxSalary)")
    List<Job> searchJobs(@Param("keyword") String keyword, 
                        @Param("location") String location, 
                        @Param("minSalary") Integer minSalary, 
                        @Param("maxSalary") Integer maxSalary);
    
    /**
     * Find jobs by title containing the given string (case insensitive)
     * @param title the title to search for
     * @return List of jobs with matching titles
     */
    @Query("SELECT j FROM Job j WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%')) AND j.isDeleted = false")
    List<Job> findByTitleContainingIgnoreCase(@Param("title") String title);
    
    /**
     * Find jobs by location containing the given string (case insensitive)
     * @param location the location to search for
     * @return List of jobs in the specified location
     */
    @Query("SELECT j FROM Job j WHERE LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')) AND j.isDeleted = false")
    List<Job> findByLocationContainingIgnoreCase(@Param("location") String location);
}
