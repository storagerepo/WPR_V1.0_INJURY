package com.deemsys.project.DirectReportCallerMap;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.DirectReportCallerAdminMap;
import com.deemsys.project.entity.DirectReportCallerAdminMapId;
import com.deemsys.project.login.LoginService;
/**
 * 
 * @author Deemsys
 *
 * DirectReportCallerAdminMap 	 - Entity
 * directReportCallerAdminMap 	 - Entity Object
 * directReportCallerAdminMaps 	 - Entity List
 * directReportCallerAdminMapDAO   - Entity DAO
 * directReportCallerAdminMapForms - EntityForm List
 * DirectReportCallerAdminMapForm  - EntityForm
 *
 */
@Service
@Transactional
public class DirectReportCallerMapService {

	@Autowired
	DirectReportCallerMapDAO directReportCallerAdminMapDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	CallerService callerService;
	
	// Move to Archive
	public boolean directReportMoveOrRelaseArchive(DirectReportCallerMapForm directReportCallerMapForm){
		
		try{
			String role=loginService.getCurrentRole();
			CallerAdmin callerAdmin = null;
			if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)||role.equals(InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE)){
				callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
			}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)||role.equals(InjuryConstants.INJURY_SHOP_ROLE)){
				callerAdmin=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin();
			}
			
			for (String crashId : directReportCallerMapForm.getCrashId()) {
				DirectReportCallerAdminMap directReportCallerAdminMap=new DirectReportCallerAdminMap();
				directReportCallerAdminMap=directReportCallerAdminMapDAO.getPatientMapsByCallerAdminId(crashId, callerAdmin.getCallerAdminId());
				
				if(directReportCallerAdminMap==null){
					DirectReportCallerAdminMapId directReportCallerAdminMapId = new DirectReportCallerAdminMapId(crashId, callerAdmin.getCallerAdminId());
					directReportCallerAdminMap = new DirectReportCallerAdminMap(directReportCallerAdminMapId, callerAdmin, new CrashReport(crashId));
				}
				directReportCallerAdminMap.setIsArchived(directReportCallerMapForm.getStatus());
				if(directReportCallerMapForm.getStatus()==1){
					directReportCallerAdminMap.setArchivedDate(new Date());
					directReportCallerAdminMap.setArchivedDateTime(InjuryConstants.convertUSAFormatWithTime(new Date()));
				}else{
					directReportCallerAdminMap.setArchivedDate(null);
					directReportCallerAdminMap.setArchivedDateTime(null);
				}
				
				directReportCallerAdminMapDAO.merge(directReportCallerAdminMap);			
				
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	//Delete an Entry
	public int deleteDirectReportCallerAdminMap(Integer id)
	{
		directReportCallerAdminMapDAO.delete(id);
		return 1;
	}
	
	// Change Status Of Direct Report 
	public boolean changeStatusOfDirectReport(DirectReportCallerMapForm directReportCallerMapForm){	
		
		String role=loginService.getCurrentRole();
		CallerAdmin callerAdmin = null;
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)||role.equals(InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE)){
			callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)||role.equals(InjuryConstants.INJURY_SHOP_ROLE)){
			callerAdmin=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin();
		}
		
		for (String crashId : directReportCallerMapForm.getCrashId()) {
			DirectReportCallerAdminMap directReportCallerAdminMap=new DirectReportCallerAdminMap();
			directReportCallerAdminMap=directReportCallerAdminMapDAO.getPatientMapsByCallerAdminId(crashId, callerAdmin.getCallerAdminId());
			
			if(directReportCallerAdminMap==null){
				DirectReportCallerAdminMapId directReportCallerAdminMapId = new DirectReportCallerAdminMapId(crashId, callerAdmin.getCallerAdminId());
				directReportCallerAdminMap = new DirectReportCallerAdminMap(directReportCallerAdminMapId, callerAdmin, new CrashReport(crashId));
				directReportCallerAdminMap.setStatus(directReportCallerMapForm.getStatus());
			}else{
				directReportCallerAdminMap.setStatus(directReportCallerMapForm.getStatus());
			}
			directReportCallerAdminMapDAO.merge(directReportCallerAdminMap);	
		}
		
		return true;
	}
	
	
}
