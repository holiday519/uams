package com.opzoon.license.domain.request;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.Product;
import com.opzoon.license.domain.User;

public class ProductRequest {

	private Product product;
	
	private Integer loginUserId;
	
	private PageModel page;
	
	public ProductRequest() {
		
	}

	public ProductRequest(Product product, Integer loginUserId, PageModel page) {
		this.product = product;
		this.loginUserId = loginUserId;
		this.page = page;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
		return "ProductRequest [product=" + product + ", page=" + page + "]";
	}
	
}
