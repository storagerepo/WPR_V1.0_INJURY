package com.deemsys.project.UserLookupPreferences;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.UserLookupPreferences;
/**
 * 
 * @author Deemsys
 *
 */
public interface UserLookupPreferencesDAO extends IGenericDAO<UserLookupPreferences>{
	public List<UserLookupPreferences> getUserLookupPreferencesByUserId(Integer userId);
	public void deleteUserLookupPreferencesByUserId(Integer userId);
}
