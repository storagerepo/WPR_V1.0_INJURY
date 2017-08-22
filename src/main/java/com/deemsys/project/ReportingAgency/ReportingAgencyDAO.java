package com.deemsys.project.ReportingAgency;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.ReportingAgency;
import com.deemsys.project.ReportingAgency.ReportingAgencySearchParamForm;
/**
 * 
 * @author Deemsys
 *
 */
public interface ReportingAgencyDAO extends IGenericDAO<ReportingAgency>{
	public List<ReportingAgency> getReportingAgencyListByCounties(Integer[] countyIds);
	public List<ReportingAgency> getReportingAgencyByCountyAndAgencyName(Integer countyId, String agencyName);
	public ReportingAgency getReportingAgencyByCodeAndCounty(Integer countyId,String agencyCode);
	public ReportingAgencyList getReportingAgencyList2(ReportingAgencySearchParamForm searchParamForm);
	public Integer checkNcicCode(String ncicCode,Integer reportingAgencyId,Integer countyId);
}	


