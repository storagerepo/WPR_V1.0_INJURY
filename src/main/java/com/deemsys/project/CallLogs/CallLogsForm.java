package com.deemsys.project.CallLogs;

import java.util.Date;

/**
 * 
 * @author Deemsys
 * 
 */
public class CallLogsForm {

	private Long callLogId;
	private String patientId;
	private Integer callerAdminId;
	private Date timeStamp;
	private String convertedTimeStamp;
	private Integer response;
	private String notes;
	private Integer status;
	private Long appointmentId;
	private String callerFirstName;
	private String callerLastName;
	
	public Long getCallLogId() {
		return callLogId;
	}
	public void setCallLogId(Long callLogId) {
		this.callLogId = callLogId;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Integer getResponse() {
		return response;
	}
	public void setResponse(Integer response) {
		this.response = response;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCallerFirstName() {
		return callerFirstName;
	}
	public void setCallerFirstName(String callerFirstName) {
		this.callerFirstName = callerFirstName;
	}
	
	public String getCallerLastName() {
		return callerLastName;
	}
	public void setCallerLastName(String callerLastName) {
		this.callerLastName = callerLastName;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getConvertedTimeStamp() {
		return convertedTimeStamp;
	}
	public void setConvertedTimeStamp(String convertedTimeStamp) {
		this.convertedTimeStamp = convertedTimeStamp;
	}
	public CallLogsForm(Long callLogId, String patientId,
			Integer callerAdminId, String convertedTimeStamp, Integer response,
			String notes, Integer status, Long appointmentId,
			String callerfirstName,String callerLastName) {
		super();
		this.callLogId = callLogId;
		this.patientId = patientId;
		this.callerAdminId = callerAdminId;
		this.convertedTimeStamp = convertedTimeStamp;
		this.response = response;
		this.notes = notes;
		this.status = status;
		this.appointmentId = appointmentId;
		this.callerFirstName=callerfirstName;
		this.callerLastName=callerLastName;
	}
	
	public CallLogsForm(Long callLogId, String patientId,
			Integer callerAdminId, String convertedTimeStamp, Integer response,
			String notes, Integer status) {
		super();
		this.callLogId = callLogId;
		this.patientId = patientId;
		this.callerAdminId = callerAdminId;
		this.convertedTimeStamp = convertedTimeStamp;
		this.response = response;
		this.notes = notes;
		this.status = status;
	}
	public CallLogsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
 
	

}
