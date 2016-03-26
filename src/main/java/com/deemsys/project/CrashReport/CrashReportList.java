package com.deemsys.project.CrashReport;

import java.util.List;

public class CrashReportList {

	private Integer totalNoOfRecords;
	private List<CrashReportForm> crashReportForms;
	public Integer getTotalNoOfRecords() {
		return totalNoOfRecords;
	}
	public void setTotalNoOfRecords(Integer totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}
	public List<CrashReportForm> getCrashReportForms() {
		return crashReportForms;
	}
	public void setCrashReportForms(List<CrashReportForm> crashReportForms) {
		this.crashReportForms = crashReportForms;
	}
	public CrashReportList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CrashReportList(Integer totalNoOfRecords,
			List<CrashReportForm> crashReportForms) {
		super();
		this.totalNoOfRecords = totalNoOfRecords;
		this.crashReportForms = crashReportForms;
	}
	
	
	
}
