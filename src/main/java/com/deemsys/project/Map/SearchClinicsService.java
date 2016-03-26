package com.deemsys.project.Map;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.ClinicTimings.ClinicTimingList;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Clinics.ClinicsForm;
import com.deemsys.project.Clinics.ClinicsService;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Patient;
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

		Patient patients = patientDAO.getPatientByPatientId(patientId);
		List<Clinic> clinics = new ArrayList<Clinic>();
		clinics = clinicsDAO.getClinicsLists();

		for (Clinic clinics2 : clinics) {
			Double distance = geoLocation.distance(patients.getLatitude(),
					clinics2.getLatitude(), patients.getLongitude(),
					clinics2.getLongitude());
			if (distance < convertedRange) {
				clinicTimingLists = clinicsService
						.getClinicTimingLists(clinics2.getClinicId());
				ClinicsForm clinicsForm = new ClinicsForm(
						clinics2.getClinicId(), clinics2.getClinicName(),
						clinics2.getAddress(), clinics2.getCity(),
						clinics2.getState(), clinics2.getCounty(),
						clinics2.getCountry(), clinics2.getZipcode(),
						clinics2.getLatitude(), clinics2.getLongitude(),
						clinics2.getOfficeNumber(), clinics2.getFaxNumber(),
						clinics2.getServiceArea(), clinics2.getDirections(),
						clinics2.getNotes(), clinics2.getStatus(),
						geoLocation.convertKiloMeterToMiles(distance),
						clinicTimingLists);
				clinicsForms.add(clinicsForm);
			}
		}

		clinicLocationForm = new ClinicLocationForm(patients.getLatitude(),
				patients.getLongitude(), radius, clinicsForms);

		return clinicLocationForm;

	}

}
