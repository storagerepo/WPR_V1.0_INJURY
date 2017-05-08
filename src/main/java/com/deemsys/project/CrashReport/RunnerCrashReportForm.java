package com.deemsys.project.CrashReport;

import java.util.List;

import com.deemsys.project.patient.PatientForm;

public class RunnerCrashReportForm {
	private String localReportNumber;
	private String crashDate;
	private String county;
	private String filePath;
	private Integer reportFrom;
	private String reportPrefixCode;
	private List<PatientForm> patientForms;
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	public String getCrashDate() {
		return crashDate;
	}
	public void setCrashDate(String crashDate) {
		this.crashDate = crashDate;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getReportFrom() {
		return reportFrom;
	}
	public void setReportFrom(Integer reportFrom) {
		this.reportFrom = reportFrom;
	}
	public List<PatientForm> getPatientForms() {
		return patientForms;
	}
	public String getReportPrefixCode() {
		return reportPrefixCode;
	}
	public void setReportPrefixCode(String reportPrefixCode) {
		this.reportPrefixCode = reportPrefixCode;
	}
	public void setPatientForms(List<PatientForm> patientForms) {
		this.patientForms = patientForms;
	}
	public RunnerCrashReportForm(String localReportNumber, String crashDate,
			String county, String filePath, String crashIdPrefix, Integer reportFrom, String reportPrefixCode, List<PatientForm> patientForms) {
		super();
		this.localReportNumber = localReportNumber;
		this.crashDate = crashDate;
		this.county = county;
		this.filePath = filePath;
		this.reportFrom = reportFrom;
		this.reportPrefixCode = reportPrefixCode;
		this.patientForms = patientForms;
	}
	public RunnerCrashReportForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
