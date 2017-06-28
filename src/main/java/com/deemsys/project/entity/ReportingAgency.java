package com.deemsys.project.entity;

// Generated 20 Jun, 2017 11:39:16 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ReportingAgency generated by hbm2java
 */
@Entity
@Table(name = "reporting_agency", catalog = "injuryreportsdb")
public class ReportingAgency implements java.io.Serializable {

	private Integer reportingAgencyId;
	private County county;
	private String reportingAgencyName;
	private String code;
	private Integer status;

	public ReportingAgency() {
	}

	public ReportingAgency(County county, String reportingAgencyName,
			String code, Integer status) {
		this.county = county;
		this.reportingAgencyName = reportingAgencyName;
		this.code = code;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "reporting_agency_id", unique = true, nullable = false)
	public Integer getReportingAgencyId() {
		return this.reportingAgencyId;
	}

	public void setReportingAgencyId(Integer reportingAgencyId) {
		this.reportingAgencyId = reportingAgencyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id")
	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@Column(name = "reporting_agency_name", length = 150)
	public String getReportingAgencyName() {
		return this.reportingAgencyName;
	}

	public void setReportingAgencyName(String reportingAgencyName) {
		this.reportingAgencyName = reportingAgencyName;
	}

	@Column(name = "code", length = 45)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}