package com.deemsys.project.Lawyers;

import java.util.List;

import com.deemsys.project.County.CountyForm;
import com.deemsys.project.entity.Lawyer;


public class LawyersForm {

	private Integer lawyerId;
	private Integer lawyerAdminId;
	private Integer userId;
	private String username;
	private String firstName;
	private String lastName;
	private String streetAddress;
	private String city;
	private String state;
	private String zipcode;
	private String emailAddress;
	private String phoneNumber;
	private String notes;
	private Integer status;
	private List<CountyForm> countyForms;
	private Integer[] countyId;
	
	
	public Integer getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}
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
	
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	public Integer[] getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer[] countyId) {
		this.countyId = countyId;
	}
	public List<CountyForm> getCountyForms() {
		return countyForms;
	}
	public void setCountyForms(List<CountyForm> countyForms) {
		this.countyForms = countyForms;
	}
	
	public LawyersForm(Integer lawyerId, Integer lawyerAdminId, Integer userId, String firstName, String lastName,
			String streetAddress,
			String city, String state, String zipcode, String emailAddress,
			String phoneNumber, String notes, Integer status) {
		super();
		this.lawyerId = lawyerId;
		this.lawyerAdminId = lawyerAdminId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.notes = notes;
		this.status = status;
	}
	
	public Lawyer getLawyersDetails(LawyersForm lawyersForm){
		Lawyer lawyers=new Lawyer();
		
		
		return lawyers;
	}
	
	public LawyersForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
