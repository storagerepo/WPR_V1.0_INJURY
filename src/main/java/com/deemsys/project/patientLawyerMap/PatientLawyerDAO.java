package com.deemsys.project.patientLawyerMap;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.entity.PatientCallerAdminMapId;
import com.deemsys.project.entity.PatientLawyerAdminMap;

public interface PatientLawyerDAO extends IGenericDAO<PatientLawyerAdminMap> {


	public PatientLawyerAdminMap getPatientLawyerAdminMap(String patientId,
			Integer callerId);

	public PatientLawyerAdminMap getPatientMapsByLawyerAdminId(String patientId,
			Integer lawyerAdminId);
	
}
