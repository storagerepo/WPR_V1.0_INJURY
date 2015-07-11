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
	private String scheduledDate;
	private String notes;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPatientId() {
		return patientId;
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
	
	public AppointmentsForm(Integer id, Integer patientId,
			String scheduledDate, String notes, Integer status) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.scheduledDate = scheduledDate;
		this.notes = notes;
		this.status = status;
	}
	
	public AppointmentsForm(Integer status) {
		
		// TODO Auto-generated constructor stub
		this.status = status;
	}
	
	
}
