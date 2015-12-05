package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class PasswordIncorrectException extends BasicException {

	public PasswordIncorrectException() {
		
	}
	
	public PasswordIncorrectException(String errorMessage) {
		super(errorMessage);
	}
	
	public PasswordIncorrectException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [PasswordIncorrectException] " + getErrorMessage();
	}
}
