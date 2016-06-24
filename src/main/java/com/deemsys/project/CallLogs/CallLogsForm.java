package com.deemsys.project.CallLogs;

import java.util.Date;

import com.deemsys.project.Appointments.AppointmentsForm;

/**
 * 
 * @author Deemsys
 * 
 */
public class CallLogsForm {

	private Long callLogId;
	private String patientId;
	private String[] multiplePatientId;
	private Integer callerAdminId;
	private Integer callerId;
	private String timeStamp;
	private Integer response;
	private String notes;
	private Integer status;
	private Long appointmentId;
	private AppointmentsForm appointmentsForm;
	private String callerFirstName;
	private String callerLastName;
	private String callerAdminFirstName;
	private String callerAdminLastName;
	private Integer isAllowToEdit;
	
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
	public String[] getMultiplePatientId() {
		return multiplePatientId;
	}
	public void setMultiplePatientId(String[] multiplePatientId) {
		this.multiplePatientId = multiplePatientId;
	}
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
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
	
	public String getCallerAdminFirstName() {
		return callerAdminFirstName;
	}
	public void setCallerAdminFirstName(String callerAdminFirstName) {
		this.callerAdminFirstName = callerAdminFirstName;
	}
	public String getCallerAdminLastName() {
		return callerAdminLastName;
	}
	public void setCallerAdminLastName(String callerAdminLastName) {
		this.callerAdminLastName = callerAdminLastName;
	}
	public Integer getIsAllowToEdit() {
		return isAllowToEdit;
	}
	public void setIsAllowToEdit(Integer isAllowToEdit) {
		this.isAllowToEdit = isAllowToEdit;
	}
	public AppointmentsForm getAppointmentsForm() {
		return appointmentsForm;
	}
	public void setAppointmentsForm(AppointmentsForm appointmentsForm) {
		this.appointmentsForm = appointmentsForm;
	}
	public CallLogsForm(Long callLogId, String patientId,
			Integer callerAdminId, String timeStamp, Integer response,
			String notes, Integer status, AppointmentsForm appointmentsForm,
			String callerfirstName,String callerLastName) {
		super();
		this.callLogId = callLogId;
		this.patientId = patientId;
		this.callerAdminId = callerAdminId;
		this.timeStamp=timeStamp;
		this.response = response;
		this.notes = notes;
		this.status = status;
		this.appointmentsForm = appointmentsForm;
		this.callerFirstName=callerfirstName;
		this.callerLastName=callerLastName;
	}
	public CallLogsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
 
	

}
