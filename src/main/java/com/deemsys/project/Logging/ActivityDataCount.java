package com.deemsys.project.Logging;

public class ActivityDataCount {

	private String activity;
	private Integer activityId;
	private Long accessCount;
	private Long noOfRecordsExported;
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
	public ActivityDataCount(String activity, Integer activityId,
			Long accessCount, Long noOfRecordsExported) {
		super();
		this.activity = activity;
		this.activityId = activityId;
		this.accessCount = accessCount;
		this.noOfRecordsExported = noOfRecordsExported;
	}
	public ActivityDataCount() {
		// TODO Auto-generated constructor stub
	}

}
