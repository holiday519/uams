package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class RsaException extends BasicException {

	public RsaException() {
		
	}
	
	public RsaException(String errorMessage) {
		super(errorMessage);
	}
	
	public RsaException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [RsaException] " + getErrorMessage();
	}
}
