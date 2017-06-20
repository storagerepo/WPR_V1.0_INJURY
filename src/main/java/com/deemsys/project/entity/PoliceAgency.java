package com.deemsys.project.entity;

// Generated 20 Jun, 2017 11:39:16 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * PoliceAgency generated by hbm2java
 */
@Entity
@Table(name = "police_agency", catalog = "injuryreportsdb")
public class PoliceAgency implements java.io.Serializable {

	private int mapId;
	private County county;
	private Integer agencyId;
	private String name;
	private Integer schedulerType;
	private Integer status;
	private Set<CrashReport> crashReports = new HashSet<CrashReport>(0);

	public PoliceAgency() {
	}

	public PoliceAgency(int mapId) {
		this.mapId = mapId;
	}

	public PoliceAgency(int mapId, County county, Integer agencyId,
			String name, Integer schedulerType, Integer status,
			Set<CrashReport> crashReports) {
		this.mapId = mapId;
		this.county = county;
		this.agencyId = agencyId;
		this.name = name;
		this.schedulerType = schedulerType;
		this.status = status;
		this.crashReports = crashReports;
	}

	@Id
	@Column(name = "map_id", unique = true, nullable = false)
	public int getMapId() {
		return this.mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county")
	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@Column(name = "agency_id")
	public Integer getAgencyId() {
		return this.agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "scheduler_type")
	public Integer getSchedulerType() {
		return this.schedulerType;
	}

	public void setSchedulerType(Integer schedulerType) {
		this.schedulerType = schedulerType;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "policeAgency")
	public Set<CrashReport> getCrashReports() {
		return this.crashReports;
	}

	public void setCrashReports(Set<CrashReport> crashReports) {
		this.crashReports = crashReports;
	}

}
