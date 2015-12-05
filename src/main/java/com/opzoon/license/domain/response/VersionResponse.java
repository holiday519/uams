package com.opzoon.license.domain.response;

import java.util.List;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.Version;

public class VersionResponse extends BasicResponse {

	private List<Version> versions;
	
	private PageModel page;
	
	public VersionResponse() {
		
	}
	
	public VersionResponse(int errorCode, List<Version> versions, PageModel page) {
		super(errorCode);
		this.versions = versions;
		this.page = page;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "VersionResponse [versions=" + versions + ", page=" + page + "]";
	}
	
}
