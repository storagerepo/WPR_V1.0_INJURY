package com.deemsys.project.Logging;

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
}
