package com.deemsys.project.Clinics;

import java.util.List;

import com.deemsys.project.ClinicTimings.ClinicTimingList;

public class ClinicsForm {
	private Integer clinicId;
	private String clinicName;
	private String address;
	private String city;
	private String state;
	private String county;
	private String country;
	private String zipcode;
	private String officeNumber;
	private String faxNumber;
	private String directions;
	private String notes;
	private List<ClinicTimingList> clinicTimingList;
	
	public Integer getClinicId() {
		return clinicId;
	}
	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public String getDirections() {
		return directions;
	}
	public void setDirections(String directions) {
		this.directions = directions;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<ClinicTimingList> getClinicTimingList() {
		return clinicTimingList;
	}
	public void setClinicTimingList(List<ClinicTimingList> clinicTimingList) {
		this.clinicTimingList = clinicTimingList;
	}
	public ClinicsForm(Integer clinicId, String clinicName, String address,
			String city, String state, String county, String country,
			String zipcode, String officeNumber, String faxNumber,
			String directions, String notes,List<ClinicTimingList> clinicTimingList) {
		super();
		this.clinicId = clinicId;
		this.clinicName = clinicName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.county = county;
		this.country = country;
		this.zipcode = zipcode;
		this.officeNumber = officeNumber;
		this.faxNumber = faxNumber;
		this.directions = directions;
		this.notes = notes;
		this.clinicTimingList=clinicTimingList;
	}
	public ClinicsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
