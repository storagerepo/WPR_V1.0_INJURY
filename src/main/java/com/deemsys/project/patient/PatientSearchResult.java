package com.deemsys.project.patient;

import java.util.List;

public class PatientSearchResult {

	private String archivedDate;
	private List<PatientSearchResultGroupBy> searchResult;
	
	public String getArchivedDate() {
		return archivedDate;
	}
	public void setArchivedDate(String archivedDate) {
		this.archivedDate = archivedDate;
	}
	public List<PatientSearchResultGroupBy> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(List<PatientSearchResultGroupBy> searchResult) {
		this.searchResult = searchResult;
	}
	public PatientSearchResult(String archivedDate,
			List<PatientSearchResultGroupBy> searchResult) {
		super();
		this.archivedDate = archivedDate;
		this.searchResult = searchResult;
	}
	public PatientSearchResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
