package com.opzoon.license.domain.request;

import com.opzoon.license.domain.ClusterUKey;
import com.opzoon.license.domain.PageModel;

public class ClusterUKeyRequest {

	private ClusterUKey clusterUKey;
	
	private PageModel page;

	public ClusterUKeyRequest() {
		
	}

	public ClusterUKeyRequest(ClusterUKey clusterUKey, PageModel page) {
		this.clusterUKey = clusterUKey;
		this.page = page;
	}

	public ClusterUKey getClusterUKey() {
		return clusterUKey;
	}

	public void setClusterUKey(ClusterUKey clusterUKey) {
		this.clusterUKey = clusterUKey;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "ClusterUKeyRequest [clusterUKey=" + clusterUKey + ", page="
				+ page + "]";
	}
	
}
