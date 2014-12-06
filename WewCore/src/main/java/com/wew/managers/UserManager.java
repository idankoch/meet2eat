package com.wew.managers;

import com.wew.daos.UserDao;
import com.wew.entities.User;
import com.wew.exceptions.UserApplicationException;
import com.wew.validators.UserValidator;

public class UserManager {

	private UserDao userDao = new UserDao();

	public void addUser(User user) throws UserApplicationException {
		UserValidator.validateUserArgument(user);
		User userFromDB = getUser(user);
		UserValidator.validateUserNotAlreadyExists(userFromDB);

		userDao.insertUser(user);
	}

	public User getUser(User user) throws UserApplicationException {
		UserValidator.validateUserArgument(user);

		return userDao.getUser(user);
	}

	public void deleteUser(User user) throws UserApplicationException {
		UserValidator.validateUserArgument(user);

		userDao.deleteUser(user);
	}

	public void addBuddy(User user, User buddy) throws UserApplicationException {
		UserValidator.validateUserArgument(user);
		UserValidator.validateBuddyNotNull(buddy);
		UserValidator.validateBuddyNotSameAsUser(user,buddy);
		User userFromDB = getUser(user);
		UserValidator.validateUserNotExist(userFromDB,user);
		User buddyFromDB = getUser(buddy);
		UserValidator.validateUserNotExist(buddyFromDB,buddy);
		UserValidator.validateNotAlreadyBuddyOfUser(userFromDB,buddy);
		
		userDao.addBuddy(user, buddy);
	}
	
	public void removeBuddy(User user, User buddy) throws UserApplicationException {
		UserValidator.validateUserArgument(user);
		UserValidator.validateBuddyNotNull(buddy);
		UserValidator.validateBuddyNotSameAsUser(user,buddy);
		User userFromDB = getUser(user);
		UserValidator.validateUserNotExist(userFromDB,user);
		User buddyFromDB = getUser(buddy);
		UserValidator.validateUserNotExist(buddyFromDB,buddy);
		UserValidator.validateBuddyOfUser(userFromDB,buddy);
		
		userDao.removeBuddy(user, buddy);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
