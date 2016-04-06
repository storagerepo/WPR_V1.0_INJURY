package com.deemsys.project.Appointments;

import java.util.Date;

/**
 * 
 * @author Deemsys
 * 
 */
public class AppointmentsForm {

	private Long id;
	private String patientId;
	private String patientName;
	private Date scheduledDate;
	private String scheduledDateTime;
	private String notes;
	private Integer status;
	private Long callLogId;
	private Integer clinicId;
	private Integer doctorId;
	private String doctorName;
	private String clinicName;
	private String callerFirstName;
	private String callerLastName;
	private String callerAdminFirstName;
	private String callerAdminLastName;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getScheduledDateTime() {
		return scheduledDateTime;
	}

	public void setScheduledDateTime(String scheduledDateTime) {
		this.scheduledDateTime = scheduledDateTime;
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

	public Long getCallLogId() {
		return callLogId;
	}

	public void setCallLogId(Long callLogId) {
		this.callLogId = callLogId;
	}

	public Integer getClinicId() {
		return clinicId;
	}

	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	
	public AppointmentsForm(Long id, String patientId, String patientName,String scheduledDateTime, String notes, Integer status,
			Long callLogId, Integer clinicId, Integer doctorId,
			String doctorName, String clinicName) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.patientName = patientName;
		this.scheduledDateTime=scheduledDateTime;
		this.notes = notes;
		this.status = status;
		this.callLogId = callLogId;
		this.clinicId = clinicId;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.clinicName = clinicName;
	}

	public AppointmentsForm(Integer status) {

		// TODO Auto-generated constructor stub
		this.status = status;
	}

	public AppointmentsForm() {
		// TODO Auto-generated constructor stub
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
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

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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

	
	
}
