package com.deemsys.project.pdfcrashreport;

public class ReportMotoristPageForm {
	private String unitNumber;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String adddressCityStateZip;
	private String contactPhone;
	private String injuries;
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
	public ReportMotoristPageForm(String unitNumber, String name,
			String dateOfBirth, String gender, String adddressCityStateZip,
			String contactPhone, String injuries) {
		super();
		this.unitNumber = unitNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.adddressCityStateZip = adddressCityStateZip;
		this.contactPhone = contactPhone;
		this.injuries = injuries;
	}
	
	
}
