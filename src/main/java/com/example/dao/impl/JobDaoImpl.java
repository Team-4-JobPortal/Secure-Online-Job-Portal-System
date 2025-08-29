package com.example.dao.impl;

import com.example.dao.JobDao;
import com.example.entity.Job;
import com.example.helper.HibernateUtil;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JobDaoImpl implements JobDao {

    private final HibernateUtil hibernateUtil;

    @Autowired
    public JobDaoImpl(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    /* 
     * Persists a new Job entity to the database.
     * Uses transactional session to ensure data consistency.
     * @param job The Job entity to be saved
     */
    @Override
    public void save(Job job) {
        hibernateUtil.executeInTransaction(session -> {
            session.save(job);
            return null;
        });
    }

    /* 
     * Retrieves a specific Job entity by its unique identifier.
     * Uses read-only session for optimal performance.
     * @param id The unique identifier of the job
     * @return Job entity if found, null if not found
     */
    @Override
    public Job get(int id) {
        return hibernateUtil.executeReadOnly(session ->
            session.get(Job.class, id)
        );
    }

    /* 
     * Retrieves all non-deleted Job entities from the database.
     * Filters out soft-deleted jobs using isDeleted flag.
     * @return List<Job> containing all active job postings
     */
    @Override
    public List<Job> list() {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Job j WHERE j.isDeleted = false";
            return session.createQuery(hql, Job.class).list();
        });
    }

    /* 
     * Retrieves all non-deleted jobs posted by a specific employer using email.
     * Filters jobs by employer email and excludes soft-deleted jobs.
     * @param email The email address of the employer
     * @return List<Job> containing employer's active job postings
     */
    @Override
    public List<Job> findByEmployerEmail(String email) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Job j WHERE j.user.email = :email AND j.isDeleted = false";
            return session.createQuery(hql, Job.class)
                    .setParameter("email", email)
                    .list();
        });
    }

    /* 
     * Updates an existing Job entity in the database.
     * Uses transactional session to maintain data integrity.
     * @param job The Job entity with updated information
     */
    @Override
    public void update(Job job) {
        hibernateUtil.executeInTransaction(session -> {
            session.update(job);
            return null;
        });
    }

    /* 
     * Performs soft delete on a Job entity by setting isDeleted flag to true.
     * Preserves job data for historical purposes while hiding from active listings.
     * @param id The unique identifier of the job to delete
     */
    @Override
    public void delete(int id) {
        hibernateUtil.executeInTransaction(session -> {
            Job job = session.get(Job.class, id);
            if (job != null) {
                job.setDeleted(true); // soft delete
                session.update(job);
            }
            return null;
        });
    }

    /* 
     * Searches for jobs based on multiple criteria using dynamic query building.
     * Supports filtering by keyword (job title), location, and salary range.
     * Uses Criteria API for flexible query construction.
     * @param keyword Search term for job title (case-insensitive partial match)
     * @param location Exact location match (case-insensitive)
     * @param minSalary Minimum salary threshold
     * @param maxSalary Maximum salary threshold
     * @return List<Job> containing jobs matching the search criteria
     */
    @Override
    public List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary) {
        return hibernateUtil.executeReadOnly(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Job> cq = cb.createQuery(Job.class);
            Root<Job> job = cq.from(Job.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(job.get("isDeleted"), false));

            if (keyword != null && !keyword.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(job.get("title")), "%" + keyword.toLowerCase() + "%"));
            }

            if (location != null && !location.trim().isEmpty()) {
                predicates.add(cb.equal(cb.lower(job.get("location")), location.toLowerCase()));
            }

            if (minSalary != null) {
                predicates.add(cb.greaterThanOrEqualTo(job.get("min_salary"), minSalary));
            }

            if (maxSalary != null) {
                predicates.add(cb.lessThanOrEqualTo(job.get("max_salary"), maxSalary));
            }

            cq.where(cb.and(predicates.toArray(new Predicate[0])));

            return session.createQuery(cq).getResultList();
        });
    }

    /* 
     * Retrieves all non-deleted jobs posted by a specific employer using user ID.
     * Filters jobs by employer's user ID and excludes soft-deleted jobs.
     * @param empId The unique identifier of the employer
     * @return List<Job> containing employer's active job postings
     */
    @Override
    public List<Job> findByEmployerUserid(int empId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Job j WHERE j.user.user_id = :employerId AND j.isDeleted = false";
            Query<Job> query = session.createQuery(hql, Job.class);
            query.setParameter("employerId", empId);
            return query.getResultList();
        });
    }

    /* 
     * Retrieves all soft-deleted jobs posted by a specific employer.
     * Provides access to job history for employers to view their deleted postings.
     * @param empId The unique identifier of the employer
     * @return List<Job> containing employer's deleted job postings
     */
    @Override
    public List<Job> findDeletedJobsByEmployerUserid(int empId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Job j WHERE j.user.user_id = :employerId AND j.isDeleted = true";
            Query<Job> query = session.createQuery(hql, Job.class);
            query.setParameter("employerId", empId);
            return query.getResultList();
        });
    }
}
