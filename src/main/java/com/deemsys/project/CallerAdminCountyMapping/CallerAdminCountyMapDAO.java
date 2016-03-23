package com.deemsys.project.CallerAdminCountyMapping;

import java.util.List;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.CallerAdminCountyMap;

public interface CallerAdminCountyMapDAO extends IGenericDAO<CallerAdminCountyMap>{
	public List<CallerAdminCountyMap> getCallerAdminCountyMapByCallerAdminId(Integer callerAdminId);
	public void deleteCallerAdminCountyMapByCallerAdminIdAndCountyId(Integer callerAdminId,Integer countyId);
	public void deleteCallerAdminCountyMapByCallerAdminId(Integer callerAdminId);
	public List<CountyList> getCountyListByCallerAdminId(Integer callerAdminId);
}
