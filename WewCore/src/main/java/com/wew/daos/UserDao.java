package com.wew.daos;

import java.util.HashMap;
import java.util.Map;

import com.wew.entities.User;
import com.wew.exceptions.UserApplicationException;

public class UserDao {

	Map<String,User> users = new HashMap<String,User>();

	public void insertUser(User user) throws UserApplicationException {
		String email = user.getEmail();
		if(users.containsKey(email)){
			throw new UserApplicationException(String.format("User %s already exists",user.getEmail()),UserApplicationException.USER_ALREADY_EXISTS);
		}
		users.put(email, user);	
	}

	public User getUser(User user) {
		return users.get(user.getEmail());
	}

	public void deleteUser(User user) {
		String email = user.getEmail();
		if(users.containsKey(email)){
			users.remove(email);
		}
	}

	public void addBuddy(User user, User buddy) {
		User userFromDB = getUser(user);
		userFromDB.addBuddy(buddy);
	}

	public void removeBuddy(User user, User buddy) {
		User userFromDB = getUser(user);
		userFromDB.removeBuddy(buddy);
	}

}
