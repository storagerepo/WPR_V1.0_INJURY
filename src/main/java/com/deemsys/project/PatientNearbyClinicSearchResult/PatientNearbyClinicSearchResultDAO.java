package com.deemsys.project.PatientNearbyClinicSearchResult;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.PatientNearbyClinicSearchResult;
import com.deemsys.project.entity.Template;
/**
 * 
 * @author Deemsys
 *
 */
public interface PatientNearbyClinicSearchResultDAO extends IGenericDAO<PatientNearbyClinicSearchResult>{
	public List<PatientNearbyClinicSearchResult> getSearchResultByPatientId(String patientId);
	public void deleteOldSearchResults(String date);
}
