package com.niit.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.UserDao;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@Controller
public class UserController {
@Autowired
private UserDao userDao;

public UserController(){
	System.out.println("UserController");
}

//C:\Users\AKSHATA CHAVAN\Desktop\proj copy\proj2frontend\WebContent\resources\images
//  public String saveProduct(@ModelAttribute("product")Product product,@RequestParam("pimage")MultipartFile fileDetail,Model m
@RequestMapping(value="/registeruser",method=RequestMethod.POST)
public ResponseEntity<?> registerUser(@RequestBody User user){//5
	System.out.println("UserController123");
	try{
		if(!userDao.isUsernameValid(user.getUsername())){//Duplicate Username
			ErrorClazz error=new ErrorClazz(2,"Username already exists..please choose different username");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.CONFLICT);
		}
		if(!userDao.isEmailValid(user.getEmail())){
			ErrorClazz error=new ErrorClazz(3,"EmailId already exists..please enter different email address");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.CONFLICT);
		}
	   userDao.registerUser(user);
	}catch(Exception e){
	System.out.println(e);
	System.out.println("registered");	
		ErrorClazz error=new ErrorClazz(1,"Unable to register user details");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
				
	}
	
    
	return new ResponseEntity<User>(user,HttpStatus.OK);	
}

@RequestMapping(value="/login",method=RequestMethod.POST)
public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
	
	System.out.println("login");
	User validUser=userDao.login(user);
	if(validUser==null){
		ErrorClazz errorClazz=new ErrorClazz(4,"Invalid username/password");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//ErrorClazz,401
	}
	else{
		validUser.setOnline(true);
		session.setAttribute("username", validUser.getUsername());
		System.out.println(session.getAttribute("username"));
		userDao.updateUser(validUser);
		System.out.println("user1");
		return new ResponseEntity<User>(validUser,HttpStatus.OK);
	}
}
@RequestMapping(value="/logout",method=RequestMethod.GET)
public ResponseEntity<?> logout(HttpSession session){
	String username=(String)session.getAttribute("username");
	if(username==null){//without login
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);//401 -> login.html
	}
	User user=userDao.getUserByUsername(username);//select * from user where username=?
	user.setOnline(false);
	userDao.updateUser(user);//update User set online=false where username=?
	session.removeAttribute("username");
	session.invalidate();
	
	return new ResponseEntity<User>(user,HttpStatus.OK);
}

@RequestMapping(value="/getuserbyusername",method=RequestMethod.GET)
public ResponseEntity<?> getUserByUsername(HttpSession session){
	String username=(String)session.getAttribute("username");
	if(username==null){
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
	}
	User user=userDao.getUserByUsername(username);
	
	return new ResponseEntity<User>(user,HttpStatus.OK);
}

@RequestMapping(value="/updateuser",method=RequestMethod.PUT)
public ResponseEntity<?> updateUser(@RequestBody User user,HttpSession session){
	String username=(String)session.getAttribute("username");
	if(username==null){
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
	}
    try{
        /*String path="C:\\Users\\AKSHATA CHAVAN\\Desktop\\proj copy\\proj2frontend\\WebContent\\resources\\images";
	    
	    String totalFileWithPath=path+String.valueOf(user.getUsername())+".jpg";
	     
	    File profilepic=new File(totalFileWithPath);*/
    userDao.update(user);	
    return new ResponseEntity<User>(user,HttpStatus.OK);
    }catch(Exception e){
		ErrorClazz error=new ErrorClazz(6,e.getMessage());
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		
		
    }
   
	
	
}


}

