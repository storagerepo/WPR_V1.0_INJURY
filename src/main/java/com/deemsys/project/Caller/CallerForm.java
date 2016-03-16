package com.deemsys.project.Caller;

/**
 * 
 * @author Deemsys
 *
 */
public class CallerForm {

	private Integer id;
	private String role;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String notes;
	private Integer status;
	private Integer assignedPatientSize;
	private String callerName;
	
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
	public String getCallerName() {
		return callerName;
	}
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}
	public CallerForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CallerForm(Integer id, String username, String password,
			String firstName, String lastName, String phoneNumber,
			String emailAddress, String notes,Integer status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.status=status;
	}
	// Only For get All Callers
	public CallerForm(Integer id, String username, String password,
			String firstName, String lastName, String phoneNumber,
			String emailAddress, String notes,Integer status,Integer assignedPatientSize) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.notes = notes;
		this.status=status;
		this.assignedPatientSize=assignedPatientSize;
	}
	public Integer getstatus() {
		return status;
	}
	public void setstatus(Integer status) {
		this.status = status;
	}
	
	
	
	public CallerForm(Integer id, String firstName)
	{
		this.id=id;
		this.firstName=firstName;
	}
	public CallerForm(String username)
	{
		this.username=username;
		
	}
}