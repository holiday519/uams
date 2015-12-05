package com.opzoon.license.domain;

public class ClusterUKey {

	private Integer licenseId;
	
	private String guid;
	
	private String licenseName;
	
	private Integer version = 1;
	
	private Integer suiteCount;
	
	private Integer suiteTerm;
	
	private Integer vdiCount;
	
	private Integer vdiTerm;
	
	private Integer fwCount;
	
	private Integer fwTerm;
	
	private String note;
	
	private String licenseURL;
	
	private String ukeyName;

	public ClusterUKey() {

	}

	public ClusterUKey(Integer licenseId, String guid, String licenseName, 
			Integer version, Integer suiteCount, Integer suiteTerm, Integer vdiCount,
			Integer vdiTerm, Integer fwCount, Integer fwTerm, String note, String licenseURL, 
			String ukeyName) {
		this.licenseId = licenseId;
		this.guid = guid;
		this.licenseName = licenseName;
		this.version = version;
		this.suiteCount = suiteCount;
		this.suiteTerm = suiteTerm;
		this.vdiCount = vdiCount;
		this.vdiTerm = vdiTerm;
		this.fwCount = fwCount;
		this.fwTerm = fwTerm;
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

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSuiteCount() {
		return suiteCount;
	}

	public void setSuiteCount(Integer suiteCount) {
		this.suiteCount = suiteCount;
	}

	public Integer getSuiteTerm() {
		return suiteTerm;
	}

	public void setSuiteTerm(Integer suiteTerm) {
		this.suiteTerm = suiteTerm;
	}

	public Integer getVdiCount() {
		return vdiCount;
	}

	public void setVdiCount(Integer vdiCount) {
		this.vdiCount = vdiCount;
	}

	public Integer getVdiTerm() {
		return vdiTerm;
	}

	public void setVdiTerm(Integer vdiTerm) {
		this.vdiTerm = vdiTerm;
	}

	public Integer getFwCount() {
		return fwCount;
	}

	public void setFwCount(Integer fwCount) {
		this.fwCount = fwCount;
	}

	public Integer getFwTerm() {
		return fwTerm;
	}

	public void setFwTerm(Integer fwTerm) {
		this.fwTerm = fwTerm;
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
		return "ClusterUKey [licenseId=" + licenseId + ", licenseName=" + licenseName + "]";
	}

}
