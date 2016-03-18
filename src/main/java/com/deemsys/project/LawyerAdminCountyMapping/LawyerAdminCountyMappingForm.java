package com.deemsys.project.LawyerAdminCountyMapping;

public class LawyerAdminCountyMappingForm {

	private Integer lawyerAdminId;
	private Integer countyId;
	private String subscribedDate;
	private Integer status;
	
	public Integer getLawyerAdminId() {
		return lawyerAdminId;
	}
	public void setLawyerAdminId(Integer lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	
	
	public String getSubscribedDate() {
		return subscribedDate;
	}
	public void setSubscribedDate(String subscribedDate) {
		this.subscribedDate = subscribedDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public LawyerAdminCountyMappingForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LawyerAdminCountyMappingForm(Integer lawyerAdminId,
			Integer countyId,  String subscribedDate,Integer status) {
		super();
		this.lawyerAdminId = lawyerAdminId;
		this.countyId = countyId;
		this.subscribedDate=subscribedDate;
		this.status = status;
	}
	
	
}
