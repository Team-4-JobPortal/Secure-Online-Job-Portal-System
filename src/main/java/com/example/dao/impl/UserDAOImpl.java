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

    @Override
    public List<User> findAll() {
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery("FROM User", User.class).list()
        );
    }

    @Override
    public User findById(int id) {
        return hibernateUtil.executeReadOnly(session ->
            session.get(User.class, id)
        );
    }

    @Override
    public List<User> findByRole(String role) {
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery("FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", role)
                .getResultList()
        );
    }

    // emailExists used internally, use readOnly session
    private boolean emailExists(String email) {
        String hql = "SELECT 1 FROM User WHERE email = :email";
        return hibernateUtil.executeReadOnly(session ->
            session.createQuery(hql)
                .setParameter("email", email)
                .uniqueResult() != null
        );
    }

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
