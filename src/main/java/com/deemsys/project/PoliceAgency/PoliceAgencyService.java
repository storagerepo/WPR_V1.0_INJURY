package com.deemsys.project.PoliceAgency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.CrashReport.CrashReportDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.PoliceAgency;
/**
 * 
 * @author Deemsys
 *
 * PoliceAgency 	 - Entity
 * policeAgency 	 - Entity Object
 * policeAgencys 	 - Entity List
 * policeAgencyDAO   - Entity DAO
 * policeAgencyForms - EntityForm List
 * PoliceAgencyForm  - EntityForm
 *
 */
@Service
@Transactional
public class PoliceAgencyService {

	@Autowired
	PoliceAgencyDAO policeAgencyDAO;
	
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	CrashReportDAO crashReportDAO;
	
	@Autowired
	InjuryProperties injuryProperties;
	
	//Get All Entries
	public List<PoliceAgencyForm> getPoliceAgencyList()
	{
		List<PoliceAgencyForm> policeAgencyForms=new ArrayList<PoliceAgencyForm>();
		
		List<PoliceAgency> policeAgencys=new ArrayList<PoliceAgency>();
		
		policeAgencys=policeAgencyDAO.getAll();
		
		for (PoliceAgency policeAgency : policeAgencys) {
			PoliceAgencyForm agencyForm=new PoliceAgencyForm(policeAgency.getMapId(), policeAgency.getCounty().getCountyId(),policeAgency.getCounty().getName(), policeAgency.getAgencyId(), policeAgency.getName(), policeAgency.getSchedulerType(), policeAgency.getStatus(),InjuryConstants.convertMonthFormat(policeAgency.getLastUpdatedDate()),policeAgency.getReportStatus());
			policeAgencyForms.add(agencyForm);
		}
		
		return policeAgencyForms;
	}
	
