package com.opzoon.license.domain.request;

import java.util.Arrays;

public class IdList {

	private Integer[] ids;
	
	public IdList() {
		
	}

	public IdList(Integer[] ids) {
		this.ids = ids;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "IdList [ids=" + Arrays.toString(ids) + "]";
	}
}
