package com.deemsys.project.LawyerAdmin;


public class LawyerAdminForm {

	private Integer id;
	private Integer userId;
	private String username;
	private String firstName;
	private String lastName;
	private String middleName;
	private String address;
	private String emailAddress;
	private String phoneNumber;
	private String notes;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public LawyerAdminForm(Integer id, Integer userId, String firstName,
			String lastName, String middleName, String address,
			String emailAddress, String phoneNumber, String notes,
			Integer status) {
		super();
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.address = address;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.status = status;
	}
	
	public LawyerAdminForm(Integer id, Integer userId, String firstName,
			String lastName, String middleName, String username,String address,
			String emailAddress, String phoneNumber, String notes,
			Integer status) {
		super();
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.username = username;
		this.address = address;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.status = status;
	}
	
	public LawyerAdminForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
