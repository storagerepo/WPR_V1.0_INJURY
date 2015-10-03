package com.deemsys.project.ClinicTimings;

import java.util.List;

import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.ClinicTimings;

public interface ClinicTimingsDAO extends IGenericDAO<ClinicTimings>{

	public List<ClinicTimings> getClinicTimings(Integer clinicId);
}
