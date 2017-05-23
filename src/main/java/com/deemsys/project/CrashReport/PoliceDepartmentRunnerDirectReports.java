package com.deemsys.project.CrashReport;

public class PoliceDepartmentRunnerDirectReports {
	
	private String localReportNumber;
	private String typeOfReport;
	private Integer countyId;
	private String crashDate;
	private String pdfUrl;
	private Integer mapId;
	public String getLocalReportNumber() {
		return localReportNumber;
	}
	public void setLocalReportNumber(String localReportNumber) {
		this.localReportNumber = localReportNumber;
	}
	public String getTypeOfReport() {
		return typeOfReport;
	}
	public void setTypeOfReport(String typeOfReport) {
		this.typeOfReport = typeOfReport;
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
	public String getPdfUrl() {
		return pdfUrl;
	}
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	public Integer getMapId() {
		return mapId;
	}
	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}
	public PoliceDepartmentRunnerDirectReports() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PoliceDepartmentRunnerDirectReports(String localReportNumber,
			String typeOfReport, Integer countyId, String crashDate,
			String pdfUrl,Integer mapId) {
		super();
		this.localReportNumber = localReportNumber;
		this.typeOfReport = typeOfReport;
		this.countyId = countyId;
		this.crashDate = crashDate;
		this.pdfUrl = pdfUrl;
		this.mapId = mapId;
	}
	
	
	
}
