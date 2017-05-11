package com.deemsys.project.CrashReport;

public class ImportCrashReportStatus {

	private String crashId;
	private boolean isSuccess;
	private String message;
	public String getCrashId() {
		return crashId;
	}
	public void setCrashId(String crashId) {
		this.crashId = crashId;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ImportCrashReportStatus(String crashId, boolean isSuccess,
			String message) {
		super();
		this.crashId = crashId;
		this.isSuccess = isSuccess;
		this.message = message;
	}
	public ImportCrashReportStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
