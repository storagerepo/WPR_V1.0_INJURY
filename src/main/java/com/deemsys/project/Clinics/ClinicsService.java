package com.deemsys.project.Clinics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminDAO;
import com.deemsys.project.ClinicTimings.ClinicTimingList;
import com.deemsys.project.ClinicTimings.ClinicTimingsDAO;
import com.deemsys.project.Doctors.DoctorsDAO;
import com.deemsys.project.Doctors.DoctorsForm;
import com.deemsys.project.Doctors.DoctorsService;
import com.deemsys.project.Map.GeoLocation;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.ClinicTimings;
import com.deemsys.project.entity.ClinicTimingsId;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.PatientDAO;

/**
 * @author Deemsys
 * 
 */
@Service
@Transactional
public class ClinicsService {

	@Autowired
	ClinicsDAO clinicsDAO;

	@Autowired
	ClinicTimingsDAO clinicTimingsDAO;

	@Autowired
	DoctorsDAO doctorsDAO;

	@Autowired
	GeoLocation geoLocation;

	@Autowired
	PatientDAO patientDAO;

	@Autowired
	DoctorsService doctorsService;
	
	@Autowired
	CallerAdminDAO callerAdminDAO;
	
	@Autowired
	CallerService callerService;
	
	@Autowired
	LoginService loginService;

	/**
	 * Get Clinic Details For Edit
	 * 
	 * @param clinicId
	 * @return
	 */
	public ClinicsForm getClinic(Integer clinicId) {

		Clinic clinics = clinicsDAO.get(clinicId);
		List<ClinicTimings> clinicTimings = clinicTimingsDAO
				.getClinicTimings(clinicId);

		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		// Set ClinicTiming List
		for (ClinicTimings clinicTimingss : clinicTimings) {
			ClinicTimingList clinicTimingList = new ClinicTimingList(
					clinicTimingss.getId().getDay(),
					clinicTimingss.getId().getClinicId(),
					InjuryConstants.convertAMPMTime(clinicTimingss
							.getStartTime()),
					InjuryConstants.convertAMPMTime(clinicTimingss.getEndTime()),
					InjuryConstants.convertAMPMTime(clinicTimingss
							.getStartsBreak()), InjuryConstants
							.convertAMPMTime(clinicTimingss.getEndsBreak()),
					clinicTimingss.getIsWorkingDay(), clinicTimingss
							.getIsAppointmentDay());
			clinicTimingLists.add(clinicTimingList);
		}

		// Get Doctors List
		List<DoctorsForm> doctorsForms = doctorsService
				.getDoctorsByClinic(clinicId);

		// Set Clinic
		ClinicsForm clinicsForm = new ClinicsForm(clinics.getClinicId(),
				clinics.getClinicName(), clinics.getAddress(),
				clinics.getCity(), clinics.getState(), clinics.getCounty(),
				clinics.getCountry(), clinics.getZipcode(),
				clinics.getOfficeNumber(), clinics.getFaxNumber(),
				clinics.getServiceArea(), clinics.getDirections(),
				clinics.getNotes(),clinics.getStatus(), clinicTimingLists, doctorsForms);

		return clinicsForm;
	}

