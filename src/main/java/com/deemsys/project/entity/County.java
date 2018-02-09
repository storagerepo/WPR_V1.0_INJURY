package com.deemsys.project.entity;

// Generated 19 Aug, 2017 10:03:05 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * County generated by hbm2java
 */
@Entity
@Table(name = "county", catalog = "injuryreportsdbpro")
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region="county")
public class County implements java.io.Serializable {

	private Integer countyId;
	private String name;
	private Integer status;
	private Set<Patient> patients = new HashSet<Patient>(0);
	private Set<LawyerAdminCountyMap> lawyerAdminCountyMaps = new HashSet<LawyerAdminCountyMap>(
			0);
	private Set<PoliceAgency> policeAgencies = new HashSet<PoliceAgency>(0);
	private Set<ReportingAgency> reportingAgencies = new HashSet<ReportingAgency>(
			0);
	private Set<LawyerCountyMap> lawyerCountyMaps = new HashSet<LawyerCountyMap>(
			0);
	private Set<CallerCountyMap> callerCountyMaps = new HashSet<CallerCountyMap>(
			0);
	private Set<CrashReport> crashReports = new HashSet<CrashReport>(0);
	private Set<CallerAdminCountyMap> callerAdminCountyMaps = new HashSet<CallerAdminCountyMap>(
			0);

	public County() {
	}

	public County(String name, Integer status, Set<Patient> patients,
			Set<LawyerAdminCountyMap> lawyerAdminCountyMaps,
			Set<PoliceAgency> policeAgencies,
			Set<ReportingAgency> reportingAgencies,
			Set<LawyerCountyMap> lawyerCountyMaps,
			Set<CallerCountyMap> callerCountyMaps,
			Set<CrashReport> crashReports,
			Set<CallerAdminCountyMap> callerAdminCountyMaps) {
		this.name = name;
		this.status = status;
		this.patients = patients;
		this.lawyerAdminCountyMaps = lawyerAdminCountyMaps;
		this.policeAgencies = policeAgencies;
		this.reportingAgencies = reportingAgencies;
		this.lawyerCountyMaps = lawyerCountyMaps;
		this.callerCountyMaps = callerCountyMaps;
		this.crashReports = crashReports;
		this.callerAdminCountyMaps = callerAdminCountyMaps;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "county_id", unique = true, nullable = false)
	public Integer getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<Patient> getPatients() {
		return this.patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<LawyerAdminCountyMap> getLawyerAdminCountyMaps() {
		return this.lawyerAdminCountyMaps;
	}

	public void setLawyerAdminCountyMaps(
			Set<LawyerAdminCountyMap> lawyerAdminCountyMaps) {
		this.lawyerAdminCountyMaps = lawyerAdminCountyMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<PoliceAgency> getPoliceAgencies() {
		return this.policeAgencies;
	}

	public void setPoliceAgencies(Set<PoliceAgency> policeAgencies) {
		this.policeAgencies = policeAgencies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<ReportingAgency> getReportingAgencies() {
		return this.reportingAgencies;
	}

	public void setReportingAgencies(Set<ReportingAgency> reportingAgencies) {
		this.reportingAgencies = reportingAgencies;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<LawyerCountyMap> getLawyerCountyMaps() {
		return this.lawyerCountyMaps;
	}

	public void setLawyerCountyMaps(Set<LawyerCountyMap> lawyerCountyMaps) {
		this.lawyerCountyMaps = lawyerCountyMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<CallerCountyMap> getCallerCountyMaps() {
		return this.callerCountyMaps;
	}

	public void setCallerCountyMaps(Set<CallerCountyMap> callerCountyMaps) {
		this.callerCountyMaps = callerCountyMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<CrashReport> getCrashReports() {
		return this.crashReports;
	}

	public void setCrashReports(Set<CrashReport> crashReports) {
		this.crashReports = crashReports;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "county")
	public Set<CallerAdminCountyMap> getCallerAdminCountyMaps() {
		return this.callerAdminCountyMaps;
	}

	public void setCallerAdminCountyMaps(
			Set<CallerAdminCountyMap> callerAdminCountyMaps) {
		this.callerAdminCountyMaps = callerAdminCountyMaps;
	}

}
