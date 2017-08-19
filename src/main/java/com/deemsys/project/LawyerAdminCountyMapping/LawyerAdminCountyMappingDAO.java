package com.deemsys.project.LawyerAdminCountyMapping;

import java.util.List;

import com.deemsys.project.County.CountyList;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.LawyerAdminCountyMap;

public interface LawyerAdminCountyMappingDAO extends IGenericDAO<LawyerAdminCountyMap>{
	
	public List<LawyerAdminCountyMap> getLawyerAdminCountyMappingsByLawyerAdminId(Integer lawyerAdminId);
	public void deleteLawyerAdminCountyMappingsByLawyerAdminIdAndCountyId(Integer lawyerAdminId,Integer countyId);
	public void deleteLawyerAdminCountyMappingsByLawyerAdminId(Integer lawyerAdminId);
	public List<CountyList> getCountyListByLawyerAdminId(Integer lawyerAdminId);
	public List<LawyerAdminCountyMap> getLawyerAdminCountyMappingsByCountyId(Integer countyId);
}
