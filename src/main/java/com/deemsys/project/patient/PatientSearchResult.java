package com.deemsys.project.patient;

import java.util.List;

public class PatientSearchResult {

	private Integer totalNoOfRecord;
	private List<PatientSearchList> patientSearchLists;
	public Integer getTotalNoOfRecord() {
		return totalNoOfRecord;
	}
	public void setTotalNoOfRecord(Integer totalNoOfRecord) {
		this.totalNoOfRecord = totalNoOfRecord;
	}
	public List<PatientSearchList> getPatientSearchLists() {
		return patientSearchLists;
	}
	public void setPatientSearchLists(List<PatientSearchList> patientSearchLists) {
		this.patientSearchLists = patientSearchLists;
	}
	public PatientSearchResult(Integer totalNoOfRecord,
			List<PatientSearchList> patientSearchLists) {
		super();
		this.totalNoOfRecord = totalNoOfRecord;
		this.patientSearchLists = patientSearchLists;
	}
	public PatientSearchResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
