package com.deemsys.project.entity;

// Generated Oct 1, 2015 1:05:03 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ClinicTimings generated by hbm2java
 */
@Entity
@Table(name = "ClinicTimings", catalog = "injury")
public class ClinicTimings implements java.io.Serializable {

	private ClinicTimingsId id;
	private Clinics clinics;
	private String startTime;
	private String endTime;
	private Integer isWorkingDay;

	public ClinicTimings() {
	}

	public ClinicTimings(ClinicTimingsId id, Clinics clinics) {
		this.id = id;
		this.clinics = clinics;
	}

	public ClinicTimings(ClinicTimingsId id, Clinics clinics, String startTime,
			String endTime, Integer isWorkingDay) {
		this.id = id;
		this.clinics = clinics;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isWorkingDay = isWorkingDay;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "clinicId", column = @Column(name = "clinic_id", nullable = false)),
			@AttributeOverride(name = "day", column = @Column(name = "day", nullable = false)) })
	public ClinicTimingsId getId() {
		return this.id;
	}

	public void setId(ClinicTimingsId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", nullable = false, insertable = false, updatable = false)
	public Clinics getClinics() {
		return this.clinics;
	}

	public void setClinics(Clinics clinics) {
		this.clinics = clinics;
	}

	@Column(name = "start_time", length = 45)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 45)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "is_working_day")
	public Integer getIsWorkingDay() {
		return this.isWorkingDay;
	}

	public void setIsWorkingDay(Integer isWorkingDay) {
		this.isWorkingDay = isWorkingDay;
	}

}
