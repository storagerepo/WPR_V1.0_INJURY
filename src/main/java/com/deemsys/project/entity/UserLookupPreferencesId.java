package com.deemsys.project.entity;

// Generated 2 Jun, 2017 3:50:38 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserLookupPreferencesId generated by hbm2java
 */
@Embeddable
public class UserLookupPreferencesId implements java.io.Serializable {

	private int userId;
	private Integer type;
	private Integer preferedId;
	private Integer status;

	public UserLookupPreferencesId() {
	}

	public UserLookupPreferencesId(int userId) {
		this.userId = userId;
	}

	public UserLookupPreferencesId(int userId, Integer type,
			Integer preferedId, Integer status) {
		this.userId = userId;
		this.type = type;
		this.preferedId = preferedId;
		this.status = status;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "prefered_id")
	public Integer getPreferedId() {
		return this.preferedId;
	}

	public void setPreferedId(Integer preferedId) {
		this.preferedId = preferedId;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserLookupPreferencesId))
			return false;
		UserLookupPreferencesId castOther = (UserLookupPreferencesId) other;

		return (this.getUserId() == castOther.getUserId())
				&& ((this.getType() == castOther.getType()) || (this.getType() != null
						&& castOther.getType() != null && this.getType()
						.equals(castOther.getType())))
				&& ((this.getPreferedId() == castOther.getPreferedId()) || (this
						.getPreferedId() != null
						&& castOther.getPreferedId() != null && this
						.getPreferedId().equals(castOther.getPreferedId())))
				&& ((this.getStatus() == castOther.getStatus()) || (this
						.getStatus() != null && castOther.getStatus() != null && this
						.getStatus().equals(castOther.getStatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getUserId();
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		result = 37
				* result
				+ (getPreferedId() == null ? 0 : this.getPreferedId()
						.hashCode());
		result = 37 * result
				+ (getStatus() == null ? 0 : this.getStatus().hashCode());
		return result;
	}

}
