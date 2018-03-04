package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.FriendDao;
import com.niit.model.ErrorClazz;
import com.niit.model.Friend;
import com.niit.model.User;



@RestController
	public class FriendController {
		@Autowired
		private FriendDao friendDao;

		@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
		public ResponseEntity<?> getAllUsers(HttpSession session) {
			System.out.println("friends");
			String user = (String) session.getAttribute("username");
			System.out.println(session.getAttribute("username"));
			System.out.println(user);
			if (user == null) {
				ErrorClazz error = new ErrorClazz(1, "Unauthorized user");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
			}
			List<User> users = friendDao.getSuggestedUsers(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);

		}
		@RequestMapping(value = "/friendRequest/{tousername}", method = RequestMethod.PUT)
		public ResponseEntity<?> friendRequest(@PathVariable String tousername, HttpSession session) {
			System.out.println("friendreq");
			String user = (String) session.getAttribute("username");
			System.out.println(session.getAttribute("username"));
			System.out.println(user);
			System.out.println(tousername);
			if (user == null) {
				ErrorClazz error = new ErrorClazz(1, "Unauthorized user");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
			}
			friendDao.friendRequest(user, tousername);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		@RequestMapping(value = "/pendingRequests", method = RequestMethod.GET)
		public ResponseEntity<?> pendingRequests(HttpSession session) 
		{
			System.out.println("pendingfriendreq");
			String user = (String) session.getAttribute("username");
			System.out.println(user);
			if (user == null) {
				ErrorClazz error = new ErrorClazz(1, "Unauthorized user");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
			}
			List<Friend> pendingRequests = friendDao.pendingRequests(user);
			return new ResponseEntity<List<Friend>>(pendingRequests, HttpStatus.OK);

		}
		
		@RequestMapping(value = "/updatependingrequest/{fromusername}/{status}", method = RequestMethod.PUT)
		public ResponseEntity<?> updatePendingRequest(@PathVariable String fromusername, @PathVariable char status,
				HttpSession session) {
		String user = (String) session.getAttribute("username");
		System.out.println("######");
		System.out.println(user);
			if (user == null) {
				ErrorClazz error = new ErrorClazz(1, "Unauthorized user");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
			}
			friendDao.updatePendingRequest(fromusername,user,status);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		@RequestMapping(value = "/friendslist", method = RequestMethod.GET)
		public ResponseEntity<?> getAllFriends(HttpSession session) {
			String user = (String) session.getAttribute("username");
			System.out.println("$$$$$$");
			if (user == null) {
				ErrorClazz error = new ErrorClazz(1, "Unauthorized user");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
			}
			List<Friend> friends = friendDao.listOfFriends(user);
			return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		}
}
