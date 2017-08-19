package com.deemsys.project.userExportPreferences;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Users.UsersDAO;
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
	public List<ExportFieldsForm> getUserExportPreferencesList()
	{
		List<ExportFieldsForm> exportFieldsForms =userExportPreferencesDAO.getUserExportPreferenceList(loginService.getPreferenceUserId());
		
		return exportFieldsForms;
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
			if(exportFieldsForm.getFormat()==null){
				exportFieldsForm.setFormat(0);
			}
			userExportPreferencesDAO.save(new UserExportPreferences(new UserExportPreferencesId(userId, exportFieldsForm.getFieldId(), sequenceNo++, exportFieldsForm.getDefaultValue(), exportFieldsForm.getFormat(), 1),users));
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
	
	// Check Custom User Export Preference available or not
	public Integer checkUserExportPreferenceStatus(){
		Integer status=0;
		// User Id
		List<UserExportPreferences> userExportPreferencess=userExportPreferencesDAO.getAll(loginService.getPreferenceUserId());
		if(userExportPreferencess.size()>0){
			status=1;
		}
		return status;
	}
	
}
