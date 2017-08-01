package com.deemsys.project.County;

public class CountyList {

	private Integer countyId;
	private String countyName;
	private Long newCount;
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
	public CountyList(Integer countyId, String countyName, Long newCount) {
		super();
		this.countyId = countyId;
		this.countyName = countyName;
		this.newCount = newCount;
	}
	public CountyList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
