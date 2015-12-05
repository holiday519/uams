package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class UserNotFoundException extends BasicException {

	public UserNotFoundException() {
		
	}
	
	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public UserNotFoundException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [UserNotFoundException] " + getErrorMessage();
	}
}
