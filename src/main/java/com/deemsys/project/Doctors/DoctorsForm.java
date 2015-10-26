package com.deemsys.project.Doctors;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.deemsys.project.entity.Clinics;



/**
 * 
 * @author Deemsys
 *
 */
public class DoctorsForm {


	private Integer id;
	private Integer clinicId;
	private String clinicName;
	private String doctorName;
	private String emailId;
	private String contactNumber;
	private String specialistIn;
	private String notes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getSpecialistIn() {
		return specialistIn;
	}
	public void setSpecialistIn(String specialistIn) {
		this.specialistIn = specialistIn;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getClinicId() {
		return clinicId;
	}
	
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public DoctorsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DoctorsForm(Integer id, Integer clinicId, String doctorName,
			String emailId, String contactNumber, String specialistIn,
			String notes) {
		super();
		this.id = id;
		this.clinicId = clinicId;
		this.doctorName = doctorName;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.specialistIn = specialistIn;
		this.notes = notes;
	}
	
	public DoctorsForm(Integer id, Integer clinicId, String clinicName,String doctorName,
			String emailId, String contactNumber, String specialistIn,
			String notes) {
		super();
		this.id = id;
		this.clinicId = clinicId;
		this.clinicName=clinicName;
		this.doctorName = doctorName;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.specialistIn = specialistIn;
		this.notes = notes;
	}
	public DoctorsForm(Integer id,String doctorName)
	{
		this.id=id;
		this.doctorName=doctorName;
		

	}
	
}
