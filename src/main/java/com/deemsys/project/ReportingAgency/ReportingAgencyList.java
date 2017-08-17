package com.deemsys.project.ReportingAgency;

import java.util.List;

public class ReportingAgencyList {
	
	private Long totalRecords;
	private List<ReportingAgencyForm> reportingAgencyForms;
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<ReportingAgencyForm> getReportingAgencyForms() {
		return reportingAgencyForms;
	}
	public void setReportingAgencyForms(List<ReportingAgencyForm> reportingAgencyForms) {
		this.reportingAgencyForms = reportingAgencyForms;
	}
	public ReportingAgencyList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReportingAgencyList(Long totalRecords2, List<ReportingAgencyForm> reportingAgencyForms) {
		super();
		this.totalRecords = totalRecords2;
		this.reportingAgencyForms = reportingAgencyForms;
	}
	
	

}
