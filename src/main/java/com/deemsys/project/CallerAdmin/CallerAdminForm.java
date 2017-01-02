package com.deemsys.project.CallerAdmin;

import java.util.List;

import com.deemsys.project.County.CountyForm;

/**
 * 
 * @author Deemsys
 * 
 */
public class CallerAdminForm {

	private Integer callerAdminId;
	private Integer userId;
	private String username;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String emailAddress;
	private String phoneNumber;
	private String notes;
	private Integer isPrivilegedUser;
	private Integer status;
	List<Integer> county;
	List<CountyForm> countyForms;
	private String productToken;
	
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public Integer getIsPrivilegedUser() {
		return isPrivilegedUser;
	}
	public void setIsPrivilegedUser(Integer isPrivilegedUser) {
		this.isPrivilegedUser = isPrivilegedUser;
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
	public String getProductToken() {
		return productToken;
	}
	public void setProductToken(String productToken) {
		this.productToken = productToken;
	}
	public CallerAdminForm(Integer callerAdminId, Integer userId,
			String firstName, String lastName, String street, String city,
			String state, String zipcode, String emailAddress,
			String phoneNumber, String notes, Integer isPrivilegedUser, Integer status) {
		super();
		this.callerAdminId = callerAdminId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.isPrivilegedUser = isPrivilegedUser;
		this.status = status;
	}
	
	public CallerAdminForm(Integer callerAdminId, Integer userId,
			String username, String firstName, String lastName, String street,
			String city, String state, String zipcode, String emailAddress,
			String phoneNumber, String notes, Integer isPrivilegedUser, Integer status) {
		super();
		this.callerAdminId = callerAdminId;
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.isPrivilegedUser = isPrivilegedUser;
		this.status = status;
	}
	public CallerAdminForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
