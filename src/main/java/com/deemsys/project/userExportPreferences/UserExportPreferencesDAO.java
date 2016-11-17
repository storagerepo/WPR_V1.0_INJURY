package com.deemsys.project.userExportPreferences;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.UserExportPreferences;
/**
 * 
 * @author Deemsys
 *
 */
public interface UserExportPreferencesDAO extends IGenericDAO<UserExportPreferences>{

	public List<UserExportPreferences> getAll(Integer userId);
	
	public void deleteAllByUserId(Integer userId);;

}
