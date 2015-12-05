package com.opzoon.license.domain.response;

import java.util.List;

import com.opzoon.license.domain.ClusterUKey;
import com.opzoon.license.domain.PageModel;

public class ClusterUKeyResponse extends BasicResponse {

	private List<ClusterUKey> clusterUKeys;
	
	private PageModel page;
	
	public ClusterUKeyResponse() {
		
	}

	public ClusterUKeyResponse(int errorCode, List<ClusterUKey> clusterUKeys, PageModel page) {
		super(errorCode);
		this.clusterUKeys = clusterUKeys;
		this.page = page;
	}

	public List<ClusterUKey> getClusterUKeys() {
		return clusterUKeys;
	}

	public void setClusterUKeys(List<ClusterUKey> clusterUKeys) {
		this.clusterUKeys = clusterUKeys;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "ClusterUKeyResponse [clusterUKeys=" + clusterUKeys + ", page="
				+ page + "]";
	}
	
}
