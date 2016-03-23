package com.deemsys.project.County;

public class CountyList {

	private Integer countyId;
	private String countyName;
	private Integer newCount;
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
	public Integer getNewCount() {
		return newCount;
	}
	public void setNewCount(Integer newCount) {
		this.newCount = newCount;
	}
	public CountyList(Integer countyId, String countyName, Integer newCount) {
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
