package com.opzoon.license.exception.custom;

import com.opzoon.license.exception.BasicException;

public class UploadFileException extends BasicException {

	public UploadFileException() {
		
	}
	
	public UploadFileException(String errorMessage) {
		super(errorMessage);
	}
	
	public UploadFileException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		return "<=license=> Exception: [UploadFileException] " + getErrorMessage();
	}
}