	/**
	 * Get Clinic Details For View
	 * 
	 * @param clinicId
	 * @return
	 */
	public ClinicsForm getClinicDetails(Integer clinicId) {

		Clinic clinics = clinicsDAO.get(clinicId);
		List<ClinicTimings> clinicTimings = clinicTimingsDAO
				.getClinicTimings(clinicId);

		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		// Set ClinicTiming List
		for (ClinicTimings clinicTimingss : clinicTimings) {
			ClinicTimingList clinicTimingList = new ClinicTimingList(
					clinicTimingss.getId().getDay(),
					clinicTimingss.getId().getClinicId(),
					InjuryConstants.convertAMPMTime(clinicTimingss
							.getStartTime()),
					InjuryConstants.convertAMPMTime(clinicTimingss.getEndTime()),
					InjuryConstants.convertAMPMTime(clinicTimingss
							.getStartsBreak()), InjuryConstants
							.convertAMPMTime(clinicTimingss.getEndsBreak()),
					clinicTimingss.getIsWorkingDay(), clinicTimingss
							.getIsAppointmentDay());
			clinicTimingLists.add(clinicTimingList);
		}

		// Get Doctors List
		List<DoctorsForm> doctorsForms = doctorsService
				.getDoctorsDetailsByClinic(clinicId);

		// Set Clinic
		ClinicsForm clinicsForm = new ClinicsForm(clinics.getClinicId(),
				clinics.getClinicName(), clinics.getAddress(),
				clinics.getCity(), clinics.getState(), clinics.getCounty(),
				clinics.getCountry(), clinics.getZipcode(),
				clinics.getOfficeNumber(), clinics.getFaxNumber(),
				clinics.getServiceArea(), clinics.getDirections(),
				clinics.getNotes(), clinics.getStatus(), clinicTimingLists, doctorsForms);

		return clinicsForm;
	}

	/**
	 * Get All Clinics
	 * 
	 * @return
	 */
	public List<ClinicsForm> getClinicsList() {
		List<ClinicsForm> clinicsForms = new ArrayList<ClinicsForm>();
		List<Clinic> clinicses = new ArrayList<Clinic>();
		String role=loginService.getCurrentRole();
		Integer callerAdminId=0;
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			callerAdminId=callerAdminDAO.getCallerAdminByUserId(callerService.getCurrentUserId()).getCallerAdminId();
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			callerAdminId=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId();
		}
		
		clinicses = clinicsDAO.getClinicsByCallerAdmin(callerAdminId);

		for (Clinic clinics : clinicses) {
			ClinicsForm clinicsForm = new ClinicsForm(clinics.getClinicId(),
					clinics.getClinicName(), clinics.getAddress(),
					clinics.getCity(), clinics.getState(), clinics.getCounty(),
					clinics.getCountry(), clinics.getZipcode(),
					clinics.getOfficeNumber(), clinics.getFaxNumber(),
					clinics.getServiceArea(), clinics.getDirections(),
					clinics.getNotes(),clinics.getStatus(), null, null);
			clinicsForms.add(clinicsForm);
		}

