package com.deemsys.project.Logging;

public class ActivityLogsSearchForm {
	
	public ActivityLogsSearchForm() {
		// TODO Auto-generated constructor stub
	}
	private Integer roleId;
	private String loginId;
	private String primaryLoginId;
	private String ipAddress;
	private String fromDateTime;
	private String toDateTime;
	private Integer activityId;
	private Integer pageNumber;
	private Integer itemsPerPage;
	private Integer exportFormat;
	private boolean isSkipMyIp;
	private String skipMyIpAddress;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPrimaryLoginId() {
		return primaryLoginId;
	}
	public void setPrimaryLoginId(String primaryLoginId) {
		this.primaryLoginId = primaryLoginId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(String fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	public String getToDateTime() {
		return toDateTime;
	}
	public void setToDateTime(String toDateTime) {
		this.toDateTime = toDateTime;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	public Integer getExportFormat() {
		return exportFormat;
	}
	public void setExportFormat(Integer exportFormat) {
		this.exportFormat = exportFormat;
	}
	public boolean getIsSkipMyIp() {
		return isSkipMyIp;
	}
	public void setIsSkipMyIp(boolean isSkipMyIp) {
		this.isSkipMyIp = isSkipMyIp;
	}
	public String getSkipMyIpAddress() {
		return skipMyIpAddress;
	}
	public void setSkipMyIpAddress(String skipMyIpAddress) {
		this.skipMyIpAddress = skipMyIpAddress;
	}
	public ActivityLogsSearchForm(Integer roleId, String loginId,
			String primaryLoginId, String ipAddress, String fromDateTime,
			String toDateTime, Integer activityId, Integer pageNumber, Integer itemsPerPage,
			Integer exportFormat, boolean isSkipMyIp) {
		super();
		this.roleId = roleId;
		this.loginId = loginId;
		this.primaryLoginId = primaryLoginId;
		this.ipAddress = ipAddress;
		this.fromDateTime = fromDateTime;
		this.toDateTime = toDateTime;
		this.activityId = activityId;
		this.pageNumber = pageNumber;
		this.itemsPerPage = itemsPerPage;
		this.exportFormat = exportFormat;
		this.isSkipMyIp = isSkipMyIp;
	}
	
	
}
