package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.UserDAO;
import com.example.entity.User;
import com.example.exception.UserEmailExistException;
import com.example.exception.UserEmailNotFoundException;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    
	
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserDAOImpl(PasswordEncoder passwordEncoder) {
	    this.passwordEncoder = passwordEncoder;
	}
	
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
    
    // this method is not part the userDao
    // It used to check for the duplicate email 
    public boolean emailExists(String email) {
        String hql = "SELECT 1 FROM User WHERE email = :email";
        return getSession().createQuery(hql)
                           .setParameter("email", email)
                           .uniqueResult() != null;
    }


    @Override
    public void save(User user) {
    	
    	if (emailExists(user.getEmail())) {
            throw new UserEmailExistException("Email already exists.");
    }	
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        
    	// Set back references for bidirectional mapping
        if ("candidate".equalsIgnoreCase(user.getRole()) && user.getCandidateProfile() != null) {
            user.getCandidateProfile().setUser(user);
        }
        if ("employer".equalsIgnoreCase(user.getRole()) && user.getEmployerProfile() != null) {
            user.getEmployerProfile().setUser(user);
        }
        
        getSession().save(user);
    }

    @Override
    public void update(User user) {
		// Ensure bidirectional links are intact before update
        if (user.getCandidateProfile() != null) {
            user.getCandidateProfile().setUser(user);
        }
        if (user.getEmployerProfile() != null) {
            user.getEmployerProfile().setUser(user);
        }

        getSession().update(user);

        // Persist nested profiles explicitly to be safe
        if (user.getCandidateProfile() != null) {
            getSession().saveOrUpdate(user.getCandidateProfile());
        }
        if (user.getEmployerProfile() != null) {
            getSession().saveOrUpdate(user.getEmployerProfile());
        }
    }

    @Override
    public void delete(int id) {
        User user = getSession().get(User.class, id);
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
		
		if(users.isEmpty()) {
			throw new UserEmailNotFoundException("No user found by the email: "+email);
		}

	    return  users.get(0);
	}
}