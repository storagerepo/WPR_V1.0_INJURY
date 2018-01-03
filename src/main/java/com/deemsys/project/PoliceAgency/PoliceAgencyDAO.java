package com.deemsys.project.PoliceAgency;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.PoliceAgency;
/**
 * 
 * @author Deemsys
 *
 */
public interface PoliceAgencyDAO extends IGenericDAO<PoliceAgency>{
	
	public List<PoliceAgency> getPoliceAgenciesBystatus(Integer status);
	public List<PoliceAgency> getPoliceAgenciesForScheduler(Integer schedulerType);
	//public PoliceAgency getPoliceAgencyByMapId(Integer mapId);
	public List<PoliceAgency> searchPoliceDepartments(Integer countyParam,Integer reportPullingTypeParam,Integer reportStatus);
	public Integer getMaximumMapId(Integer schedularType);
	void updateMapId(Integer oldMapId, Integer newMapId);
}
