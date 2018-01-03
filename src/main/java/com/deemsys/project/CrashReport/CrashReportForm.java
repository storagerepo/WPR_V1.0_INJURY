package com.deemsys.project.CrashReport;

import java.util.Date;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;



/**
 * 
 * @author Deemsys
 * 
 */
public class CrashReportForm extends InjuryProperties{

	private Long crashReportId;
	private String crashReportError;
	private String localReportNumber;
	private String crashId;
	private String crashDate;
	private String county;
	private String addedDate;
	private String filePath;
	private Integer numberOfPatients;
	private Integer vehicleCount;
	private Integer isRunnerReport;
	private String runnerReportAddedDate;
	private Integer reportFrom;
	private Integer status;
	private Integer callerAdminId;
	private Integer callerId;
	private Integer lawyerAdminId;
	private Integer lawyerId;
	private Integer isArchived;
	private String archivedDate;
	private String archivedDateTime;
	private Integer directReportStatus;
	private String oldFilePath;
	private String reportFromDepartment;
	private Date addedDateTime;
	private Date runnerReportAddedDateTime;
	public Long getCrashReportId() {
		return crashReportId;
	}
	public void setCrashReportId(Long crashReportId) {
		this.crashReportId = crashReportId;
	}
	public String getCrashReportError() {
		return crashReportError;
	}
	public void setCrashReportError(String crashReportError) {
		this.crashReportError = crashReportError;
	}
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	public String getCrashId() {
		return crashId;
	}
	public void setCrashId(String crashId) {
		this.crashId = crashId;
	}
	public String getCrashDate() {
		return crashDate;
	}
	public void setCrashDate(Date crashDate) {
		this.crashDate = InjuryConstants.convertMonthFormat(crashDate);
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = InjuryConstants.convertMonthFormat(addedDate);
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		if(this.isRunnerReport==1){
			if(this.reportFrom==Integer.parseInt(getProperty("reportFromDeemsys"))){
				this.filePath = getProperty("runnerBucketURL")+filePath;
			}else{
				this.filePath = getProperty("policeDepartmentBucketURL")+this.reportFrom+getProperty("policeDepartmentFolder")+filePath;
			}
		}else{
			this.filePath = getProperty("bucketURL")+filePath;
		}
	}
	
	public Integer getNumberOfPatients() {
		return numberOfPatients;
	}
	public void setNumberOfPatients(Integer numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}
	public Integer getVehicleCount() {
		return vehicleCount;
	}
	public void setVehicleCount(Integer vehicleCount) {
		this.vehicleCount = vehicleCount;
	}
	public Integer getIsRunnerReport() {
		return isRunnerReport;
	}
	public void setIsRunnerReport(Integer isRunnerReport) {
		this.isRunnerReport = isRunnerReport;
	}
	public String getRunnerReportAddedDate() {
		return runnerReportAddedDate;
	}
	public void setRunnerReportAddedDate(Date runnerReportAddedDate) {
		this.runnerReportAddedDate = InjuryConstants.convertMonthFormat(runnerReportAddedDate);;
	}
	public Integer getReportFrom() {
		return reportFrom;
	}
	public void setReportFrom(Integer reportFrom) {
		this.reportFrom = reportFrom;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getArchivedDate() {
		return archivedDate;
	}
	public void setArchivedDate(Date archivedDate) {
		this.archivedDate = InjuryConstants.convertMonthFormat(archivedDate);
	}
	public String getArchivedDateTime() {
		return archivedDateTime;
	}
	public void setArchivedDateTime(String archivedDateTime) {
		this.archivedDateTime = InjuryConstants.convertUSAFormatWithTimeAMPM(archivedDateTime);
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
	public Integer getIsArchived() {
		return isArchived;
	}
	public void setIsArchived(Integer isArchived) {
		this.isArchived = isArchived;
	}
	public Integer getDirectReportStatus() {
		return directReportStatus;
	}
	public void setDirectReportStatus(Integer directReportStatus) {
		this.directReportStatus = directReportStatus;
	}
	public String getOldFilePath() {
		return oldFilePath;
	}
	public void setOldFilePath(String oldFilePath) {
		if(oldFilePath!=null){
			if(this.isRunnerReport==2){
				if(this.reportFrom==Integer.parseInt(getProperty("reportFromDeemsys"))){
					this.oldFilePath = getProperty("runnerBucketURL")+oldFilePath;
				}else{
					this.oldFilePath = getProperty("policeDepartmentBucketURL")+this.reportFrom+getProperty("policeDepartmentFolder")+oldFilePath;
				}
			}else{
				this.oldFilePath = getProperty("bucketURL")+oldFilePath;
			}
		}else{
			this.oldFilePath=oldFilePath;
		}
	}
	public String getReportFromDepartment() {
		return reportFromDepartment;
	}
	public void setReportFromDepartment(String reportFromDepartment) {
		this.reportFromDepartment = reportFromDepartment;
	}
	public Date getAddedDateTime() {
		return addedDateTime;
	}
	public void setAddedDateTime(Date addedDateTime) {
		this.addedDateTime = addedDateTime;
	}
	public Date getRunnerReportAddedDateTime() {
		return runnerReportAddedDateTime;
	}
	public void setRunnerReportAddedDateTime(Date runnerReportAddedDateTime) {
		this.runnerReportAddedDateTime = runnerReportAddedDateTime;
	}
	public CrashReportForm(Long crashReportId, String crashReportError,
			String localReportNumber, String crashId, String crashDate,
			String county, String addedDate, String filePath,Integer numberOfPatients, Integer isRunnerReport, String runnerReportAddedDate,Integer status) {
		super();
		this.crashReportId = crashReportId;
		this.crashReportError = crashReportError;
		this.localReportNumber = localReportNumber;
		this.crashId = crashId;
		this.crashDate = crashDate;
		this.county = county;
		this.addedDate = addedDate;
		this.filePath = filePath;
		this.numberOfPatients=numberOfPatients;
		this.isRunnerReport = isRunnerReport;
		this.runnerReportAddedDate = runnerReportAddedDate;
		this.status = status;
	}
	public CrashReportForm(String crashReportError,
			String localReportNumber, String crashId, String crashDate,
			String county, String addedDate, String filePath,Integer numberOfPatients, Integer vehicleCount, Integer isRunnerReport, String runnerReportAddedDate,Integer status,
			Integer reportFrom,Date addedDateTime, Date runnerReportAddedDateTime) {
		super();
		this.crashReportError = crashReportError;
		this.localReportNumber = localReportNumber;
		this.crashId = crashId;
		this.crashDate = crashDate;
		this.county = county;
		this.addedDate = addedDate;
		this.filePath = filePath;
		this.numberOfPatients=numberOfPatients;
		this.vehicleCount = vehicleCount;
		this.isRunnerReport = isRunnerReport;
		this.runnerReportAddedDate = runnerReportAddedDate;
		this.status = status;
		this.reportFrom = reportFrom;
		this.addedDateTime = addedDateTime;
		this.runnerReportAddedDateTime = runnerReportAddedDateTime;
	}
	public CrashReportForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
