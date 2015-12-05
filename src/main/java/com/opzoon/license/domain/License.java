package com.opzoon.license.domain;

import java.util.Date;

public class License {

	private Integer licenseId;

	private String licenseName;

	private Version version;

	private User createUser;

	private Date createDate;
	
	private Integer validdayAmount;
	
	private String registerInfo;

	private String note;

	private String licenseURL;

	public License() {

	}

	public License(Integer licenseId, String licenseName, Version version,
			User createUser, Date createDate, Integer validdayAmount, 
			String registerInfo, String note, String licenseURL) {
		this.licenseId = licenseId;
		this.licenseName = licenseName;
		this.version = version;
		this.createUser = createUser;
		this.createDate = createDate;
		this.validdayAmount = validdayAmount;
		this.registerInfo = registerInfo;
		this.note = note;
		this.licenseURL = licenseURL;
	}

	public Integer getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Integer licenseId) {
		this.licenseId = licenseId;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
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

	public Integer getValiddayAmount() {
		return validdayAmount;
	}

	public void setValiddayAmount(Integer validdayAmount) {
		this.validdayAmount = validdayAmount;
	}

	public String getRegisterInfo() {
		return registerInfo;
	}

	public void setRegisterInfo(String registerInfo) {
		this.registerInfo = registerInfo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLicenseURL() {
		return licenseURL;
	}

	public void setLicenseURL(String licenseURL) {
		this.licenseURL = licenseURL;
	}

	@Override
	public String toString() {
		return "License [licenseId=" + licenseId + ", licenseName=" + licenseName + "]";
	}
	
}
