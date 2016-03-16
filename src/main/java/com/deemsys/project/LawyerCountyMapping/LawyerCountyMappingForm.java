package com.deemsys.project.LawyerCountyMapping;


public class LawyerCountyMappingForm {

	private Integer lawyerId;
	private Integer countyId;
	private Integer status;
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public LawyerCountyMappingForm(Integer lawyerId,
			Integer countyId,Integer status) {
		super();
		this.lawyerId = lawyerId;
		this.countyId = countyId;
		this.status=status;
	}
	public LawyerCountyMappingForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
