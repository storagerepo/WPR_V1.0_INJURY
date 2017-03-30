package com.deemsys.project.CrashReport;

import java.util.List;

public class CrashReportSearchForm {
	private String localReportNumber;
	private String crashId;
	private String crashFromDate;
	private String crashToDate;
	private String county;
	private String addedFromDate;
	private String addedToDate;
	private String numberOfDays;
	private Integer recordsPerPage;
	private Integer pageNumber;
	private List<CrashReportForm> crashReportForms;
	private Integer totalRecords;
	private Integer isRunnerReport;
	
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	public String getCrashId() {
		return crashId;
	}
	public void setCrashId(String crashId) {
		this.crashId = crashId;
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
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddedFromDate() {
		return addedFromDate;
	}
	public void setAddedFromDate(String addedFromDate) {
		this.addedFromDate = addedFromDate;
	}
	public String getAddedToDate() {
		return addedToDate;
	}
	public void setAddedToDate(String addedToDate) {
		this.addedToDate = addedToDate;
	}
	public String getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<CrashReportForm> getCrashReportForms() {
		return crashReportForms;
	}
	public void setCrashReportForms(List<CrashReportForm> crashReportForms) {
		this.crashReportForms = crashReportForms;
	}
	public Integer getIsRunnerReport() {
		return isRunnerReport;
	}
	public void setIsRunnerReport(Integer isRunnerReport) {
		this.isRunnerReport = isRunnerReport;
	}
	public CrashReportSearchForm(String localReportNumber, String crashId,
			String crashFromDate, String crashToDate, String county,
			String addedFromDate, String addedToDate, String numberOfDays,
			Integer recordsPerPage, Integer pageNumber,Integer isRunnerReport) {
		super();
		this.localReportNumber = localReportNumber;
		this.crashId = crashId;
		this.crashFromDate = crashFromDate;
		this.crashToDate = crashToDate;
		this.county = county;
		this.addedFromDate = addedFromDate;
		this.addedToDate = addedToDate;
		this.numberOfDays = numberOfDays;
		this.recordsPerPage = recordsPerPage;
		this.pageNumber = pageNumber;
		this.isRunnerReport = isRunnerReport;
	}
	
	public CrashReportSearchForm(List<CrashReportForm> crashReportForms,
			Integer totalRecords) {
		super();
		this.crashReportForms = crashReportForms;
		this.totalRecords = totalRecords;
	}
	
	public CrashReportSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
