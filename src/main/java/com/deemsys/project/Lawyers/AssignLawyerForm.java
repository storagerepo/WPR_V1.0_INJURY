package com.deemsys.project.Lawyers;

import java.util.List;

public class AssignLawyerForm {

	private List<String> patientId;
	private Integer lawyerId;
	public List<String> getPatientId() {
		return patientId;
	}
	public void setPatientId(List<String> patientId) {
		this.patientId = patientId;
	}
	public Integer getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}
	public AssignLawyerForm(List<String> patientId, Integer lawyerId) {
		super();
		this.patientId = patientId;
		this.lawyerId = lawyerId;
	}
	public AssignLawyerForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
