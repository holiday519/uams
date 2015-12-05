package com.opzoon.license.domain;

public class SuiteLicense extends License {
	
	private Integer cpuAmount;
	
	private Integer nuclearAmout;
	
	private Integer hostAmount;

	public SuiteLicense() {
		
	}
	
	public SuiteLicense(String fingerprintInfo,
			Integer cpuAmount, Integer nuclearAmout, Integer hostAmount) {
		super();
		this.cpuAmount = cpuAmount;
		this.nuclearAmout = nuclearAmout;
		this.hostAmount = hostAmount;
	}

	public Integer getCpuAmount() {
		return cpuAmount;
	}

	public void setCpuAmount(Integer cpuAmount) {
		this.cpuAmount = cpuAmount;
	}

	public Integer getNuclearAmout() {
		return nuclearAmout;
	}

	public void setNuclearAmout(Integer nuclearAmout) {
		this.nuclearAmout = nuclearAmout;
	}

	public Integer getHostAmount() {
		return hostAmount;
	}

	public void setHostAmount(Integer hostAmount) {
		this.hostAmount = hostAmount;
	}

	@Override
	public String toString() {
		return "SuiteLicense [cpuAmount=" + cpuAmount + ", nuclearAmout="
				+ nuclearAmout + ", hostAmount=" + hostAmount + "]";
	}

}
