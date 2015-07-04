package com.deemsys.project.patients;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.deemsys.project.entity.Doctors;

/**
 * 
 * @author Deemsys
 *
 */
public class PatientsForm {

	private Integer id;
	private Integer doctorsId;
	private Integer callerId;
	private String firstName;
	private String lastName;
	private String reportNumber;
	private String phoneNumber;
	private String address;
	private String injury;
	private String dateOfCrash;
	private String otherPassengers;
	private String notes;
	public PatientsForm(Integer id, Integer doctorsId, String firstName,
			String lastName, String reportNumber, String phoneNumber,
			String address, String injury, String dateOfCrash,
			String otherPassengers, String notes) {
		super();
		this.id = id;
		this.doctorsId = doctorsId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.reportNumber = reportNumber;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.injury = injury;
		this.dateOfCrash = dateOfCrash;
		this.otherPassengers = otherPassengers;
		this.notes = notes;
	}
	public PatientsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PatientsForm(Integer id, Integer doctorsId, Integer callerId,
			String firstName, String lastName, String reportNumber,
			String phoneNumber, String address, String injury,
		String dateOfCrash, String otherPassengers, String notes) {
		super();
		this.id = id;
		this.doctorsId = doctorsId;
		this.callerId = callerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.reportNumber = reportNumber;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.injury = injury;
		this.dateOfCrash = dateOfCrash;
		this.otherPassengers = otherPassengers;
		this.notes = notes;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDoctorsId() {
		return doctorsId;
	}
	public void setDoctorsId(Integer doctorsId) {
		this.doctorsId = doctorsId;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
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
	public String getReportNumber() {
		return reportNumber;
	}
	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInjury() {
		return injury;
	}
	public void setInjury(String injury) {
		this.injury = injury;
	}
	public String getDateOfCrash() {
		return dateOfCrash;
	}
	public void setDateOfCrash(String dateOfCrash) {
		this.dateOfCrash = dateOfCrash;
	}
	public String getOtherPassengers() {
		return otherPassengers;
	}
	public void setOtherPassengers(String otherPassengers) {
		this.otherPassengers = otherPassengers;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}
