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
	public List<PoliceAgency> getPoliceAgenciesForScheduler();
}
