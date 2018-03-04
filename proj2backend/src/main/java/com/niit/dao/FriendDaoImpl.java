package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;

@Repository
public class FriendDaoImpl implements FriendDao {
	@Autowired
	private SessionFactory sessionFactory;

	public List<User> getSuggestedUsers(String user) {
		Session session = sessionFactory.openSession();
		SQLQuery query = session.createSQLQuery(
				"select * from user_s180133 where username in (select username from user_s180133 where username!=? minus (select fromusername from friend12 where tousername=?"
						+ "union select tousername from friend12 where fromusername=?" + "))");
		query.setString(0, user);
		query.setString(1, user);
		query.setString(2, user);
		query.addEntity(User.class);
		List<User> users = query.list();
		session.close();
		return users;
	}

	@Transactional
	public void friendRequest(String fromusername, String tousername) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println(fromusername);
		System.out.println(tousername);
		Friend friend = new Friend();
		friend.setFromusername(fromusername);
		friend.setTousername(tousername);
		friend.setStatus('P');
		friend.setOnline(true);
		System.out.println("*******");
		System.out.println(friend.getFromusername());
		System.out.println(friend.getTousername());
		System.out.println(friend.getStatus());
		session.saveOrUpdate(friend);

	}

	public List<Friend> pendingRequests(String tousername) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where tousername=? and status=?");
		query.setString(0, tousername);
		query.setCharacter(1, 'P');
		List<Friend> pendingRequests = query.list();
		System.out.println(pendingRequests.toString());
		session.close();
		return pendingRequests;
	}

	public void updatePendingRequest(String fromusername, String username, char status) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("update Friend set status=? where fromusername=? and tousername=?");
		query.setCharacter(0, status);
		query.setString(1, fromusername);
		query.setString(2, username);
		int count = query.executeUpdate();
		System.out.println("Number of records updated " + count);
		session.flush();
		session.close();
	}

	public List<Friend> listOfFriends(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where (fromusername=? or tousername=?) and status=?");
		query.setString(0, username);
		query.setString(1, username);
		query.setCharacter(2, 'A');
		/*query.addEntity(Friend.class);*/
		List<Friend> friends = query.list();
		System.out.println("Your friend list is:");
		session.close();
		return friends;
	}

}