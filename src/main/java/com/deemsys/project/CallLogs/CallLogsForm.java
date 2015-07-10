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

	private Integer id;
	private Integer appointmentId;
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
	public CallLogsForm(Integer id, Integer appointmentId,
			String timeStamp, String response, String notes) {
		super();
		this.id = id;
		this.appointmentId = appointmentId;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
	}
	public CallLogsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
