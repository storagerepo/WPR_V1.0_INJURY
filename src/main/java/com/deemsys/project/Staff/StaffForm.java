package com.deemsys.project.Staff;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Deemsys
 *
 */
public class StaffForm {

	private Integer id;
	private String role;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String notes;
	private Integer isEnable;
	private Integer assignedPatientSize;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	public Integer getAssignedPatientSize() {
		return assignedPatientSize;
	}
	public void setAssignedPatientSize(Integer assignedPatientSize) {
		this.assignedPatientSize = assignedPatientSize;
	}
	public StaffForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StaffForm(Integer id, String role, String username, String password,
			String firstName, String lastName, String phoneNumber,
			String emailAddress, String notes,Integer isEnable) {
		super();
		this.id = id;
		this.role = role;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.isEnable=isEnable;
	}
	// Only For get All Staffs
	public StaffForm(Integer id, String role, String username, String password,
			String firstName, String lastName, String phoneNumber,
			String emailAddress, String notes,Integer isEnable,Integer assignedPatientSize) {
		super();
		this.id = id;
		this.role = role;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.isEnable=isEnable;
		this.assignedPatientSize=assignedPatientSize;
	}
	public Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	
	
	
	public StaffForm(Integer id, String firstName)
	{
		this.id=id;
		this.firstName=firstName;
	}
	public StaffForm(String username)
	{
		this.username=username;
		
	}
}
