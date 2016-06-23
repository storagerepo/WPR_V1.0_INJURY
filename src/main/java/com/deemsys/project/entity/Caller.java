package com.deemsys.project.entity;

// Generated Mar 16, 2016 12:32:39 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Caller generated by hbm2java
 */
@Entity
@Table(name = "caller", catalog = "injuryreportsdb")
public class Caller implements java.io.Serializable {

	private Integer callerId;
	private CallerAdmin callerAdmin;
	private Users users;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String notes;
	private Integer status;
	private Integer isDelete;
	private Set<PatientCallerAdminMap> patientCallerAdminMaps = new HashSet<PatientCallerAdminMap>(
			0);
	private Set<CallerCountyMap> callerCountyMaps = new HashSet<CallerCountyMap>(
			0);
	private Set<CallLog> callLogs = new HashSet<CallLog>(
			0);
	public Caller() {
	}

	public Caller(CallerAdmin callerAdmin, Users users, String firstName,
			String lastName, String phoneNumber, String emailAddress,
			String notes, Integer status, Integer isDelete,
			Set<PatientCallerAdminMap> patientCallerAdminMaps,
			Set<CallerCountyMap> callerCountyMaps) {
		this.callerAdmin = callerAdmin;
		this.users = users;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.status = status;
		this.isDelete = isDelete;
		this.patientCallerAdminMaps = patientCallerAdminMaps;
		this.callerCountyMaps = callerCountyMaps;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "caller_id", unique = true, nullable = false)
	public Integer getCallerId() {
		return this.callerId;
	}

	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "caller_admin_id")
	public CallerAdmin getCallerAdmin() {
		return this.callerAdmin;
	}

	public void setCallerAdmin(CallerAdmin callerAdmin) {
		this.callerAdmin = callerAdmin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "first_name", length = 45)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 45)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "phone_number", length = 20)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "email_address", length = 60)
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	
	@Column(name = "is_delete")
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caller")
	public Set<PatientCallerAdminMap> getPatientCallerAdminMaps() {
		return this.patientCallerAdminMaps;
	}

	public void setPatientCallerAdminMaps(
			Set<PatientCallerAdminMap> patientCallerAdminMaps) {
		this.patientCallerAdminMaps = patientCallerAdminMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caller")
	public Set<CallerCountyMap> getCallerCountyMaps() {
		return this.callerCountyMaps;
	}

	public void setCallerCountyMaps(Set<CallerCountyMap> callerCountyMaps) {
		this.callerCountyMaps = callerCountyMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caller")
	public Set<CallLog> getCallLogs() {
		return callLogs;
	}

	public void setCallLogs(Set<CallLog> callLogs) {
		this.callLogs = callLogs;
	}
	
	

}
