package com.example.dao.impl;

import com.example.dao.ApplicationDAO;
import com.example.entity.Application;
import com.example.helper.HibernateUtil;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationDAOImpl implements ApplicationDAO {

    private final HibernateUtil hibernateUtil;

    @Autowired
    public ApplicationDAOImpl(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    /* 
     * Retrieves all Application records from the database.
     * Uses read-only session for optimal performance.
     * @return List<Application> containing all application entities
     */
    @Override
    public List<Application> findAll() {
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery("FROM Application", Application.class).getResultList()
        );
    }

    /* 
     * Finds a specific Application entity by its unique identifier.
     * Uses read-only session as this is a query operation.
     * @param id The unique identifier of the application
     * @return Application entity if found, null if not found
     */
    @Override
    public Application findById(int id) {
        return hibernateUtil.executeReadOnly(session ->
            session.get(Application.class, id)
        );
    }

    /* 
     * Persists a new Application entity to the database.
     * Uses transactional session to ensure data integrity.
     * @param application The Application entity to be saved
     */
    @Override
    public void save(Application application) {
        hibernateUtil.executeInTransaction(session -> {
            session.save(application);
            return null;
        });
    }

    /* 
     * Updates an existing Application entity in the database.
     * Uses transactional session to ensure atomicity of the operation.
     * @param application The Application entity with updated data
     */
    @Override
    public void update(Application application) {
        hibernateUtil.executeInTransaction(session -> {
            session.update(application);
            return null;
        });
    }

    /* 
     * Permanently deletes an Application entity from the database.
     * Checks for entity existence before attempting deletion to prevent errors.
     * @param id The unique identifier of the application to delete
     */
    @Override
    public void delete(int id) {
        hibernateUtil.executeInTransaction(session -> {
            Application application = session.get(Application.class, id);
            if (application != null) {
                session.delete(application);
            }
            return null;
        });
    }

    /* 
     * Retrieves all applications submitted by a specific user.
     * Results are ordered by application ID in descending order (newest first).
     * @param userId The unique identifier of the user
     * @return List<Application> containing user's applications
     */
    @Override
    public List<Application> findByUserId(int userId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Application a WHERE a.user.user_id = :userId ORDER BY a.application_id DESC";
            Query<Application> query = session.createQuery(hql, Application.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        });
    }

    /* 
     * Checks if a user has already applied for a specific job.
     * Prevents duplicate applications by verifying existence.
     * @param userId The unique identifier of the user
     * @param jobId The unique identifier of the job
     * @return boolean true if application exists, false otherwise
     */
    @Override
    public boolean existsByUserIdAndJobId(int userId, int jobId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "SELECT COUNT(a) FROM Application a WHERE a.user.user_id = :userId AND a.job.job_id = :jobId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("userId", userId);
            query.setParameter("jobId", jobId);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        });
    }

    /* 
     * Retrieves all applications for jobs posted by a specific employer.
     * Enables employers to view applications to their job postings.
     * Results ordered by application ID in descending order (newest first).
     * @param employerId The unique identifier of the employer
     * @return List<Application> containing applications for employer's jobs
     */
    @Override
    public List<Application> findApplicationsByEmployer(int employerId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Application a WHERE a.job.user.user_id = :employerId ORDER BY a.application_id DESC";
            Query<Application> query = session.createQuery(hql, Application.class);
            query.setParameter("employerId", employerId);
            return query.getResultList();
        });
    }
}
