package com.wew.exceptions;

public class UserDailyPreferencesApplicationException extends WewApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DAILY_USER_PREFERENCES_NOT_FOUND = "DAILY_USER_PREFERENCES_NOT_FOUND";
	public static final String USER_DAILY_PREFERENCES_NULL = "USER_DAILY_PREFERENCES_NULL";
	public static final String USER_DAILY_PREFERENCES_PLACES_NULL = "USER_DAILY_PREFERENCES_PLACES_NULL";
	
	
	public UserDailyPreferencesApplicationException(String message,
			String errorCode) {
		super(message, errorCode);
	}
}
