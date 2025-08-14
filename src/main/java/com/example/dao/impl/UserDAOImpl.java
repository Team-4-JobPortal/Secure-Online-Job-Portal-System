package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.UserDAO;
import com.example.entity.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<User> findAll() {
        return getSession().createQuery("FROM User", User.class).list();
    }

    @Override
    public User findById(int id) {
        return getSession().get(User.class, id);
    }

    @Override
    public List<User> findByRole(String role) {
        return getSession()
                .createQuery("FROM User u WHERE u.role = :role", User.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    public void save(User user) {
        getSession().save(user);
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public void delete(int id) {
        User user = findById(id);
        if (user != null) {
            getSession().delete(user);
        }
    }

	@Override
	public User findByEmail(String email) {
		List<User> users = getSession()
	            .createQuery("from User u where u.email = :email", User.class)
	            .setParameter("email", email)
	            .getResultList();

	    return users.isEmpty() ? null : users.get(0);
	}
}