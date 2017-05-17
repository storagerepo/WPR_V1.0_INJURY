package com.deemsys.project.DirectReportLawyerMap;


import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author Deemsys
 * 
 */
public class DirectReportLaywerMapForm {

	private String[] crashId;
	private Integer lawyerId;
	private Integer status;

	public String[] getCrashId() {
		return crashId;
	}

	public void setCrashId(String[] crashId) {
		this.crashId = crashId;
	}

	public Integer getLawyerId() {
		return lawyerId;
	}

	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public DirectReportLaywerMapForm(String[] crashId, Integer lawyerId,
			Integer status) {
		super();
		this.crashId = crashId;
		this.lawyerId = lawyerId;
		this.status = status;
	}

	public DirectReportLaywerMapForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
