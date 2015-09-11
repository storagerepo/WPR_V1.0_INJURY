package com.deemsys.project.Doctors;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * 
 * @author Deemsys
 *
 */
public class DoctorsForm {

	private Integer id;
	private String clinicName;
	private String doctorName;
	private String address;
	private String city;
	private String country;
	private String state;
	private String zip;
	private String officeNumber;
	private String faxNumber;
	private String[] workingDays;
	private String officeHoursFromTime;
	private String officeHoursToTime;
	private String direction;
	private String notes;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getClinicName() {
		return clinicName;
	}


	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}


	public String getDoctorName() {
		return doctorName;
	}


	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getOfficeNumber() {
		return officeNumber;
	}


	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}


	public String getFaxNumber() {
		return faxNumber;
	}


	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}


	public String[] getWorkingDays() {
		return workingDays;
	}


	public void setWorkingDays(String[] workingDays) {
		this.workingDays = workingDays;
	}


	public String getOfficeHoursFromTime() {
		return officeHoursFromTime;
	}


	public void setOfficeHoursFromTime(String officeHoursFromTime) {
		this.officeHoursFromTime = officeHoursFromTime;
	}


	public String getOfficeHoursToTime() {
		return officeHoursToTime;
	}


	public void setOfficeHoursToTime(String officeHoursToTime) {
		this.officeHoursToTime = officeHoursToTime;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public DoctorsForm(Integer id, String clinicName, String doctorName,
			String address, String city, String country, String state,
			String zip, String officeNumber, String faxNumber,
			String[] workingDays, String officeHoursFromTime,
			String officeHoursToTime, String direction, String notes) {
		super();
		this.id = id;
		this.clinicName = clinicName;
		this.doctorName = doctorName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
		this.officeNumber = officeNumber;
		this.faxNumber = faxNumber;
		this.workingDays = workingDays;
		this.officeHoursFromTime = officeHoursFromTime;
		this.officeHoursToTime = officeHoursToTime;
		this.direction = direction;
		this.notes = notes;
	}


	public DoctorsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public DoctorsForm(Integer id,String doctorName)
	{
		this.id=id;
		this.doctorName=doctorName;
		

	}
	
}
