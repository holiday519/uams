package com.opzoon.license.domain.response;

public class BasicResponse {

	private int errorCode;
	
	public BasicResponse() {
		
	}
	
	public BasicResponse(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
