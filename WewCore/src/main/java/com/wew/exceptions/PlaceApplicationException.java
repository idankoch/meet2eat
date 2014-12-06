package com.wew.exceptions;

public class PlaceApplicationException extends WewApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String INVALID_PLACE_ARGUMENT = "INVALID_PLACE_ARGUMENT";

		
	public PlaceApplicationException(String message, String errorCode) {
		super(message, errorCode);
	}

	
}
