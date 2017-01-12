package com.deemsys.project.common;

public class CurrentUserDetailsForm {
	private String productId;
	private String role;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public CurrentUserDetailsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CurrentUserDetailsForm(String productId, String role,
			String firstName, String lastName, String phoneNumber,
			String emailId) {
		super();
		this.productId = productId;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailId = emailId;
	}
	
	
}
