package com.deemsys.project.patient;

/**
 * 
 * @author Deemsys
 * 
 */
public class PatientViewForm {
	
	private String patientId;
	private String localReportNumber;
	private Integer countyId;
	private String county;
	private String crashDate;
	private String crashSeverity;
	private String addedDate;
	private String name;
	private String crashReportLink;
	
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCrashDate() {
		return crashDate;
	}
	public void setCrashDate(String crashDate) {
		this.crashDate = crashDate;
	}
	public String getCrashSeverity() {
		return crashSeverity;
	}
	public void setCrashSeverity(String crashSeverity) {
		this.crashSeverity = crashSeverity;
	}
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getCrashReportLink() {
		return crashReportLink;
	}
	public void setCrashReportLink(String crashReportLink) {
		this.crashReportLink = crashReportLink;
	}
	public PatientViewForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PatientViewForm(String patientId, String localReportNumber,
			String crashDate,
			String crashSeverity, String addedDate, String name,
			String crashReportLink) {
		super();
		this.patientId = patientId;
		this.localReportNumber = localReportNumber;
		this.crashDate = crashDate;
		this.crashSeverity = crashSeverity;
		this.addedDate = addedDate;
		this.name = name;
		this.crashReportLink = crashReportLink;
	}
	
	
	
}