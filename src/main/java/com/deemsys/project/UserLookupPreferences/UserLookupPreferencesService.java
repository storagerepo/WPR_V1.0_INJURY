package com.deemsys.project.UserLookupPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.ReportingAgency.ReportingAgencyDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.ReportingAgency;
import com.deemsys.project.entity.UserLookupPreferences;
import com.deemsys.project.entity.UserLookupPreferencesId;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * UserLookupPreferences 	 - Entity
 * userLookupPreferences 	 - Entity Object
 * userLookupPreferencess 	 - Entity List
 * userLookupPreferencesDAO   - Entity DAO
 * userLookupPreferencesForms - EntityForm List
 * UserLookupPreferencesForm  - EntityForm
 *
 */
@Service
@Transactional
public class UserLookupPreferencesService {

	@Autowired
	UserLookupPreferencesDAO userLookupPreferencesDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	CountyService countyService;
	
	@Autowired
	ReportingAgencyDAO reportingAgencyDAO;
	
	//Get All Entries
	public List<UserLookupPreferencesForm> getUserLookupPreferencesList()
	{
		List<UserLookupPreferencesForm> userLookupPreferencesForms=new ArrayList<UserLookupPreferencesForm>();
		return userLookupPreferencesForms;
	}
	
	//Get Particular Entry
	public UserLookupPreferencesForm getUserLookupPreferences()
	{
		
		Integer userId=loginService.getPreferenceUserId();
		
		List<UserLookupPreferences> userLookupPreferencess=userLookupPreferencesDAO.getUserLookupPreferencesByUserId(userId);
		UserLookupPreferencesForm userLookupPreferencesForm=new UserLookupPreferencesForm();
		
		//TODO: Convert Entity to Form
		//Start
		List<Integer> countyPreference=new ArrayList<Integer>();
		List<Integer>  tierPreference=new ArrayList<Integer>();
		List<Integer>  agePreference=new ArrayList<Integer>();
		for (UserLookupPreferences userLookupPreferences : userLookupPreferencess) {
			
			switch (userLookupPreferences.getId().getType()) {
			case 1:
				countyPreference.add(userLookupPreferences.getId().getPreferedId());
				break;
			case 2:
				tierPreference.add(userLookupPreferences.getId().getPreferedId());
				break;
			case 3:
				agePreference.add(userLookupPreferences.getId().getPreferedId());
				break;
			default:
				break;
			}
		}
		
		// User Lookup Mapped Form
		List<UserLookupPreferenceMappedForm> userLookupPreferenceMappedForms = new ArrayList<UserLookupPreferenceMappedForm>();
		
		UserLookupPreferenceMappedForm userLookupPreferenceMappedForm = new UserLookupPreferenceMappedForm();
		
		// County Preferences
		userLookupPreferenceMappedForm = new UserLookupPreferenceMappedForm(InjuryConstants.COUNTY_LOOKUP, countyPreference);
		userLookupPreferenceMappedForms.add(userLookupPreferenceMappedForm);
		
		// tier Preferences
		userLookupPreferenceMappedForm = new UserLookupPreferenceMappedForm(InjuryConstants.TIER_LOOKUP, tierPreference);
		userLookupPreferenceMappedForms.add(userLookupPreferenceMappedForm);
		
		// Age Preferences
		userLookupPreferenceMappedForm = new UserLookupPreferenceMappedForm(InjuryConstants.AGE_LOOKUP, agePreference);
		userLookupPreferenceMappedForms.add(userLookupPreferenceMappedForm);
		
		userLookupPreferencesForm=new UserLookupPreferencesForm(userId, userLookupPreferenceMappedForms);
		//End
		
		return userLookupPreferencesForm;
	}
	
	
	// Get Reporting Agency Mapped List
	public UserLookupPreferenceMappedForm getReportingAgencyUserLookupPreferrenceForm(Integer countyId){
		Integer userId=loginService.getPreferenceUserId();
		List<Integer> countyIds=new ArrayList<Integer>();
		countyIds.add(countyId);
		List<UserLookupPreferences> userLookupPreferencess=userLookupPreferencesDAO.getReportingAgencyUserLookupPreferences(userId, countyIds);
		
		List<Integer> reportingAgencyPerferedId = new ArrayList<Integer>();
		for (UserLookupPreferences userLookupPreferences : userLookupPreferencess) {
			reportingAgencyPerferedId.add(userLookupPreferences.getId().getPreferedId());
		}
		// 4 - Look up preference type (Reporting Agency Look up Type)
		UserLookupPreferenceMappedForm userLookupPreferenceMappedForms = new UserLookupPreferenceMappedForm(InjuryConstants.REPORTING_AGENCY_LOOKUP, reportingAgencyPerferedId);
		userLookupPreferenceMappedForms.setCountyId(countyId);
		return userLookupPreferenceMappedForms;
	}
	
