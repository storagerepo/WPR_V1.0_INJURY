package com.deemsys.project.entity;

// Generated Mar 16, 2016 12:32:39 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CrashReport generated by hbm2java
 */
@Entity
@Table(name = "crash_report", catalog = "injuryreportsdbtest")
public class CrashReport implements java.io.Serializable {

	private CrashReportError crashReportError;
	private String localReportNumber;
	private String crashId;
	private Date crashDate;
	private County county;
	private Date addedDate;
	private String filePath;
	private Integer status;
	private Integer numberOfPatients;
	private Set<Patient> patients=new HashSet<Patient>(0);
	
	public CrashReport() {
	}

	public CrashReport(CrashReportError crashReportError,
			String localReportNumber, String crashId, Date crashDate,
			County county, Date addedDate, String filePath,Integer numberOfPatients,Integer status) {
		this.crashReportError = crashReportError;
		this.localReportNumber = localReportNumber;
		this.crashId = crashId;
		this.crashDate = crashDate;
		this.county = county;
		this.addedDate = addedDate;
		this.filePath = filePath;
		this.numberOfPatients=numberOfPatients;
		this.status = status;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "error_id")
	public CrashReportError getCrashReportError() {
		return this.crashReportError;
	}

	public void setCrashReportError(CrashReportError crashReportError) {
		this.crashReportError = crashReportError;
	}

	@Column(name = "local_report_number", length = 45, unique = true, nullable = false)
	public String getLocalReportNumber() {
		return this.localReportNumber;
	}

	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}

	@Id
	@Column(name = "crash_id", length = 45)
	public String getCrashId() {
		return this.crashId;
	}

	public void setCrashId(String crashId) {
		this.crashId = crashId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "crash_date", length = 10)
	public Date getCrashDate() {
		return this.crashDate;
	}

	public void setCrashDate(Date crashDate) {
		this.crashDate = crashDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "county_id")
	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "added_date", length = 45)
	public Date getAddedDate() {
		return this.addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	@Column(name = "file_path", length = 100)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "number_of_patients")
	public Integer getNumberOfPatients() {
		return this.numberOfPatients;
	}

	public void setNumberOfPatients(Integer numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "crashReport",cascade=CascadeType.ALL)
	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

}
