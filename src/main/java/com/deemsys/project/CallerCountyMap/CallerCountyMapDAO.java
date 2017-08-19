package com.deemsys.project.CallerCountyMap;

import java.util.List;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.CallerCountyMap;

public interface CallerCountyMapDAO extends IGenericDAO<CallerCountyMap>{
	public List<CallerCountyMap> getCallerCountyMapByCallerId(Integer callerId);
	public void deleteCallerCountyMapByCallerIdAndCountyId(Integer callerId,Integer countyId);
	public void deleteCallerCountyMapByCallerId(Integer callerId);
	public List<CountyList> getCountyListByCallerId(Integer callerId);
}
