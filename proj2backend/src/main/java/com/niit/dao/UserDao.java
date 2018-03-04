package com.niit.dao;

import java.util.List;

import com.niit.model.BlogPost;
import com.niit.model.User;

public interface UserDao {
void registerUser(User user);
boolean isEmailValid(String email);
boolean isUsernameValid(String username);
User login(User user);
void updateUser(User user);
User getUserByUsername(String username);
/*User getUser(String friend_username);*/
void update(User validUser);

}
