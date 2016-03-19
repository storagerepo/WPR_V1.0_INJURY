package com.deemsys.project.CrashReport;



/**
 * 
 * @author Deemsys
 * 
 */
public class CrashReportForm {

	private Long crashReportId;
	private String crashReportError;
	private String localReportNumber;
	private String crashId;
	private String crashDate;
	private String county;
	private String addedDate;
	private String filePath;
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
	public void setCrashDate(String crashDate) {
		this.crashDate = crashDate;
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
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public CrashReportForm(Long crashReportId, String crashReportError,
			String localReportNumber, String crashId, String crashDate,
			String county, String addedDate, String filePath, Integer status) {
		super();
		this.crashReportId = crashReportId;
		this.crashReportError = crashReportError;
		this.localReportNumber = localReportNumber;
		this.crashId = crashId;
		this.crashDate = crashDate;
		this.county = county;
		this.addedDate = addedDate;
		this.filePath = filePath;
		this.status = status;
	}
	public CrashReportForm(String crashReportError,
			String localReportNumber, String crashId, String crashDate,
			String county, String addedDate, String filePath, Integer status) {
		super();
		this.crashReportError = crashReportError;
		this.localReportNumber = localReportNumber;
		this.crashId = crashId;
		this.crashDate = crashDate;
		this.county = county;
		this.addedDate = addedDate;
		this.filePath = filePath;
		this.status = status;
	}
	public CrashReportForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
