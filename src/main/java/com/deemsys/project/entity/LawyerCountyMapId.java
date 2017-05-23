package com.deemsys.project.entity;

// Generated 23 May, 2017 4:20:19 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * LawyerCountyMapId generated by hbm2java
 */
@Embeddable
public class LawyerCountyMapId implements java.io.Serializable {

	private int lawyerId;
	private int countyId;

	public LawyerCountyMapId() {
	}

	public LawyerCountyMapId(int lawyerId, int countyId) {
		this.lawyerId = lawyerId;
		this.countyId = countyId;
	}

	@Column(name = "lawyer_id", nullable = false)
	public int getLawyerId() {
		return this.lawyerId;
	}

	public void setLawyerId(int lawyerId) {
		this.lawyerId = lawyerId;
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
		if (!(other instanceof LawyerCountyMapId))
			return false;
		LawyerCountyMapId castOther = (LawyerCountyMapId) other;

		return (this.getLawyerId() == castOther.getLawyerId())
				&& (this.getCountyId() == castOther.getCountyId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getLawyerId();
		result = 37 * result + this.getCountyId();
		return result;
	}

}
