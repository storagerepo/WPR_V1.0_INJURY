package com.deemsys.project.patient;

public class CallerPatientSearchForm {

	private Integer callerAdminId;	
	private Integer countyId;
	private Integer tier;
	private Integer patientStatus;
	private String crashFromDate;
	private Integer numberOfDays;
	private String crashToDate;
	private String localReportNumber;
	private String patientName;
	private Integer age;
	private Integer callerId;
	private Integer lawyerAdminId;
	private Integer lawyerId;
	private String phoneNumber;
	private Integer pageNumber;
	private Integer itemsPerPage;
	private String addedOnFromDate;
	private String addedOnToDate;
	private Integer isArchived;
	
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
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public Integer getLawyerAdminId() {
		return lawyerAdminId;
	}
	public void setLawyerAdminId(Integer lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
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
	public Integer getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public Integer getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}
	
	public String getAddedOnFromDate() {
		return addedOnFromDate;
	}
	public void setAddedOnFromDate(String addedOnFromDate) {
		this.addedOnFromDate = addedOnFromDate;
	}
	public String getAddedOnToDate() {
		return addedOnToDate;
	}
	public void setAddedOnToDate(String addedOnToDate) {
		this.addedOnToDate = addedOnToDate;
	}
	
	public Integer getIsArchived() {
		return isArchived;
	}
	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}
	public CallerPatientSearchForm(Integer callerAdminId, Integer countyId,
			Integer tier, Integer patientStatus, String crashFromDate,Integer numberOfDays,
			String crashToDate, String localReportNumber, String patientName,Integer age,
			Integer callerId,Integer lawyerAdminId,Integer lawyerId,String phoneNumber,Integer pageNumber,Integer itemsPerPage,String addedOnFromDate,String addedOnToDate,Integer isArchived) {
		super();
		this.callerAdminId = callerAdminId;
		this.countyId = countyId;
		this.tier = tier;
		this.patientStatus = patientStatus;
		this.crashFromDate = crashFromDate;
		this.numberOfDays=numberOfDays;
		this.crashToDate = crashToDate;
		this.localReportNumber = localReportNumber;
		this.patientName = patientName;
		this.age=age;
		this.callerId = callerId;
		this.lawyerAdminId=lawyerAdminId;
		this.lawyerId=lawyerId;
		this.phoneNumber = phoneNumber;
		this.pageNumber=pageNumber;
		this.itemsPerPage=itemsPerPage;
		this.addedOnFromDate=addedOnFromDate;
		this.addedOnToDate=addedOnToDate;
		this.isArchived=isArchived;
	}
	public CallerPatientSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
