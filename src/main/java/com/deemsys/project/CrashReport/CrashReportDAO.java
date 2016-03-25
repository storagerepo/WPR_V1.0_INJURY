package com.deemsys.project.CrashReport;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.CrashReport;
/**
 * 
 * @author Deemsys
 *
 */
public interface CrashReportDAO extends IGenericDAO<CrashReport>{

	public CrashReportList searchCrashReports(String localReportNumber,String crashId,String crashFromDate,String crashToDate,String county,String addedFromDate,String addedToDate,Integer recordsPerPage,Integer pageNumber);
	public Integer getTotalRecords(String localReportNumber,String crashId,String crashFromDate,String crashToDate,String county,String addedFromDate,String addedToDate);
}
