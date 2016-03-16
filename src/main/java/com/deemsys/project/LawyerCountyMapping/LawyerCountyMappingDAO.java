package com.deemsys.project.LawyerCountyMapping;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.LawyerCountyMap;

public interface LawyerCountyMappingDAO extends IGenericDAO<LawyerCountyMap> {

	public List<LawyerCountyMap> getLawyerCountyMappingsByLawyerId(Integer lawyerId);
	public void deleteLawyerCountyMappingsByLawyerIdAndCountyId(Integer lawyerId,Integer countyId);
}
