package com.deemsys.project.patient;

/**
 * 
 * @author Deemsys
 * 
 */
public class PatientForm {
	
	private String patientId;
	private String crashId;
	private Integer callerId;
	private String callerName;
	private String localReportNumber;
	private String crashSeverity;
	private String reportingAgencyNcic;
	private String reportingAgencyName;
	private String numberOfUnits;
	private String unitInError;
	private Integer countyId;
	private String county;
	private String cityVillageTownship;
	private String crashDate;
	private String addedDate;
	private String timeOfCrash;
	private String unitNumber;
	private String name;
	private String dateOfBirth;
	private Integer age;
	private String gender;
	private String address;
	private Double latitude;
	private Double longitude;
	private String phoneNumber;
	private String injuries;
	private String emsAgency;
	private String medicalFacility;
	private String atFaultInsuranceCompany;
	private String atFaultPolicyNumber;
	private String victimInsuranceCompany;
	private String victimPolicyNumber;
	private Integer tier;
	private String seatingPosition;
	private Integer damageScale;
	private String vehicleMake;
	private String vehicleYear;
	private String vin;
	private String licensePlateNumber;
	private Integer typeOfUse;
	private Integer isOwner;
	private Integer patientStatus;
	private String crashReportFileName;
	private Integer isRunnerReport;
	private Integer status;
	// For Parse PDF
	private Integer isOccupantAvailable;
	
	public String getPatientId() {
		return patientId;
	}
	public String getCrashId() {
		return crashId;
	}
	public void setCrashId(String crashId) {
		this.crashId = crashId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public String getCallerName() {
		return callerName;
	}
	public void setCallerName(String callerName) {
		this.callerName = callerName;
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
	public String getReportingAgencyNcic() {
		return reportingAgencyNcic;
	}
	public void setReportingAgencyNcic(String reportingAgencyNcic) {
		this.reportingAgencyNcic = reportingAgencyNcic;
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
	
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
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
	public String getVictimInsuranceCompany() {
		return victimInsuranceCompany;
	}
	public void setVictimInsuranceCompany(String victimInsuranceCompany) {
		this.victimInsuranceCompany = victimInsuranceCompany;
	}
	public String getVictimPolicyNumber() {
		return victimPolicyNumber;
	}
	public void setVictimPolicyNumber(String victimPolicyNumber) {
		this.victimPolicyNumber = victimPolicyNumber;
	}
	
	public Integer getTier() {
		return tier;
	}
	public void setTier(Integer tier) {
		this.tier = tier;
	}
	public String getVehicleMake() {
		return vehicleMake;
	}
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}
	public String getVehicleYear() {
		return vehicleYear;
	}
	public void setVehicleYear(String vehicleYear) {
		this.vehicleYear = vehicleYear;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	public Integer getTypeOfUse() {
		return typeOfUse;
	}
	public void setTypeOfUse(Integer typeOfUse) {
		this.typeOfUse = typeOfUse;
	}
	public Integer getIsOwner() {
		return isOwner;
	}
	public void setIsOwner(Integer isOwner) {
		this.isOwner = isOwner;
	}
	public Integer getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}
	public String getCrashReportFileName() {
		return crashReportFileName;
	}
	public void setCrashReportFileName(String crashReportFileName) {
		this.crashReportFileName = crashReportFileName;
	}
	public Integer getIsRunnerReport() {
		return isRunnerReport;
	}
	public void setIsRunnerReport(Integer isRunnerReport) {
		this.isRunnerReport = isRunnerReport;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSeatingPosition() {
		return seatingPosition;
	}
	public void setSeatingPosition(String seatingPosition) {
		this.seatingPosition = seatingPosition;
	}
	public Integer getDamageScale() {
		return damageScale;
	}
	public void setDamageScale(Integer damageScale) {
		this.damageScale = damageScale;
	}
	
	// For Parse PDF
	public Integer getIsOccupantAvailable() {
		return isOccupantAvailable;
	}
	public void setIsOccupantAvailable(Integer isOccupantAvailable) {
		this.isOccupantAvailable = isOccupantAvailable;
	}
	public PatientForm(String patientId,String crashId, String localReportNumber, String crashSeverity,
			String reportingAgencyNcic,
			String reportingAgencyName, String numberOfUnits,
			String unitInError, String cityVillageTownship,
			String crashDate, String addedDate, String timeOfCrash,
			String unitNumber, String name, String dateOfBirth,Integer age,String gender,
			String address, Double latitude, Double longitude,
			String phoneNumber, String injuries, String emsAgency,
			String medicalFacility, String atFaultInsuranceCompany,
			String atFaultPolicyNumber, String victimInsuranceCompany,
			String victimPolicyNumber,Integer tier, String vehicleMake, String vehicleYear,
			String VIN, String licensePlateNumber, Integer typeOfUse, Integer isOwner,
			Integer patientStatus,
			String crashReportFileName, Integer status,String seatingPosition, Integer damageScale,Integer isRunnerReport) {
		super();
		this.patientId = patientId;
		this.crashId=crashId;
		this.localReportNumber = localReportNumber;
		this.crashSeverity = crashSeverity;
		this.reportingAgencyNcic = reportingAgencyNcic;
		this.reportingAgencyName = reportingAgencyName;
		this.numberOfUnits = numberOfUnits;
		this.unitInError = unitInError;
		this.cityVillageTownship = cityVillageTownship;
		this.crashDate = crashDate;
		this.addedDate = addedDate;
		this.timeOfCrash = timeOfCrash;
		this.unitNumber = unitNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.age=age;
		this.gender = gender;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phoneNumber = phoneNumber;
		this.injuries = injuries;
		this.emsAgency = emsAgency;
		this.medicalFacility = medicalFacility;
		this.atFaultInsuranceCompany = atFaultInsuranceCompany;
		this.atFaultPolicyNumber = atFaultPolicyNumber;
		this.victimInsuranceCompany = victimInsuranceCompany;
		this.victimPolicyNumber = victimPolicyNumber;
		this.tier=tier;
		this.vehicleMake =  vehicleMake;
		this.vehicleYear = vehicleYear;
		this.vin = VIN;
		this.licensePlateNumber = licensePlateNumber;
		this.typeOfUse = typeOfUse;
		this.isOwner = isOwner;
		this.patientStatus = patientStatus;
		this.crashReportFileName = crashReportFileName;
		this.status = status;
		this.seatingPosition = seatingPosition;
		this.damageScale = damageScale;
		this.isRunnerReport = isRunnerReport;
	}
	public PatientForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
