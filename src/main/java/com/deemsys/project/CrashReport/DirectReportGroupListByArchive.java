package com.deemsys.project.CrashReport;

import java.util.List;

public class DirectReportGroupListByArchive {
	private String archivedDate;
	private List<CrashReportForm> crashReportForms;
	public String getArchivedDate() {
		return archivedDate;
	}
	public void setArchivedDate(String archivedDate) {
		this.archivedDate = archivedDate;
	}
	public List<CrashReportForm> getCrashReportForms() {
		return crashReportForms;
	}
	public void setCrashReportForms(List<CrashReportForm> crashReportForms) {
		this.crashReportForms = crashReportForms;
	}
	public DirectReportGroupListByArchive(String archivedDate,
			List<CrashReportForm> crashReportForms) {
		super();
		this.archivedDate = archivedDate;
		this.crashReportForms = crashReportForms;
	}
	public DirectReportGroupListByArchive() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
