package com.deemsys.project.entity;

// Generated Oct 1, 2015 1:05:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CallLogs generated by hbm2java
 */
@Entity
@Table(name = "CallLogs", catalog = "injury")
public class CallLogs implements java.io.Serializable {

	private Integer id;
	private Patients patients;
	private Appointments appointments;
	private Date timeStamp;
	private String response;
	private String notes;

	public CallLogs() {
	}

	public CallLogs(Patients patients, Appointments appointments,
			Date timeStamp, String response, String notes) {
		this.patients = patients;
		this.appointments = appointments;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	public Patients getPatients() {
		return this.patients;
	}

	public void setPatients(Patients patients) {
		this.patients = patients;
	}

	@ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "appointment_id")
	public Appointments getAppointments() {
		return this.appointments;
	}

	public void setAppointments(Appointments appointments) {
		this.appointments = appointments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time_stamp", length = 19)
	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Column(name = "response", length = 100)
	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Column(name = "notes", length = 600)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
