package com.deemsys.project.Logging;

import com.deemsys.project.common.InjuryConstants;


/**
 * 
 * @author Deemsys
 * 
 */
public class ActivityLogsForm {

	private String sessionId;
	private String role;
	private Integer roleId;
	private String loginUsername;
	private Integer loginId;
	private String activity;
	private Integer activityId;
	private String primaryUsername;
	private Integer primaryId;
	private String ipAddress;
	private String ipLocation;
	private String accessDate;
	private String accessInTime;
	private String accessOutTime;
	private Integer status;
	private Long accessCount;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = InjuryConstants.getRoleAsText(role);
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getLoginUsername() {
		return loginUsername;
	}
	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
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
	public String getPrimaryUsername() {
		return primaryUsername;
	}
	public void setPrimaryUsername(String primaryUsername) {
		this.primaryUsername = primaryUsername;
	}
	public Integer getPrimaryId() {
		return primaryId;
	}
	public void setPrimaryId(Integer primaryId) {
		this.primaryId = primaryId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getIpLocation() {
		return ipLocation;
	}
	public void setIpLocation(String ipLocation) {
		this.ipLocation = ipLocation;
	}
	public String getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(String accessDate) {
		this.accessDate = accessDate;
	}
	public String getAccessInTime() {
		return accessInTime;
	}
	public void setAccessInTime(String accessInTime) {
		this.accessInTime = accessInTime;
	}
	public String getAccessOutTime() {
		return accessOutTime;
	}
	public void setAccessOutTime(String accessOutTime) {
		this.accessOutTime = accessOutTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getAccessCount() {
		return accessCount;
	}
	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}
	public ActivityLogsForm(String sessionId, String role, Integer roleId,
			String loginUsername, Integer loginId, String activity, Integer activityId,
			String primaryUsername, Integer primaryId, String ipAddress,
			String ipLocation, String accessDate, String accessInTime, String accessOutTime,
			Integer status) {
		super();
		this.sessionId = sessionId;
		this.role = role;
		this.roleId = roleId;
		this.loginUsername = loginUsername;
		this.loginId = loginId;
		this.activity = activity;
		this.activityId = activityId;
		this.primaryUsername = primaryUsername;
		this.primaryId = primaryId;
		this.ipAddress = ipAddress;
		this.ipLocation = ipLocation;
		this.accessDate = accessDate;
		this.accessInTime = accessInTime;
		this.accessOutTime = accessOutTime;
		this.status = status;
	}
	public ActivityLogsForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}
