package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.User;

public interface FriendDao {
List<User> getSuggestedUsers(String user);
public void friendRequest(String fromusername, String tousername);
public List<Friend> pendingRequests(String tousername);
void updatePendingRequest(String fromusername, String username, char status);
public List<Friend> listOfFriends(String username);
}