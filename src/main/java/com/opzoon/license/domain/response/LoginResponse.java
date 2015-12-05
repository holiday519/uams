package com.opzoon.license.domain.response;

import com.opzoon.license.domain.User;

public class LoginResponse extends BasicResponse {

	private User user;
	
	public LoginResponse() {
		
	}
	
	public LoginResponse(int errorCode, User user) {
		super(errorCode);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
