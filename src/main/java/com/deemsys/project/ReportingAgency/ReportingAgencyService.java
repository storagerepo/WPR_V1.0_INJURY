package com.deemsys.project.ReportingAgency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.UserLookupPreferences.UserLookupPreferenceMappedForm;
import com.deemsys.project.UserLookupPreferences.UserLookupPreferencesService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.ReportingAgency;
/**
 * 
 * @author Deemsys
 *
 * ReportingAgency 	 - Entity
 * reportingAgency 	 - Entity Object
 * reportingAgencys 	 - Entity List
 * reportingAgencyDAO   - Entity DAO
 * reportingAgencyForms - EntityForm List
 * ReportingAgencyForm  - EntityForm
 *
 */
@Service
@Transactional
public class ReportingAgencyService {

	@Autowired
	ReportingAgencyDAO reportingAgencyDAO;
	
	@Autowired
	UserLookupPreferencesService userLookupPreferencesService;
	
	//Get All Entries
	public List<ReportingAgencyForm> getReportingAgencyList()
	{
		List<ReportingAgencyForm> reportingAgencyForms=new ArrayList<ReportingAgencyForm>();
		
		List<ReportingAgency> reportingAgencys=new ArrayList<ReportingAgency>();
		
		reportingAgencys=reportingAgencyDAO.getAll();
		Integer countyId;
		for (ReportingAgency reportingAgency : reportingAgencys) {
			//TODO: Fill the List
			if(reportingAgency.getCounty()!=null)
				countyId=reportingAgency.getCounty().getCountyId();
			else
				countyId=null;
			reportingAgencyForms.add(new ReportingAgencyForm(reportingAgency.getReportingAgencyId(),countyId, reportingAgency.getReportingAgencyName(), reportingAgency.getCode(), reportingAgency.getStatus()));
		}
		
		return reportingAgencyForms;
	}
	
	//Get Particular Entry
	public ReportingAgencyForm getReportingAgency(Integer getId)
	{
		ReportingAgency reportingAgency=new ReportingAgency();
		
		reportingAgency=reportingAgencyDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start		
		Integer countyId;
		if(reportingAgency.getCounty()!=null)
			countyId=reportingAgency.getCounty().getCountyId();
		else
			countyId=null;
		ReportingAgencyForm reportingAgencyForm=new ReportingAgencyForm(reportingAgency.getReportingAgencyId(),countyId, reportingAgency.getReportingAgencyName(), reportingAgency.getCode(), reportingAgency.getStatus());

		//End
		
		return reportingAgencyForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeReportingAgency(ReportingAgencyForm reportingAgencyForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		ReportingAgency reportingAgency=new ReportingAgency();
		
		//Logic Ends
		
		
		reportingAgencyDAO.merge(reportingAgency);
		return 1;
	}
	
	//Save an Entry
	public int saveReportingAgency(ReportingAgencyForm reportingAgencyForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ReportingAgency reportingAgency=new ReportingAgency();
		
		//Logic Ends
		
		reportingAgencyDAO.save(reportingAgency);
		return 1;
	}
	
	//Update an Entry
	public int updateReportingAgency(ReportingAgencyForm reportingAgencyForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		ReportingAgency reportingAgency=new ReportingAgency();
		
		//Logic Ends
		
		reportingAgencyDAO.update(reportingAgency);
		return 1;
	}
	
	//Delete an Entry
	public int deleteReportingAgency(Integer id)
	{
		reportingAgencyDAO.delete(id);
		return 1;
	}
	
	//Get by Counties
	public List<ReportingAgencyForm> getReportingAgencyByCounties(Integer[] countyIds){
		List<ReportingAgencyForm> reportingAgencyForms=new ArrayList<ReportingAgencyForm>();
		
		List<ReportingAgency> reportingAgencys=new ArrayList<ReportingAgency>();
		
		reportingAgencys=reportingAgencyDAO.getReportingAgencyListByCounties(countyIds);
		Integer countyId;
		for (ReportingAgency reportingAgency : reportingAgencys) {
			//TODO: Fill the List
			if(reportingAgency.getCounty()!=null)
				countyId=reportingAgency.getCounty().getCountyId();
			else
				countyId=null;
			String agencyName=countyId+InjuryConstants.REPORTING_AGENCY_NAME_SEPARATOR+reportingAgency.getReportingAgencyName()+InjuryConstants.REPORTING_AGENCY_NAME_SEPARATOR+reportingAgency.getCode();
			reportingAgencyForms.add(new ReportingAgencyForm(reportingAgency.getReportingAgencyId(),countyId, agencyName, reportingAgency.getCode(), reportingAgency.getStatus()));
		}
		
		return reportingAgencyForms;
		
	}
	
	public List<ReportingAgencyForm> getReportingAgencyByCountiesAndPreference(Integer[] countyIds,Integer agencyPreferenceType){
		List<ReportingAgencyForm> reportingAgencyForms=new ArrayList<ReportingAgencyForm>();
		
		List<ReportingAgency> reportingAgencys=new ArrayList<ReportingAgency>();
		
		reportingAgencys=reportingAgencyDAO.getReportingAgencyListByCounties(countyIds);
		Integer countyId;
		if(agencyPreferenceType==1){
			for (ReportingAgency reportingAgency : reportingAgencys) {
				//TODO: Fill the List
				if(reportingAgency.getCounty()!=null)
					countyId=reportingAgency.getCounty().getCountyId();
				else
					countyId=null;
				
				String agencyCode=reportingAgency.getCode()+InjuryConstants.REPORTING_AGENCY_CODE_SEPARATOR+countyId;
				String agencyName=countyId+InjuryConstants.REPORTING_AGENCY_NAME_SEPARATOR+reportingAgency.getReportingAgencyName()+InjuryConstants.REPORTING_AGENCY_NAME_SEPARATOR+reportingAgency.getCode();
				reportingAgencyForms.add(new ReportingAgencyForm(reportingAgency.getReportingAgencyId(),countyId, agencyName, agencyCode, reportingAgency.getStatus()));
			}
		}else{
			UserLookupPreferenceMappedForm userLookupPreferenceMappedForms = userLookupPreferencesService.getReportingAgencyUserLookupPreferrenceByCountyList(Arrays.asList(countyIds));
			for (ReportingAgency reportingAgency : reportingAgencys) {
				if(userLookupPreferenceMappedForms.getPreferredId().contains(reportingAgency.getReportingAgencyId())){
					if(reportingAgency.getCounty()!=null)
						countyId=reportingAgency.getCounty().getCountyId();
					else
						countyId=null;
					String agencyCode=reportingAgency.getCode()+InjuryConstants.REPORTING_AGENCY_CODE_SEPARATOR+countyId;
					String agencyName=countyId+InjuryConstants.REPORTING_AGENCY_NAME_SEPARATOR+reportingAgency.getReportingAgencyName()+InjuryConstants.REPORTING_AGENCY_NAME_SEPARATOR+reportingAgency.getCode();
					reportingAgencyForms.add(new ReportingAgencyForm(reportingAgency.getReportingAgencyId(),countyId, agencyName, agencyCode, reportingAgency.getStatus()));
				}
			}
		}
		
		
		return reportingAgencyForms;
		
	}
	
	
}
