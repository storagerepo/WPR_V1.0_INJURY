package com.deemsys.project.Clinics;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Clinics;

public interface ClinicsDAO extends IGenericDAO<Clinics>{

	public List<Clinics> getClinicsLists();
	public List<Clinics> getClinicId();
	
}
