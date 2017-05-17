package com.deemsys.project.entity;

// Generated 17 May, 2017 10:38:37 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PatientCallerAdminMapId generated by hbm2java
 */
@Embeddable
public class PatientCallerAdminMapId implements java.io.Serializable {

	private String patientId;
	private int callerAdminId;

	public PatientCallerAdminMapId() {
	}

	public PatientCallerAdminMapId(String patientId, int callerAdminId) {
		this.patientId = patientId;
		this.callerAdminId = callerAdminId;
	}

	@Column(name = "patient_id", nullable = false, length = 32)
	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@Column(name = "caller_admin_id", nullable = false)
	public int getCallerAdminId() {
		return this.callerAdminId;
	}

	public void setCallerAdminId(int callerAdminId) {
		this.callerAdminId = callerAdminId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PatientCallerAdminMapId))
			return false;
		PatientCallerAdminMapId castOther = (PatientCallerAdminMapId) other;

		return ((this.getPatientId() == castOther.getPatientId()) || (this
				.getPatientId() != null && castOther.getPatientId() != null && this
				.getPatientId().equals(castOther.getPatientId())))
				&& (this.getCallerAdminId() == castOther.getCallerAdminId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPatientId() == null ? 0 : this.getPatientId().hashCode());
		result = 37 * result + this.getCallerAdminId();
		return result;
	}

}
