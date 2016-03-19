package com.deemsys.project.patient;

/**
 * 
 * @author Deemsys
 * 
 */
public class PatientForm {
	
	private String id;
	private Integer callerId;
	private String callerName;
	private Integer clinicId;
	private String clinicName;
	private Integer doctorId;
	private String doctorName;
	private String localReportNumber;
	private String crashSeverity;
	private String reportingAgencyName;
	private String numberOfUnits;
	private String unitInError;
	private String county;
	private String cityVillageTownship;
	private String crashDate;
	private String addedDate;
	private String timeOfCrash;
	private String unitNumber;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String address;
	private Double latitude;
	private Double longitude;
	private String phoneNumber;
	private String injuries;
	private String emsAgency;
	private String medicalFacility;
	private String insuranceCompany;
	private String policyNumber;
	private String atFaultInsuranceCompany;
	private String atFaultPolicyNumber;
	private String crashReportFileName;
	private Integer patientStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCallerId() {
		return callerId;
	}

	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}

	public Integer getClinicId() {
		return clinicId;
	}

	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
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



	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
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

	
	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public String getTimeOfCrash() {
		return timeOfCrash;
	}

	public void setTimeOfCrash(String timeOfCrash) {
		this.timeOfCrash = timeOfCrash;
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

	public String getAtFaultInsuranceCompany() {
		return atFaultInsuranceCompany;
	}

	public void setAtFaultInsuranceCompany(String atFaultInsuranceCompany) {
		this.atFaultInsuranceCompany = atFaultInsuranceCompany;
	}

	public String getAtFaultPolicyNumber() {
		return atFaultPolicyNumber;
	}

	public void setAtFaultPolicyNumber(String atFaultPolicyNumber) {
		this.atFaultPolicyNumber = atFaultPolicyNumber;
	}

	public Integer getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}

	public PatientForm(String id, Integer callerId, Integer clinicId,
			Integer doctorId, String localReportNumber, String crashSeverity,
			String reportingAgencyName, String numberOfUnits,
			String unitInError, String county, String cityVillageTownship,
			String crashDate, String timeOfCrash, String unitNumber,
			String name, String dateOfBirth, String gender, String address,
			String phoneNumber, String injuries, String emsAgency,
			String medicalFacility, String crashReportFileName,
			Integer patientStatus) {
		super();
		this.id = id;
		this.callerId = callerId;
		this.clinicId = clinicId;
		this.doctorId = doctorId;
		this.localReportNumber = localReportNumber;
		this.crashSeverity = crashSeverity;
		this.reportingAgencyName = reportingAgencyName;
		this.numberOfUnits = numberOfUnits;
		this.unitInError = unitInError;
		this.county = county;
		this.cityVillageTownship = cityVillageTownship;
		this.crashDate = crashDate;
		this.timeOfCrash = timeOfCrash;
		this.unitNumber = unitNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.injuries = injuries;
		this.emsAgency = emsAgency;
		this.medicalFacility = medicalFacility;
		this.crashReportFileName = crashReportFileName;
		this.patientStatus = patientStatus;
	}

	
	
	public PatientForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PatientForm(Integer callerId, Integer clinicId, Integer doctorId) {
		this.callerId = callerId;
		this.clinicId = clinicId;
		this.doctorId = doctorId;
	}

	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
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

	public PatientForm(String localReportNumber,
			String crashSeverity, String reportingAgencyName,
			String numberOfUnits, String unitInError, String county,
			String cityVillageTownship, String crashDate, String addedDate, String timeOfCrash,
			String unitNumber, String name, String dateOfBirth, String gender,
			String address, Double latitude, Double longitude,
			String phoneNumber, String injuries, String emsAgency,
			String medicalFacility, String insuranceCompany,
			String policyNumber, String atFaultInsuranceCompany,
			String atFaultPolicyNumber, String crashReportFileName,
			Integer patientStatus) {
		super();
		this.localReportNumber = localReportNumber;
		this.crashSeverity = crashSeverity;
		this.reportingAgencyName = reportingAgencyName;
		this.numberOfUnits = numberOfUnits;
		this.unitInError = unitInError;
		this.county = county;
		this.cityVillageTownship = cityVillageTownship;
		this.crashDate = crashDate;
		this.addedDate= addedDate;
		this.timeOfCrash = timeOfCrash;
		this.unitNumber = unitNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phoneNumber = phoneNumber;
		this.injuries = injuries;
		this.emsAgency = emsAgency;
		this.medicalFacility = medicalFacility;
		this.insuranceCompany = insuranceCompany;
		this.policyNumber = policyNumber;
		this.atFaultInsuranceCompany = atFaultInsuranceCompany;
		this.atFaultPolicyNumber = atFaultPolicyNumber;
		this.crashReportFileName = crashReportFileName;
		this.patientStatus = patientStatus;
	}

	
}
