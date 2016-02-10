package com.deemsys.project.entity;

// Generated Oct 1, 2015 1:05:03 PM by Hibernate Tools 3.4.0.CR1

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
 * Doctors generated by hbm2java
 */
@Entity
@Table(name = "Doctors", catalog = "injurytest")
public class Doctors implements java.io.Serializable {

	private Integer id;
	private Clinics clinics;
	private String doctorName;
	private String emailId;
	private String contactNumber;
	private String specialistIn;
	private String notes;
	private Integer titleDr;
	private Integer titleDc;
	private Set<Patients> patientses = new HashSet<Patients>(0);

	public Doctors() {
	}

	public Doctors(Clinics clinics, String doctorName, String emailId,
			String contactNumber, String specialistIn, String notes,
			Set<Patients> patientses) {
		this.clinics = clinics;
		this.doctorName = doctorName;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.specialistIn = specialistIn;
		this.notes = notes;
		this.patientses = patientses;
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
	@JoinColumn(name = "clinic_id")
	public Clinics getClinics() {
		return this.clinics;
	}

	public void setClinics(Clinics clinics) {
		this.clinics = clinics;
	}

	@Column(name = "doctor_name", length = 60)
	public String getDoctorName() {
		return this.doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Column(name = "email_id", length = 45)
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "contact_number", length = 45)
	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "specialist_in", length = 45)
	public String getSpecialistIn() {
		return this.specialistIn;
	}

	public void setSpecialistIn(String specialistIn) {
		this.specialistIn = specialistIn;
	}

	@Column(name = "notes", length = 65535)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "title_dr")
	public Integer getTitleDr() {
		return this.titleDr;
	}

	public void setTitleDr(Integer titleDr) {
		this.titleDr = titleDr;
	}
	
	@Column(name = "title_dc")
	public Integer getTitleDc() {
		return this.titleDc;
	}

	public void setTitleDc(Integer titleDc) {
		this.titleDc = titleDc;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doctors")
	public Set<Patients> getPatientses() {
		return this.patientses;
	}

	public void setPatientses(Set<Patients> patientses) {
		this.patientses = patientses;
	}

}
