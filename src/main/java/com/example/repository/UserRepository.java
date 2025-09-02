package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for User entity.
 * Provides CRUD operations and custom query methods for user management.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /**
     * Find user by email address
     * @param email the email address to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users by role
     * @param role the role to filter by
     * @return List of users with the specified role
     */
    List<User> findByRole(String role);
    
    /**
     * Check if a user exists with the given email
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Find users by first name containing the given string (case insensitive)
     * @param firstName the first name to search for
     * @return List of users matching the criteria
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))")
    List<User> findByFirstNameContainingIgnoreCase(@Param("firstName") String firstName);
    
    /**
     * Find users by last name containing the given string (case insensitive)
     * @param lastName the last name to search for
     * @return List of users matching the criteria
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))")
    List<User> findByLastNameContainingIgnoreCase(@Param("lastName") String lastName);
}
