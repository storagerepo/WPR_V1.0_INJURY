package com.deemsys.project.Logging;

import java.util.List;

public class ActivityIPAddress {

	private String ipAddress;
	private String ipCity;
	private String ipRegion;
	private String ipCountry;
	private List<ActivityDataCount> activityDataCounts;
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
	public List<ActivityDataCount> getActivityDataCounts() {
		return activityDataCounts;
	}
	public void setActivityDataCounts(List<ActivityDataCount> activityDataCounts) {
		this.activityDataCounts = activityDataCounts;
	}
	public ActivityIPAddress(String ipAddress, String ipCity, String ipRegion,
			String ipCountry, List<ActivityDataCount> activityDataCounts) {
		super();
		this.ipAddress = ipAddress;
		this.ipCity = ipCity;
		this.ipRegion = ipRegion;
		this.ipCountry = ipCountry;
		this.activityDataCounts = activityDataCounts;
	}
	public ActivityIPAddress() {
		// TODO Auto-generated constructor stub
	}

}