	// Get Reporting Agency List By List Of Counties
	public UserLookupPreferenceMappedForm getReportingAgencyUserLookupPreferrenceByCountyList(List<Integer> countyIds){
		Integer userId=loginService.getPreferenceUserId();
	
		List<UserLookupPreferences> userLookupPreferencess=userLookupPreferencesDAO.getReportingAgencyUserLookupPreferences(userId, countyIds);
		
		List<Integer> reportingAgencyPerferedId = new ArrayList<Integer>();
		for (UserLookupPreferences userLookupPreferences : userLookupPreferencess) {
			reportingAgencyPerferedId.add(userLookupPreferences.getId().getPreferedId());
		}
		// 4 - Look up preference type (Reporting Agency Look up Type)
		UserLookupPreferenceMappedForm userLookupPreferenceMappedForms = new UserLookupPreferenceMappedForm(4, reportingAgencyPerferedId);
		
		return userLookupPreferenceMappedForms;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeUserLookupPreferences(UserLookupPreferencesForm userLookupPreferencesForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		// 4 - User Look Up Preference Type (Reporting Agency)
		Integer userId=loginService.getCurrentUserID();
		
		Users users = usersDAO.get(userId);
		List<UserLookupPreferenceMappedForm> userLookupPreferenceMappedForms = userLookupPreferencesForm.getUserLookupPreferenceMappedForms();
		for (UserLookupPreferenceMappedForm userLookupPreferenceMappedForm : userLookupPreferenceMappedForms) {
			// Delete Previous Mapping
			if(userLookupPreferenceMappedForm.getType()!=InjuryConstants.REPORTING_AGENCY_LOOKUP){
				this.deleteUserLookupPreferencesByType(userId, userLookupPreferenceMappedForm.getType());
			}
			
			for (Integer mappedId : userLookupPreferenceMappedForm.getPreferredId()) {
				UserLookupPreferencesId userLookupPreferencesId = new UserLookupPreferencesId(userId, userLookupPreferenceMappedForm.getType(), 0, mappedId, 1);
				UserLookupPreferences userLookupPreferences=new UserLookupPreferences(userLookupPreferencesId,users);
				userLookupPreferencesDAO.merge(userLookupPreferences);
			}	
		}
		//Logic Ends
		
		return 1;
	}
	
	public int mergeReportingAgencyUserLookupPreferences(UserLookupPreferenceMappedForm userLookupPreferenceMappedForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		// 4 - User Look Up Preference Type (Reporting Agency)
		Integer userId=loginService.getCurrentUserID();
		
		Users users = usersDAO.get(userId);
		// Delete Previous Mapping
		this.deleteReportingAgencyPreferences(userId, userLookupPreferenceMappedForm.getCountyId());
		
		for (Integer mappedId : userLookupPreferenceMappedForm.getPreferredId()) {
			UserLookupPreferencesId userLookupPreferencesId = new UserLookupPreferencesId(userId, userLookupPreferenceMappedForm.getType(), userLookupPreferenceMappedForm.getCountyId(), mappedId, 1);
			UserLookupPreferences userLookupPreferences=new UserLookupPreferences(userLookupPreferencesId,users);
			userLookupPreferencesDAO.merge(userLookupPreferences);
		}	
		
		//Logic Ends
		
		return 1;
	}
	
	// Update Reporting Agency Preference While Subscribe county
	public int saveAndUpdateReportingAgencyUserLookupPreferencesByCounty(Integer userId,Integer countyId)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		Users users = usersDAO.get(userId);
		// Delete Previous Mapping
		this.deleteReportingAgencyPreferences(userId, countyId);
		
		// Get All Reporting Agency By countyId // Parameter is integer array
		Integer[] countyIds=new Integer[]{1};
		countyIds[0]=countyId;
		List<ReportingAgency> reportingAgencies = reportingAgencyDAO.getReportingAgencyListByCounties(countyIds);
		
		for (ReportingAgency reportingAgency : reportingAgencies) {
			UserLookupPreferencesId userLookupPreferencesId = new UserLookupPreferencesId(userId, InjuryConstants.REPORTING_AGENCY_LOOKUP, countyId, reportingAgency.getReportingAgencyId(), 1);
			UserLookupPreferences userLookupPreferences=new UserLookupPreferences(userLookupPreferencesId,users);
			userLookupPreferencesDAO.merge(userLookupPreferences);
		}	
		
		//Logic Ends
		
		return 1;
	}
	
	// Check and Update Reporting Agency Preference while charging bill
	public int checkAndUpdateReportingAgencyUserLookupPreferencesByCounty(Integer userId,Integer countyId)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		Users users = usersDAO.get(userId);
		
