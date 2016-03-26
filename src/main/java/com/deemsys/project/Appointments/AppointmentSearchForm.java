package com.deemsys.project.Appointments;

public class AppointmentSearchForm {

	private Integer year;
	private Integer month;
	private String date;
	private String patientName;
	private String clinicName;
	private String callerFirstName;
	private String callerLastName;
	private Integer status;
	private Integer clinicId;
	private Integer pageNumber;
	private Integer itemsPerPage;
	private Integer callerAdminId;
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getClinicId() {
		return clinicId;
	}
	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
	}
	public String getCallerFirstName() {
		return callerFirstName;
	}
	public void setCallerFirstName(String callerFirstName) {
		this.callerFirstName = callerFirstName;
	}
	public String getCallerLastName() {
		return callerLastName;
	}
	public void setCallerLastName(String callerLastName) {
		this.callerLastName = callerLastName;
	}
	public AppointmentSearchForm(Integer year, Integer month, String date,
			String patientName, Integer status, Integer clinicId,
			Integer pageNumber, Integer itemsPerPage) {
		super();
		this.year = year;
		this.month = month;
		this.date = date;
		this.patientName = patientName;
		this.status = status;
		this.clinicId = clinicId;
		this.pageNumber = pageNumber;
		this.itemsPerPage = itemsPerPage;
	}
	public AppointmentSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
