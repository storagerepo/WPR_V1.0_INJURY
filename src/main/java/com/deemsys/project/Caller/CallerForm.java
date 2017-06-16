package com.deemsys.project.Caller;

import java.util.List;

import com.deemsys.project.County.CountyForm;


/**
 * 
 * @author Deemsys
 *
 */
public class CallerForm {

	private Integer callerId;
	private Integer callerAdminId;
	private Integer userId;
	private String username;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String notes;
	private Integer status;
	private List<Integer> county;
	private List<CountyForm> countyForms;
	private Integer roleId;
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Integer> getCounty() {
		return county;
	}
	public void setCounty(List<Integer> county) {
		this.county = county;
	}
	public List<CountyForm> getCountyForms() {
		return countyForms;
	}
	public void setCountyForms(List<CountyForm> countyForms) {
		this.countyForms = countyForms;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public CallerForm(Integer callerId, Integer callerAdminId, Integer userId,
			String username, String firstName, String lastName,
			String phoneNumber, String emailAddress, String notes,
			Integer status) {
		super();
		this.callerId = callerId;
		this.callerAdminId = callerAdminId;
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.status = status;
	}
	public CallerForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
