package com.deemsys.project.PoliceAgency;



/**
 * 
 * @author Deemsys
 * 
 */
public class PoliceAgencyForm {

	private Integer mapId;
	private Integer countyId;
	private String countyName;
	private Integer agencyId;
	private String name;
	private Integer schedulerType;
	private Integer status;
	private String lastUpdatedDate;
	private Integer reportStatus;
	
	public Integer getMapId() {
		return mapId;
	}
	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSchedulerType() {
		return schedulerType;
	}
	public void setSchedulerType(Integer schedulerType) {
		this.schedulerType = schedulerType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public Integer getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public PoliceAgencyForm(Integer mapId, Integer countyId,String countyName, Integer agencyId,
			String name, Integer schedulerType, Integer status, String lastUpdatedDate, Integer reportStatus) {
		super();
		this.mapId = mapId;
		this.countyId = countyId;
		this.countyName=countyName;
		this.agencyId = agencyId;
		this.name = name;
		this.schedulerType = schedulerType;
		this.status = status;
		this.lastUpdatedDate = lastUpdatedDate;
		this.reportStatus = reportStatus;
	}
	public PoliceAgencyForm() {
		super();
		// TODO Auto-generated constructor stub
	}
}
