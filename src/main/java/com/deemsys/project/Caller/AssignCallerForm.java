package com.deemsys.project.Caller;

import java.util.List;

public class AssignCallerForm {

	private List<String> patientId;
	private Integer callerId;
	public List<String> getPatientId() {
		return patientId;
	}
	public void setPatientId(List<String> patientId) {
		this.patientId = patientId;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public AssignCallerForm(List<String> patientId, Integer callerId) {
		super();
		this.patientId = patientId;
		this.callerId = callerId;
	}
	public AssignCallerForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
