package com.deemsys.project.PatientCallerMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Caller.AssignCallerForm;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.entity.PatientCallerAdminMapId;
import com.deemsys.project.login.LoginService;


@Service
@Transactional
public class PatientCallerService {
	
	@Autowired
	PatientCallerDAO patientCallerDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	CallerService callerService;

	public boolean assignCaller(AssignCallerForm assignCallerForm){	
		
		CallerAdmin callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
		
		
		for (String patientId : assignCallerForm.getPatientId()) {
			PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap();
			patientCallerAdminMap=patientCallerDAO.getPatientCallerAdminMap(patientId, assignCallerForm.getCallerId());
			if(patientCallerAdminMap==null){
				
			//Generate Caller	
			Caller caller=new Caller();
			caller.setCallerId(assignCallerForm.getCallerId());
			
			//Generate Patient
			Patient patient=new Patient(patientId);
			
			patientCallerAdminMap=new PatientCallerAdminMap(new PatientCallerAdminMapId(patientId,callerAdmin.getCallerAdminId()),callerAdmin,caller, patient, "", 0, 1, null, null);
			patientCallerDAO.merge(patientCallerAdminMap);
			
		}else{
			//Generate Caller	
			Caller caller=new Caller();
			caller.setCallerId(assignCallerForm.getCallerId());
			
			patientCallerAdminMap.setCaller(caller);
			patientCallerDAO.merge(patientCallerAdminMap);
		}
		}
		
		return true;
	}
	
public boolean releaseCaller(AssignCallerForm assignCallerForm){	
				
		String role=loginService.getCurrentRole();
		CallerAdmin callerAdmin = null;
		if(role==InjuryConstants.INJURY_CALLER_ADMIN_ROLE){
			callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
		}else if(role==InjuryConstants.INJURY_CALLER_ROLE){
			callerAdmin=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin();
		}
		
		for (String patientId : assignCallerForm.getPatientId()) {
			PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap();
			patientCallerAdminMap=patientCallerDAO.getPatientMapsByCallerAdminId(patientId, callerAdmin.getCallerAdminId());
			if(patientCallerAdminMap!=null){
				patientCallerAdminMap.setCaller(null);
				patientCallerDAO.merge(patientCallerAdminMap);			
			}
		}
		
		return true;
	}

public boolean moveToArchive(AssignCallerForm assignCallerForm,Integer archiveStatus){	
	
	String role=loginService.getCurrentRole();
	CallerAdmin callerAdmin = null;
	if(role==InjuryConstants.INJURY_CALLER_ADMIN_ROLE){
		callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
	}else if(role==InjuryConstants.INJURY_CALLER_ROLE){
		callerAdmin=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin();
	}
	
	for (String patientId : assignCallerForm.getPatientId()) {
		PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap();
		patientCallerAdminMap=patientCallerDAO.getPatientMapsByCallerAdminId(patientId, callerAdmin.getCallerAdminId());
		if(patientCallerAdminMap!=null){
			patientCallerAdminMap=new PatientCallerAdminMap(new PatientCallerAdminMapId(patientId, callerAdmin.getCallerAdminId()), callerAdmin, new Patient(patientId));
		}
		patientCallerAdminMap.setIsArchived(archiveStatus);
		patientCallerDAO.merge(patientCallerAdminMap);			
		
	}
	
	return true;
}
	
}
