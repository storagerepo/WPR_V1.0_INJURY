package com.deemsys.project.patient;

import java.util.List;

public class PatientGroupedSearchResult {
	private Long totalNoOfRecord;
	private List<PatientSearchResult> patientSearchResults;
	public Long getTotalNoOfRecord() {
		return totalNoOfRecord;
	}
	public void setTotalNoOfRecord(Long totalNoOfRecord) {
		this.totalNoOfRecord = totalNoOfRecord;
	}
	public List<PatientSearchResult> getPatientSearchResults() {
		return patientSearchResults;
	}
	public void setPatientSearchResults(
			List<PatientSearchResult> patientSearchResults) {
		this.patientSearchResults = patientSearchResults;
	}
	public PatientGroupedSearchResult(Long totalNoOfRecord,
			List<PatientSearchResult> patientSearchResults) {
		super();
		this.totalNoOfRecord = totalNoOfRecord;
		this.patientSearchResults = patientSearchResults;
	}
	public PatientGroupedSearchResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
