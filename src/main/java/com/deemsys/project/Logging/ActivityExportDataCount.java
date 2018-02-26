package com.deemsys.project.Logging;

import java.util.Date;

import com.deemsys.project.common.InjuryConstants;

public class ActivityExportDataCount {

	private String username;
	private String accessDate;
	private String ipAddress;
	private String ipCity;
	private String ipRegion;
	private String ipCountry;
	private String activity;
	private Integer activityId;
	private Long accessCount;
	private Long noOfRecordsExported;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = InjuryConstants.convertMonthFormat(accessDate);
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpCity() {
		return ipCity;
	}

	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
	}

	public String getIpRegion() {
		return ipRegion;
	}

	public void setIpRegion(String ipRegion) {
		this.ipRegion = ipRegion;
	}

	public String getIpCountry() {
		return ipCountry;
	}

	public void setIpCountry(String ipCountry) {
		this.ipCountry = ipCountry;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Long getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}

	public Long getNoOfRecordsExported() {
		return noOfRecordsExported;
	}

	public void setNoOfRecordsExported(Long noOfRecordsExported) {
		this.noOfRecordsExported = noOfRecordsExported;
	}

	public ActivityExportDataCount(String username, String accessDate,
			String ipAddress, String ipCity, String ipRegion, String ipCountry,
			String activity, Integer activityId, Long accessCount,
			Long noOfRecordsExported) {
		super();
		this.username = username;
		this.accessDate = accessDate;
		this.ipAddress = ipAddress;
		this.ipCity = ipCity;
		this.ipRegion = ipRegion;
		this.ipCountry = ipCountry;
		this.activity = activity;
		this.activityId = activityId;
		this.accessCount = accessCount;
		this.noOfRecordsExported = noOfRecordsExported;
	}

	public ActivityExportDataCount() {
		// TODO Auto-generated constructor stub
	}

}
