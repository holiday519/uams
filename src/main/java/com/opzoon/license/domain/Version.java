package com.opzoon.license.domain;

import java.util.Date;

public class Version {

	private Integer versionId;
	
	private String versionNo;
	
	private Product product;
	
	private User createUser;
	
	private Date createDate;
	
	private String note;
	
	private String privateKey;
	
	private String publicKey;
	
	public Version() {
		
	}

	public Version(Integer versionId, String versionNo, Product product,
			User createUser, Date createDate, String note, String privateKey,
			String publicKey) {
		this.versionId = versionId;
		this.versionNo = versionNo;
		this.product = product;
		this.createUser = createUser;
		this.createDate = createDate;
		this.note = note;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return "Version [versionId=" + versionId + ", versionNo=" + versionNo + "]";
	} 
	
	
}
