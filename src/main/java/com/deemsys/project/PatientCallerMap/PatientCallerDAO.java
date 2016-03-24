package com.deemsys.project.PatientCallerMap;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.entity.PatientCallerAdminMapId;

public interface PatientCallerDAO extends IGenericDAO<PatientCallerAdminMap> {


	public PatientCallerAdminMap getPatientCallerAdminMap(String patientId,
			Integer callerId);

	public PatientCallerAdminMap getPatientMapsByCallerAdminId(String patientId,
			Integer callerAdminId);
	
}
