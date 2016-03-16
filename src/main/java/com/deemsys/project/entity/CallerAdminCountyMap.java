package com.deemsys.project.entity;

// Generated Mar 16, 2016 12:32:39 PM by Hibernate Tools 3.4.0.CR1

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
 * CallerAdminCountyMap generated by hbm2java
 */
@Entity
@Table(name = "caller_admin_county_map", catalog = "injury_latest")
public class CallerAdminCountyMap implements java.io.Serializable {

	private CallerAdminCountyMapId id;
	private CallerAdmin callerAdmin;
	private County county;
	private Integer status;

	public CallerAdminCountyMap() {
	}

	public CallerAdminCountyMap(CallerAdminCountyMapId id,
			CallerAdmin callerAdmin, County county) {
		this.id = id;
		this.callerAdmin = callerAdmin;
		this.county = county;
	}

	public CallerAdminCountyMap(CallerAdminCountyMapId id,
			CallerAdmin callerAdmin, County county, Integer status) {
		this.id = id;
		this.callerAdmin = callerAdmin;
		this.county = county;
		this.status = status;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "callerAdminId", column = @Column(name = "caller_admin_id", nullable = false)),
			@AttributeOverride(name = "countyId", column = @Column(name = "county_id", nullable = false)) })
	public CallerAdminCountyMapId getId() {
		return this.id;
	}

	public void setId(CallerAdminCountyMapId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "caller_admin_id", nullable = false, insertable = false, updatable = false)
	public CallerAdmin getCallerAdmin() {
		return this.callerAdmin;
	}

	public void setCallerAdmin(CallerAdmin callerAdmin) {
		this.callerAdmin = callerAdmin;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id", nullable = false, insertable = false, updatable = false)
	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
