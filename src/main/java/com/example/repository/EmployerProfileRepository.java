package com.example.repository;

import com.example.entity.EmployerProfile;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for EmployerProfile entity.
 * Provides CRUD operations and custom query methods for employer profile management.
 */
@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, Integer> {
    
    /**
     * Find employer profile by user
     * @param user the user
     * @return the employer profile
     */
    EmployerProfile findByUser(User user);
    
    /**
     * Find employer profiles by company name containing the given string (case insensitive)
     * @param companyName the company name to search for
     * @return List of employer profiles with matching company names
     */
    @Query("SELECT ep FROM EmployerProfile ep WHERE LOWER(ep.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))")
    List<EmployerProfile> findByCompanyNameContainingIgnoreCase(@Param("companyName") String companyName);
    
    /**
     * Find employer profiles by current profile containing the given string (case insensitive)
     * @param currentProfile the current profile to search for
     * @return List of employer profiles with matching current profiles
     */
    @Query("SELECT ep FROM EmployerProfile ep WHERE LOWER(ep.currentProfile) LIKE LOWER(CONCAT('%', :currentProfile, '%'))")
    List<EmployerProfile> findByCurrentProfileContainingIgnoreCase(@Param("currentProfile") String currentProfile);
    
    /**
     * Search employer profiles by multiple criteria
     * @param companyName the company name to search for
     * @param currentProfile the current profile to search for
     * @return List of employer profiles matching the search criteria
     */
    @Query("SELECT ep FROM EmployerProfile ep WHERE " +
           "(:companyName IS NULL OR LOWER(ep.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))) " +
           "AND (:currentProfile IS NULL OR LOWER(ep.currentProfile) LIKE LOWER(CONCAT('%', :currentProfile, '%')))")
    List<EmployerProfile> searchProfiles(@Param("companyName") String companyName, 
                                        @Param("currentProfile") String currentProfile);
}
