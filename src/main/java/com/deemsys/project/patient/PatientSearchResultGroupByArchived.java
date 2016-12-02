package com.deemsys.project.patient;

import java.util.List;

public class PatientSearchResultGroupByArchived {
	private String archivedDate;
	private List<PatientSearchList> patientSearchLists;
	public String getArchivedDate() {
		return archivedDate;
	}
	public void setArchivedDate(String archivedDate) {
		this.archivedDate = archivedDate;
	}
	public List<PatientSearchList> getPatientSearchLists() {
		return patientSearchLists;
	}
	public void setPatientSearchLists(List<PatientSearchList> patientSearchLists) {
		this.patientSearchLists = patientSearchLists;
	}
	public PatientSearchResultGroupByArchived(String archivedDate,
			List<PatientSearchList> patientSearchLists) {
		super();
		this.archivedDate = archivedDate;
		this.patientSearchLists = patientSearchLists;
	}
	public PatientSearchResultGroupByArchived() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
