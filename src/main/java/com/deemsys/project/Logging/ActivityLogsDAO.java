package com.deemsys.project.Logging;

import java.util.Date;
import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.ActivityLogs;
/**
 * 
 * @author Deemsys
 *
 */
public interface ActivityLogsDAO extends IGenericDAO<ActivityLogs>{
	public ActivityLogs getActivityLogsBySessionId(String sessionId);
	public ActivityLogsSearchResult searchActivityLogs(ActivityLogsSearchForm activityLogsSearchForm);
	public ActivityLogsSearchResult getIPAccessList(ActivityLogsSearchForm activityLogsSearchForm);
	public ActivityLogs getActivityLogsBySessionIdAndActivityId(String sessionId, Integer activityId);
	public void updateActivityLogsBySessionIdAndActivityId(ActivityLogsForm activityLogsForm);
	public List<ActivityLogs> getActivityLogsByIpAddressAndDate(String ipAddress,Date accessDate);
	public List<ActivityExportDataCount> exportActivityLogs(ActivityLogsSearchForm activityLogsSearchForm);
	public void deleteActivityLogsByDate(Date addedDate);
	public List<String> getActivityLogsByDateForIP(Date accessDate);
	public Long getActivityLogsCountByIpAddress(String ipAddress);
}
