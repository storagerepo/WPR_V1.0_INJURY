package com.deemsys.project.Clinics;

import java.util.List;

import com.deemsys.project.ClinicTimings.ClinicTimingList;
import com.deemsys.project.Doctors.DoctorsForm;

public class ClinicsForm {
	private Integer clinicId;
	private Integer callerAdminId;
	private String clinicName;
	private String address;
	private String city;
	private String state;
	private String county;
	private String country;
	private String zipcode;
	private Double latitude;
	private Double longitude;
	private String officeNumber;
	private String faxNumber;
	private String serviceArea;
	private String directions;
	private String notes;
	private Integer status;
	private Double farAway;
	private List<ClinicTimingList> clinicTimingList;
	private List<DoctorsForm> doctorsForms;

	public Integer getClinicId() {
		return clinicId;
	}

	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}

	public Integer getCallerAdminId() {
		return callerAdminId;
	}

	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getFarAway() {
		return farAway;
	}

	public void setFarAway(Double farAway) {
		this.farAway = farAway;
	}

	public List<ClinicTimingList> getClinicTimingList() {
		return clinicTimingList;
	}

	public void setClinicTimingList(List<ClinicTimingList> clinicTimingList) {
		this.clinicTimingList = clinicTimingList;
	}

	public List<DoctorsForm> getDoctorsForms() {
		return doctorsForms;
	}

	public void setDoctorsForms(List<DoctorsForm> doctorsForms) {
		this.doctorsForms = doctorsForms;
	}

	public ClinicsForm(Integer clinicId, String clinicName, String address,
			String city, String state, String county, String country,
			String zipcode, Double latitude, Double longitude,
			String officeNumber, String faxNumber, String serviceArea,
			String directions, String notes,Integer status, Double farAway,
			List<ClinicTimingList> clinicTimingList) {
		super();
		this.clinicId = clinicId;
		this.clinicName = clinicName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.county = county;
		this.country = country;
		this.zipcode = zipcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.officeNumber = officeNumber;
		this.faxNumber = faxNumber;
		this.serviceArea = serviceArea;
		this.directions = directions;
		this.notes = notes;
		this.status=status;
		this.farAway = farAway;
		this.clinicTimingList = clinicTimingList;
	}

	public ClinicsForm(Integer clinicId, String clinicName, String address,
			String city, String state, String county, String country,
			String zipcode, String officeNumber, String faxNumber,
			String serviceArea, String directions, String notes,Integer status,
			List<ClinicTimingList> clinicTimingList,
			List<DoctorsForm> doctorsForms) {
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
		this.serviceArea = serviceArea;
		this.directions = directions;
		this.notes = notes;
		this.status=status;
		this.clinicTimingList = clinicTimingList;
		this.doctorsForms = doctorsForms;
	}

	public ClinicsForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClinicsForm(Integer clinicId, String clinicName) {
		this.clinicId = clinicId;
		this.clinicName = clinicName;

	}

}
