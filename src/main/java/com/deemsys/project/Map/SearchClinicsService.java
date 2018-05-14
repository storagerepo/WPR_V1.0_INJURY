package com.deemsys.project.Map;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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
import com.deemsys.project.PatientNearbyClinicSearchResult.PatientNearbyClinicSearchResultDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.entity.PatientNearbyClinicSearchResult;
import com.deemsys.project.entity.PatientNearbyClinicSearchResultId;
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
	
	@Autowired
	PatientNearbyClinicSearchResultDAO patientNearbyClinicSearchResultDAO;

	@SuppressWarnings("static-access")
	public ClinicLocationForm getNearByClinics(NearByClinicSearchForm nearByClinicSearchForm) {

		List<ClinicsForm> clinicsForms = new ArrayList<ClinicsForm>();
		ClinicLocationForm clinicLocationForm = new ClinicLocationForm();
		List<ClinicsForm> drivingDistanceFilteredClinics = new ArrayList<ClinicsForm>();
		
		// Convert to Miles to KiloMeter
		Double convertedRange = geoLocation
				.convertMilesToKiloMeter(nearByClinicSearchForm.getSearchRange());
		// Convert Miles to Meter for Circle in Map
		Double radius = geoLocation.convertMilesToMeter(nearByClinicSearchForm.getSearchRange());
		
		// Final Correct Lat, Long
		BigDecimal correctedLatBigDecimal = new BigDecimal(0);
		BigDecimal correctedLongBigDecimal = new BigDecimal(0);
		String correctedAddress = nearByClinicSearchForm.getCorrectedAddress();
		
		// Check Whether Address Corrected and Search By User 1 -> Address is Not Changed
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
					if (latLong!=null) {
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
		CallerAdmin callerAdmin=new CallerAdmin();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)||role.equals(InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE)){
			callerAdmin=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID());
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)||role.equals(InjuryConstants.INJURY_SHOP_ROLE)){
			callerAdmin=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin();
		}
		
		// Get Enabled Clinic Details By Caller Admin Id
		clinics = clinicsDAO.getEnabledClinicsListsByCallerAdmin(callerAdmin.getCallerAdminId());

		// Get Clinics List For Normal Distance Calculation User
		clinicsForms=this.getFilteredClinicByNormalDistanceCalculation(clinics, convertedRange, correctedLatBigDecimal, correctedLongBigDecimal);
		
		// Check Whether Driving Distance Enabled
		if(callerAdmin.getIsDrivingDistance()!=null&&callerAdmin.getIsDrivingDistance()==1){
			List<PatientNearbyClinicSearchResult> patientNearbyClinicSearchResults=patientNearbyClinicSearchResultDAO.getSearchResultByPatientId(correctedLatBigDecimal,correctedLongBigDecimal);
			// Origins
			String origin = correctedLatBigDecimal+","+correctedLongBigDecimal;
			for (ClinicsForm clinic : clinicsForms) {
				// Mention Driving Distance Status
				clinic.setIsDrivingDistance(1);
				// Destinations
				String destination = clinic.getLatitude()+","+clinic.getLongitude();
				// check With Old Search Results
				PatientNearbyClinicSearchResult matchedPatientNearbyClinicSearchResult = this.checkingWithOldSearchList(patientNearbyClinicSearchResults, clinic);
				if(matchedPatientNearbyClinicSearchResult!=null){
					clinic.setFarAway(InjuryConstants.convertBigDecimaltoDouble(matchedPatientNearbyClinicSearchResult.getDistance()));
					clinic.setTravellingTime(matchedPatientNearbyClinicSearchResult.getTravellingTime());
				}else{
					// Use Google Map Driving Distance API
					List<String> distanceResult=geoLocation.getDrivingDistance(origin, destination);
					clinic.setFarAway(InjuryConstants.convertBigDecimaltoDouble(this.splitDistanceWithoutMetric(distanceResult.get(0))));
					clinic.setTravellingTime(distanceResult.get(1));
					matchedPatientNearbyClinicSearchResult = new PatientNearbyClinicSearchResult(new PatientNearbyClinicSearchResultId(correctedLongBigDecimal, correctedLatBigDecimal, InjuryConstants.convertDoubleBigDecimal(clinic.getLatitude()), InjuryConstants.convertDoubleBigDecimal(clinic.getLongitude())), 
					this.splitDistanceWithoutMetric(distanceResult.get(0)), distanceResult.get(1), new Date(), 1);
					patientNearbyClinicSearchResultDAO.merge(matchedPatientNearbyClinicSearchResult);
				}
			}
			drivingDistanceFilteredClinics = this.getFinalResultAfterDrivingDistance(clinicsForms, nearByClinicSearchForm.getSearchRange().doubleValue());
			clinicLocationForm = new ClinicLocationForm(InjuryConstants.convertBigDecimaltoDouble(correctedLatBigDecimal),
					InjuryConstants.convertBigDecimaltoDouble(correctedLongBigDecimal), radius, correctedAddress, drivingDistanceFilteredClinics);
		}else{
			clinicLocationForm = new ClinicLocationForm(InjuryConstants.convertBigDecimaltoDouble(correctedLatBigDecimal),
					InjuryConstants.convertBigDecimaltoDouble(correctedLongBigDecimal), radius, correctedAddress, clinicsForms);
		}
		return clinicLocationForm;

	}

	// Filter the Clinic List By Normal Distance
	public List<ClinicsForm> getFilteredClinicByNormalDistanceCalculation(List<Clinic> clinics, Double convertedRange, BigDecimal centerLatitude, BigDecimal centerLongitude){
		List<ClinicsForm> filteredClinicsForms=new ArrayList<ClinicsForm>();
		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		
		for (Clinic clinics2 : clinics) {
			if((centerLatitude.compareTo(new BigDecimal("0.0"))!=0&&centerLongitude.compareTo(new BigDecimal("0.0"))!=0)
				&& (centerLatitude.compareTo(new BigDecimal("0.1"))!=0&&centerLongitude.compareTo(new BigDecimal("0.1"))!=0)){
				Double distance = geoLocation.distance(InjuryConstants.convertBigDecimaltoDouble(centerLatitude),
						InjuryConstants.convertBigDecimaltoDouble(clinics2.getLatitude()), InjuryConstants.convertBigDecimaltoDouble(centerLongitude),
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
							"", 0,
							clinicTimingLists);
					filteredClinicsForms.add(clinicsForm);
				}
			}
		}
		return filteredClinicsForms;
	}
	
	// Check whether clinic matched with Old Search List
	public PatientNearbyClinicSearchResult checkingWithOldSearchList(List<PatientNearbyClinicSearchResult> patientNearbyClinicSearchResults,ClinicsForm clinic){
		PatientNearbyClinicSearchResult matchedNearbyClinicSearchResult = null;
		for (PatientNearbyClinicSearchResult patientNearbyClinicSearchResult : patientNearbyClinicSearchResults) {
			if(clinic.getLatitude()==InjuryConstants.convertBigDecimaltoDouble(patientNearbyClinicSearchResult.getId().getDestinationLatitude())
					&&clinic.getLongitude()==InjuryConstants.convertBigDecimaltoDouble(patientNearbyClinicSearchResult.getId().getDestinationLongitude())){
				matchedNearbyClinicSearchResult=patientNearbyClinicSearchResult;
				break;
			}
		}
		
		return matchedNearbyClinicSearchResult;
	}
	
	// Split the Distance without metric from Response
	public BigDecimal splitDistanceWithoutMetric(String distance){
		String[] splittedValue=distance.split(" ");
		return new BigDecimal(splittedValue[0]);
	}
	
	// Filter Clinic After Google Driving Distance
	public List<ClinicsForm> getFinalResultAfterDrivingDistance(List<ClinicsForm> clinicsForms, Double searchRange){
		List<ClinicsForm> filterClinicsForms = new ArrayList<ClinicsForm>();
		for (ClinicsForm clinicsForm : clinicsForms) {
			if(clinicsForm.getFarAway()<searchRange){
				filterClinicsForms.add(clinicsForm);
			}
		}
		return filterClinicsForms;
	}
	
	// Delete Old Search Results
	public void deleteOldSearchResults(Date date){
		patientNearbyClinicSearchResultDAO.deleteOldSearchResults(date);
	}
}