	//Get Particular Entry
	public PoliceAgencyForm getPoliceAgency(Integer mapId)
	{
		PoliceAgency policeAgency=new PoliceAgency();
		
		policeAgency=policeAgencyDAO.get(mapId);
		
		
		
		PoliceAgencyForm policeAgencyForm=new PoliceAgencyForm(policeAgency.getMapId(), policeAgency.getCounty().getCountyId(),policeAgency.getCounty().getName(), policeAgency.getAgencyId(), policeAgency.getName(),policeAgency.getSchedulerType(), policeAgency.getStatus(),InjuryConstants.convertMonthFormat(policeAgency.getLastUpdatedDate()),policeAgency.getReportStatus());
		
		
		return policeAgencyForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergePoliceAgency(PoliceAgencyForm policeAgencyForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		PoliceAgency policeAgency=new PoliceAgency();
		
		//Logic Ends
		
		
		policeAgencyDAO.merge(policeAgency);
		return 1;
	}
	
	//Save an Entry
	public Long savePoliceAgency(PoliceAgencyForm policeAgencyForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
	
		Integer mapId=policeAgencyDAO.getMaximumMapId(policeAgencyForm.getSchedulerType());
		//Logic Ends
		PoliceAgency policeAgency=new PoliceAgency(mapId,countyDAO.get(policeAgencyForm.getCountyId()),policeAgencyForm.getAgencyId(), policeAgencyForm.getName(),policeAgencyForm.getSchedulerType(), 3,  null, 1, null);
		policeAgencyDAO.save(policeAgency);
		return null;
	}
	
	//Update an Entry
	public int updatePoliceAgency(PoliceAgencyForm policeAgencyForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		PoliceAgency policeAgency=new PoliceAgency();
		policeAgency=policeAgencyDAO.get(policeAgencyForm.getMapId());
		
		if(policeAgency.getSchedulerType()!=policeAgencyForm.getSchedulerType())
		{
			//List<CrashReport> crashReports=new ArrayList<CrashReport>();
			Integer mapId=policeAgencyDAO.getMaximumMapId(policeAgencyForm.getSchedulerType());
			
			PoliceAgency policeAgency2=new PoliceAgency(mapId,countyDAO.get(policeAgencyForm.getCountyId()) ,policeAgencyForm.getAgencyId(),policeAgencyForm.getName(),policeAgencyForm.getSchedulerType(),policeAgencyForm.getStatus(), null, 1, null);
			
			policeAgencyDAO.save(policeAgency2);
			
		crashReportDAO.updateMapId(policeAgency, policeAgency2);
		
		policeAgencyDAO.delete(policeAgency.getMapId());
			
		}else{
			//Logic Ends - Update
			policeAgency.setMapId(policeAgencyForm.getMapId());
			policeAgency.setCounty(countyDAO.get(policeAgencyForm.getCountyId()));
			policeAgency.setAgencyId(policeAgencyForm.getAgencyId());
			policeAgency.setName(policeAgencyForm.getName());
			policeAgency.setSchedulerType(policeAgencyForm.getSchedulerType());
			policeAgency.setStatus(policeAgencyForm.getStatus());
			policeAgency.setCrashReports(null);
			
			policeAgencyDAO.update(policeAgency);
		}
		return 1;
	}
	
	//Delete an Entry
	public int deletePoliceAgency(Integer id)
	{
	
		List<CrashReport> crashReports=new ArrayList<CrashReport>();
		crashReports=crashReportDAO.checkPoliceAgencyMappedToReports(id);
		 if(crashReports.isEmpty())
		 {
			 //policeAgencyDAO.delete(id);
	            return 1;
		 }
		 else
		 {
			 return 0;
		 }
		
		
	}
	
	// Get Scheduler Police Agencies
	public List<PoliceAgencyForm> getPoliceAgenciesByStatus(Integer status){
		
		List<PoliceAgencyForm> policeAgencyForms = new ArrayList<PoliceAgencyForm>();
		List<PoliceAgency> policeAgencies = policeAgencyDAO.getPoliceAgenciesBystatus(status);
		for (PoliceAgency policeAgency : policeAgencies) {
			PoliceAgencyForm agencyForm=new PoliceAgencyForm(policeAgency.getMapId(), policeAgency.getCounty().getCountyId(),policeAgency.getCounty().getName(), policeAgency.getAgencyId(), policeAgency.getName(), policeAgency.getSchedulerType(), policeAgency.getStatus(),InjuryConstants.convertMonthFormat(policeAgency.getLastUpdatedDate()),policeAgency.getReportStatus());
			policeAgencyForms.add(agencyForm);
		}
		
		return policeAgencyForms;
	}
	
	public List<PoliceAgencyForm> getPoliceAgenciesForScheduler(Integer schedulerType){
		List<PoliceAgencyForm> policeAgencyForms = new ArrayList<PoliceAgencyForm>();
		List<PoliceAgency> policeAgencies = policeAgencyDAO.getPoliceAgenciesForScheduler(schedulerType);
		for (PoliceAgency policeAgency : policeAgencies) {
			PoliceAgencyForm agencyForm=new PoliceAgencyForm(policeAgency.getMapId(), policeAgency.getCounty().getCountyId(),policeAgency.getCounty().getName(), policeAgency.getAgencyId(), policeAgency.getName(), policeAgency.getSchedulerType(), policeAgency.getStatus(),InjuryConstants.convertMonthFormat(policeAgency.getLastUpdatedDate()),policeAgency.getReportStatus());
			policeAgencyForms.add(agencyForm);
		}
		
		return policeAgencyForms;
	}
	public List<PoliceAgencyForm> searchPoliceDepartments(Integer countyParam,Integer reportPullingParam, Integer reportStatus)
	{
		List<PoliceAgencyForm> policeAgencyForms=new ArrayList<PoliceAgencyForm>();
		
		List<PoliceAgency> policeAgencys=new ArrayList<PoliceAgency>();
		
		policeAgencys=policeAgencyDAO.searchPoliceDepartments(countyParam,reportPullingParam,reportStatus);
		
		for (PoliceAgency policeAgency : policeAgencys) {
			PoliceAgencyForm agencyForm=new PoliceAgencyForm(policeAgency.getMapId(), policeAgency.getCounty().getCountyId(),policeAgency.getCounty().getName(), policeAgency.getAgencyId(), policeAgency.getName(), policeAgency.getSchedulerType(), policeAgency.getStatus(), InjuryConstants.convertMonthFormat(policeAgency.getLastUpdatedDate()), policeAgency.getReportStatus());
			policeAgencyForms.add(agencyForm);
		}
		
		return policeAgencyForms;
	}
	
	public void updateStatusOfReports(){
		List<PoliceAgency> policeAgencies = policeAgencyDAO.getActiveList();
		Date previousDate = new LocalDate().minusDays(Integer.parseInt(injuryProperties.getProperty("checkingNumberOfDaysBefore"))).toDate();
		System.out.println("Previous Date--->"+previousDate);
		for (PoliceAgency policeAgency : policeAgencies) {
			if(policeAgency.getLastUpdatedDate().compareTo(previousDate)<0){
				System.out.println("Previous than old date... So update the status");
				policeAgency.setReportStatus(0);
				policeAgencyDAO.update(policeAgency);
			}
		}
	}
	
	
}
