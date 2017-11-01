package com.deemsys.project.Map;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.ClinicTimings.ClinicTimingList;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Clinics.ClinicsForm;
import com.deemsys.project.Clinics.ClinicsService;
import com.deemsys.project.PatientCallerMap.PatientCallerService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.PatientDAO;

@Service
@Transactional
public class SearchClinicsService {

	@Autowired
	ClinicsDAO clinicsDAO;

	@Autowired
	PatientDAO patientDAO;

	@Autowired
	GeoLocation geoLocation;

	@Autowired
	ClinicsService clinicsService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CallerService callerService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	PatientCallerService patientCallerService;

	@SuppressWarnings("static-access")
	public ClinicLocationForm getNearByClinics(NearByClinicSearchForm nearByClinicSearchForm) {

		List<ClinicsForm> clinicsForms = new ArrayList<ClinicsForm>();
		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		ClinicLocationForm clinicLocationForm = new ClinicLocationForm();

		// Convert to Miles to KiloMeter
		Double convertedRange = geoLocation
				.convertMilesToKiloMeter(nearByClinicSearchForm.getSearchRange());
		// Convert Miles to Meter for Circle in Map
		Double radius = geoLocation.convertMilesToMeter(nearByClinicSearchForm.getSearchRange());
		
		// Final Correct Lat, Long
		BigDecimal correctedLatBigDecimal = new BigDecimal(0);
		BigDecimal correctedLongBigDecimal = new BigDecimal(0);
		String correctedAddress = nearByClinicSearchForm.getCorrectedAddress();
		
		// Check Whether Address Corrected and Search By User
		if(nearByClinicSearchForm.getSearchBy()==1){
			PatientCallerAdminMap patientCallerAdminMap = patientCallerService.getPatientCallerAdminMap(nearByClinicSearchForm.getPatientId());
			if(patientCallerAdminMap!=null && patientCallerAdminMap.getLatitude()!=null && patientCallerAdminMap.getLongitude()!=null){
				correctedLatBigDecimal = patientCallerAdminMap.getLatitude();
				correctedLongBigDecimal = patientCallerAdminMap.getLongitude();
				correctedAddress = patientCallerAdminMap.getAddress();
			}else{
				Patient patient = patientDAO.getPatientByPatientId(nearByClinicSearchForm.getPatientId());
				Double currentLat=InjuryConstants.convertBigDecimaltoDouble(patient.getLatitude());
				Double currentLong=InjuryConstants.convertBigDecimaltoDouble(patient.getLongitude());
				if(currentLat.equals(0.0)&&currentLong.equals(0.0)){
					// Get Lat Long First Time (Address From Report)
					BigDecimal longitude = new BigDecimal(0);
					BigDecimal latitude = new BigDecimal(0);
					String latLong = geoLocation.getLocation(patient.getAddress());
					if (!latLong.equals("NONE")) {
						String[] latitudeLongitude = latLong.split(",");
						latitude = new BigDecimal(latitudeLongitude[0]);
						longitude = new BigDecimal(latitudeLongitude[1]);
					}
					patientDAO.updateLatLongByAddress(latitude, longitude, patient.getAddress());
					correctedLatBigDecimal = latitude;
					correctedLongBigDecimal = longitude;
				}else{
					// Correct Address From Report with Lat, Long
					correctedLatBigDecimal = patient.getLatitude();
					correctedLongBigDecimal = patient.getLongitude();
				}
			}
		}else{
			// Address Corrected with Lat, Long By User Entry
			correctedLatBigDecimal = new BigDecimal(nearByClinicSearchForm.getCorrectedLat()).setScale(11,RoundingMode.UP);
			correctedLongBigDecimal = new BigDecimal(nearByClinicSearchForm.getCorrectedLong()).setScale(11,RoundingMode.UP);
			// Update in Patient Mapped Tables
			patientCallerService.updateCorrectedAddress(nearByClinicSearchForm.getPatientId(), correctedAddress, correctedLatBigDecimal, correctedLongBigDecimal);
		}
		
		List<Clinic> clinics = new ArrayList<Clinic>();
		String role=loginService.getCurrentRole();
		Integer callerAdminId=0;
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			callerAdminId=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId();
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			callerAdminId=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId();
		}
		clinics = clinicsDAO.getEnabledClinicsListsByCallerAdmin(callerAdminId);

		for (Clinic clinics2 : clinics) {
			
			Double distance = geoLocation.distance(InjuryConstants.convertBigDecimaltoDouble(correctedLatBigDecimal),
					InjuryConstants.convertBigDecimaltoDouble(clinics2.getLatitude()), InjuryConstants.convertBigDecimaltoDouble(correctedLongBigDecimal),
					InjuryConstants.convertBigDecimaltoDouble(clinics2.getLongitude()));
			if (distance < convertedRange) {
				clinicTimingLists = clinicsService
						.getClinicTimingLists(clinics2.getClinicId());
				ClinicsForm clinicsForm = new ClinicsForm(
						clinics2.getClinicId(), clinics2.getClinicName(),
						clinics2.getAddress(), clinics2.getCity(),
						clinics2.getState(), clinics2.getCounty(),
						clinics2.getCountry(), clinics2.getZipcode(),
						InjuryConstants.convertBigDecimaltoDouble(clinics2.getLatitude()), InjuryConstants.convertBigDecimaltoDouble(clinics2.getLongitude()),
						clinics2.getOfficeNumber(), clinics2.getFaxNumber(),
						clinics2.getServiceArea(), clinics2.getDirections(),
						clinics2.getNotes(), clinics2.getStatus(),
						geoLocation.convertKiloMeterToMiles(distance),
						clinicTimingLists);
				clinicsForms.add(clinicsForm);
			}
		}

		clinicLocationForm = new ClinicLocationForm(InjuryConstants.convertBigDecimaltoDouble(correctedLatBigDecimal),
				InjuryConstants.convertBigDecimaltoDouble(correctedLongBigDecimal), radius, correctedAddress, clinicsForms);

		return clinicLocationForm;

	}

}