		return clinicsForms;
	}

	/**
	 * Save Clinic
	 * 
	 * @param clinicsForm
	 * @return
	 */
	public Integer saveClinic(ClinicsForm clinicsForm) {
		Integer status = 0;

		// Get Lat Lang of Address
		String latLong = geoLocation.getLocation(this
				.getAddressForGeoCode(clinicsForm));
		System.out.println("CONVERT ADDRESS--"
				+ this.getAddressForGeoCode(clinicsForm));
		String[] latiudeLongitude = latLong.split(",");
		CallerAdmin callerAdmin=callerAdminDAO.getCallerAdminByUserId(callerService.getCurrentUserId());
		// Save Clinic
		Clinic clinics = new Clinic(callerAdmin,clinicsForm.getClinicName(),
				clinicsForm.getAddress(), 
				Double.parseDouble(latiudeLongitude[0]),
				Double.parseDouble(latiudeLongitude[1]),
				clinicsForm.getCity(),
				clinicsForm.getState(), clinicsForm.getCounty(),
				clinicsForm.getCountry(), clinicsForm.getZipcode(),
				clinicsForm.getOfficeNumber(), clinicsForm.getFaxNumber(),
				clinicsForm.getServiceArea(), clinicsForm.getDirections(),
				clinicsForm.getNotes(), 1, null, null);
		clinicsDAO.save(clinics);

		// Save Clinic Timings
		List<ClinicTimingList> clinicTimingList = clinicsForm
				.getClinicTimingList();
		for (ClinicTimingList clinicTimingList2 : clinicTimingList) {
			ClinicTimingsId clinicTimingsId = new ClinicTimingsId(
					clinics.getClinicId(), clinicTimingList2.getDay());
			ClinicTimings clinicTimings = new ClinicTimings(clinicTimingsId,
					clinics,
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getStartTime()),
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getEndTime()),
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getStartsBreak()),
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getEndsBreak()),
					clinicTimingList2.getIsWorkingDay(),
					clinicTimingList2.getIsAppointmentDay(),1);
			clinicTimingsDAO.save(clinicTimings);
		}

		// Save Doctors List
		List<DoctorsForm> doctorsForms = clinicsForm.getDoctorsForms();
		for (DoctorsForm doctorsForm : doctorsForms) {
			doctorsForm.setClinicId(clinics.getClinicId());
			doctorsService.saveDoctors(doctorsForm);
		}

		return status;
	}

	/**
	 * Update Clinic
	 * 
	 * @param clinicsForm
	 * @return
	 */
	public Integer updateClinic(ClinicsForm clinicsForm) {
		Integer status = 0;

		// Get Lat Long For Address
		String latLong = geoLocation.getLocation(this
				.getAddressForGeoCode(clinicsForm));
		String[] latiudeLongitude = latLong.split(",");
		CallerAdmin callerAdmin=callerAdminDAO.getCallerAdminByUserId(callerService.getCurrentUserId());
		// Update Clinics
		Clinic clinics = new Clinic(callerAdmin,clinicsForm.getClinicName(),
				clinicsForm.getAddress(),Double.parseDouble(latiudeLongitude[0]),
				Double.parseDouble(latiudeLongitude[1]), clinicsForm.getCity(),
				clinicsForm.getState(), clinicsForm.getCounty(),
				clinicsForm.getCountry(), clinicsForm.getZipcode(),
				clinicsForm.getOfficeNumber(), clinicsForm.getFaxNumber(),
				clinicsForm.getServiceArea(), clinicsForm.getDirections(),
				clinicsForm.getNotes(), 1,null, null);
		clinics.setClinicId(clinicsForm.getClinicId());
		clinicsDAO.update(clinics);

		// Update Clinic Timings
		List<ClinicTimingList> clinicTimingList = clinicsForm
				.getClinicTimingList();
		for (ClinicTimingList clinicTimingList2 : clinicTimingList) {
			ClinicTimingsId clinicTimingsId = new ClinicTimingsId(
					clinicTimingList2.getClinicId(), clinicTimingList2.getDay());
			ClinicTimings clinicTimings = new ClinicTimings(clinicTimingsId,
					clinics,
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getStartTime()),
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getEndTime()),
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getStartsBreak()),
					InjuryConstants.convert24HourTime(clinicTimingList2
							.getEndsBreak()),
					clinicTimingList2.getIsWorkingDay(),
					clinicTimingList2.getIsAppointmentDay(),1);
			clinicTimingsDAO.update(clinicTimings);
		}

		// Update Doctors List
		List<DoctorsForm> doctorsForms = clinicsForm.getDoctorsForms();
		for (DoctorsForm doctorsForm : doctorsForms) {
			doctorsForm.setClinicId(clinicsForm.getClinicId());
			if (doctorsForm.getId() == null) {
				doctorsService.saveDoctors(doctorsForm);
			} else {
				doctorsService.updateDoctors(doctorsForm);
			}

		}

		return status;
	}

	/**
	 * Delete a Clinic
	 * 
	 * @param clinicId
	 * @return
	 */
	public Integer deleteClinic(Integer clinicId) {
		Integer status = 0;

		Integer size = doctorsDAO.getDoctorsSizeByClinicId(clinicId);
		if (size > 0) {
			status = 0;
		} else {
			clinicTimingsDAO.deleteClinicTimingsByClinicId(clinicId);
			clinicsDAO.delete(clinicId);
			status = 1;
		}

		return status;
	}

	public List<ClinicsForm> getClinicId() {
		List<ClinicsForm> clinicsForms = new ArrayList<ClinicsForm>();

		List<Clinic> clinicss = new ArrayList<Clinic>();
		String role=loginService.getCurrentRole();
		Integer callerAdminId=0;
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			callerAdminId=callerAdminDAO.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId();
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			callerAdminId=callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId();
		}
		clinicss = clinicsDAO.getClinicId(callerAdminId);

		for (Clinic clinics : clinicss) {
			// TODO: Fill the List
				ClinicsForm clinicsForm = new ClinicsForm(clinics.getClinicId(),
						clinics.getClinicName());
				clinicsForms.add(clinicsForm);
			
		}

		return clinicsForms;
	}

	/**
	 * Get No Of clinics
	 * 
	 * @return
	 */
	public Integer getNoOfClinics() {
		Integer noOfClinics = 0;
		String role=loginService.getCurrentRole();
		Integer callerAdminId=0;
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			callerAdminId=callerAdminDAO.getCallerAdminByUserId(callerService.getCurrentUserId()).getCallerAdminId();
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			callerAdminId=callerService.getCallerByUserId(callerService.getCurrentUserId()).getCallerAdmin().getCallerAdminId();
		}
		
		noOfClinics = clinicsDAO.getClinicsByCallerAdmin(callerAdminId).size();
		return noOfClinics;
	}

	public String getAddressForGeoCode(ClinicsForm clinicsForm) {
		String address = "";
		address = clinicsForm.getAddress().replace(" ", "+") + "+"
				+ clinicsForm.getCity().replace(" ", "+") + "+"
				+ clinicsForm.getState().replace(" ", "+") + "+"
				+ clinicsForm.getCountry().replace(" ", "+") + "+"
				+ clinicsForm.getZipcode();
		return address;
	}

	/*
	 * Get Clinic Timing Lists
	 */
	public List<ClinicTimingList> getClinicTimingLists(Integer clinicId) {

		List<ClinicTimings> clinicTimings = clinicTimingsDAO
				.getClinicTimings(clinicId);

		List<ClinicTimingList> clinicTimingLists = new ArrayList<ClinicTimingList>();
		// Set ClinicTiming List
		for (ClinicTimings clinicTimingss : clinicTimings) {
			ClinicTimingList clinicTimingList = new ClinicTimingList(
					clinicTimingss.getId().getDay(),
					clinicTimingss.getId().getClinicId(),
					InjuryConstants.convertAMPMTime(clinicTimingss
							.getStartTime()),
					InjuryConstants.convertAMPMTime(clinicTimingss.getEndTime()),
					InjuryConstants.convertAMPMTime(clinicTimingss
							.getStartsBreak()), InjuryConstants
							.convertAMPMTime(clinicTimingss.getEndsBreak()),
					clinicTimingss.getIsWorkingDay(), clinicTimingss
							.getIsAppointmentDay());
			clinicTimingLists.add(clinicTimingList);
		}

		return clinicTimingLists;
	}

	// Unassign the Patient from clinic
	public Integer removeAssignedClinic(Integer clinicId) {
		Integer status = 0;
		try {
			List<Patient> patient = patientDAO
					.getpatientByClinicId(clinicId);
			for (Patient patient2 : patient) {
				//patientDAO.removeAssignedClinic(patient2.getPatientId());
			}
			status = 1;
		} catch (Exception e) {
			System.out.println(e.toString());
			status = 0;
		}

		return status;
	}
	
	// Enable or Disable Clinic
	public int enableOrDisableClinic(Integer clinicId)
	{ 
		Clinic clinic=clinicsDAO.get(clinicId);
		if(clinic.getStatus()==1){
			clinicsDAO.disable(clinicId);
		}else if(clinic.getStatus()==0){
			clinicsDAO.enable(clinicId);
		}
		return 1;
	}

}
