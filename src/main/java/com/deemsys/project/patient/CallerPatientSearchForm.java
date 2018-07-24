package com.deemsys.project.patient;

public class CallerPatientSearchForm {

	private Integer callerAdminId;	
	private Integer[] countyId;
	private Integer[] tier;
	private Integer patientStatus;
	private String crashFromDate;
	private Integer numberOfDays;
	private String crashToDate;
	private String localReportNumber;
	private String[] reportingAgency;
	private String patientName;
	private Integer[] age;
	private Integer callerId;
	private Integer lawyerAdminId;
	private Integer lawyerId;
	private String phoneNumber;
	private Integer pageNumber;
	private Integer itemsPerPage;
	private String addedOnFromDate;
	private String addedOnToDate;
	private Integer isArchived;
	private String archivedFromDate;
	private String archivedToDate;
	private Integer formatType;
	private Integer isRunnerReport;
	private Integer[] damageScale; 
	private Integer directReportStatus;
	private Integer[] seatingPosition;
	
	// Vehicle Search
	private String vehicleMake;
	private String vehicleYear;
	private Integer isOwner;
	private Integer[] typeOfUse;
	
	// Export Excel
	private Integer exportType;
	private String[] exportPatientIds;
	private boolean excludeState;
	
	public Integer getCallerAdminId() {
		return callerAdminId;
	}
	public void setCallerAdminId(Integer callerAdminId) {
		this.callerAdminId = callerAdminId;
	}
	public Integer[] getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer[] countyId) {
		this.countyId = countyId;
	}
	public Integer[] getTier() {
		return tier;
	}
	public void setTier(Integer[] tier) {
		this.tier = tier;
	}
	public Integer getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(Integer patientStatus) {
		this.patientStatus = patientStatus;
	}
	public String getCrashFromDate() {
		return crashFromDate;
	}
	public void setCrashFromDate(String crashFromDate) {
		this.crashFromDate = crashFromDate;
	}
	public String getCrashToDate() {
		return crashToDate;
	}
	public void setCrashToDate(String crashToDate) {
		this.crashToDate = crashToDate;
	}
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	
	public String[] getReportingAgency() {
		return reportingAgency;
	}
	public void setReportingAgency(String[] reportingAgency) {
		this.reportingAgency = reportingAgency;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	public Integer[] getAge() {
		return age;
	}
	public void setAge(Integer[] age) {
		this.age = age;
	}
	public Integer getCallerId() {
		return callerId;
	}
	public void setCallerId(Integer callerId) {
		this.callerId = callerId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public Integer getLawyerAdminId() {
		return lawyerAdminId;
	}
	public void setLawyerAdminId(Integer lawyerAdminId) {
		this.lawyerAdminId = lawyerAdminId;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public Integer getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public Integer getLawyerId() {
		return lawyerId;
	}
	public void setLawyerId(Integer lawyerId) {
		this.lawyerId = lawyerId;
	}
	
	public String getAddedOnFromDate() {
		return addedOnFromDate;
	}
	public void setAddedOnFromDate(String addedOnFromDate) {
		this.addedOnFromDate = addedOnFromDate;
	}
	public String getAddedOnToDate() {
		return addedOnToDate;
	}
	public void setAddedOnToDate(String addedOnToDate) {
		this.addedOnToDate = addedOnToDate;
	}
	
	public Integer getIsArchived() {
		return isArchived;
	}
	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}
	
	public String getArchivedFromDate() {
		return archivedFromDate;
	}
	public void setArchivedFromDate(String archivedFromDate) {
		this.archivedFromDate = archivedFromDate;
	}
	public String getArchivedToDate() {
		return archivedToDate;
	}
	public void setArchivedToDate(String archivedToDate) {
		this.archivedToDate = archivedToDate;
	}
	public Integer getFormatType() {
		return formatType;
	}
	public void setFormatType(Integer formatType) {
		this.formatType = formatType;
	}
	public Integer getIsRunnerReport() {
		return isRunnerReport;
	}
	public void setIsRunnerReport(Integer isRunnerReport) {
		this.isRunnerReport = isRunnerReport;
	}
	public Integer[] getDamageScale() {
		return damageScale;
	}
	public void setDamageScale(Integer[] damageScale) {
		this.damageScale = damageScale;
	}
	public Integer getDirectReportStatus() {
		return directReportStatus;
	}
	public void setDirectReportStatus(Integer directReportStatus) {
		this.directReportStatus = directReportStatus;
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
	public Integer getIsOwner() {
		return isOwner;
	}
	public void setIsOwner(Integer isOwner) {
		this.isOwner = isOwner;
	}
	public Integer[] getTypeOfUse() {
		return typeOfUse;
	}
	public void setTypeOfUse(Integer[] typeOfUse) {
		this.typeOfUse = typeOfUse;
	}
	public Integer[] getSeatingPosition() {
		return seatingPosition;
	}
	public void setSeatingPosition(Integer[] seatingPosition) {
		this.seatingPosition = seatingPosition;
	}
	public Integer getExportType() {
		return exportType;
	}
	public void setExportType(Integer exportType) {
		this.exportType = exportType;
	}
	public String[] getExportPatientIds() {
		return exportPatientIds;
	}
	public void setExportPatientIds(String[] exportPatientIds) {
		this.exportPatientIds = exportPatientIds;
	}
	public boolean isExcludeState() {
		return excludeState;
	}
	public void setExcludeState(boolean excludeState) {
		this.excludeState = excludeState;
	}
	public CallerPatientSearchForm(Integer callerAdminId, Integer countyId[],
			Integer tier[], Integer patientStatus, String crashFromDate,Integer numberOfDays,
			String crashToDate, String localReportNumber,String[] reportingAgency,String patientName,Integer age[],Integer callerId,Integer lawyerAdminId,Integer lawyerId,String phoneNumber,Integer pageNumber,Integer itemsPerPage,String addedOnFromDate,String addedOnToDate,Integer isArchived,String archivedFromDate, String archivedToDate,
			Integer isRunnerReport,Integer[] damageScale,Integer directReportStatus,String vehicleMake, String vehicleYear, Integer isOwner, 
			Integer[] typeOfUse, Integer[] seatingPostion) {
		super();
		this.callerAdminId = callerAdminId;
		this.countyId = countyId;
		this.tier = tier;
		this.patientStatus = patientStatus;
		this.crashFromDate = crashFromDate;
		this.numberOfDays=numberOfDays;
		this.crashToDate = crashToDate;
		this.localReportNumber = localReportNumber;
		this.reportingAgency=reportingAgency;
		this.patientName = patientName;
		this.age=age;
		this.callerId = callerId;
		this.lawyerAdminId=lawyerAdminId;
		this.lawyerId=lawyerId;
		this.phoneNumber = phoneNumber;
		this.pageNumber=pageNumber;
		this.itemsPerPage=itemsPerPage;
		this.addedOnFromDate=addedOnFromDate;
		this.addedOnToDate=addedOnToDate;
		this.isArchived=isArchived;
		this.archivedFromDate=archivedFromDate;
		this.archivedToDate=archivedToDate;
		this.isRunnerReport=isRunnerReport;
		this.damageScale=damageScale;
		this.directReportStatus=directReportStatus;
		this.vehicleMake = vehicleMake;
		this.vehicleYear = vehicleYear;
		this.isOwner = isOwner;
		this.typeOfUse = typeOfUse;
		this.seatingPosition = seatingPostion;
	}
	public CallerPatientSearchForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
