package com.deemsys.project.entity;

// Generated 5 Jun, 2017 1:18:21 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CrashReport generated by hbm2java
 */
@Entity
@Table(name = "crash_report", catalog = "injuryreportsdbtest")
public class CrashReport implements java.io.Serializable {

	private String crashId;
	private CrashReportError crashReportError;
	private PoliceAgency policeAgency;
	private County county;
	private String localReportNumber;
	private Date crashDate;
	private Date addedDate;
	private Integer numberOfPatients;
	private String filePath;
	private String oldFilePath;
	private Integer isRunnerReport;
	private Date runnerReportAddedDate;
	private Integer status;
	private Set<DirectReportCallerAdminMap> directReportCallerAdminMaps = new HashSet<DirectReportCallerAdminMap>(
			0);
	private Set<Patient> patients = new HashSet<Patient>(0);
	private Set<DirectReportLawyerAdminMap> directReportLawyerAdminMaps = new HashSet<DirectReportLawyerAdminMap>(
			0);

	public CrashReport() {
	}

	public CrashReport(String crashId) {
		this.crashId = crashId;
	}

	public CrashReport(String crashId, CrashReportError crashReportError,
			PoliceAgency policeAgency, County county, String localReportNumber,
			Date crashDate, Date addedDate, Integer numberOfPatients,
			String filePath, String oldFilePath, Integer isRunnerReport,
			Date runnerReportAddedDate, Integer status,
			Set<DirectReportCallerAdminMap> directReportCallerAdminMaps,
			Set<Patient> patients,
			Set<DirectReportLawyerAdminMap> directReportLawyerAdminMaps) {
		this.crashId = crashId;
		this.crashReportError = crashReportError;
		this.policeAgency = policeAgency;
		this.county = county;
		this.localReportNumber = localReportNumber;
		this.crashDate = crashDate;
		this.addedDate = addedDate;
		this.numberOfPatients = numberOfPatients;
		this.filePath = filePath;
		this.oldFilePath = oldFilePath;
		this.isRunnerReport = isRunnerReport;
		this.runnerReportAddedDate = runnerReportAddedDate;
		this.status = status;
		this.directReportCallerAdminMaps = directReportCallerAdminMaps;
		this.patients = patients;
		this.directReportLawyerAdminMaps = directReportLawyerAdminMaps;
	}

	@Id
	@Column(name = "crash_id", unique = true, nullable = false, length = 45)
	public String getCrashId() {
		return this.crashId;
	}

	public void setCrashId(String crashId) {
		this.crashId = crashId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "error_id")
	public CrashReportError getCrashReportError() {
		return this.crashReportError;
	}

	public void setCrashReportError(CrashReportError crashReportError) {
		this.crashReportError = crashReportError;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_from")
	public PoliceAgency getPoliceAgency() {
		return this.policeAgency;
	}

	public void setPoliceAgency(PoliceAgency policeAgency) {
		this.policeAgency = policeAgency;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id")
	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@Column(name = "local_report_number", length = 45)
	public String getLocalReportNumber() {
		return this.localReportNumber;
	}

	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "crash_date", length = 10)
	public Date getCrashDate() {
		return this.crashDate;
	}

	public void setCrashDate(Date crashDate) {
		this.crashDate = crashDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "added_date", length = 10)
	public Date getAddedDate() {
		return this.addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	@Column(name = "number_of_patients")
	public Integer getNumberOfPatients() {
		return this.numberOfPatients;
	}

	public void setNumberOfPatients(Integer numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}

	@Column(name = "file_path", length = 100)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "old_file_path", length = 100)
	public String getOldFilePath() {
		return this.oldFilePath;
	}

	public void setOldFilePath(String oldFilePath) {
		this.oldFilePath = oldFilePath;
	}

	@Column(name = "is_runner_report")
	public Integer getIsRunnerReport() {
		return this.isRunnerReport;
	}

	public void setIsRunnerReport(Integer isRunnerReport) {
		this.isRunnerReport = isRunnerReport;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "runner_report_added_date", length = 10)
	public Date getRunnerReportAddedDate() {
		return this.runnerReportAddedDate;
	}

	public void setRunnerReportAddedDate(Date runnerReportAddedDate) {
		this.runnerReportAddedDate = runnerReportAddedDate;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crashReport")
	public Set<DirectReportCallerAdminMap> getDirectReportCallerAdminMaps() {
		return this.directReportCallerAdminMaps;
	}

	public void setDirectReportCallerAdminMaps(
			Set<DirectReportCallerAdminMap> directReportCallerAdminMaps) {
		this.directReportCallerAdminMaps = directReportCallerAdminMaps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crashReport")
	public Set<Patient> getPatients() {
		return this.patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crashReport")
	public Set<DirectReportLawyerAdminMap> getDirectReportLawyerAdminMaps() {
		return this.directReportLawyerAdminMaps;
	}

	public void setDirectReportLawyerAdminMaps(
			Set<DirectReportLawyerAdminMap> directReportLawyerAdminMaps) {
		this.directReportLawyerAdminMaps = directReportLawyerAdminMaps;
	}

}
