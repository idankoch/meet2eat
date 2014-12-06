package com.wew.exceptions;

public class UserApplicationException extends WewApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String INVALID_USER_ARGUMENT = "INVALID_USER_ARGUMENT";
	public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS"; 
	public static final String BUDDY_SAME_AS_USER = "BUDDY_SAME_AS_USER";


	public UserApplicationException(String message, String errorCode){
		super(message, errorCode);
	}


}
