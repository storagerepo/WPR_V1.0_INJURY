package com.deemsys.project.UserLookupPreferences;

import java.util.List;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.UserLookupPreferences;
/**
 * 
 * @author Deemsys
 *
 */
public interface UserLookupPreferencesDAO extends IGenericDAO<UserLookupPreferences>{
	public List<UserLookupPreferences> getUserLookupPreferencesByUserId(Integer userId);
	public List<UserLookupPreferences> getUserLookupPreferencesByUserIdAndType(Integer userId,Integer type);
	public void deleteUserLookupPreferencesByUserId(Integer userId);
	public void deleteUserLookupPreferenceByUserAndPPreferedId(Integer userId, Integer prefredType, Integer preferredId);
	public List<UserLookupPreferences> getReportingAgencyUserLookupPreferences(Integer userId, List<Integer> countyId);
	public List<CountyList> getReportingAgencyUserLookupPreferencesCount(Integer userId,List<Integer> countyId);
	public void deleteReportingAgencyPreferences(Integer userId, Integer countyId);
	public void deleteUserLookupPreferencesByUserIdAndType(Integer userId,Integer type);
	public void deleteUserLookupPreferencesNotInCountyList(Integer userId,List<Integer> countyIds);
	public List<UserLookupPreferences> getReportingAgencyUserLookupPreferencesNotInCountyList(Integer userId, List<Integer> countyId);
}
