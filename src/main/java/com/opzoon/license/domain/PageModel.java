package com.opzoon.license.domain;

public class PageModel {

	private String sortkey;
	//升序1，降序0
	private int ascend = 1;

	private int pageNo = 1;

	private int pageSize = -1;
	
	private int amount;
	
	public PageModel() {
		
	}
	
	public PageModel(String sortkey, int ascend, int pageNo, int pageSize,
			int amount) {
		this.sortkey = sortkey;
		this.ascend = ascend;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.amount = amount;
	}

	public String getSortkey() {
		return sortkey;
	}

	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}

	public int getAscend() {
		return ascend;
	}

	public void setAscend(int ascend) {
		this.ascend = ascend;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PageModel [sortkey=" + sortkey + ", ascend=" + ascend
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", amount="
				+ amount + "]";
	}
	
}
