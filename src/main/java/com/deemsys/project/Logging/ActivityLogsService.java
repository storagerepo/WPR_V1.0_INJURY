package com.deemsys.project.Logging;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.IPAccessList.IPAccessListDAO;
import com.deemsys.project.IPAddresses.IPAddressesDAO;
import com.deemsys.project.IPGeoLocation.IPGeoLocationService;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Activity;
import com.deemsys.project.entity.ActivityLogs;
import com.deemsys.project.entity.IpAccessList;
import com.deemsys.project.entity.IpAddresses;
import com.deemsys.project.entity.IpAddressesId;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;

@Service
@Transactional
public class ActivityLogsService {

	@Autowired
	ActivityLogsDAO activityLogsDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	IPGeoLocationServiceImpl ipGeoLocationServiceImpl;
	
	@Autowired
	IPGeoLocationService ipGeoLocationService;
	
	@Autowired
	IPAccessListDAO ipAccessListDAO;
	
	// Merge 
	public int mergeActivityLogs(ActivityLogsForm activityLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Users usersLoginId = usersDAO.getByUserName(activityLogsForm.getLoginUsername());
		String loginRole = usersLoginId.getRoles().getRole();
		Integer primaryLoginId=loginService.getUserIdOfAdmin(loginRole, usersLoginId.getUserId());
		Users usersPrimaryId=null;
		if(primaryLoginId!=null){
			usersPrimaryId= usersDAO.get(primaryLoginId);
		}else{
			usersPrimaryId=usersLoginId;
			primaryLoginId=usersLoginId.getUserId();
		}
		Activity activity = new Activity();
		activity.setId(activityLogsForm.getActivityId());
		// IP Addresses 
		IpAccessList ipAccessList = ipAccessListDAO.getByIpAddressAndLoginId(activityLogsForm.getIpAddress(), primaryLoginId);
		ActivityLogs activityLogs = new ActivityLogs(activityLogsForm.getSessionId(), ipAccessList, usersLoginId, activity, usersPrimaryId, InjuryConstants.convertYearFormat(activityLogsForm.getAccessDate()), activityLogsForm.getAccessInTime(), activityLogsForm.getAccessOutTime(), activityLogsForm.getStatus());
		// Logic Ends

		activityLogsDAO.merge(activityLogs);
		return 1;
	}

	// Save an Entry
	public int saveActivityLogs(ActivityLogsForm activityLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Users usersLoginId = usersDAO.getByUserName(activityLogsForm.getLoginUsername());
		String loginRole = usersLoginId.getRoles().getRole();
		Integer primaryLoginId=loginService.getUserIdOfAdmin(loginRole, usersLoginId.getUserId());
		Users usersPrimaryId=null;
		if(primaryLoginId!=null){
			usersPrimaryId= usersDAO.get(primaryLoginId);
		}else{
			usersPrimaryId=usersLoginId;
			primaryLoginId=usersLoginId.getUserId();
		}
		Activity activity = new Activity();
		activity.setId(activityLogsForm.getActivityId());
		// IP Address
		IpAccessList ipAccessList = ipAccessListDAO.getByIpAddressAndLoginId(activityLogsForm.getIpAddress(), primaryLoginId);
		ActivityLogs activityLogs = new ActivityLogs(activityLogsForm.getSessionId(), ipAccessList, usersLoginId, activity, usersPrimaryId, InjuryConstants.convertYearFormat(activityLogsForm.getAccessDate()), activityLogsForm.getAccessInTime(), activityLogsForm.getAccessOutTime(), activityLogsForm.getStatus());
		// Logic Ends
		activityLogsDAO.save(activityLogs);
		return 1;
	}

	// Update an Entry
	public int updateActivityLogs(ActivityLogsForm activityLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Users usersLoginId= new Users();
		usersLoginId.setUserId(activityLogsForm.getLoginId());
		Users usersPrimaryId= new Users();
		usersPrimaryId.setUserId(activityLogsForm.getPrimaryId());
		Activity activity = new Activity();
		activity.setId(activityLogsForm.getActivityId());
		// IP Address
		IpAccessList ipAccessList = ipAccessListDAO.getByIpAddressAndLoginId(activityLogsForm.getIpAddress(), activityLogsForm.getPrimaryId());
		ActivityLogs activityLogs = new ActivityLogs(activityLogsForm.getSessionId(), ipAccessList, usersLoginId, activity, usersPrimaryId, InjuryConstants.convertYearFormat(activityLogsForm.getAccessDate()), activityLogsForm.getAccessInTime(), activityLogsForm.getAccessOutTime(), activityLogsForm.getStatus());
		// Logic Ends

		activityLogsDAO.update(activityLogs);
		return 1;
	}
	
	// Update Logs by Session Id
	public void updateActivityLogsBySessionId(ActivityLogsForm activityLogsForm){
		ActivityLogs activityLogs = activityLogsDAO.getActivityLogsBySessionId(activityLogsForm.getSessionId());
		if(activityLogs!=null){
			activityLogs.setAccessOutTime(activityLogsForm.getAccessOutTime());
			activityLogsDAO.update(activityLogs);
		}
	}
	
	// Search Activity Logs
	public ActivityLogsSearchResult searchActivityLogs(ActivityLogsSearchForm activityLogsSearchForm){
		return activityLogsDAO.searchActivityLogs(activityLogsSearchForm);
	}

	public void deleteActivityLogs(Integer id) {
		// TODO Auto-generated method stub
		
	}

	// Activity Logs By Session Id
	public ActivityLogsForm getActivityLogs(String sessionId) {
		// TODO Auto-generated method stub
		ActivityLogs activityLogs = activityLogsDAO.getActivityLogsBySessionId(sessionId);
		ActivityLogsForm activityLogsForm = new ActivityLogsForm(sessionId, "", null, "", activityLogs.getUsersByLoginId().getUserId(), activityLogs.getActivity().getName(), activityLogs.getActivity().getId(), "", activityLogs.getUsersByPrimaryId().getUserId(), 
											activityLogs.getIpAccessList().getId().getIpAddress(), "", InjuryConstants.convertMonthFormat(activityLogs.getAccessDate()), activityLogs.getAccessInTime(), activityLogs.getAccessOutTime(), activityLogs.getStatus());
		return activityLogsForm;
	}
	
	// Get IP List with Count
	public ActivityLogsSearchResult getIPAccessList(ActivityLogsSearchForm activityLogsSearchForm){
		return activityLogsDAO.getIPAccessList(activityLogsSearchForm);
	}
}
