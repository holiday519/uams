package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class ResourceNotFoundException extends BasicException {

	public ResourceNotFoundException() {
		
	}
	
	public ResourceNotFoundException(String errorMessage) {
		super(errorMessage);
	}
	
	public ResourceNotFoundException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [ResourceNotFoundException] " + getErrorMessage();
	}
}
