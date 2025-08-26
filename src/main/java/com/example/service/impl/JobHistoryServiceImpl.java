package com.example.service.impl;

import com.example.dao.JobHistroyDao;
import com.example.entity.JobHistory;
import com.example.service.JobHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobHistoryServiceImpl implements JobHistoryService {

    @Autowired
    private JobHistroyDao jobHistroyDao;

    	@Override
	public void save(JobHistory job) {
    		jobHistroyDao.save(job);
	}

	

	@Override
	public List<JobHistory> list() {
		return jobHistroyDao.list();
	}

    
    
   

}
