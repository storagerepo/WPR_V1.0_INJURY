package com.deemsys.project.entity;

// Generated 5 Jun, 2017 1:18:21 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CallerAdminCountyMapId generated by hbm2java
 */
@Embeddable
public class CallerAdminCountyMapId implements java.io.Serializable {

	private int callerAdminId;
	private int countyId;

	public CallerAdminCountyMapId() {
	}

	public CallerAdminCountyMapId(int callerAdminId, int countyId) {
		this.callerAdminId = callerAdminId;
		this.countyId = countyId;
	}

	@Column(name = "caller_admin_id", nullable = false)
	public int getCallerAdminId() {
		return this.callerAdminId;
	}

	public void setCallerAdminId(int callerAdminId) {
		this.callerAdminId = callerAdminId;
	}

	@Column(name = "county_id", nullable = false)
	public int getCountyId() {
		return this.countyId;
	}

	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CallerAdminCountyMapId))
			return false;
		CallerAdminCountyMapId castOther = (CallerAdminCountyMapId) other;

		return (this.getCallerAdminId() == castOther.getCallerAdminId())
				&& (this.getCountyId() == castOther.getCountyId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCallerAdminId();
		result = 37 * result + this.getCountyId();
		return result;
	}

}
