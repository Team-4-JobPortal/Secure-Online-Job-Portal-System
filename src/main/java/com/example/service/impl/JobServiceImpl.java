package com.example.service.impl;

import com.example.dao.JobDao;
import com.example.entity.Job;
import com.example.exception.JobNotFoundException;
import com.example.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobDao jobDao;

    @Override
    public void createJob(Job job) {
        jobDao.save(job);
    }

    @Override
    public Job getJobById(int id) {
        return jobDao.get(id);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobDao.list();
    }

    @Override
    public void updateJob(Job job) {
        jobDao.update(job);
    }

    @Override
    public void deleteJob(int id) {
        jobDao.delete(id);
    }

    @Override
    public List<Job> searchJobs(String keyword, String location, Integer minSalary, Integer maxSalary) {
        	List<Job> jobs = jobDao.searchJobs(keyword, location, minSalary, maxSalary);
        		
        	if(jobs.isEmpty() || jobs == null) {
        		throw new JobNotFoundException("No jobs found matching your criteria.");
        	}
        	
    		return jobs;
    }
    
    @Override
    public List<Job> getJobsByEmployer(String email) {
        return jobDao.findByEmployerEmail(email);
    }

	@Override
	public List<Job> findJobsByEmployer(int user_id) {
		// TODO Auto-generated method stub
		return jobDao.findByEmployerUserid(user_id);
	}

}
