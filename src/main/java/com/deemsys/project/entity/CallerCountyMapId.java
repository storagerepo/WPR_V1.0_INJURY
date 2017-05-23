package com.deemsys.project.entity;

// Generated 23 May, 2017 4:20:19 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CallerCountyMapId generated by hbm2java
 */
@Embeddable
public class CallerCountyMapId implements java.io.Serializable {

	private int callerId;
	private int countyId;

	public CallerCountyMapId() {
	}

	public CallerCountyMapId(int callerId, int countyId) {
		this.callerId = callerId;
		this.countyId = countyId;
	}

	@Column(name = "caller_id", nullable = false)
	public int getCallerId() {
		return this.callerId;
	}

	public void setCallerId(int callerId) {
		this.callerId = callerId;
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
		if (!(other instanceof CallerCountyMapId))
			return false;
		CallerCountyMapId castOther = (CallerCountyMapId) other;

		return (this.getCallerId() == castOther.getCallerId())
				&& (this.getCountyId() == castOther.getCountyId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCallerId();
		result = 37 * result + this.getCountyId();
		return result;
	}

}
