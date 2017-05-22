package com.deemsys.project.DirectReportLawyerMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.DirectReportCallerMap.DirectReportCallerMapForm;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.DirectReportCallerAdminMap;
import com.deemsys.project.entity.DirectReportCallerAdminMapId;
import com.deemsys.project.entity.DirectReportLawyerAdminMap;
import com.deemsys.project.entity.DirectReportLawyerAdminMapId;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientLawyerAdminMap;
import com.deemsys.project.entity.PatientLawyerAdminMapId;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * DirectReportLawyerAdminMap 	 - Entity
 * directReportLawyerAdminMap 	 - Entity Object
 * directReportLawyerAdminMaps 	 - Entity List
 * directReportLawyerAdminMapDAO   - Entity DAO
 * directReportLawyerAdminMapForms - EntityForm List
 * DirectReportLawyerAdminMapForm  - EntityForm
 *
 */
@Service
@Transactional
public class DirectReportLaywerMapService {

	@Autowired
	DirectReportLaywerMapDAO directReportLawyerAdminMapDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	LawyersService lawyersService;

	// Move to or Release From Archive
	public boolean directReportMoveOrReleaseFromArchive(DirectReportLaywerMapForm directReportLaywerMapForm){
		
		try{
			String role=loginService.getCurrentRole();
			LawyerAdmin lawyerAdmin = null;
			if(role==InjuryConstants.INJURY_LAWYER_ADMIN_ROLE){
				lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID());
			}else if(role==InjuryConstants.INJURY_LAWYER_ROLE){
				lawyerAdmin=lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin();
			}
			
			for (String crashId : directReportLaywerMapForm.getCrashId()) {
				DirectReportLawyerAdminMap directReportLawyerAdminMap =new DirectReportLawyerAdminMap();
				directReportLawyerAdminMap=directReportLawyerAdminMapDAO.getPatientMapsByLawyerAdminId(crashId, lawyerAdmin.getLawyerAdminId());
				if(directReportLawyerAdminMap==null){
					directReportLawyerAdminMap=new DirectReportLawyerAdminMap(new DirectReportLawyerAdminMapId(crashId, lawyerAdmin.getLawyerAdminId()), new CrashReport(crashId), lawyerAdmin);
				}
				directReportLawyerAdminMap.setIsArchived(directReportLaywerMapForm.getStatus());
				if(directReportLaywerMapForm.getStatus()==1){
					directReportLawyerAdminMap.setArchivedDate(new Date());
					directReportLawyerAdminMap.setArchivedDateTime(InjuryConstants.convertUSAFormatWithTime(new Date()));
				}else{
					directReportLawyerAdminMap.setArchivedDate(null);
					directReportLawyerAdminMap.setArchivedDateTime(null);
				}
				directReportLawyerAdminMapDAO.merge(directReportLawyerAdminMap);			
				
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	//Delete an Entry
	public int deleteDirectReportLawyerAdminMap(Integer id)
	{
		directReportLawyerAdminMapDAO.delete(id);
		return 1;
	}
	
	
	// Change status of Direct Report
	public boolean changeStatusOfDirectReport(DirectReportLaywerMapForm directReportLaywerMapForm){	
		
		boolean status=true;
		try{
			String role=loginService.getCurrentRole();
			LawyerAdmin lawyerAdmin = null;
			if(role==InjuryConstants.INJURY_LAWYER_ADMIN_ROLE){
				lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID());
			}else if(role==InjuryConstants.INJURY_LAWYER_ROLE){
				lawyerAdmin=lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin();
			}
			
			for (String crashId : directReportLaywerMapForm.getCrashId()) {
				DirectReportLawyerAdminMap directReportLawyerAdminMap =new DirectReportLawyerAdminMap();
				directReportLawyerAdminMap=directReportLawyerAdminMapDAO.getPatientMapsByLawyerAdminId(crashId, lawyerAdmin.getLawyerAdminId());
				if(directReportLawyerAdminMap==null){
					directReportLawyerAdminMap=new DirectReportLawyerAdminMap(new DirectReportLawyerAdminMapId(crashId, lawyerAdmin.getLawyerAdminId()), new CrashReport(crashId), lawyerAdmin);
					directReportLawyerAdminMap.setStatus(directReportLaywerMapForm.getStatus());
				}else{
					directReportLawyerAdminMap.setStatus(directReportLaywerMapForm.getStatus());
				}
				directReportLawyerAdminMapDAO.merge(directReportLawyerAdminMap);	
			}
		}catch(Exception e){
			e.printStackTrace();
			status=false;
		}
		
		
		return status;
	}
	
	
}
