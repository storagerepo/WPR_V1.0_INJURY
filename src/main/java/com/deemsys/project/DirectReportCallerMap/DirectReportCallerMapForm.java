package com.deemsys.project.DirectReportCallerMap;



/**
 * 
 * @author Deemsys
 * 
 */
public class DirectReportCallerMapForm {

	private String[] crashId;
	private Integer callerId;
	private Integer status;
	
	public String[] getCrashId() {
		return crashId;
	}

	public void setCrashId(String[] crashId) {
		this.crashId = crashId;
	}

	public Integer getCallerId() {
		return callerId;
	}

	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public DirectReportCallerMapForm(String[] crashId, Integer callerId,
			Integer status) {
		super();
		this.crashId = crashId;
		this.callerId = callerId;
		this.status = status;
	}

	public DirectReportCallerMapForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
