package com.opzoon.license.domain.request;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.User;
import com.opzoon.license.domain.Version;

public class VersionRequest {

	private Version version;
	
	private Integer loginUserId;
	
	private PageModel page;
	
	public VersionRequest() {
		
	}
	
	public VersionRequest(Version version, Integer loginUserId, PageModel page) {
		this.version = version;
		this.loginUserId = loginUserId;
		this.page = page;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	public Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "VersionPequest [version=" + version + ", page=" + page + "]";
	}

}
