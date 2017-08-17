package com.deemsys.project.County;

public class CountyList {

	private Integer countyId;
	private String countyName;
	private Long newCount=new Long(0);
	private Long reportingAgencyCount;
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public Long getNewCount() {
		return newCount;
	}
	public void setNewCount(Long newCount) {
		this.newCount = newCount;
	}
	public Long getReportingAgencyCount() {
		return reportingAgencyCount;
	}
	public void setReportingAgencyCount(Long reportingAgencyCount) {
		this.reportingAgencyCount = reportingAgencyCount;
	}
	public CountyList(Integer countyId, String countyName, Long newCount, Long reportingAgencyCount) {
		super();
		this.countyId = countyId;
		this.countyName = countyName;
		this.newCount = newCount;
		this.reportingAgencyCount = reportingAgencyCount;
	}
	public CountyList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
