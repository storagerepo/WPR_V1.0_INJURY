package com.deemsys.project.Doctors;


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
	private Integer titleDr;
	private Integer titleDc;
	private Integer isRemoveable;

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

	public Integer getTitleDr() {
		return titleDr;
	}

	public void setTitleDr(Integer titleDr) {
		this.titleDr = titleDr;
	}

	public Integer getTitleDc() {
		return titleDc;
	}

	public void setTitleDc(Integer titleDc) {
		this.titleDc = titleDc;
	}

	public Integer getIsRemoveable() {
		return isRemoveable;
	}

	public void setIsRemoveable(Integer isRemoveable) {
		this.isRemoveable = isRemoveable;
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

	public DoctorsForm(Integer id, Integer clinicId, String clinicName,
			String doctorName, String emailId, String contactNumber,
			String specialistIn, String notes) {
		super();
		this.id = id;
		this.clinicId = clinicId;
		this.clinicName = clinicName;
		this.doctorName = doctorName;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.specialistIn = specialistIn;
		this.notes = notes;
	}

	public DoctorsForm(Integer id, String doctorName) {
		this.id = id;
		this.doctorName = doctorName;
	}

	public DoctorsForm(Integer id, String doctorName, Integer titleDr,
			Integer titleDc) {
		super();
		this.id = id;
		this.doctorName = doctorName;
		this.titleDr = titleDr;
		this.titleDc = titleDc;
	}

}
