package com.deemsys.project.Logging;

import java.util.List;

public class ActivityLogsSearchResult {

	private List<ActivityLogsForm> activityLogsForms;
	private Long totalRecords;
	public List<ActivityLogsForm> getActivityLogsForms() {
		return activityLogsForms;
	}
	public void setActivityLogsForms(List<ActivityLogsForm> activityLogsForms) {
		this.activityLogsForms = activityLogsForms;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public ActivityLogsSearchResult(List<ActivityLogsForm> activityLogsForms,
			Long totalRecords) {
		super();
		this.activityLogsForms = activityLogsForms;
		this.totalRecords = totalRecords;
	}
	public ActivityLogsSearchResult() {
		// TODO Auto-generated constructor stub
	}

}