		// Get All Reporting Agency By countyId // Parameter is integer array
		Integer[] countyIds=new Integer[]{1};
		countyIds[0]=countyId;
		List<UserLookupPreferences> oldLookupPreferences = userLookupPreferencesDAO.getReportingAgencyUserLookupPreferences(userId, Arrays.asList(countyIds));
		if(oldLookupPreferences.size()==0){
			List<ReportingAgency> reportingAgencies = reportingAgencyDAO.getReportingAgencyListByCounties(countyIds);
			for (ReportingAgency reportingAgency : reportingAgencies) {
				UserLookupPreferencesId userLookupPreferencesId = new UserLookupPreferencesId(userId, InjuryConstants.REPORTING_AGENCY_LOOKUP, countyId, reportingAgency.getReportingAgencyId(), 1);
				UserLookupPreferences userLookupPreferences=new UserLookupPreferences(userLookupPreferencesId,users);
				userLookupPreferencesDAO.merge(userLookupPreferences);
			}	
		}
		//Logic Ends
		
		return 1;
	}
	
	// Save Single Reporting Agency
	public int saveReportingAgencyPreference(Integer userId, UserLookupPreferenceMappedForm userLookupPreferenceMappedForm){
		
		Users users = usersDAO.get(userId);
		
		for (Integer mappedId : userLookupPreferenceMappedForm.getPreferredId()) {
			UserLookupPreferencesId userLookupPreferencesId = new UserLookupPreferencesId(userId, userLookupPreferenceMappedForm.getType(), userLookupPreferenceMappedForm.getCountyId(), mappedId, 1);
			UserLookupPreferences userLookupPreferences=new UserLookupPreferences(userLookupPreferencesId,users);
			userLookupPreferencesDAO.merge(userLookupPreferences);
		}	
		
		//Logic Ends
		
		return 1;
	}
	
	//Delete an Entry
	public int deleteUserLookupPreferences(Integer userId)
	{
		userLookupPreferencesDAO.deleteUserLookupPreferencesByUserId(userId);
		return 1;
	}
	
	public int deleteUserLookupPreferencesByType(Integer userId,Integer type)
	{
		userLookupPreferencesDAO.deleteUserLookupPreferencesByUserIdAndType(userId, type);
		return 1;
	}
	
	public int deleteReportingAgencyPreferences(Integer userId,Integer countyId)
	{
		userLookupPreferencesDAO.deleteReportingAgencyPreferences(userId,countyId);
		return 1;
	}

	public int deleteReportingAgencyPreferencesNotInCountyList(Integer userId,List<Integer> countyIds)
	{
		userLookupPreferencesDAO.deleteUserLookupPreferencesNotInCountyList(userId, countyIds);
		return 1;
	}

	
	// get Mapped preference County List
	public List<CountyList> getPreferenceCountyList(Integer countyListType) {
		List<CountyList> countyLists = countyService.getMyCountyList();
		List<CountyList> resultCountyList=new ArrayList<CountyList>();
		List<Integer> preferenceCounty= new ArrayList<Integer>();
		UserLookupPreferencesForm userLookupPreferencesForm = this.getUserLookupPreferences();
		for (UserLookupPreferenceMappedForm userLookupPreferenceMappedForm : userLookupPreferencesForm.getUserLookupPreferenceMappedForms()) {
			if(userLookupPreferenceMappedForm.getType()==InjuryConstants.COUNTY_LOOKUP){
				preferenceCounty=userLookupPreferenceMappedForm.getPreferredId();
			}
		}
		// Get Final County List
		if(countyListType==2){
			// Preferred County List
			if(preferenceCounty.size()>0){
				for (CountyList county : countyLists) {
					if(preferenceCounty.contains(county.getCountyId())){
						resultCountyList.add(county);
					}
				}
			}
			return resultCountyList;
		}else{
			// Subscribed County List
			return countyLists;
		}
	}
	
	// Check County List Type
	public Integer checkCountyListType() {
		Integer countyListType=1;
		List<Integer> preferenceCounty= new ArrayList<Integer>();
		UserLookupPreferencesForm userLookupPreferencesForm = this.getUserLookupPreferences();
		for (UserLookupPreferenceMappedForm userLookupPreferenceMappedForm : userLookupPreferencesForm.getUserLookupPreferenceMappedForms()) {
			if(userLookupPreferenceMappedForm.getType()==InjuryConstants.COUNTY_LOOKUP){
				preferenceCounty=userLookupPreferenceMappedForm.getPreferredId();
			}
		}
		// Get Final County List
		if(preferenceCounty.size()>0){
			countyListType=2;
		}
		
		return countyListType;
	}
	
	public Integer checkReportingAgencyListType() {
		Integer countyListType=1;
		List<UserLookupPreferences> userLookupPreferences = userLookupPreferencesDAO.getUserLookupPreferencesByUserIdAndType(loginService.getPreferenceUserId(), InjuryConstants.REPORTING_AGENCY_LOOKUP);
		// Get Final County List
		if(userLookupPreferences.size()>0){
			countyListType=2;
		}
		
		return countyListType;
	}
}
