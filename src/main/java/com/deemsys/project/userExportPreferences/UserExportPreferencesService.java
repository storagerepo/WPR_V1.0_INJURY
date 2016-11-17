package com.deemsys.project.userExportPreferences;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.UserExportPreferences;
import com.deemsys.project.entity.UserExportPreferencesId;
import com.deemsys.project.entity.Users;
import com.deemsys.project.exportFields.ExportFieldsForm;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * UserExportPreferences 	 - Entity
 * userExportPreferences 	 - Entity Object
 * userExportPreferencess 	 - Entity List
 * userExportPreferencesDAO   - Entity DAO
 * userExportPreferencesForms - EntityForm List
 * UserExportPreferencesForm  - EntityForm
 *
 */
@Service
@Transactional
public class UserExportPreferencesService {

	@Autowired
	UserExportPreferencesDAO userExportPreferencesDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UsersDAO usersDAO;
	
	//Get All Entries
	public List<UserExportPreferencesForm> getUserExportPreferencesList()
	{
		List<UserExportPreferencesForm> userExportPreferencesForms=new ArrayList<UserExportPreferencesForm>();
		
		List<UserExportPreferences> userExportPreferencess=new ArrayList<UserExportPreferences>();
		
		userExportPreferencess=userExportPreferencesDAO.getAll(loginService.getPreferenceUserId());
		
		for (UserExportPreferences userExportPreferences : userExportPreferencess) {
			//TODO: Fill the List
			userExportPreferencesForms.add(new UserExportPreferencesForm(userExportPreferences.getId().getUserId(), userExportPreferences.getId().getFieldId(), userExportPreferences.getId().getSequenceNo(),userExportPreferences.getId().getStatus()));
		}
		
		return userExportPreferencesForms;
	}
	
	
	//Save an Entry
	public int saveUserExportPreferences(List<ExportFieldsForm> exportFieldsForms)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		Integer userId=loginService.getPreferenceUserId();
		Integer sequenceNo=1;
		//Delete All
		deleteUserExportPreferences(userId);
		
		//Save All
		//Users
		Users users=usersDAO.get(userId);
		
		for (ExportFieldsForm exportFieldsForm : exportFieldsForms) {			
			userExportPreferencesDAO.save(new UserExportPreferences(new UserExportPreferencesId(userId, exportFieldsForm.getFieldId(), sequenceNo++, 1),users));
		}
		//Logic Ends	
		
		return 1;
	}
	
	
	//Delete an Entry
	public int deleteUserExportPreferences(Integer userId)
	{
		userExportPreferencesDAO.deleteAllByUserId(userId);
		return 1;
	}
	
	
	
}
