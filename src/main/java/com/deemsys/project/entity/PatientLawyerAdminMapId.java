package com.deemsys.project.entity;

// Generated Mar 16, 2016 12:32:39 PM by Hibernate Tools 3.4.0.CR1

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PatientLawyerAdminMapId generated by hbm2java
 */
@Embeddable
public class PatientLawyerAdminMapId implements java.io.Serializable {

	private byte[] patientId;
	private int lawyerAdminId;

	public PatientLawyerAdminMapId() {
	}

	public PatientLawyerAdminMapId(byte[] patientId, int lawyerAdminId) {
		this.patientId = patientId;
		this.lawyerAdminId = lawyerAdminId;
	}

	@Column(name = "patient_id", nullable = false)
	public byte[] getPatientId() {
		return this.patientId;
	}

	public void setPatientId(byte[] patientId) {
		this.patientId = patientId;
	}

	@Column(name = "lawyer_admin_id", nullable = false)
	public int getLawyerAdminId() {
		return this.lawyerAdminId;
	}

	public void setLawyerAdminId(int lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PatientLawyerAdminMapId))
			return false;
		PatientLawyerAdminMapId castOther = (PatientLawyerAdminMapId) other;

		return ((this.getPatientId() == castOther.getPatientId()) || (this
				.getPatientId() != null && castOther.getPatientId() != null && Arrays
					.equals(this.getPatientId(), castOther.getPatientId())))
				&& (this.getLawyerAdminId() == castOther.getLawyerAdminId());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getPatientId() == null ? 0 : Arrays.hashCode(this
						.getPatientId()));
		result = 37 * result + this.getLawyerAdminId();
		return result;
	}

}