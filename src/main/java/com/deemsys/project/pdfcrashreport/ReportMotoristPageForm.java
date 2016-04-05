package com.deemsys.project.pdfcrashreport;

public class ReportMotoristPageForm {
	private String unitNumber;
	private String name;
	private String dateOfBirth;
	private String age;
	private String gender;
	private String adddressCityStateZip;
	private String contactPhone;
	private String injuries;
	private String emsAgency;
	private String medicalFacility;
	public String getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAdddressCityStateZip() {
		return adddressCityStateZip;
	}
	public void setAdddressCityStateZip(String adddressCityStateZip) {
		this.adddressCityStateZip = adddressCityStateZip;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getInjuries() {
		return injuries;
	}
	public void setInjuries(String injuries) {
		this.injuries = injuries;
	}
	public String getEmsAgency() {
		return emsAgency;
	}
	public void setEmsAgency(String emsAgency) {
		this.emsAgency = emsAgency;
	}
	public String getMedicalFacility() {
		return medicalFacility;
	}
	public void setMedicalFacility(String medicalFacility) {
		this.medicalFacility = medicalFacility;
	}
	public ReportMotoristPageForm(String unitNumber, String name,
			String dateOfBirth,String age,String gender, String adddressCityStateZip,
			String contactPhone, String injuries, String emsAgency,
			String medicalFacility) {
		super();
		this.unitNumber = unitNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.age=age;
		this.gender = gender;
		this.adddressCityStateZip = adddressCityStateZip;
		this.contactPhone = contactPhone;
		this.injuries = injuries;
		this.emsAgency = emsAgency;
		this.medicalFacility = medicalFacility;
	}
	public ReportMotoristPageForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
