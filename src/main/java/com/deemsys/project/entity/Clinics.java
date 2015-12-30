package com.deemsys.project.entity;

// Generated Oct 1, 2015 1:05:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clinics generated by hbm2java
 */
@Entity
@Table(name = "Clinics", catalog = "injurytest")
public class Clinics implements java.io.Serializable {

	private Integer clinicId;
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
	private String directions;
	private String serviceArea;
	private String notes;
	private Set<Doctors> doctorses = new HashSet<Doctors>(0);
	private Set<ClinicTimings> clinicTimingses = new HashSet<ClinicTimings>(0);

	public Clinics() {
	}

	public Clinics(String clinicName, String address, String city,
			String state, String county, String country, String zipcode,
			Double latitude, Double longitude,String officeNumber, String faxNumber, String directions,
			String serviceArea, String notes, Set<Doctors> doctorses,
			Set<ClinicTimings> clinicTimingses) {
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
		this.directions = directions;
		this.serviceArea = serviceArea;
		this.notes = notes;
		this.doctorses = doctorses;
		this.clinicTimingses = clinicTimingses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "clinic_id", unique = true, nullable = false)
	public Integer getClinicId() {
		return this.clinicId;
	}

	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}

	@Column(name = "clinic_name", length = 90)
	public String getClinicName() {
		return this.clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	@Column(name = "address", length = 150)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "latitude")
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude")
	public Double getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "city", length = 60)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", length = 60)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "county", length = 60)
	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "country", length = 60)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "zipcode", length = 10)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "office_number", length = 45)
	public String getOfficeNumber() {
		return this.officeNumber;
	}

	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}

	@Column(name = "fax_number", length = 45)
	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	@Column(name = "directions", length = 65535)
	public String getDirections() {
		return this.directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	@Column(name = "service_area", length = 65535)
	public String getServiceArea() {
		return this.serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	
	@Column(name = "notes", length = 65535)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinics")
	public Set<Doctors> getDoctorses() {
		return this.doctorses;
	}

	public void setDoctorses(Set<Doctors> doctorses) {
		this.doctorses = doctorses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clinics")
	public Set<ClinicTimings> getClinicTimingses() {
		return this.clinicTimingses;
	}

	public void setClinicTimingses(Set<ClinicTimings> clinicTimingses) {
		this.clinicTimingses = clinicTimingses;
	}

}
