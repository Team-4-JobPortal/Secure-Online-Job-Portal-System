package com.example.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.dao.UserDAO;
import com.example.entity.User;
import com.example.exception.UserEmailExistException;
import com.example.exception.UserEmailNotFoundException;
import com.example.helper.HibernateUtil;

@Repository
public class UserDAOImpl implements UserDAO {

    private final PasswordEncoder passwordEncoder;
    private final HibernateUtil hibernateUtil;

    @Autowired
    public UserDAOImpl(PasswordEncoder passwordEncoder, HibernateUtil hibernateUtil) {
        this.passwordEncoder = passwordEncoder;
        this.hibernateUtil = hibernateUtil;
    }

    /* 
     * Retrieves all User entities from the database.
     * Uses read-only session for optimal performance.
     * @return List<User> containing all registered users
     */
    @Override
    public List<User> findAll() {
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery("FROM User", User.class).list()
        );
    }

    /* 
     * Finds a specific User entity by its unique identifier.
     * Uses read-only session as this is a query operation.
     * @param id The unique identifier of the user
     * @return User entity if found, null if not found
     */
    @Override
    public User findById(int id) {
        return hibernateUtil.executeReadOnly(session ->
            session.get(User.class, id)
        );
    }

    /* 
     * Retrieves all users with a specific role (e.g., 'candidate' or 'employer').
     * Useful for role-based user management and statistics.
     * @param role The role to filter users by
     * @return List<User> containing users with the specified role
     */
    @Override
    public List<User> findByRole(String role) {
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery("FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", role)
                .getResultList()
        );
    }

    /* 
     * Private helper method to check if an email address already exists.
     * Prevents duplicate email registrations during user creation.
     * @param email The email address to check
     * @return boolean true if email exists, false otherwise
     */
    private boolean emailExists(String email) {
        String hql = "SELECT 1 FROM User WHERE email = :email";
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery(hql)
                .setParameter("email", email)
                .uniqueResult() != null
        );
    }

    /* 
     * Persists a new User entity to the database with validation and encryption.
     * Performs email uniqueness check, password encoding, and profile association.
     * Handles both candidate and employer profile creation based on user role.
     * @param user The User entity to be saved
     * @throws UserEmailExistException if email address is already registered
     */
    @Override
    public void save(User user) {
        hibernateUtil.executeInTransaction(session -> {
            if (emailExists(user.getEmail())) {
                throw new UserEmailExistException("Email already exists.");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if ("candidate".equalsIgnoreCase(user.getRole()) && user.getCandidateProfile() != null) {
                user.getCandidateProfile().setUser(user);
            }
            if ("employer".equalsIgnoreCase(user.getRole()) && user.getEmployerProfile() != null) {
                user.getEmployerProfile().setUser(user);
            }

            session.save(user);
            return null;
        });
    }

    /* 
     * Updates an existing User entity and associated profiles.
     * Maintains bidirectional relationships between User and Profile entities.
     * Uses saveOrUpdate to handle both new and existing profile entities.
     * @param user The User entity with updated information
     */
    @Override
    public void update(User user) {
        hibernateUtil.executeInTransaction(session -> {
            if (user.getCandidateProfile() != null) {
                user.getCandidateProfile().setUser(user);
            }
            if (user.getEmployerProfile() != null) {
                user.getEmployerProfile().setUser(user);
            }

            session.update(user);

            if (user.getCandidateProfile() != null) {
                session.saveOrUpdate(user.getCandidateProfile());
            }
            if (user.getEmployerProfile() != null) {
                session.saveOrUpdate(user.getEmployerProfile());
            }
            return null;
        });
    }

    /* 
     * Permanently deletes a User entity from the database.
     * Cascade deletion will also remove associated profile information.
     * @param id The unique identifier of the user to delete
     */
    @Override
    public void delete(int id) {
        hibernateUtil.executeInTransaction(session -> {
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            return null;
        });
    }

    /* 
     * Finds a User entity by email address for authentication purposes.
     * Email serves as unique identifier for login and user lookup.
     * @param email The email address to search for
     * @return User entity associated with the email
     * @throws UserEmailNotFoundException if no user found with the given email
     */
    @Override
    public User findByEmail(String email) {
        return hibernateUtil.executeReadOnly(session -> {
            List<User> users = session.createQuery("from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

            if (users.isEmpty()) {
                throw new UserEmailNotFoundException("No user found by the email: " + email);
            }
            return users.get(0);
        });
    }
}
