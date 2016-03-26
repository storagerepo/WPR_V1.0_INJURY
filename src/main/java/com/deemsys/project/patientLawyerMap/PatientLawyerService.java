package com.deemsys.project.patientLawyerMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.AssignLawyerForm;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Lawyer;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientLawyerAdminMap;
import com.deemsys.project.entity.PatientLawyerAdminMapId;
import com.deemsys.project.login.LoginService;


@Service
@Transactional
public class PatientLawyerService {
	
	@Autowired
	PatientLawyerDAO patientLawyerDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	LawyersService lawyerService;

	public boolean assignLawyer(AssignLawyerForm assignLawyerForm){	
		
		LawyerAdmin lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID());
		
		
		for (String patientId : assignLawyerForm.getPatientId()) {
			PatientLawyerAdminMap patientLawyerAdminMap=new PatientLawyerAdminMap();
			patientLawyerAdminMap=patientLawyerDAO.getPatientLawyerAdminMap(patientId, assignLawyerForm.getLawyerId());
			if(patientLawyerAdminMap==null){
				
			//Generate Lawyer	
			Lawyer lawyer=new Lawyer();
			lawyer.setLawyerId(assignLawyerForm.getLawyerId());
			
			//Generate Patient
			Patient patient=new Patient(patientId);
			
			patientLawyerAdminMap=new PatientLawyerAdminMap(new PatientLawyerAdminMapId(patientId,lawyerAdmin.getLawyerAdminId()),lawyer,lawyerAdmin, patient, "", 1, 0,1);
			patientLawyerDAO.merge(patientLawyerAdminMap);
			
		}else{
			//Generate Lawyer	
			Lawyer lawyer=new Lawyer();
			lawyer.setLawyerId(assignLawyerForm.getLawyerId());
			
			patientLawyerAdminMap.setLawyer(lawyer);
			patientLawyerDAO.merge(patientLawyerAdminMap);
		}
		}
		
		return true;
	}
	
public boolean releaseLawyer(AssignLawyerForm assignLawyerForm){	
				
		String role=loginService.getCurrentRole();
		LawyerAdmin lawyerAdmin = null;
		if(role==InjuryConstants.INJURY_LAWYER_ADMIN_ROLE){
			lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID());
		}else if(role==InjuryConstants.INJURY_LAWYER_ROLE){
			lawyerAdmin=lawyerService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin();
		}
		
		for (String patientId : assignLawyerForm.getPatientId()) {
			PatientLawyerAdminMap patientLawyerAdminMap=new PatientLawyerAdminMap();
			patientLawyerAdminMap=patientLawyerDAO.getPatientMapsByLawyerAdminId(patientId, lawyerAdmin.getLawyerAdminId());
			if(patientLawyerAdminMap!=null){
				patientLawyerAdminMap.setLawyer(null);
				patientLawyerDAO.merge(patientLawyerAdminMap);			
			}
		}
		
		return true;
	}

public boolean moveToArchive(AssignLawyerForm assignLawyerForm,Integer archiveStatus){	
	
	String role=loginService.getCurrentRole();
	LawyerAdmin lawyerAdmin = null;
	if(role==InjuryConstants.INJURY_LAWYER_ADMIN_ROLE){
		lawyerAdmin=lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID());
	}else if(role==InjuryConstants.INJURY_LAWYER_ROLE){
		lawyerAdmin=lawyerService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin();
	}
	
	for (String patientId : assignLawyerForm.getPatientId()) {
		PatientLawyerAdminMap patientLawyerAdminMap=new PatientLawyerAdminMap();
		patientLawyerAdminMap=patientLawyerDAO.getPatientMapsByLawyerAdminId(patientId, lawyerAdmin.getLawyerAdminId());
		if(patientLawyerAdminMap==null){
			patientLawyerAdminMap=new PatientLawyerAdminMap(new PatientLawyerAdminMapId(patientId, lawyerAdmin.getLawyerAdminId()), lawyerAdmin, new Patient(patientId));
		}
		patientLawyerAdminMap.setIsArchived(1);
		patientLawyerDAO.merge(patientLawyerAdminMap);			
		
	}
	
	return true;
}
	
}
