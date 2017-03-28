package com.deemsys.project.patient;

import java.util.List;

public class PatientSearchResultGroupBy {

	private String localReportNumber;
	private String unitInError;
	private String crashDate;
	private String addedDate;
	private Integer isRunnerReport;
	private String runnerReportAddedDate;
	private Integer numberOfPatients;
	private List<PatientSearchList> patientSearchLists;

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

	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public List<PatientSearchList> getPatientSearchLists() {
		return patientSearchLists;
	}

	public void setPatientSearchLists(List<PatientSearchList> patientSearchLists) {
		this.patientSearchLists = patientSearchLists;
	}

	public Integer getIsRunnerReport() {
		return isRunnerReport;
	}

	public void setIsRunnerReport(Integer isRunnerReport) {
		this.isRunnerReport = isRunnerReport;
	}

	public String getRunnerReportAddedDate() {
		return runnerReportAddedDate;
	}

	public void setRunnerReportAddedDate(String runnerReportAddedDate) {
		this.runnerReportAddedDate = runnerReportAddedDate;
	}

	public Integer getNumberOfPatients() {
		return numberOfPatients;
	}

	public void setNumberOfPatients(Integer numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}

	public String getUnitInError() {
		return unitInError;
	}

	public void setUnitInError(String unitInError) {
		this.unitInError = unitInError;
	}

	public PatientSearchResultGroupBy(String localReportNumber,String unitInError,String crashDate,
			String addedDate, Integer isRunnerReport, String runnerReportAddedDate, Integer numberOfPatients,List<PatientSearchList> patientSearchLists) {
		super();
		this.localReportNumber = localReportNumber;
		this.unitInError=unitInError;
		this.crashDate = crashDate;
		this.addedDate = addedDate;
		this.isRunnerReport = isRunnerReport;
		this.runnerReportAddedDate = runnerReportAddedDate;
		this.numberOfPatients=numberOfPatients;
		this.patientSearchLists=patientSearchLists;
	}

	public PatientSearchResultGroupBy() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
