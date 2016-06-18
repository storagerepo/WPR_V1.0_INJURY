package com.deemsys.project.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "contact_us", catalog = "injuryreportsdb")
public class ContactUs {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String firmName;
	private Date addedDateTime;
	private Date logDateTime;
	private String updatedBy;
	private Integer status;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "first_name", length = 45)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name = "last_name", length = 45)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name = "email", length = 60)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "phone_number", length = 20)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "added_date_time",length = 19)
	public Date getAddedDateTime() {
		return addedDateTime;
	}
	public void setAddedDateTime(Date addedDateTime) {
		this.addedDateTime = addedDateTime;
	}
	@Column(name = "firm_name",length = 150)
	public String getFirmName() {
		return firmName;
	}
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_date_time",length = 19)
	public Date getLogDateTime() {
		return logDateTime;
	}
	public void setLogDateTime(Date logDateTime) {
		this.logDateTime = logDateTime;
	}
	@Column(name = "update_by",length = 45)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public ContactUs(String firstName, String lastName, String email,
			String phoneNumber, String firmName, Date addedDateTime, Date logDateTime,
			String updatedBy, Integer status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.firmName = firmName;
		this.addedDateTime = addedDateTime;
		this.logDateTime = logDateTime;
		this.updatedBy = updatedBy;
		this.status = status;
	}
	public ContactUs() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
