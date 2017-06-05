package com.deemsys.project.entity;

// Generated 5 Jun, 2017 1:18:21 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DirectReportCallerAdminMap generated by hbm2java
 */
@Entity
@Table(name = "direct_report_caller_admin_map", catalog = "injuryreportsdbtest")
public class DirectReportCallerAdminMap implements java.io.Serializable {

	private DirectReportCallerAdminMapId id;
	private CallerAdmin callerAdmin;
	private CrashReport crashReport;
	private Caller caller;
	private String notes;
	private Integer isArchived;
	private Date archivedDate;
	private String archivedDateTime;
	private Integer status;

	public DirectReportCallerAdminMap() {
	}

	public DirectReportCallerAdminMap(DirectReportCallerAdminMapId id,
			CallerAdmin callerAdmin, CrashReport crashReport) {
		this.id = id;
		this.callerAdmin = callerAdmin;
		this.crashReport = crashReport;
	}

	public DirectReportCallerAdminMap(DirectReportCallerAdminMapId id,
			CallerAdmin callerAdmin, CrashReport crashReport, Caller caller,
			String notes, Integer isArchived, Date archivedDate,
			String archivedDateTime, Integer status) {
		this.id = id;
		this.callerAdmin = callerAdmin;
		this.crashReport = crashReport;
		this.caller = caller;
		this.notes = notes;
		this.isArchived = isArchived;
		this.archivedDate = archivedDate;
		this.archivedDateTime = archivedDateTime;
		this.status = status;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "crashId", column = @Column(name = "crash_id", nullable = false, length = 45)),
			@AttributeOverride(name = "callerAdminId", column = @Column(name = "caller_admin_id", nullable = false)) })
	public DirectReportCallerAdminMapId getId() {
		return this.id;
	}

	public void setId(DirectReportCallerAdminMapId id) {
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
	@JoinColumn(name = "crash_id", nullable = false, insertable = false, updatable = false)
	public CrashReport getCrashReport() {
		return this.crashReport;
	}

	public void setCrashReport(CrashReport crashReport) {
		this.crashReport = crashReport;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "caller_id")
	public Caller getCaller() {
		return this.caller;
	}

	public void setCaller(Caller caller) {
		this.caller = caller;
	}

	@Column(name = "notes", length = 400)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "is_archived")
	public Integer getIsArchived() {
		return this.isArchived;
	}

	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "archived_date", length = 10)
	public Date getArchivedDate() {
		return this.archivedDate;
	}

	public void setArchivedDate(Date archivedDate) {
		this.archivedDate = archivedDate;
	}

	@Column(name = "archived_date_time", length = 45)
	public String getArchivedDateTime() {
		return this.archivedDateTime;
	}

	public void setArchivedDateTime(String archivedDateTime) {
		this.archivedDateTime = archivedDateTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
