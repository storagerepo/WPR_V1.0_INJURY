package com.deemsys.project.patient;

/**
 * 
 * @author Deemsys
 * 
 */
public class PatientSearchForm {
	
	private Integer pageNumber;
	private Integer itemsPerPage;
	private String localReportNumber;
	private String county;
	private String crashDate;
	private Integer days;
	private String recordedFromDate;
	private String recordedToDate;
	private String name;
	private String customDate;
	
	
	
	
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
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
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
	
	
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getRecordedFromDate() {
		return recordedFromDate;
	}
	public void setRecordedFromDate(String recordedFromDate) {
		this.recordedFromDate = recordedFromDate;
	}
	public String getRecordedToDate() {
		return recordedToDate;
	}
	public void setRecordedToDate(String recordedToDate) {
		this.recordedToDate = recordedToDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String getCustomDate() {
		return customDate;
	}
	public void setCustomDate(String customDate) {
		this.customDate = customDate;
	}
	public PatientSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PatientSearchForm(Integer pageNumber, Integer itemsPerPage, String localReportNumber,
			String county, String crashDate,Integer days,String recordedFromDate,String recordedToDate, String name, String customDate) {
		super();
		this.pageNumber =pageNumber;
		this.itemsPerPage=itemsPerPage;
		this.localReportNumber = localReportNumber;
		this.county = county;
		this.crashDate = crashDate;
		this.days=days;
		this.recordedFromDate=recordedFromDate;
		this.recordedToDate=recordedToDate;
		this.name = name;
		this.customDate=customDate;
	}
	
	
	
}