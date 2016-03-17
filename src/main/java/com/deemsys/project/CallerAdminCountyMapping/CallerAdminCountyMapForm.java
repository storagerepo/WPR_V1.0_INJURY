package com.deemsys.project.CallerAdminCountyMapping;

public class CallerAdminCountyMapForm {
    private Integer callerAdminId;
    private Integer countyId;
    private String subscribedDate;
    private Integer status;
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
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
	public CallerAdminCountyMapForm(Integer callerAdminId, Integer countyId,String subscribedDate,
			Integer status) {
		super();
		this.callerAdminId = callerAdminId;
		this.countyId = countyId;
		this.subscribedDate=subscribedDate;
		this.status = status;
		
	}
	public CallerAdminCountyMapForm() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
