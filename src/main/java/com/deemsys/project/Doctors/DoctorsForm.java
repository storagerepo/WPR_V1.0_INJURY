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
	private String name;
	
	private String address;
	private String city;
	private String country;
	private String state;
	private String zip;
	private String officeHours;
	private String newField;
	private String notes;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}

	public String getNewField() {
		return newField;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public DoctorsForm(Integer id, String name, String address, String city,
			String country, String state, String zip, String officeHours,
			String newField, String notes) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
		this.officeHours = officeHours;
		this.newField = newField;
		this.notes = notes;
	}

	public DoctorsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public DoctorsForm(Integer id,String name)
	{
		this.id=id;
		this.name=name;
		

	}
	
}
