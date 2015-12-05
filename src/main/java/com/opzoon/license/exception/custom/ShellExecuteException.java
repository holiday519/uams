package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class ShellExecuteException extends BasicException {

	public ShellExecuteException() {
		
	}
	
	public ShellExecuteException(String errorMessage) {
		super(errorMessage);
	}
	
	public ShellExecuteException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [ShellExecuteException] " + getErrorMessage();
	}
}
