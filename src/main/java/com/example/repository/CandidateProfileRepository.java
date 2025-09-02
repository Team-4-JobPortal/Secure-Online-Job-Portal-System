package com.example.repository;

import com.example.entity.CandidateProfile;
import com.example.entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA Repository for CandidateProfile entity.
 * Provides CRUD operations and custom query methods for candidate profile management.
 */
@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Integer> {
    
    /**
     * Find candidate profile by user
     * @param user the user
     * @return the candidate profile
     */
    CandidateProfile findByUser(User user);
    
    /**
     * Find candidate profiles by experience level
     * @param experienceLevel the experience level
     * @return List of candidate profiles with the specified experience level
     */
    @Query("SELECT cp FROM CandidateProfile cp WHERE cp.experienceLevel = :experienceLevel")
    List<CandidateProfile> findByExperienceLevel(@Param("experienceLevel") String experienceLevel);
    
    /**
     * Find candidate profiles by current location containing the given string (case insensitive)
     * @param location the location to search for
     * @return List of candidate profiles in the specified location
     */
    @Query("SELECT cp FROM CandidateProfile cp WHERE LOWER(cp.currentLocation) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<CandidateProfile> findByCurrentLocationContainingIgnoreCase(@Param("location") String location);
    
    /**
     * Find candidate profiles by skills containing the given string (case insensitive)
     * @param skills the skills to search for
     * @return List of candidate profiles with the specified skills
     */
    @Query("SELECT cp FROM CandidateProfile cp WHERE LOWER(cp.skills) LIKE LOWER(CONCAT('%', :skills, '%'))")
    List<CandidateProfile> findBySkillsContainingIgnoreCase(@Param("skills") String skills);
    
    /**
     * Search candidate profiles by multiple criteria
     * @param location the location to search for
     * @param skills the skills to search for
     * @param experienceLevel the experience level
     * @return List of candidate profiles matching the search criteria
     */
    @Query("SELECT cp FROM CandidateProfile cp WHERE " +
           "(:location IS NULL OR LOWER(cp.currentLocation) LIKE LOWER(CONCAT('%', :location, '%'))) " +
           "AND (:skills IS NULL OR LOWER(cp.skills) LIKE LOWER(CONCAT('%', :skills, '%'))) " +
           "AND (:experienceLevel IS NULL OR cp.experienceLevel = :experienceLevel)")
    List<CandidateProfile> searchProfiles(@Param("location") String location, 
                                         @Param("skills") String skills, 
                                         @Param("experienceLevel") String experienceLevel);
}
