package com.deemsys.project.Logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.IPAccessList.IPAccessListDAO;
import com.deemsys.project.IPAddresses.IPAddressesDAO;
import com.deemsys.project.IPGeoLocation.IPGeoLocationService;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.Activity;
import com.deemsys.project.entity.ActivityLogs;
import com.deemsys.project.entity.ActivityLogsId;
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
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	InjuryProperties injuryProperties;
	
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
		ActivityLogsId activityLogsId = new ActivityLogsId(activityLogsForm.getSessionId(), usersLoginId.getUserId(), primaryLoginId, activityLogsForm.getActivityId(), activityLogsForm.getIpAddress(), InjuryConstants.convertYearFormat(activityLogsForm.getAccessDate()), activityLogsForm.getAccessInTime(), activityLogsForm.getAccessOutTime(), activityLogsForm.getRecordsCount(), activityLogsForm.getDescription(), activityLogsForm.getStatus());
		ActivityLogs activityLogs = new ActivityLogs(activityLogsId, ipAccessList, usersLoginId, activity, usersPrimaryId);
		
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
		ActivityLogsId activityLogsId = new ActivityLogsId(activityLogsForm.getSessionId(), usersLoginId.getUserId(), primaryLoginId, activityLogsForm.getActivityId(), activityLogsForm.getIpAddress(), InjuryConstants.convertYearFormat(activityLogsForm.getAccessDate()), activityLogsForm.getAccessInTime(), activityLogsForm.getAccessOutTime(), activityLogsForm.getRecordsCount(), activityLogsForm.getDescription(), activityLogsForm.getStatus());
		ActivityLogs activityLogs = new ActivityLogs(activityLogsId, ipAccessList, usersLoginId, activity, usersPrimaryId);
		
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
		ActivityLogsId activityLogsId = new ActivityLogsId(activityLogsForm.getSessionId(), usersLoginId.getUserId(), activityLogsForm.getPrimaryId(), activityLogsForm.getActivityId(), activityLogsForm.getIpAddress(), InjuryConstants.convertYearFormat(activityLogsForm.getAccessDate()), activityLogsForm.getAccessInTime(), activityLogsForm.getAccessOutTime(), activityLogsForm.getRecordsCount(), activityLogsForm.getDescription(), activityLogsForm.getStatus());
		ActivityLogs activityLogs = new ActivityLogs(activityLogsId, ipAccessList, usersLoginId, activity, usersPrimaryId);
		
		// Logic Ends

		activityLogsDAO.update(activityLogs);
		return 1;
	}
	
	// Update Logs by Session Id
	public void updateActivityLogsBySessionId(ActivityLogsForm activityLogsForm){
		
		activityLogsDAO.updateActivityLogsBySessionIdAndActivityId(activityLogsForm);

	}
	
	// Search Activity Logs
	public ActivityLogsSearchResult searchActivityLogs(ActivityLogsSearchForm activityLogsSearchForm){
		if(activityLogsSearchForm.getIsSkipMyIp()){
			String ipAddress=InjuryConstants.getRemoteAddr(request);
			activityLogsSearchForm.setSkipMyIpAddress(ipAddress);
		}
		return activityLogsDAO.searchActivityLogs(activityLogsSearchForm);
	}

	public void deleteActivityLogsByDate(Date addedDate) {
		// TODO Auto-generated method stub
		activityLogsDAO.deleteActivityLogsByDate(addedDate);
	}

	// Activity Logs By Session Id
	public ActivityLogsForm getActivityLogs(String sessionId) {
		// TODO Auto-generated method stub
		ActivityLogs activityLogs = activityLogsDAO.getActivityLogsBySessionId(sessionId);
		ActivityLogsForm activityLogsForm = new ActivityLogsForm(sessionId, "", null, "", activityLogs.getUsersByLoginId().getUserId(), activityLogs.getActivity().getName(), activityLogs.getActivity().getId(), "", activityLogs.getUsersByPrimaryId().getUserId(), 
											activityLogs.getIpAccessList().getId().getIpAddress(), "", InjuryConstants.convertMonthFormat(activityLogs.getId().getAccessDate()), activityLogs.getId().getAccessInTime(), activityLogs.getId().getAccessOutTime(), activityLogs.getId().getRecordsCount(), activityLogs.getId().getDescription(), activityLogs.getId().getStatus());
		return activityLogsForm;
	}
	
	// Get IP List with Count
	public ActivityLogsSearchResult getIPAccessList(ActivityLogsSearchForm activityLogsSearchForm){
		return activityLogsDAO.getIPAccessList(activityLogsSearchForm);
	}
	
	// get Excel Data
	public List<ActivityExportData> getActivityExportData(ActivityLogsSearchForm activityLogsSearchForm){
		if(activityLogsSearchForm.getIsSkipMyIp()){
			String ipAddress=InjuryConstants.getRemoteAddr(request);
			activityLogsSearchForm.setSkipMyIpAddress(ipAddress);
		}
		List<ActivityExportDataCount> activityExportDataCounts = activityLogsDAO.exportActivityLogs(activityLogsSearchForm);
		List<ActivityExportData> activityExportDatas = new ArrayList<ActivityExportData>();
		ActivityExportData activityExportData = new ActivityExportData();
		ActivityDate activityDate = new ActivityDate();

		List<ActivityDate> activityDates = new ArrayList<ActivityDate>();
		ActivityIPAddress activityIPAddress = new ActivityIPAddress();
		List<ActivityIPAddress> activityIPAddresses = new ArrayList<ActivityIPAddress>();
		ActivityDataCount activityDataCount = new ActivityDataCount();
		List<ActivityDataCount> activityDataCounts = new ArrayList<ActivityDataCount>();
		int rowCount=0;
		int elseRowCount=0;
		boolean isNewDate,isNewIpAddress=false;
		String username="";
		String date="";
		String ipAddress="";
		for (ActivityExportDataCount activityExportDataCount : activityExportDataCounts) {
			isNewDate=false;
			isNewIpAddress=false;
			if(!username.equals(activityExportDataCount.getUsername())){
				date="";
				username= activityExportDataCount.getUsername();
				if(rowCount!=0){
					activityExportDatas.add(activityExportData);
				}
				activityExportData = new ActivityExportData(username, new ArrayList<ActivityDate>());
				if(!date.equals(activityExportDataCount.getAccessDate())){
					isNewDate=true;
					date=activityExportDataCount.getAccessDate();
					ipAddress="";
					if(rowCount!=0){
						activityDates.add(activityDate);
					}
					activityDate = new ActivityDate(date, new ArrayList<ActivityIPAddress>());
				}
				
				if(!ipAddress.equals(activityExportDataCount.getIpAddress())){
					ipAddress=activityExportDataCount.getIpAddress();
					if(rowCount!=0){
						activityIPAddresses.add(activityIPAddress);
					}
					activityIPAddress = new ActivityIPAddress(ipAddress, activityExportDataCount.getIpCity(), activityExportDataCount.getIpRegion(), activityExportDataCount.getIpCountry(), new ArrayList<ActivityDataCount>());
				}
				activityDataCount = new ActivityDataCount(activityExportDataCount.getActivity(), activityExportDataCount.getActivityId(), activityExportDataCount.getAccessCount(), activityExportDataCount.getNoOfRecordsExported());
				activityIPAddress.getActivityDataCounts().add(activityDataCount);
				activityDate.getActivityIPAddresses().add(activityIPAddress);
				rowCount++;
			}else{
				if(!date.equals(activityExportDataCount.getAccessDate())){
					isNewDate=true;
					date=activityExportDataCount.getAccessDate();
					ipAddress="";
					if(elseRowCount!=0){
						activityDates.add(activityDate);
					}
					activityDate = new ActivityDate(date, new ArrayList<ActivityIPAddress>());
				}
				
				if(!ipAddress.equals(activityExportDataCount.getIpAddress())){
					isNewIpAddress=true;
					ipAddress=activityExportDataCount.getIpAddress();
					if(elseRowCount!=0){
						activityIPAddresses.add(activityIPAddress);
					}
					activityIPAddress = new ActivityIPAddress(ipAddress, activityExportDataCount.getIpCity(), activityExportDataCount.getIpRegion(), activityExportDataCount.getIpCountry(), new ArrayList<ActivityDataCount>());
				}
				activityDataCount = new ActivityDataCount(activityExportDataCount.getActivity(), activityExportDataCount.getActivityId(), activityExportDataCount.getAccessCount(), activityExportDataCount.getNoOfRecordsExported());
				activityIPAddress.getActivityDataCounts().add(activityDataCount);
				if(isNewIpAddress)
					activityDate.getActivityIPAddresses().add(activityIPAddress);
				
				elseRowCount++;
			}
			if(isNewDate)
				activityExportData.getActivityDates().add(activityDate);
		}
		if(rowCount>0)
			activityExportDatas.add(activityExportData);
		
		return activityExportDatas;
	}
	
	// Include Print Activity
	public ActivityLogsForm addPrintActivityLog(Integer activityId,Integer totalRecords){
		ActivityLogsForm activityLogsForm = new ActivityLogsForm(request.getSession().getId(), "", null, loginService.getCurrentUsername(), null, "", activityId, "", null, InjuryConstants.getRemoteAddr(request), "", InjuryConstants.convertMonthFormat(new Date()), InjuryConstants.convertUSAFormatWithTime(new Date()), "", totalRecords, totalRecords+" "+injuryProperties.getProperty("printActivityDescription"), 1);
		this.saveActivityLogs(activityLogsForm);
		return activityLogsForm;
	}
}
