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
	private Integer isRunnerReport;
	private String runnerReportAddedDate;
	private Integer reportFrom;
	private Integer status;
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
			String county, String addedDate, String filePath,Integer numberOfPatients, Integer isRunnerReport, String runnerReportAddedDate,Integer status) {
		super();
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
	public CrashReportForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
