package com.deemsys.project.Clinics;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Clinic;

public interface ClinicsDAO extends IGenericDAO<Clinic> {

	public List<Clinic> getClinicsLists();
	
	public List<Clinic> getClinicsByCallerAdmin(Integer callerAdmin);

	public List<Clinic> getClinicId(Integer callerAdminId);
}
