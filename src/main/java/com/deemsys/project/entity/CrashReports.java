package com.deemsys.project.entity;

// Generated Aug 6, 2015 2:51:18 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CrashReports generated by hbm2java
 */
@Entity
@Table(name = "CrashReports", catalog = "injury")
public class CrashReports implements java.io.Serializable {

	private Integer id;
	private String reportNumber;
	private Date dateOfCrash;
	private String content;
	private String notes;

	public CrashReports() {
	}

	public CrashReports(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public CrashReports(String reportNumber, Date dateOfCrash, String content,
			String notes) {
		this.reportNumber = reportNumber;
		this.dateOfCrash = dateOfCrash;
		this.content = content;
		this.notes = notes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "report_number", nullable = false, length = 50)
	public String getReportNumber() {
		return this.reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_crash", length = 10)
	public Date getDateOfCrash() {
		return this.dateOfCrash;
	}

	public void setDateOfCrash(Date dateOfCrash) {
		this.dateOfCrash = dateOfCrash;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "notes", length = 600)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
