package com.opzoon.license.domain.request;

import org.hibernate.validator.constraints.NotEmpty;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.User;

public class UserRequest {
	
	@NotEmpty
	private User user;
	@NotEmpty
	private PageModel page;
	
	public UserRequest() {
		
	}

	public UserRequest(User user, PageModel page) {
		this.user = user;
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "UserRequest [user=" + user + ", page=" + page + "]";
	}
	
}
