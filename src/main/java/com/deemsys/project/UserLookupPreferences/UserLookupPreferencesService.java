package com.deemsys.project.UserLookupPreferences;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.entity.County;
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
	
	//Get All Entries
	public List<UserLookupPreferencesForm> getUserLookupPreferencesList()
	{
		List<UserLookupPreferencesForm> userLookupPreferencesForms=new ArrayList<UserLookupPreferencesForm>();
		
		List<UserLookupPreferences> userLookupPreferencess=new ArrayList<UserLookupPreferences>();
		
		userLookupPreferencess=userLookupPreferencesDAO.getAll();
		
		for (UserLookupPreferences userLookupPreferences : userLookupPreferencess) {
			//TODO: Fill the List
			
		}
		
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
		userLookupPreferenceMappedForm = new UserLookupPreferenceMappedForm(1, countyPreference);
		userLookupPreferenceMappedForms.add(userLookupPreferenceMappedForm);
		
		// tier Preferences
		userLookupPreferenceMappedForm = new UserLookupPreferenceMappedForm(2, tierPreference);
		userLookupPreferenceMappedForms.add(userLookupPreferenceMappedForm);
		
		// Age Preferences
		userLookupPreferenceMappedForm = new UserLookupPreferenceMappedForm(3, agePreference);
		userLookupPreferenceMappedForms.add(userLookupPreferenceMappedForm);
		
		userLookupPreferencesForm=new UserLookupPreferencesForm(userId, userLookupPreferenceMappedForms);
		//End
		
		return userLookupPreferencesForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeUserLookupPreferences(UserLookupPreferencesForm userLookupPreferencesForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		Integer userId=loginService.getCurrentUserID();
		// Delete Previous Mapping
		this.deleteUserLookupPreferences(userId);
		Users users = usersDAO.get(userId);
		List<UserLookupPreferenceMappedForm> userLookupPreferenceMappedForms = userLookupPreferencesForm.getUserLookupPreferenceMappedForms();
		for (UserLookupPreferenceMappedForm userLookupPreferenceMappedForm : userLookupPreferenceMappedForms) {
			for (Integer mappedId : userLookupPreferenceMappedForm.getpreferredId()) {
				UserLookupPreferencesId userLookupPreferencesId = new UserLookupPreferencesId(userId, userLookupPreferenceMappedForm.getType(), mappedId, 1);
				UserLookupPreferences userLookupPreferences=new UserLookupPreferences(userLookupPreferencesId,users);
				userLookupPreferencesDAO.merge(userLookupPreferences);
			}	
		}
		//Logic Ends
		
		return 1;
	}
	
	//Save an Entry
	public int saveUserLookupPreferences(UserLookupPreferencesForm userLookupPreferencesForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		UserLookupPreferences userLookupPreferences=new UserLookupPreferences();
		
		//Logic Ends
		
		userLookupPreferencesDAO.save(userLookupPreferences);
		return 1;
	}
	
	//Update an Entry
	public int updateUserLookupPreferences(UserLookupPreferencesForm userLookupPreferencesForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		UserLookupPreferences userLookupPreferences=new UserLookupPreferences();
		
		//Logic Ends
		
		userLookupPreferencesDAO.update(userLookupPreferences);
		return 1;
	}
	
	//Delete an Entry
	public int deleteUserLookupPreferences(Integer userId)
	{
		userLookupPreferencesDAO.deleteUserLookupPreferencesByUserId(userId);
		return 1;
	}
	
	// get Mapped preference County List
	public List<CountyList> getPreferenceCountyList(Integer countyListType) {
		List<CountyList> countyLists = countyService.getMyCountyList();
		List<CountyList> resultCountyList=new ArrayList<CountyList>();
		List<Integer> preferenceCounty= new ArrayList<Integer>();
		UserLookupPreferencesForm userLookupPreferencesForm = this.getUserLookupPreferences();
		for (UserLookupPreferenceMappedForm userLookupPreferenceMappedForm : userLookupPreferencesForm.getUserLookupPreferenceMappedForms()) {
			if(userLookupPreferenceMappedForm.getType()==1){
				preferenceCounty=userLookupPreferenceMappedForm.getpreferredId();
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
			if(userLookupPreferenceMappedForm.getType()==1){
				preferenceCounty=userLookupPreferenceMappedForm.getpreferredId();
			}
		}
		// Get Final County List
		if(preferenceCounty.size()>0){
			countyListType=2;
		}
		
		return countyListType;
	}
	
}
