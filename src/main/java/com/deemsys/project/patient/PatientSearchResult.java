package com.deemsys.project.patient;

import java.util.List;

public class PatientSearchResult {

	private Long totalNoOfRecord;
	private List<PatientSearchResultGroupBy> searchResult;
	public Long getTotalNoOfRecord() {
		return totalNoOfRecord;
	}
	public void setTotalNoOfRecord(Long totalNoOfRecord) {
		this.totalNoOfRecord = totalNoOfRecord;
	}
	public List<PatientSearchResultGroupBy> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(List<PatientSearchResultGroupBy> searchResult) {
		this.searchResult = searchResult;
	}
	public PatientSearchResult(Long totalNoOfRecord,
			List<PatientSearchResultGroupBy> searchResult) {
		super();
		this.totalNoOfRecord = totalNoOfRecord;
		this.searchResult = searchResult;
	}
	public PatientSearchResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
