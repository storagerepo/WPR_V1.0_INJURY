package com.deemsys.project.pdfcrashreport;

public class ReportFirstPageForm {

	  private String localReportNumber;
	  private String crashSeverity;
	  private String numberOfUnits;
	  private String unitInError;
	  private String county;
	  private String cityVillageTownship;
	  private String crashDate;
	  private String timeOfCrash;
	
	  public ReportFirstPageForm(String localReportNumber, String crashSeverity,
			String numberOfUnits, String unitInError, String county,
			String cityVillageTownship, String crashDate, String timeOfCrash) {
		super();
		this.localReportNumber = localReportNumber;
		this.crashSeverity = crashSeverity;
		this.numberOfUnits = numberOfUnits;
		this.unitInError = unitInError;
		this.county = county;
		this.cityVillageTownship = cityVillageTownship;
		this.crashDate = crashDate;
		this.timeOfCrash = timeOfCrash;
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

	public String getTimeOfCrash() {
		return timeOfCrash;
	}

	public void setTimeOfCrash(String timeOfCrash) {
		this.timeOfCrash = timeOfCrash;
	}
	  
	  
}
