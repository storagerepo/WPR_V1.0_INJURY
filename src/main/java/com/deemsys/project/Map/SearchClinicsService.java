package com.deemsys.project.Map;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Patients;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.ClinicTimings.ClinicTimingList;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Clinics.ClinicsForm;
import com.deemsys.project.Clinics.ClinicsService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Patient;
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

	@SuppressWarnings("static-access")
	public ClinicLocationForm getNearByClinics(String patientId,
			Integer searchRange) {

		List<ClinicsForm> clinicsForms = new ArrayList<ClinicsForm>();
		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		ClinicLocationForm clinicLocationForm = new ClinicLocationForm();

		// Convert to Miles to KiloMeter
		Double convertedRange = geoLocation
				.convertMilesToKiloMeter(searchRange);
		// Convert Miles to Meter for Circle in Map
		Double radius = geoLocation.convertMilesToMeter(searchRange);

		Patient patient = patientDAO.getPatientByPatientId(patientId);
		Double currentLat=InjuryConstants.convertBigDecimaltoDouble(patient.getLatitude());
		Double currentLong=InjuryConstants.convertBigDecimaltoDouble(patient.getLongitude());
		if(currentLat.equals(0.0)&&currentLong.equals(0.0)){
			List<Patient> patients=patientDAO.getPatientListByAddressWithoutCurrentPatient(patient.getAddress(),patientId);
			BigDecimal longitude = new BigDecimal(0);
			BigDecimal latitude = new BigDecimal(0);
			if(patients.size()>0){
				for (Patient oldPatient : patients) {
					Double oldLat=InjuryConstants.convertBigDecimaltoDouble(oldPatient.getLatitude());
					Double oldLong=InjuryConstants.convertBigDecimaltoDouble(oldPatient.getLongitude());
					if(!oldLat.equals(0.0)&&!oldLong.equals(0.0)){
						latitude=oldPatient.getLatitude();
						longitude=oldPatient.getLongitude();
						break;
					}
				}
			}else{
				String latLong = geoLocation.getLocation(patient.getAddress());
				if (!latLong.equals("NONE")) {
					String[] latitudeLongitude = latLong.split(",");
					latitude = new BigDecimal(latitudeLongitude[0]);
					longitude = new BigDecimal(latitudeLongitude[1]);
				}
			}
			patient.setLatitude(latitude);
			patient.setLongitude(longitude);
			
			// Update Patient With Latitude Longitude
			patientDAO.update(patient);
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
			
			Double distance = geoLocation.distance(InjuryConstants.convertBigDecimaltoDouble(patient.getLatitude()),
					InjuryConstants.convertBigDecimaltoDouble(clinics2.getLatitude()), InjuryConstants.convertBigDecimaltoDouble(patient.getLongitude()),
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

		clinicLocationForm = new ClinicLocationForm(InjuryConstants.convertBigDecimaltoDouble(patient.getLatitude()),
				InjuryConstants.convertBigDecimaltoDouble(patient.getLongitude()), radius, clinicsForms);

		return clinicLocationForm;

	}

}
