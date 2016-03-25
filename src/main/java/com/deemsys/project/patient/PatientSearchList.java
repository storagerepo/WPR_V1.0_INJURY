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
	private Integer countyId;
	private String county;
	private String crashDate;
	private String crashSeverity;
	private String addedDate;
	private String name;
	private String phoneNumber;
	private String address;
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
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
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
	public String getCrashDate() {
		return crashDate;
	}
	public void setCrashDate(String crashDate) {
		this.crashDate = crashDate;
	}
	public String getCrashSeverity() {
		return crashSeverity;
	}
	public void setCrashSeverity(String crashSeverity) {
		this.crashSeverity = crashSeverity;
	}
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
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
	public PatientSearchList(String patientId, String localReportNumber,
			Integer countyId, String county, String crashDate,
			String crashSeverity, String addedDate, String name,
			String phoneNumber, String address, String crashReportFileName,
			Integer callerAdminId, Integer callerId, String callerFirstName,
			String callerLastName, Integer lawyerAdminId, Integer lawyerId,
			String lawyerFirstName, String lawyerLastName, String notes,
			Integer isArchived, Integer patientStatus) {
		super();
		this.patientId = patientId;
		this.localReportNumber = localReportNumber;
		this.countyId = countyId;
		this.county = county;
		this.crashDate = crashDate;
		this.crashSeverity = crashSeverity;
		this.addedDate = addedDate;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
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
	
	
	
	
	
	
}
