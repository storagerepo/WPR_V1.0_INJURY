package com.deemsys.project.patient;

import java.util.List;

public class PatientSearchResultGroupBy {

	private String localReportNumber;
	private String crashDate;
	private String addedDate;
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

		
	public Integer getNumberOfPatients() {
		return numberOfPatients;
	}

	public void setNumberOfPatients(Integer numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}

	public PatientSearchResultGroupBy(String localReportNumber, String crashDate,
			String addedDate,Integer numberOfPatients,List<PatientSearchList> patientSearchLists) {
		super();
		this.localReportNumber = localReportNumber;
		this.crashDate = crashDate;
		this.addedDate = addedDate;
		this.numberOfPatients=numberOfPatients;
		this.patientSearchLists=patientSearchLists;
	}

	public PatientSearchResultGroupBy() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
