package com.deemsys.project.LawyerCountyMap;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.LawyerCountyMap;

public interface LawyerCountyMapDAO extends IGenericDAO<LawyerCountyMap>{
	public List<LawyerCountyMap> getLawyerCountyMapByLawyerId(Integer callerId);
	public void deleteLawyerCountyMapByLawyerIdAndCountyId(Integer callerId,Integer countyId);
	public void deleteLawyerCountyMapByLawyerId(Integer callerId);
}
