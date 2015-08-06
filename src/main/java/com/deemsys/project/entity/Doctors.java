package com.deemsys.project.entity;

// Generated Aug 6, 2015 2:51:18 PM by Hibernate Tools 3.4.0.CR1

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
 * Doctors generated by hbm2java
 */
@Entity
@Table(name = "Doctors", catalog = "injury")
public class Doctors implements java.io.Serializable {

	private Integer id;
	private String name;
	private String address;
	private String city;
	private String country;
	private String state;
	private String zip;
	private String officeHours;
	private String newField;
	private String notes;
	private Set<Patients> patientses = new HashSet<Patients>(0);

	public Doctors() {
	}

	public Doctors(String name, String address, String city, String country,
			String state, String zip, String officeHours, String newField,
			String notes, Set<Patients> patientses) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
		this.officeHours = officeHours;
		this.newField = newField;
		this.notes = notes;
		this.patientses = patientses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city", length = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "country", length = 45)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "state", length = 45)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "zip", length = 10)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "office_hours", length = 45)
	public String getOfficeHours() {
		return this.officeHours;
	}

	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}

	@Column(name = "new_field", length = 45)
	public String getNewField() {
		return this.newField;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

	@Column(name = "notes", length = 600)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doctors")
	public Set<Patients> getPatientses() {
		return this.patientses;
	}

	public void setPatientses(Set<Patients> patientses) {
		this.patientses = patientses;
	}

}
