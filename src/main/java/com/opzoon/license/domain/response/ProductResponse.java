package com.opzoon.license.domain.response;

import java.util.List;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.Product;

public class ProductResponse extends BasicResponse {

	private List<Product> products;
	
	private PageModel page;
	
	public ProductResponse() {
		
	}

	public ProductResponse(int errorCode, List<Product> products, PageModel page) {
		super(errorCode);
		this.products = products;
		this.page = page;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}
	
}
