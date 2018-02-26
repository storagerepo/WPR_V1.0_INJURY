package com.deemsys.project.Logging;

import java.util.List;

public class ActivityExportData {

	private String username;
	private List<ActivityDate> activityDates;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<ActivityDate> getActivityDates() {
		return activityDates;
	}
	public void setActivityDates(List<ActivityDate> activityDates) {
		this.activityDates = activityDates;
	}
	public ActivityExportData(String username, List<ActivityDate> activityDates) {
		super();
		this.username = username;
		this.activityDates = activityDates;
	}
	public ActivityExportData() {
		// TODO Auto-generated constructor stub
	}

}
