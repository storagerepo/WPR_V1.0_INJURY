package com.deemsys.project.CrashReport;

import java.util.List;

public class CrashReportSearchForm {
	private String localReportNumber;
	private String crashId;
	private String crashFromDate;
	private String crashToDate;
	private Integer[] countyId;
	private String addedFromDate;
	private String addedToDate;
	private String numberOfDays;
	private Integer recordsPerPage;
	private Integer pageNumber;
	private List<CrashReportForm> crashReportForms;
	private Integer totalRecords;
	private Integer isRunnerReport;
	private Integer callerAdminId;
	private Integer callerId;
	private Integer lawyerAdminId;
	private Integer lawyerId;
	private Integer isArchived;
	private String archivedFromDate;
	private String archivedToDate;
	private Integer directReportStatus;
	private Integer reportFrom;
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
	public Integer[] getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer[] countyId) {
		this.countyId = countyId;
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
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public Integer getLawyerAdminId() {
		return lawyerAdminId;
	}
	public void setLawyerAdminId(Integer lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
	}
	public Integer getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}
	public Integer getIsArchived() {
		return isArchived;
	}
	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}
	public String getArchivedFromDate() {
		return archivedFromDate;
	}
	public void setArchivedFromDate(String archivedFromDate) {
		this.archivedFromDate = archivedFromDate;
	}
	public String getArchivedToDate() {
		return archivedToDate;
	}
	public void setArchivedToDate(String archivedToDate) {
		this.archivedToDate = archivedToDate;
	}
	public Integer getDirectReportStatus() {
		return directReportStatus;
	}
	public void setDirectReportStatus(Integer directReportStatus) {
		this.directReportStatus = directReportStatus;
	}
	public Integer getReportFrom() {
		return reportFrom;
	}
	public void setReportFrom(Integer reportFrom) {
		this.reportFrom = reportFrom;
	}
	public CrashReportSearchForm(String localReportNumber, String crashId,
			String crashFromDate, String crashToDate, Integer[] countyId,
			String addedFromDate, String addedToDate, String numberOfDays,
			Integer recordsPerPage, Integer pageNumber,
			Integer isRunnerReport, Integer callerAdminId, Integer callerId,
			Integer lawyerAdminId, Integer lawyerId, Integer isArchived,
			String archivedFromDate, String archivedToDate,Integer directReportStatus, Integer reportFrom) {
		super();
		this.localReportNumber = localReportNumber;
		this.crashId = crashId;
		this.crashFromDate = crashFromDate;
		this.crashToDate = crashToDate;
		this.countyId = countyId;
		this.addedFromDate = addedFromDate;
		this.addedToDate = addedToDate;
		this.numberOfDays = numberOfDays;
		this.recordsPerPage = recordsPerPage;
		this.pageNumber = pageNumber;
		this.isRunnerReport = isRunnerReport;
		this.callerAdminId = callerAdminId;
		this.callerId = callerId;
		this.lawyerAdminId = lawyerAdminId;
		this.lawyerId = lawyerId;
		this.isArchived = isArchived;
		this.archivedFromDate = archivedFromDate;
		this.archivedToDate = archivedToDate;
		this.directReportStatus = directReportStatus;
		this.reportFrom = reportFrom;
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
