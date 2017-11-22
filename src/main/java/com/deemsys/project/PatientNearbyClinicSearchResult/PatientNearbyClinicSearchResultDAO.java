package com.deemsys.project.PatientNearbyClinicSearchResult;

import java.math.BigDecimal;
import java.util.Date;
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
	public List<PatientNearbyClinicSearchResult> getSearchResultByPatientId(BigDecimal originLatitude, BigDecimal originLongitude);
	public void deleteOldSearchResults(Date date);
	public List<PatientNearbyClinicSearchResult> getSearchresultByDate(Date date);
}
