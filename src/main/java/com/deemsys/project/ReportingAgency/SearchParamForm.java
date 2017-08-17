package com.deemsys.project.ReportingAgency;

public class SearchParamForm {

	private int countyId;
	private String ncicCode;
	private String reportingAgencyName;
	private Integer pageNumber;
	private Integer recordsPerPage;
	public int getCountyId() {
		return countyId;
	}
	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}
	public String getNcicCode() {
		return ncicCode;
	}
	public void setNcicCode(String ncicCode) {
		this.ncicCode = ncicCode;
	}
	public String getReportingAgencyName() {
		return reportingAgencyName;
	}
	public void setReportingAgencyName(String reportingAgencyName) {
		this.reportingAgencyName = reportingAgencyName;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public SearchParamForm(int countyId, String ncicCode, String reportingAgencyName, Integer pageNumber,
			Integer recordsPerPage) {
		super();
		this.countyId = countyId;
		this.ncicCode = ncicCode;
		this.reportingAgencyName = reportingAgencyName;
		this.pageNumber = pageNumber;
		this.recordsPerPage = recordsPerPage;
	}
	public SearchParamForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
