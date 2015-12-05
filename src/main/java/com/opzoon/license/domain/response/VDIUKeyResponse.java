package com.opzoon.license.domain.response;

import java.util.List;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.VDIUKey;

public class VDIUKeyResponse extends BasicResponse {

	private List<VDIUKey> vdiUKeys;
	
	private PageModel page;

	public VDIUKeyResponse() {

	}

	public VDIUKeyResponse(int errorCode, List<VDIUKey> vdiUKeys, PageModel page) {
		super(errorCode);
		this.vdiUKeys = vdiUKeys;
		this.page = page;
	}

	public List<VDIUKey> getVdiUKeys() {
		return vdiUKeys;
	}

	public void setVdiUKeys(List<VDIUKey> vdiUKeys) {
		this.vdiUKeys = vdiUKeys;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "VDIUKeyResponse [vdiUKeys=" + vdiUKeys + ", page=" + page + "]";
	}
	
}
