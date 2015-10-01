package com.deemsys.project.patients;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * 
 * @author Deemsys
 *
 */
public class PatientsForm {

	private Integer id;
	private Integer callerId;
	private String callerName;
	private String doctorName;
	private Integer doctorId;
	private String localReportNumber;
	private String crashSeverity;
	private String reportingAgencyName;
	private String numberOfUnits;
	private String unitInError;
	private String country;
	private String cityVillageTownship;
	private String crashDate;
	private String timeOfCrash;
	private String localReportNumber1;
	private String unitNumber;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String address;
	private String phoneNumber;
	private String injuries;
	private String emsAgency;
	private String medicalFacility;
	private String localReportNumber2;
	private String unitInError1;
	private String unitNumber1;
	private String ownerName;
	private String ownerPhoneNumber;
	private String damageScale;
	private String proofOfInsurance;
	private String insuranceCompany;
	private String policyNumber;
	private String crashReportFileName;
	private Integer patientStatus;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	public String getCrashSeverity() {
		return crashSeverity;
	}
	public void setCrashSeverity(String crashSeverity) {
		this.crashSeverity = crashSeverity;
	}
	public String getReportingAgencyName() {
		return reportingAgencyName;
	}
	public void setReportingAgencyName(String reportingAgencyName) {
		this.reportingAgencyName = reportingAgencyName;
	}
	public String getNumberOfUnits() {
		return numberOfUnits;
	}
	public void setNumberOfUnits(String numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}
	public String getUnitInError() {
		return unitInError;
	}
	public void setUnitInError(String unitInError) {
		this.unitInError = unitInError;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCityVillageTownship() {
		return cityVillageTownship;
	}
	public void setCityVillageTownship(String cityVillageTownship) {
		this.cityVillageTownship = cityVillageTownship;
	}
	public String getCrashDate() {
		return crashDate;
	}
	public void setCrashDate(String crashDate) {
		this.crashDate = crashDate;
	}
	public String getTimeOfCrash() {
		return timeOfCrash;
	}
	public void setTimeOfCrash(String timeOfCrash) {
		this.timeOfCrash = timeOfCrash;
	}
	public String getLocalReportNumber1() {
		return localReportNumber1;
	}
	public void setLocalReportNumber1(String localReportNumber1) {
		this.localReportNumber1 = localReportNumber1;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public String getLocalReportNumber2() {
		return localReportNumber2;
	}
	public void setLocalReportNumber2(String localReportNumber2) {
		this.localReportNumber2 = localReportNumber2;
	}
	public String getUnitInError1() {
		return unitInError1;
	}
	public void setUnitInError1(String unitInError1) {
		this.unitInError1 = unitInError1;
	}
	public String getUnitNumber1() {
		return unitNumber1;
	}
	public void setUnitNumber1(String unitNumber1) {
		this.unitNumber1 = unitNumber1;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}
	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}
	public String getDamageScale() {
		return damageScale;
	}
	public void setDamageScale(String damageScale) {
		this.damageScale = damageScale;
	}
	public String getProofOfInsurance() {
		return proofOfInsurance;
	}
	public void setProofOfInsurance(String proofOfInsurance) {
		this.proofOfInsurance = proofOfInsurance;
	}
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public Integer getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}
	public PatientsForm(Integer id, Integer callerId, Integer doctorId, String localReportNumber,
			String crashSeverity, String reportingAgencyName,
			String numberOfUnits, String unitInError, String country,
			String cityVillageTownship, String crashDate, String timeOfCrash,
			String localReportNumber1, String unitNumber, String name,
			String dateOfBirth, String gender, String address,
			String phoneNumber, String injuries, String emsAgency,
			String medicalFacility, String localReportNumber2,
			String unitInError1, String unitNumber1, String ownerName,
			String ownerPhoneNumber, String damageScale,
			String proofOfInsurance, String insuranceCompany, String policyNumber,String crashReportFileName,Integer patientStatus) {
		super();
		this.id = id;
		this.callerId = callerId;
		this.doctorId = doctorId;
		this.localReportNumber = localReportNumber;
		this.crashSeverity = crashSeverity;
		this.reportingAgencyName = reportingAgencyName;
		this.numberOfUnits = numberOfUnits;
		this.unitInError = unitInError;
		this.country = country;
		this.cityVillageTownship = cityVillageTownship;
		this.crashDate = crashDate;
		this.timeOfCrash = timeOfCrash;
		this.localReportNumber1 = localReportNumber1;
		this.unitNumber = unitNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.injuries = injuries;
		this.emsAgency = emsAgency;
		this.medicalFacility = medicalFacility;
		this.localReportNumber2 = localReportNumber2;
		this.unitInError1 = unitInError1;
		this.unitNumber1 = unitNumber1;
		this.ownerName = ownerName;
		this.ownerPhoneNumber = ownerPhoneNumber;
		this.damageScale = damageScale;
		this.proofOfInsurance = proofOfInsurance;
		this.insuranceCompany=insuranceCompany;
		this.policyNumber=policyNumber;
		this.crashReportFileName=crashReportFileName;
		this.patientStatus=patientStatus;
	}
	public PatientsForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PatientsForm(Integer callerId ,Integer doctorId)
	{
		this.callerId=callerId;
		this.doctorId=doctorId;
	}
	public String getCallerName() {
		return callerName;
	}
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getCrashReportFileName() {
		return crashReportFileName;
	}
	public void setCrashReportFileName(String crashReportFileName) {
		this.crashReportFileName = crashReportFileName;
	}
	
	
	}
