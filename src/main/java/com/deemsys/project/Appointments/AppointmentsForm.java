package com.deemsys.project.Appointments;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;

/**
 * 
 * @author Deemsys
 *
 */
public class AppointmentsForm {

	
	private Integer id;
	private Integer patientId;
	private String patientName;
	private String scheduledDate;
	private String notes;
	private Integer status;
	private Integer callLogId;
	private Integer clinicId;
	private Integer doctorId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
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
	
	public Integer getCallLogId() {
		return callLogId;
	}
	public void setCallLogId(Integer callLogId) {
		this.callLogId = callLogId;
	}
	public Integer getClinicId() {
		return clinicId;
	}
	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}
	public AppointmentsForm(Integer id, Integer patientId,String patientName,
			String scheduledDate, String notes, Integer status) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.patientName=patientName;
		this.scheduledDate = scheduledDate;
		this.notes = notes;
		this.status = status;
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
	
	
}
