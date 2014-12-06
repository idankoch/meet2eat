package com.wew.exceptions;

public class WewApplicationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String errorCode="Unknown_Exception";

	public WewApplicationException(String message, String errorCode){
		super(message);
		this.errorCode=errorCode;
	}

	public String getErrorCode(){
		return this.errorCode;
	}
}
