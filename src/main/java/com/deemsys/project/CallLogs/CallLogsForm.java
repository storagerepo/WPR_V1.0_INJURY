package com.deemsys.project.CallLogs;

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
public class CallLogsForm {

	public CallLogsForm(Integer id, Integer patientId, String timeStamp,
			String response, String notes) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
	}
	private Integer id;
	private Integer appointmentId;
	private Integer patientId;
	private String timeStamp;
	private String response;
	private String notes;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAppointmentId() {
		return appointmentId;
	}
	
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public CallLogsForm(Integer id, Integer appointmentId,Integer patientId,
			String timeStamp, String response, String notes) {
		super();
		this.id = id;
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
	}
	public CallLogsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CallLogsForm(Integer id)
	{
		this.id=id;
	}
	
}
