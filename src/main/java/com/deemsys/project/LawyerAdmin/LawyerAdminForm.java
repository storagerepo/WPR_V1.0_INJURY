package com.deemsys.project.LawyerAdmin;

import java.util.List;

import com.deemsys.project.County.CountyForm;
import com.deemsys.project.entity.Users;


public class LawyerAdminForm {

	private Integer lawyerAdminId;
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
	private Integer status;
	List<Integer> county;
	List<CountyForm> countyform;
	
	public Integer getLawyerAdminId() {
		return lawyerAdminId;
	}

	public void setLawyerAdminId(Integer lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
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
	
	

	public List<CountyForm> getCountyform() {
		return countyform;
	}

	public void setCountyform(List<CountyForm> countyform) {
		this.countyform = countyform;
	}

	public LawyerAdminForm(Integer lawyerAdminId, Integer userId,
			String firstName, String lastName, String street, String city,
			String state, String zipcode, String emailAddress,
			String phoneNumber, String notes, Integer status) {
		super();
		this.lawyerAdminId = lawyerAdminId;
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
		this.status = status;
	}

	public LawyerAdminForm(Integer lawyerAdminId, Integer userId,
			String username, String firstName, String lastName, String street,
			String city, String state, String zipcode, String emailAddress,
			String phoneNumber, String notes, Integer status) {
		super();
		this.lawyerAdminId = lawyerAdminId;
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
		this.status = status;
	}

	public LawyerAdminForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
