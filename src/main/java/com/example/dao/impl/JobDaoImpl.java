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

    @Override
    public void save(Job job) {
        hibernateUtil.executeInTransaction(session -> {
            session.save(job);
            return null;
        });
    }

    
    @Override
    public Job get(int id) {
        return hibernateUtil.executeReadOnly(session ->
            session.get(Job.class, id)
        );
    }

    @Override
    public List<Job> list() {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Job j WHERE j.isDeleted = false";
            return session.createQuery(hql, Job.class).list();
        });
    }

    @Override
    public List<Job> findByEmployerEmail(String email) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Job j WHERE j.user.email = :email AND j.isDeleted = false";
            return session.createQuery(hql, Job.class)
                    .setParameter("email", email)
                    .list();
        });
    }

    @Override
    public void update(Job job) {
        hibernateUtil.executeInTransaction(session -> {
            session.update(job);
            return null;
        });
    }

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

    @Override
    public List<Job> findByEmployerUserid(int empId) {
        return hibernateUtil.executeReadOnly(session -> {
            String hql = "FROM Job j WHERE j.user.user_id = :employerId AND j.isDeleted = false";
            Query<Job> query = session.createQuery(hql, Job.class);
            query.setParameter("employerId", empId);
            return query.getResultList();
        });
    }

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
