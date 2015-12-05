package com.opzoon.license.domain;

public class VDIUKey {

	private Integer licenseId;
	
	private String licenseName;
	
	private Integer validdayAmount;
	
	private Integer maxConnectAmount;
	
	private String note;
	
	private String licenseURL;
	
	private String ukeyName;

	public VDIUKey() {

	}

	public VDIUKey(Integer licenseId, String licenseName, Integer validdayAmount,
			Integer maxConnectAmount, String note, String licenseURL, String ukeyName) {
		this.licenseId = licenseId;
		this.licenseName = licenseName;
		this.validdayAmount = validdayAmount;
		this.maxConnectAmount = maxConnectAmount;
		this.note = note;
		this.licenseURL = licenseURL;
		this.ukeyName = ukeyName;
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

	public Integer getValiddayAmount() {
		return validdayAmount;
	}

	public void setValiddayAmount(Integer validdayAmount) {
		this.validdayAmount = validdayAmount;
	}

	public Integer getMaxConnectAmount() {
		return maxConnectAmount;
	}

	public void setMaxConnectAmount(Integer maxConnectAmount) {
		this.maxConnectAmount = maxConnectAmount;
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
	
	public String getUkeyName() {
		return ukeyName;
	}

	public void setUkeyName(String ukeyName) {
		this.ukeyName = ukeyName;
	}

	@Override
	public String toString() {
		return "VDIUKey [licenseId=" + licenseId + ", licenseName=" + licenseName + "]";
	}
	
}
