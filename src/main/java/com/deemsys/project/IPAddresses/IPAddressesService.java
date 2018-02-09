package com.deemsys.project.IPAddresses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.IPGeoLocation.IPGeoLocationForm;
import com.deemsys.project.IPGeoLocation.IPGeoLocationService;
import com.deemsys.project.Logging.ActivityLogsDAO;
import com.deemsys.project.Logging.IPGeoLocationServiceImpl;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.ActivityLogs;
import com.deemsys.project.entity.BlockedIp;
import com.deemsys.project.entity.IpAddress;
import com.deemsys.project.entity.IpAddresses;
import com.deemsys.project.entity.IpAddressesId;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * BlockedIp 	 - Entity
 * blockedIp 	 - Entity Object
 * blockedIps 	 - Entity List
 * blockedIpDAO   - Entity DAO
 * blockedIpForms - EntityForm List
 * BlockedIpForm  - EntityForm
 *
 */
@Service
@Transactional
public class IPAddressesService {

	@Autowired
	IPAddressesDAO ipAddressesDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	InjuryProperties injuryProperties;
	
	@Autowired
	IPGeoLocationService ipGeoLocationService;
	
	@Autowired
	IPGeoLocationServiceImpl ipGeoLocationServiceImpl;
	
	@Autowired
	ActivityLogsDAO activityLogsDAO;
	
	@Autowired
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;
	
	//Get All Entries
	public IPAddressesGrouppedResult searchIpAddressesList(IPAddressesSearchForm ipAddressesSearchForm)
	{
		List<IPAddressesSearchResultGroup> ipAddressesSearchResultGroups = new ArrayList<IPAddressesSearchResultGroup>();
		IPAddressesSearchResultGroup ipAddressesSearchResultGroup = new IPAddressesSearchResultGroup();
		IPAddressesSearchResult ipAddressesSearchResults=ipAddressesDAO.searchIPAddresses(ipAddressesSearchForm);
		Integer primaryLoginId=0;
		int rowCount=0;
		for (IPAddressesForm ipAddress : ipAddressesSearchResults.getIpAddressesForms()) {
			//TODO: Fill the List
			if(!primaryLoginId.equals(ipAddress.getPrimaryLoginId())){
				primaryLoginId=ipAddress.getPrimaryLoginId();
				if(rowCount!=0){
					ipAddressesSearchResultGroups.add(ipAddressesSearchResultGroup);
				}
				ipAddressesSearchResultGroup = new IPAddressesSearchResultGroup(primaryLoginId, ipAddress.getPrimaryUsername(), new ArrayList<IPAddressesForm>());
			}
			Date previousDate = new LocalDate().minusDays(Integer.parseInt(injuryProperties.getProperty("checkingNumberOfDaysBefore"))).toDate();
			if(ipAddress.getAddedDateYearFormat().compareTo(previousDate)<0){
				ipAddress.setIsNew(0);
			}else{
				ipAddress.setIsNew(1);
			}
			ipAddress.setAddedDateMonthFormat(InjuryConstants.convertMonthFormat(ipAddress.getAddedDateYearFormat()));
			ipAddressesSearchResultGroup.getIpAddressesForms().add(ipAddress);
			rowCount++;
		}
		if(rowCount>0)
			ipAddressesSearchResultGroups.add(ipAddressesSearchResultGroup);
		
		IPAddressesGrouppedResult ipAddressesGrouppedResult = new IPAddressesGrouppedResult(ipAddressesSearchResults.getTotalRecords(), ipAddressesSearchResultGroups);
		
		return ipAddressesGrouppedResult;
	}
	
