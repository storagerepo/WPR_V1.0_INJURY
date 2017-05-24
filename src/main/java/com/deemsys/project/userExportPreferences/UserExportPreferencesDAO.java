package com.deemsys.project.userExportPreferences;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.UserExportPreferences;
import com.deemsys.project.exportFields.ExportFieldsForm;
/**
 * 
 * @author Deemsys
 *
 */
public interface UserExportPreferencesDAO extends IGenericDAO<UserExportPreferences>{

	public List<UserExportPreferences> getAll(Integer userId);
	
	public void deleteAllByUserId(Integer userId);;
	
	public List<ExportFieldsForm> getUserExportPreferenceList(Integer userId);

}
