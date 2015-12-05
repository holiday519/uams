package com.opzoon.license.domain.request;

import com.opzoon.license.domain.License;
import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.User;

public class LicenseRequest {

	private License license;
	
	private Integer loginUserId;
	
	private PageModel page;
	
	public LicenseRequest() {
		
	}

	public LicenseRequest(License license, Integer loginUserId, PageModel page) {
		this.license = license;
		this.loginUserId = loginUserId;
		this.page = page;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
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
		return "LicenseRequest [license=" + license + ", page=" + page + "]";
	}
	
}
