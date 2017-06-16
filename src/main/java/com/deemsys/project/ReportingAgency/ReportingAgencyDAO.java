package com.deemsys.project.ReportingAgency;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.ReportingAgency;
/**
 * 
 * @author Deemsys
 *
 */
public interface ReportingAgencyDAO extends IGenericDAO<ReportingAgency>{
	public List<ReportingAgency> getReportingAgencyListByCounties(Integer[] countyIds);
}	
