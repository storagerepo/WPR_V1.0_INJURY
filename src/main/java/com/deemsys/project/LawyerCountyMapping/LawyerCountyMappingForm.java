package com.deemsys.project.LawyerCountyMapping;


public class LawyerCountyMappingForm {

	private Integer mappingId;
	private Integer lawyerId;
	private Integer countyId;
	public Integer getMappingId() {
		return mappingId;
	}
	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}
	public Integer getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	
	public LawyerCountyMappingForm(Integer mappingId, Integer lawyerId,
			Integer countyId) {
		super();
		this.mappingId = mappingId;
		this.lawyerId = lawyerId;
		this.countyId = countyId;
	}
	public LawyerCountyMappingForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
