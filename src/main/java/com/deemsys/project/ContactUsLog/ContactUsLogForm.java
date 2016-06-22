package com.deemsys.project.ContactUsLog;


import java.util.Date;


/**
 * 
 * @author Deemsys
 * 
 */
public class ContactUsLogForm {

	private String logDateTime;
	private Integer contactUsId;
	private String byWhom;
	private Integer logStatus;
	private Integer status;
	
	public String getLogDateTime() {
		return logDateTime;
	}

	public void setLogDateTime(String logDateTime) {
		this.logDateTime = logDateTime;
	}

	public Integer getContactUsId() {
		return contactUsId;
	}

	public void setContactUsId(Integer contactUsId) {
		this.contactUsId = contactUsId;
	}

	public String getByWhom() {
		return byWhom;
	}

	public void setByWhom(String byWhom) {
		this.byWhom = byWhom;
	}

	public Integer getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public ContactUsLogForm(String logDateTime, Integer contactUsId,
			String byWhom, Integer logStatus) {
		super();
		this.logDateTime = logDateTime;
		this.contactUsId = contactUsId;
		this.byWhom = byWhom;
		this.logStatus = logStatus;
	}

	public ContactUsLogForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
