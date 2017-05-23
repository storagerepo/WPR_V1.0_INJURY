package com.deemsys.project.entity;

// Generated 23 May, 2017 4:20:19 PM by Hibernate Tools 3.4.0.CR1

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
@Table(name = "clinic_timings", catalog = "injuryreportsdbtest")
public class ClinicTimings implements java.io.Serializable {

	private ClinicTimingsId id;
	private Clinic clinic;
	private String startTime;
	private String endTime;
	private String startsBreak;
	private String endsBreak;
	private Integer isWorkingDay;
	private Integer isAppointmentDay;
	private Integer status;

	public ClinicTimings() {
	}

	public ClinicTimings(ClinicTimingsId id, Clinic clinic) {
		this.id = id;
		this.clinic = clinic;
	}

	public ClinicTimings(ClinicTimingsId id, Clinic clinic, String startTime,
			String endTime, String startsBreak, String endsBreak,
			Integer isWorkingDay, Integer isAppointmentDay, Integer status) {
		this.id = id;
		this.clinic = clinic;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startsBreak = startsBreak;
		this.endsBreak = endsBreak;
		this.isWorkingDay = isWorkingDay;
		this.isAppointmentDay = isAppointmentDay;
		this.status = status;
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
	public Clinic getClinic() {
		return this.clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
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

	@Column(name = "starts_break", length = 45)
	public String getStartsBreak() {
		return this.startsBreak;
	}

	public void setStartsBreak(String startsBreak) {
		this.startsBreak = startsBreak;
	}

	@Column(name = "ends_break", length = 45)
	public String getEndsBreak() {
		return this.endsBreak;
	}

	public void setEndsBreak(String endsBreak) {
		this.endsBreak = endsBreak;
	}

	@Column(name = "is_working_day")
	public Integer getIsWorkingDay() {
		return this.isWorkingDay;
	}

	public void setIsWorkingDay(Integer isWorkingDay) {
		this.isWorkingDay = isWorkingDay;
	}

	@Column(name = "is_appointment_day")
	public Integer getIsAppointmentDay() {
		return this.isAppointmentDay;
	}

	public void setIsAppointmentDay(Integer isAppointmentDay) {
		this.isAppointmentDay = isAppointmentDay;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
