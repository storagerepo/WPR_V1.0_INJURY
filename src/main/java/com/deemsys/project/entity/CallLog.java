package com.deemsys.project.entity;

// Generated Mar 16, 2016 12:32:39 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CallLog generated by hbm2java
 */
@Entity
@Table(name = "call_log", catalog = "injury_latest")
public class CallLog implements java.io.Serializable {

	private Long callLogId;
	private PatientCallerAdminMap patientCallerAdminMap;
	private Date timeStamp;
	private Integer response;
	private String notes;
	private Integer status;
	private Set<Appointments> appointmentses = new HashSet<Appointments>(0);

	public CallLog() {
	}

	public CallLog(PatientCallerAdminMap patientCallerAdminMap, Date timeStamp,
			Integer response, String notes, Integer status,
			Set<Appointments> appointmentses) {
		this.patientCallerAdminMap = patientCallerAdminMap;
		this.timeStamp = timeStamp;
		this.response = response;
		this.notes = notes;
		this.status = status;
		this.appointmentses = appointmentses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "call_log_id", unique = true, nullable = false)
	public Long getCallLogId() {
		return this.callLogId;
	}

	public void setCallLogId(Long callLogId) {
		this.callLogId = callLogId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "caller_admin_id", referencedColumnName = "patient_id"),
			@JoinColumn(name = "patient_id", referencedColumnName = "caller_admin_id") })
	public PatientCallerAdminMap getPatientCallerAdminMap() {
		return this.patientCallerAdminMap;
	}

	public void setPatientCallerAdminMap(
			PatientCallerAdminMap patientCallerAdminMap) {
		this.patientCallerAdminMap = patientCallerAdminMap;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time_stamp", length = 19)
	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Column(name = "response")
	public Integer getResponse() {
		return this.response;
	}

	public void setResponse(Integer response) {
		this.response = response;
	}

	@Column(name = "notes", length = 600)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "callLog")
	public Set<Appointments> getAppointmentses() {
		return this.appointmentses;
	}

	public void setAppointmentses(Set<Appointments> appointmentses) {
		this.appointmentses = appointmentses;
	}

}