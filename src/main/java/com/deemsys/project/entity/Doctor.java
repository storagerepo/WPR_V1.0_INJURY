package com.deemsys.project.entity;

// Generated 16 Jun, 2017 12:16:19 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Doctor generated by hbm2java
 */
@Entity
@Table(name = "doctor", catalog = "injuryreportsdb")
public class Doctor implements java.io.Serializable {

	private Integer doctorId;
	private Clinic clinic;
	private String doctorName;
	private Integer titleDr;
	private Integer titleDc;
	private Integer status;

	public Doctor() {
	}

	public Doctor(Clinic clinic, String doctorName, Integer titleDr,
			Integer titleDc, Integer status) {
		this.clinic = clinic;
		this.doctorName = doctorName;
		this.titleDr = titleDr;
		this.titleDc = titleDc;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "doctor_id", unique = true, nullable = false)
	public Integer getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id")
	public Clinic getClinic() {
		return this.clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	@Column(name = "doctor_name", length = 60)
	public String getDoctorName() {
		return this.doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
