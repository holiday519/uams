package com.opzoon.license.domain.request;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.VDIUKey;

public class VDIUKeyRequest {

	private VDIUKey vdiUKey;
	
	private PageModel page;

	public VDIUKeyRequest() {

	}

	public VDIUKeyRequest(VDIUKey vdiUKey, PageModel page) {
		this.vdiUKey = vdiUKey;
		this.page = page;
	}

	public VDIUKey getVdiUKey() {
		return vdiUKey;
	}

	public void setVdiUKey(VDIUKey vdiUKey) {
		this.vdiUKey = vdiUKey;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "VDIUKeyRequest [vdiUKey=" + vdiUKey + ", page=" + page + "]";
	}
	
}
