package com.deemsys.project.CrashReport;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.PoliceAgency;
/**
 * 
 * @author Deemsys
 *
 */
public interface CrashReportDAO extends IGenericDAO<CrashReport>{

	public CrashReportList searchCrashReports(CrashReportSearchForm crashReportSearchForm);
	public Integer getTotalRecords(String localReportNumber,String crashId,String crashFromDate,String crashToDate,String county,String addedFromDate,String addedToDate);
	public CrashReport getCrashReport(String crashId);
	public void deleteCrashReportByCrashId(String crashId);
	
	public Long getLocalReportNumberCount(String localReportNumber);
	public Long getCrashReportCountByLocalReportNumber(String localReportNumber);
	
	public List<CrashReport> getSixMonthOldCrashReports(String fromDate,String toDate, Integer noOfRecords);
	
	public CrashReport getCrashReportForChecking(String localReportNumber,String crashDate,Integer countyId, Integer isCheckAll);
	
	public void updateCrashReportByQuery(String oldCrashId,String newCrashId,Integer crashReportErrorId,String filePath,Integer isRunnerReport);

	public void updateCrashReportFileName(String CrashId, String filePath);
	
	public List<CrashReport> checkPoliceAgencyMappedToReports(Integer mapId);
	
	public void updateMapId(PoliceAgency oldPoliceAgency,PoliceAgency newPoliceAgency);
	
	public void backupSixMonthOldDataByStoredProcedure(String date);
}
