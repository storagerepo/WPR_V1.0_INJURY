package com.deemsys.project.ContactUs;


import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.deemsys.project.ContactUsLog.ContactUsLogForm;
import com.deemsys.project.entity.ContactUsLog;

/**
 * 
 * @author Deemsys
 * 
 */
public class ContactUsForm {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String firmName;
	private String addedDate;
	private Integer status;
	private String statusText;
	private String subject;
	private String bodyMessage;
	private String logDateTime;
	private String updatedBy;
	private List<ContactUsLogForm> contactUsLogForms;
	
	// For Pojections
	private Date addedDateTable;
	private Date logDateTimeTable;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBodyMessage() {
		return bodyMessage;
	}

	public void setBodyMessage(String bodyMessage) {
		this.bodyMessage = bodyMessage;
	}
	
	public String getLogDateTime() {
		return logDateTime;
	}

	public void setLogDateTime(String logDateTime) {
		this.logDateTime = logDateTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<ContactUsLogForm> getContactUsLogForms() {
		return contactUsLogForms;
	}

	public void setContactUsLogForms(List<ContactUsLogForm> contactUsLogForms) {
		this.contactUsLogForms = contactUsLogForms;
	}
	
	public Date getAddedDateTable() {
		return addedDateTable;
	}

	public void setAddedDateTable(Date addedDateTable) {
		this.addedDateTable = addedDateTable;
	}

	public Date getLogDateTimeTable() {
		return logDateTimeTable;
	}

	public void setLogDateTimeTable(Date logDateTimeTable) {
		this.logDateTimeTable = logDateTimeTable;
	}

	public ContactUsForm(Integer id, String firstName, String lastName,
			String email, String phoneNumber, String firmName, String addedDate, Integer status,String statusText) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.firmName = firmName;
		this.addedDate = addedDate;
		this.status = status;
		this.statusText = statusText;
	}
	
	public ContactUsForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
