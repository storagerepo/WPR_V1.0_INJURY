package com.deemsys.project.patient;

public class CallerPatientSearchForm {

	private Integer callerAdminId;	
	private Integer countyId;
	private Integer tier;
	private Integer patientStatus;
	private String crashFromDate;
	private String crashToDate;
	private String localReportNumber;
	private String patientName;
	private String callerId;
	private String phoneNumber;
	
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
	public Integer getTier() {
		return tier;
	}
	public void setTier(Integer tier) {
		this.tier = tier;
	}
	public Integer getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}
	public String getCrashFromDate() {
		return crashFromDate;
	}
	public void setCrashFromDate(String crashFromDate) {
		this.crashFromDate = crashFromDate;
	}
	public String getCrashToDate() {
		return crashToDate;
	}
	public void setCrashToDate(String crashToDate) {
		this.crashToDate = crashToDate;
	}
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getCallerId() {
		return callerId;
	}
	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public CallerPatientSearchForm(Integer callerAdminId, Integer countyId,
			Integer tier, Integer patientStatus, String crashFromDate,
			String crashToDate, String localReportNumber, String patientName,
			String callerId, String phoneNumber) {
		super();
		this.callerAdminId = callerAdminId;
		this.countyId = countyId;
		this.tier = tier;
		this.patientStatus = patientStatus;
		this.crashFromDate = crashFromDate;
		this.crashToDate = crashToDate;
		this.localReportNumber = localReportNumber;
		this.patientName = patientName;
		this.callerId = callerId;
		this.phoneNumber = phoneNumber;
	}
	public CallerPatientSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
