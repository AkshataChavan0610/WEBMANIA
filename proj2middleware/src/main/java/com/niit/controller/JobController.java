package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.model.BlogPost;
import com.niit.model.ErrorClazz;
import com.niit.model.Job;
import com.niit.model.User;

@Controller
public class JobController {
   @Autowired
private JobDao jobDao;
   @Autowired
private UserDao userDao;
   @RequestMapping(value="/addjob",method=RequestMethod.POST)
public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session){
	String username=(String)session.getAttribute("username");
	if(username==null){
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
	}
	
	User user=userDao.getUserByUsername(username);
	if(!user.getRole().equals("ADMIN")){
		job.setPostedOn(new Date());
		jobDao.addJob(job);
		ErrorClazz error=new ErrorClazz(6,"Access Denied");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
	
	try{
	
	}catch(Exception e){
		ErrorClazz error=new ErrorClazz(7,"Unable to insert job details" +e.getMessage());
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
   @RequestMapping(value="/getjob/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getJob(@PathVariable int id){
//		String username=(String)session.getAttribute("username");
//		if(username==null){
//			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
//			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401
//		}
		Job job=jobDao.getJobById(id);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
   
   @RequestMapping(value="/alljobs",method=RequestMethod.GET)
   public ResponseEntity<?> getJobs(){
	   List<Job> Jobs=jobDao.getJobs();
		return new ResponseEntity <List<Job>> (Jobs,HttpStatus.OK);
  
	   
   } 
}

