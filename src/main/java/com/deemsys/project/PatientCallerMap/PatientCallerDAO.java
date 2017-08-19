package com.deemsys.project.PatientCallerMap;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.PatientCallerAdminMap;

public interface PatientCallerDAO extends IGenericDAO<PatientCallerAdminMap> {


	public PatientCallerAdminMap getPatientCallerAdminMap(String patientId,
			Integer callerId);

	public PatientCallerAdminMap getPatientMapsByCallerAdminId(String patientId,
			Integer callerAdminId);
	public List<PatientCallerAdminMap> getAssignedPatientsByCallerId(Integer callerId);
}
