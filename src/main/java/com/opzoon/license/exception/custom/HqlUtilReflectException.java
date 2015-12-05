package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class HqlUtilReflectException extends BasicException {
	
	public HqlUtilReflectException() {
		
	}
	
	public HqlUtilReflectException(String errorMessage) {
		super(errorMessage);
	}
	
	public HqlUtilReflectException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [HqlUtilReflectException] " + getErrorMessage();
	}
	
}