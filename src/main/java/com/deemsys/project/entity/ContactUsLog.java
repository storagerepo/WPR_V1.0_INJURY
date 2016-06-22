package com.deemsys.project.entity;

// Generated Jun 21, 2016 4:47:34 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ContactUsLog generated by hbm2java
 */
@Entity
@Table(name = "contact_us_log", catalog = "injuryreportsdb")
public class ContactUsLog implements java.io.Serializable {

	private Date logDateTime;
	private ContactUs contactUs;
	private String byWhom;
	private Integer logStatus;
	private Integer status;

	public ContactUsLog() {
	}

	public ContactUsLog(Date logDateTime, ContactUs contactUs) {
		this.logDateTime = logDateTime;
		this.contactUs = contactUs;
	}

	public ContactUsLog(Date logDateTime, ContactUs contactUs, String byWhom,
			Integer logStatus, Integer status) {
		this.logDateTime = logDateTime;
		this.contactUs = contactUs;
		this.byWhom = byWhom;
		this.logStatus = logStatus;
		this.status = status;
	}

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_date_time", unique = true, nullable = false, length = 19)
	public Date getLogDateTime() {
		return this.logDateTime;
	}

	public void setLogDateTime(Date logDateTime) {
		this.logDateTime = logDateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	public ContactUs getContactUs() {
		return this.contactUs;
	}

	public void setContactUs(ContactUs contactUs) {
		this.contactUs = contactUs;
	}

	@Column(name = "by_whom", length = 45)
	public String getByWhom() {
		return this.byWhom;
	}

	public void setByWhom(String byWhom) {
		this.byWhom = byWhom;
	}

	@Column(name = "log_status")
	public Integer getLogStatus() {
		return this.logStatus;
	}

	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
