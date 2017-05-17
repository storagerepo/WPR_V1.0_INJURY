package com.deemsys.project.entity;

// Generated 17 May, 2017 10:38:37 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PatientLawyerAdminMap generated by hbm2java
 */
@Entity
@Table(name = "patient_lawyer_admin_map", catalog = "injuryreportsdbtest")
public class PatientLawyerAdminMap implements java.io.Serializable {

	private PatientLawyerAdminMapId id;
	private Lawyer lawyer;
	private LawyerAdmin lawyerAdmin;
	private Patient patient;
	private String notes;
	private Integer patientStatus;
	private Integer isArchived;
	private Date archivedDate;
	private String archivedDateTime;
	private Integer status;

	public PatientLawyerAdminMap() {
	}

	public PatientLawyerAdminMap(PatientLawyerAdminMapId id,
			LawyerAdmin lawyerAdmin, Patient patient) {
		this.id = id;
		this.lawyerAdmin = lawyerAdmin;
		this.patient = patient;
	}

	public PatientLawyerAdminMap(PatientLawyerAdminMapId id, Lawyer lawyer,
			LawyerAdmin lawyerAdmin, Patient patient, String notes,
			Integer patientStatus, Integer isArchived, Date archivedDate,
			String archivedDateTime, Integer status) {
		this.id = id;
		this.lawyer = lawyer;
		this.lawyerAdmin = lawyerAdmin;
		this.patient = patient;
		this.notes = notes;
		this.patientStatus = patientStatus;
		this.isArchived = isArchived;
		this.archivedDate = archivedDate;
		this.archivedDateTime = archivedDateTime;
		this.status = status;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "patientId", column = @Column(name = "patient_id", nullable = false, length = 32)),
			@AttributeOverride(name = "lawyerAdminId", column = @Column(name = "lawyer_admin_id", nullable = false)) })
	public PatientLawyerAdminMapId getId() {
		return this.id;
	}

	public void setId(PatientLawyerAdminMapId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lawyer_id")
	public Lawyer getLawyer() {
		return this.lawyer;
	}

	public void setLawyer(Lawyer lawyer) {
		this.lawyer = lawyer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lawyer_admin_id", nullable = false, insertable = false, updatable = false)
	public LawyerAdmin getLawyerAdmin() {
		return this.lawyerAdmin;
	}

	public void setLawyerAdmin(LawyerAdmin lawyerAdmin) {
		this.lawyerAdmin = lawyerAdmin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false, insertable = false, updatable = false)
	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Column(name = "notes", length = 600)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "patient_status")
	public Integer getPatientStatus() {
		return this.patientStatus;
	}

	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}

	@Column(name = "is_archived")
	public Integer getIsArchived() {
		return this.isArchived;
	}

	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "archived_date", length = 10)
	public Date getArchivedDate() {
		return this.archivedDate;
	}

	public void setArchivedDate(Date archivedDate) {
		this.archivedDate = archivedDate;
	}

	@Column(name = "archived_date_time", length = 45)
	public String getArchivedDateTime() {
		return this.archivedDateTime;
	}

	public void setArchivedDateTime(String archivedDateTime) {
		this.archivedDateTime = archivedDateTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