	//Get Particular Entry
	public IPAddressesForm getIpAddresses(String ipAddress)
	{
		IpAddress ipAddresses=ipAddressesDAO.getByIpAddress(ipAddress);
		
		//TODO: Convert Entity to Form
		//Start
		//TODO: Fill the List
		IPAddressesForm ipAddressesForm = null;
		if(ipAddresses!=null){
			ipAddressesForm = new IPAddressesForm(ipAddresses.getIpAddress(), null, "", ipAddresses.getCity(), ipAddresses.getRegion(), ipAddresses.getCountryName(), ipAddresses.getCountryCode(), 
					ipAddresses.getContinentName(), ipAddresses.getContinentCode(), ipAddresses.getPostal(), ipAddresses.getLatitude(), ipAddresses.getLongitude(), ipAddresses.getTimeZone(), ipAddresses.getFlag(), ipAddresses.getBlockStatus(), InjuryConstants.convertMonthFormat(ipAddresses.getAddedDate()));
		}
		//End
		
		return ipAddressesForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeIpAddresses(IPAddressesForm ipAddressesForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		IpAddress ipAddress = new IpAddress(ipAddressesForm.getIpAddress(), ipAddressesForm.getCity(), ipAddressesForm.getRegion(), ipAddressesForm.getCountryName(), 
				ipAddressesForm.getCountryCode(), ipAddressesForm.getContinentName(), ipAddressesForm.getContinentCode(), ipAddressesForm.getPostal(), 
				ipAddressesForm.getLatitude(), ipAddressesForm.getLongitude(), ipAddressesForm.getTimeZone(), ipAddressesForm.getFlag(), ipAddressesForm.getBlockStatus(), ipAddressesForm.getAddedDateYearFormat(), null);
		//Logic Ends
		
		
		ipAddressesDAO.merge(ipAddress);
		return 1;
	}
	
	//Save an Entry
	public int saveIpAddresses(IPAddressesForm ipAddressesForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		IpAddress ipAddress = new IpAddress(ipAddressesForm.getIpAddress(), "", "", "", 
				"", "", "", "", "", "", "", "", 0, InjuryConstants.convertYearFormat(ipAddressesForm.getAddedDateMonthFormat()), null);
		//Logic Ends
		
		ipAddressesDAO.save(ipAddress);
		return 1;
	}
	
	//Update an Entry
	public int updateIpAddresses(IPAddressesForm ipAddressesForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		IpAddress ipAddress = ipAddressesDAO.getByIpAddress(ipAddressesForm.getIpAddress());
		
		//Logic Ends
		
		ipAddressesDAO.update(ipAddress);
		return 1;
	}
	
	//Delete an Entry
	public int deleteIpAddress(String ipAddress)
	{
		ipAddressesDAO.deleteIPAddresses(ipAddress);
		return 1;
	}
	
	// Check whether IP Already Exist or Not  
	// true - already available, false - not available
	public boolean checkIPAlreadyExistOrNot(String ipAddress){
		return ipAddressesDAO.checkIPAlreadyExistOrNot(ipAddress);
	}
	
	// Check And Update IPGeoLocation
	public void updateIPGeoLocation(String ipAddress){
		try {
			
			if(!this.checkIPLocationUpdatedOrNot(ipAddress)){
				IPGeoLocationForm ipGeoLocationForm = ipGeoLocationServiceImpl.getGeoLocationFromIP(ipAddress);
				IpAddress ipAddress2 = ipAddressesDAO.getByIpAddress(ipAddress);
				ipAddress2.setCity(ipGeoLocationForm.getCity());
				ipAddress2.setCountryCode(ipGeoLocationForm.getCountryCode());
				ipAddress2.setCountryName(ipGeoLocationForm.getContinentName());
				ipAddress2.setContinentCode(ipGeoLocationForm.getContinentCode());
				ipAddress2.setContinentName(ipGeoLocationForm.getContinentName());
				ipAddress2.setPostal(ipGeoLocationForm.getPostal());
				ipAddress2.setFlag(ipGeoLocationForm.getFlag());
				ipAddress2.setRegion(ipGeoLocationForm.getRegion());
				ipAddress2.setTimeZone(ipGeoLocationForm.getTimeZone());
				ipAddress2.setLatitude(ipGeoLocationForm.getLatitude());
				ipAddress2.setLongitude(ipGeoLocationForm.getLongitude());
				ipAddressesDAO.update(ipAddress2);
			}else{
				// Get Saved Details
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<IPAddressesForm> getBlockedIpList(){
		List<IpAddress> ipAddresses = ipAddressesDAO.getBlockedIpAddress();
		List<IPAddressesForm> ipAddressesForms = new ArrayList<IPAddressesForm>();
		for (IpAddress ip : ipAddresses) {
			IPAddressesForm ipAddresForm = new IPAddressesForm(ip.getIpAddress(), null, "", ip.getCity(), 
					ip.getRegion(), ip.getCountryName(), ip.getCountryCode(), ip.getContinentName(), ip.getContinentCode(), ip.getPostal(), ip.getLatitude(), ip.getLongitude(), ip.getTimeZone(), 
					ip.getFlag(), ip.getBlockStatus(), InjuryConstants.convertMonthFormat(ip.getAddedDate()));
			ipAddressesForms.add(ipAddresForm);
		}
		return ipAddressesForms;
	}
	
	// Checking IPLocation Updated Status
	public boolean checkIPLocationUpdatedOrNot(String ipAddress){
		IpAddress ipAddressesList = ipAddressesDAO.getByIpAddress(ipAddress);
		if(ipAddressesList.getContinentName()!=null&&!ipAddressesList.getContinentName().equals("")){
			return true;
		}
		return false;
	}
	
	public Integer checkIPBlockedOrNot(String ipAddress){
		IpAddress ipAddressesList = ipAddressesDAO.getByIpAddress(ipAddress);
		if(ipAddressesList==null){
			return 0;
		}
		else if(ipAddressesList.getBlockStatus()==1){
			return 1;
		}
		return 2;
	}
	
	public void blockOrUnblockIpAddress(String ipAddress,Integer status){
		if(status==1){
			ipAddressesDAO.blockIpAddress(ipAddress);
			List<ActivityLogs> activityLogs = activityLogsDAO.getActivityLogsByIpAddressAndDate(ipAddress, InjuryConstants.convertYearFormat(InjuryConstants.convertMonthFormat(new Date())));
			List<Object> principals = sessionRegistry.getAllPrincipals();
			for (ActivityLogs activityLog : activityLogs) {
				SessionInformation sessionInformation = sessionRegistry.getSessionInformation(activityLog.getId().getSessionId());
		    	if(sessionInformation!=null&&!sessionInformation.isExpired()){
		    		sessionInformation.expireNow();
		    	}
		    }
		}else{
			ipAddressesDAO.unblockIpAddress(ipAddress);
		}	
	}
}
