package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class ResourceConflictException extends BasicException {

	public ResourceConflictException() {
		
	}
	
	public ResourceConflictException(String errorMessage) {
		super(errorMessage);
	}
	
	public ResourceConflictException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [ResourceConflictException] " + getErrorMessage();
	}
}
