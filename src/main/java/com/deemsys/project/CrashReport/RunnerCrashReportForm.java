package com.deemsys.project.CrashReport;

import java.util.List;

import com.deemsys.project.patient.PatientForm;

public class RunnerCrashReportForm {
	private String localReportNumber;
	private String crashDate;
	private String county;
	private String filePath;
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
	public List<PatientForm> getPatientForms() {
		return patientForms;
	}
	public void setPatientForms(List<PatientForm> patientForms) {
		this.patientForms = patientForms;
	}
	public RunnerCrashReportForm(String localReportNumber, String crashDate,
			String county, String filePath, List<PatientForm> patientForms) {
		super();
		this.localReportNumber = localReportNumber;
		this.crashDate = crashDate;
		this.county = county;
		this.filePath = filePath;
		this.patientForms = patientForms;
	}
	public RunnerCrashReportForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
