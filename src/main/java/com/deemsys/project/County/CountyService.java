package com.deemsys.project.County;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdminCountyMap;
import com.deemsys.project.entity.CallerCountyMap;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.LawyerAdminCountyMap;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.CallerPatientSearchForm;
import com.deemsys.project.patient.PatientDAO;
import com.deemsys.project.patient.PatientService;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.CallerAdminCountyMapping.CallerAdminCountyMapDAO;
import com.deemsys.project.CallerCountyMap.CallerCountyMapDAO;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyForm;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.LawyerAdminCountyMapping.LawyerAdminCountyMappingDAO;
import com.deemsys.project.LawyerCountyMap.LawyerCountyMapDAO;
import com.deemsys.project.Lawyers.LawyersService;

@Service
@Transactional
public class CountyService {
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	CallerAdminCountyMapDAO callerAdminCountyMapDAO;
	
	@Autowired
	CallerCountyMapDAO callerCountyMapDAO;
	
	@Autowired
	LawyerAdminCountyMappingDAO lawyerAdminCountyMappingDAO;
	
	@Autowired
	LawyerCountyMapDAO lawyerCountyMapDAO;
	
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CallerService callerService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	LawyersService lawyersService;
	
	@Autowired
	PatientService patientService;

	// Get All Entries
	public List<CountyForm> getCountyList() {
		List<CountyForm> countyForms = new ArrayList<CountyForm>();

		List<County> countys = new ArrayList<County>();

		countys = countyDAO.getAll();

		for (County county : countys) {
			// TODO: Fill the List
			CountyForm countyForm = new CountyForm(county.getCountyId(),
					county.getName(), county.getStatus());
			countyForms.add(countyForm);

		}

		return countyForms;
	}

	// Get Particular Entry
	public CountyForm getCounty(Integer getId) {
		County county = new County();

		county = countyDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start

		CountyForm countyForm = new CountyForm(county.getCountyId(),
				county.getName(), county.getStatus());

		// End

		return countyForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeCounty(CountyForm countyForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		County county = new County(countyForm.getName(),
				countyForm.getStatus(), null,null,null,null);
		county.setCountyId(countyForm.getId());

		// Logic Ends

		countyDAO.merge(county);
		return 1;
	}

	// Save an Entry
	public int saveCounty(CountyForm countyForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		County county = new County(countyForm.getName(),
				countyForm.getStatus(), null,null,null,null);

		// Logic Ends

		countyDAO.save(county);
		return 1;
	}

	// Update an Entry
	public int updateCounty(CountyForm countyForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		County county = new County(countyForm.getName(),
				countyForm.getStatus(), null,null,null,null);
		county.setCountyId(countyForm.getId());
		// Logic Ends

		countyDAO.update(county);
		return 1;
	}

	// Delete an Entry
	public int deleteCounty(Integer id) {
		countyDAO.delete(id);
		return 1;
	}
	
	// Update an Entry
	public List<CountyList> getMyCountyList() {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		String role=loginService.getCurrentRole();
		List<CountyList> countyLists=new ArrayList<CountyList>();
		
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			countyLists=callerAdminCountyMapDAO.getCountyListByCallerAdminId(callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			//countyLists=callerCountyMapDAO.getCountyListByCallerId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerId());
			countyLists=callerAdminCountyMapDAO.getCountyListByCallerAdminId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			countyLists=lawyerAdminCountyMappingDAO.getCountyListByLawyerAdminId(lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID()).getLawyerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
			//countyLists=lawyerCountyMapDAO.getCountyListByLawyerId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerId());
			countyLists=lawyerAdminCountyMappingDAO.getCountyListByLawyerAdminId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin().getLawyerAdminId());
		}
		
		//Set New Count status
		//getNewPatientCount(countyLists);
		
		// Logic Ends

		return countyLists;
	}
	
	public List<CountyList> getNewPatientCount(List<CountyList> countyLists){
		/*for (CountyList countyList : countyLists) {
			CallerPatientSearchForm callerPatientSearchForm=new CallerPatientSearchForm(null, countyList.getCountyId(), 0, 0, "", 0, "", "", "", 0, 0, 0, "", 0, 0,"","",0);
			countyList.setNewCount(patientService.getCurrentPatientList(callerPatientSearchForm).getTotalNoOfRecord());
		}*/
		return countyLists;
	}
}
