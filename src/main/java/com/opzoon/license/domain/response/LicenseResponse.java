package com.opzoon.license.domain.response;

import java.util.List;

import com.opzoon.license.domain.License;
import com.opzoon.license.domain.PageModel;

public class LicenseResponse extends BasicResponse {

	private List<License> licenses;
	
	private PageModel page;
	
	public LicenseResponse() {
		
	}

	public LicenseResponse(int errorCode, List<License> licenses, PageModel page) {
		super(errorCode);
		this.licenses = licenses;
		this.page = page;
	}

	public List<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}
	
}
