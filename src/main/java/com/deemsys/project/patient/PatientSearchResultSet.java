package com.deemsys.project.patient;

import java.util.List;

public class PatientSearchResultSet {
	private Long totalNoOfRecords;
	private List<PatientSearchList> patientSearchLists;
	public Long getTotalNoOfRecords() {
		return totalNoOfRecords;
	}
	public void setTotalNoOfRecords(Long totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}
	public List<PatientSearchList> getPatientSearchLists() {
		return patientSearchLists;
	}
	public void setPatientSearchLists(List<PatientSearchList> patientSearchLists) {
		this.patientSearchLists = patientSearchLists;
	}
	public PatientSearchResultSet(Long totalNoOfRecords,
			List<PatientSearchList> patientSearchLists) {
		super();
		this.totalNoOfRecords = totalNoOfRecords;
		this.patientSearchLists = patientSearchLists;
	}
	
	
}
