package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class FileUtilException extends BasicException {

	public FileUtilException() {
		
	}
	
	public FileUtilException(String errorMessage) {
		super(errorMessage);
	}
	
	public FileUtilException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [FileUtilException] " + getErrorMessage();
	}
}
