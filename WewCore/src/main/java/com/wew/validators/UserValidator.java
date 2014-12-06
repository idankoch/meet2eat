package com.wew.validators;

import java.util.Set;

import com.wew.entities.User;
import com.wew.exceptions.UserApplicationException;

public class UserValidator {

	private static final String USER_S_ALREADY_EXISTS = "User %s already exist";
	private static final String USER_S_DOES_NOT_EXIST = "User %s does not exist";
	private static final String USER_S_NOT_FRIENDS_WITH = "User %s not friends with %";
	private static final String USER_S_ALREADY_FRIENDS_WITH = "User %s already friends with %";
	private static final String BUDDY_CANNOT_BE_NULL_OR_BUDDY_EMAIL_CANNOT_BE_NULL = "Buddy cannot be null or buddy email cannot be null";
	private static final String USER_CANNOT_BE_NULL_OR_USER_EMAIL_CANNOT_BE_NULL = "User cannot be null or user email cannot be null";
	private static final String USER_CANNOT_BE_SAME_AS_BUDDY = "User cannot be same as buddy";
	private static final String USER_NOT_EXISTS = "User does not exist or null";

	public static void validateBuddyNotSameAsUser(User user, User buddy) throws UserApplicationException {
		if(user.getEmail().equals(buddy.getEmail())){
			throw new UserApplicationException(USER_CANNOT_BE_SAME_AS_BUDDY,UserApplicationException.BUDDY_SAME_AS_USER);
		}
	}

	public static void validateUserArgument(User user) throws UserApplicationException {
		if(user == null || user.getEmail() == null || user.getEmail().trim().isEmpty()){
			throw new UserApplicationException(USER_CANNOT_BE_NULL_OR_USER_EMAIL_CANNOT_BE_NULL,UserApplicationException.INVALID_USER_ARGUMENT);
		}
	}

	public static void validateBuddyNotNull(User buddy) throws UserApplicationException {
		if(buddy == null || buddy.getEmail() == null || buddy.getEmail().trim().isEmpty()){
			throw new UserApplicationException(BUDDY_CANNOT_BE_NULL_OR_BUDDY_EMAIL_CANNOT_BE_NULL,UserApplicationException.INVALID_USER_ARGUMENT);
		}
	}

	public static void validateNotAlreadyBuddyOfUser(User userFromDB, User buddy) throws UserApplicationException {
		Set<User> userBuddyList = userFromDB.getBuddies();
		if(userBuddyList != null && !userBuddyList.isEmpty()){
			for (User buddyFromUserBuddyList : userBuddyList) {
				String buddyEmail = buddy.getEmail();
				if(buddyFromUserBuddyList.getEmail().equalsIgnoreCase(buddyEmail)){
					throw new UserApplicationException(String.format(USER_S_ALREADY_FRIENDS_WITH,userFromDB.getEmail(),buddyEmail), UserApplicationException.INVALID_USER_ARGUMENT);
				}
			}
		}
	}

	@SuppressWarnings("null")
	public static void validateBuddyOfUser(User userFromDB, User buddy) throws UserApplicationException {
		Set<User> userBuddyList = userFromDB.getBuddies();
		String buddyEmail = buddy.getEmail();

		if(userBuddyList == null && userBuddyList.isEmpty()){
			throw new UserApplicationException(String.format(USER_S_NOT_FRIENDS_WITH,userFromDB.getEmail(),buddyEmail), UserApplicationException.INVALID_USER_ARGUMENT);
		}

		for (User buddyFromUserBuddyList : userBuddyList) {
			if(buddyFromUserBuddyList.getEmail().equalsIgnoreCase(buddyEmail)){
				return;			
			}
		}
		
		throw new UserApplicationException(String.format(USER_S_NOT_FRIENDS_WITH,userFromDB.getEmail(),buddyEmail), UserApplicationException.INVALID_USER_ARGUMENT);

	}

	public static void validateUserNotExist(User userFromDB,User user) throws UserApplicationException {
		if(userFromDB == null){
			throw new UserApplicationException(String.format(USER_S_DOES_NOT_EXIST,user.getEmail()), UserApplicationException.INVALID_USER_ARGUMENT);
		}
	}

	public static void validateUserNotAlreadyExists(User userFromDB) throws UserApplicationException {
		if(userFromDB != null){
			throw new UserApplicationException(String.format(USER_S_ALREADY_EXISTS,userFromDB.getEmail()), UserApplicationException.USER_ALREADY_EXISTS);
		}
	}

	public static void validateUserExists(User userFromDB) throws UserApplicationException {
		if(userFromDB == null || userFromDB.getEmail() == null || userFromDB.getEmail().isEmpty()){
			throw new UserApplicationException(USER_NOT_EXISTS, UserApplicationException.USER_ALREADY_EXISTS);
		}
	}



}
