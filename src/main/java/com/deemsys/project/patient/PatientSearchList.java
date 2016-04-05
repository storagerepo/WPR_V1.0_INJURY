package com.deemsys.project.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientCallerAdminMapId;

public class PatientSearchList extends InjuryProperties{
	private String patientId;
	private String localReportNumber;
	private Integer numberOfPatients;
	private String crashDate;
	private String addedDate;	
	private Integer countyId;
	private String county;
	private String crashSeverity;	
	private String name;
	private String phoneNumber;
	private String address;
	private String dateOfBirth;
	private String age;
	private String crashReportFileName;
	private Integer callerAdminId;
	private Integer callerId;
	private String callerFirstName;
	private String callerLastName;
	private Integer lawyerAdminId;
	private Integer lawyerId;
	private String lawyerFirstName;	
	private String lawyerLastName;
	private String notes;
	private Integer isArchived;
	private Integer patientStatus;
	private boolean isSelected=false;
	private String atFaultInsuranceCompany;
	private String victimInsuranceCompany;
	private Integer tier;
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCrashSeverity() {
		return crashSeverity;
	}
	public void setCrashSeverity(String crashSeverity) {
		this.crashSeverity = crashSeverity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCrashReportFileName() {
		return crashReportFileName;
	}
	public void setCrashReportFileName(String crashReportFileName) {
		this.crashReportFileName = getProperty("bucketURL")+crashReportFileName;
	}
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public String getCallerFirstName() {
		return callerFirstName;
	}
	public void setCallerFirstName(String callerFirstName) {
		this.callerFirstName = callerFirstName;
	}
	public String getCallerLastName() {
		return callerLastName;
	}
	public void setCallerLastName(String callerLastName) {
		this.callerLastName = callerLastName;
	}
	public Integer getLawyerAdminId() {
		return lawyerAdminId;
	}
	public void setLawyerAdminId(Integer lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
	}
	public Integer getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}
	public String getLawyerFirstName() {
		return lawyerFirstName;
	}
	public void setLawyerFirstName(String lawyerFirstName) {
		this.lawyerFirstName = lawyerFirstName;
	}
	public String getLawyerLastName() {
		return lawyerLastName;
	}
	public void setLawyerLastName(String lawyerLastName) {
		this.lawyerLastName = lawyerLastName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Integer getIsArchived() {
		return isArchived;
	}
	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}
	public Integer getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}
	public String getAtFaultInsuranceCompany() {
		return atFaultInsuranceCompany;
	}
	public void setAtFaultInsuranceCompany(String atFaultInsuranceCompany) {
		this.atFaultInsuranceCompany = atFaultInsuranceCompany;
	}
	public String getVictimInsuranceCompany() {
		return victimInsuranceCompany;
	}
	public void setVictimInsuranceCompany(String victimInsuranceCompany) {
		this.victimInsuranceCompany = victimInsuranceCompany;
	}
	public PatientSearchList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Integer getTier() {
		return tier;
	}
	public void setTier(Integer tier) {
		this.tier = tier;
	}
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
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
	public Integer getNumberOfPatients() {
		return numberOfPatients;
	}
	public void setNumberOfPatients(Integer numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
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
	public PatientSearchList(String patientId, String localReportNumber,
			Integer numberOfPatients, String crashDate, String addedDate,
			Integer countyId, String county, String crashSeverity, String name,
			String phoneNumber, String address, String dateOfBirth, String age,
			String crashReportFileName, Integer callerAdminId,
			Integer callerId, String callerFirstName, String callerLastName,
			Integer lawyerAdminId, Integer lawyerId, String lawyerFirstName,
			String lawyerLastName, String notes, Integer isArchived,
			Integer patientStatus, boolean isSelected,
			String atFaultInsuranceCompany, String victimInsuranceCompany,
			Integer tier) {
		super();
		this.patientId = patientId;
		this.localReportNumber = localReportNumber;
		this.numberOfPatients = numberOfPatients;
		this.crashDate = crashDate;
		this.addedDate = addedDate;
		this.countyId = countyId;
		this.county = county;
		this.crashSeverity = crashSeverity;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.crashReportFileName = crashReportFileName;
		this.callerAdminId = callerAdminId;
		this.callerId = callerId;
		this.callerFirstName = callerFirstName;
		this.callerLastName = callerLastName;
		this.lawyerAdminId = lawyerAdminId;
		this.lawyerId = lawyerId;
		this.lawyerFirstName = lawyerFirstName;
		this.lawyerLastName = lawyerLastName;
		this.notes = notes;
		this.isArchived = isArchived;
		this.patientStatus = patientStatus;
		this.isSelected = isSelected;
		this.atFaultInsuranceCompany = atFaultInsuranceCompany;
		this.victimInsuranceCompany = victimInsuranceCompany;
		this.tier = tier;
	}
	
	
	
	
	
}
