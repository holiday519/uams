package com.opzoon.license.domain.response;

import java.util.List;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.User;

public class UserResponse extends BasicResponse {

	private List<User> users;
	
	private PageModel page;
	
	public UserResponse() {
		
	}
	
	public UserResponse(int errorCode, List<User> users, PageModel page) {
		super(errorCode);
		this.users = users;
		this.page = page;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}
}
