package com.niit.dao;

import java.util.List;


import com.niit.model.Job;


public interface JobDao {
	public void addJob(Job job);
	List<Job> getJobs();
	Job getJobById(int id);
}