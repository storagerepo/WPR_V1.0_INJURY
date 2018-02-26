package com.deemsys.project.Logging;

import java.util.List;

public class ActivityDate {

	private String accessDate;
	private List<ActivityIPAddress> activityIPAddresses;
	public String getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}
	public List<ActivityIPAddress> getActivityIPAddresses() {
		return activityIPAddresses;
	}
	public void setActivityIPAddresses(List<ActivityIPAddress> activityIPAddresses) {
		this.activityIPAddresses = activityIPAddresses;
	}
	public ActivityDate(String accessDate,
			List<ActivityIPAddress> activityIPAddresses) {
		super();
		this.accessDate = accessDate;
		this.activityIPAddresses = activityIPAddresses;
	}
	public ActivityDate() {
		// TODO Auto-generated constructor stub
	}

}
