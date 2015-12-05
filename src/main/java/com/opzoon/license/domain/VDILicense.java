package com.opzoon.license.domain;

public class VDILicense extends License {
	
	private Integer registerTypeCode;
	
	private Integer usageTypeCode;

	private Integer maxConnectAmount;
	
	public VDILicense() {
		
	}
	
	public VDILicense(RegisterType registerType, UsageType usageType, Integer maxConnectAmount) {
		super();
		this.registerTypeCode = registerType.getCode();
		this.usageTypeCode = usageType.getCode();
		this.maxConnectAmount = maxConnectAmount;
	}

	public Integer getRegisterTypeCode() {
		return registerTypeCode;
	}

	public void setRegisterTypeCode(Integer registerTypeCode) {
		this.registerTypeCode = registerTypeCode;
	}
	
	public Integer getUsageTypeCode() {
		return usageTypeCode;
	}

	public void setUsageTypeCode(Integer usageTypeCode) {
		this.usageTypeCode = usageTypeCode;
	}
	
	public Integer getMaxConnectAmount() {
		return maxConnectAmount;
	}

	public void setMaxConnectAmount(Integer maxConnectAmount) {
		this.maxConnectAmount = maxConnectAmount;
	}

	@Override
	public String toString() {
		return super.toString() + "; VDILicense [maxConnectAmount=" + maxConnectAmount + "]";
	}
	
}
