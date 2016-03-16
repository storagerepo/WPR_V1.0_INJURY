package com.deemsys.project.patient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.entity.Patient;
import com.deemsys.project.Appointments.AppointmentsDAO;
import com.deemsys.project.CallLogs.CallLogsDAO;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Map.GeoLocation;
import com.deemsys.project.Caller.CallerDAO;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Doctor;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;

/**
 * 
 * @author Deemsys
 * 
 *         Patient - Entity patient - Entity Object patients - Entity List
 *         patientDAO - Entity DAO patientForms - EntityForm List PatientForm
 *         - EntityForm
 * 
 */
@Service
@Transactional
public class PatientService {

	@Autowired
	PatientDAO patientDAO;
	@Autowired
	CallLogsDAO callLogsDAO;
	@Autowired
	ClinicsDAO clinicsDAO;
	@Autowired
	CallerDAO callerDAO;
	@Autowired
	PDFCrashReportReader crashReportReader;

	@Autowired
	CallerService callerService;
	@Autowired
	AppointmentsDAO appointmentsDAO;

	@Autowired
	GeoLocation geoLocation;

	/*
	 * @Autowired PatientFileRead patientFileRead;
	 */

	// Get All Entries
	public List<PatientForm> getPatientList() {
		List<PatientForm> patientForms = new ArrayList<PatientForm>();

		List<Patient> patients = new ArrayList<Patient>();

		String role = callerService.getCurrentRole();
		if (role == InjuryConstants.INJURY_CALLER_ADMIN_ROLE) {
			patients = patientDAO.getAll();
		}else if (role == InjuryConstants.INJURY_CALLER_ROLE) {
			Integer userId = callerService.getCurrentUserId();
			// get Caller Id
			Integer callerId = callerDAO.getByUserId(userId).getCallerId();
			patients=callerDAO.getPatientByAccessToken(callerId);
		}else{
			patients = patientDAO.getAll();
		}

		for (Patient patient : patients) {
			// TODO: Fill the List
			patientForms.add(this.getPatientFormDetails(patient));
		}

		return patientForms;
	}

	// Add to patient to Patient Forms
	public PatientForm getPatientFormDetails(Patient patient){
	
		Integer callerId = 0, clinicId = 0, doctorId = 0;
		String callerName = "N/A";
		String clinicName = "N/A";
		String doctorName = "N/A";
		callerName = "N/A";
		clinicName = "N/A";
		doctorName = "N/A";
		/*if (patient.getCaller() != null) {
			callerId = patient.getCaller().getId();
			callerName = patient.getCaller().getFirstName() + " "
					+ patient.getCaller().getLastName();
		}
		if (patient.getClinic() != null) {
			clinicId = patient.getClinics().getClinicId();
			clinicName = patient.getClinics().getClinicName();
		}
		if (patient.getDoctors() != null) {
			doctorId = patient.getDoctors().getId();
			doctorName = patient.getDoctors().getDoctorName();
		}
		PatientForm patientForm = new PatientForm(patient.getId(),
				callerId, clinicId, doctorId,
				patient.getLocalReportNumber(),
				patient.getCrashSeverity(),
				patient.getReportingAgencyName(),
				patient.getNumberOfUnits(), patient.getUnitInError(),
				patient.getCountry(), patient.getCityVillageTownship(),
				patient.getCrashDate(), patient.getTimeOfCrash()
						.toString(), patient.getUnitNumber(),
				patient.getName(), patient.getDateOfBirth(),
				patient.getGender(), patient.getAddress(),
				patient.getPhoneNumber(), patient.getInjuries(),
				patient.getEmsAgency(), patient.getMedicalFacility(),
				patient.getCrashReportFileName(),
				patient.getPatientStatus());
		patientForm.setCallerName(callerName);
		patientForm.setClinicName(clinicName);
		patientForm.setDoctorName(doctorName);
		*/

		PatientForm patientForm = new PatientForm();
		
		return patientForm;
	}
	
	// Get Particular Entry
	public PatientForm getPatient(Integer getId) {
		Patient patient = new Patient();

		patient = patientDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start
		PatientForm patientForm = new PatientForm();
		
		patientForm = this.getPatientFormDetails(patient);
		// End
		
		return patientForm;
	}

	// Get Particular Entry
	public PatientForm getPatientWithLatLong(Integer getId) {
		Patient patient = new Patient();

		patient = patientDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start
		PatientForm patientForm = new PatientForm();
		Integer callerId = 0, clinicId = 0, doctorId = 0;
		String callerName = "N/A";
		String clinicName = "N/A";
		String doctorName = "N/A";
		/*if (patient != null) {
			callerId = 0;
			clinicId = 0;
			doctorId = 0;
			if (patient.getCaller() != null) {
				callerId = patient.getCaller().getId();
				callerName = patient.getCaller().getFirstName() + " "
						+ patient.getCaller().getLastName();
			}
			if (patient.getClinics() != null) {
				clinicId = patient.getClinics().getClinicId();
				clinicName = patient.getClinics().getClinicName();
			}
			if (patient.getDoctors() != null) {
				doctorId = patient.getDoctors().getId();
				doctorName = patient.getDoctors().getDoctorName();
			}

			patientForm = new PatientForm(patient.getId(), callerId,
					clinicId, doctorId, patient.getLocalReportNumber(),
					patient.getCrashSeverity(),
					patient.getReportingAgencyName(),
					patient.getNumberOfUnits(), patient.getUnitInError(),
					patient.getCountry(), patient.getCityVillageTownship(),
					patient.getCrashDate(),
					InjuryConstants.convertAMPMTime(patient.getTimeOfCrash()
							.toString()), patient.getUnitNumber(),
					patient.getName(), patient.getDateOfBirth(),
					patient.getGender(), patient.getAddress(),
					patient.getPhoneNumber(), patient.getInjuries(),
					patient.getEmsAgency(), patient.getMedicalFacility(),
					patient.getCrashReportFileName(),
					patient.getPatientStatus());
			patientForm.setLatitude(patient.getLatitude());
			patientForm.setLongitude(patient.getLongitude());
			patientForm.setCallerName(callerName);
			patientForm.setClinicName(clinicName);
			patientForm.setDoctorName(doctorName);

		}
*/
		// End

		return patientForm;
	}

	// Update an Entry
	public int updatePatient(PatientForm patientForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		String latLong = geoLocation.getLocation(patientForm.getAddress());
		double longitude = 0.0;
		double latitude = 0.0;
		if (!latLong.equals("NONE")) {
			String[] latitudeLongitude = latLong.split(",");
			latitude = Double.parseDouble(latitudeLongitude[0]);
			longitude = Double.parseDouble(latitudeLongitude[1]);
		}
		
		Patient patient= new Patient();
		patientDAO.update(patient);

		return 1;
	}

	public int savePatient(PatientForm patientForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Caller caller = null;
		

		String latLong = geoLocation.getLocation(patientForm.getAddress());

		double longitude = 0.0;
		double latitude = 0.0;
		if (!latLong.equals("NONE")) {
			String[] latitudeLongitude = latLong.split(",");
			latitude = Double.parseDouble(latitudeLongitude[0]);
			longitude = Double.parseDouble(latitudeLongitude[1]);
		}

		Patient patient = new Patient();
			patientDAO.save(patient);

		return 1;
	}

	// Delete an Entry
	public int deletePatient(Integer id) {
		List<Appointments> appointments = patientDAO
				.getAppointmentsListByPatientId(id);
		List<CallLog> callLogs = patientDAO.getCallLogsListByPatientId(id);
		if (appointments.size() == 0 && callLogs.size() == 0) {
			System.out.println(appointments.size());
			System.out.println(callLogs.size());
			patientDAO.delete(id);

		}

		

		return 1;
	}

	
  public Integer getNoOfPatients() {
		List<Patient> patients = new ArrayList<Patient>();

		String role = callerService.getCurrentRole();
		if (role == InjuryConstants.INJURY_CALLER_ADMIN_ROLE) {
			patients = patientDAO.getAll();
		} else if (role == InjuryConstants.INJURY_CALLER_ROLE) {
			Integer userId = callerService.getCurrentUserId();
			// get Caller Id
			Integer callerId = callerDAO.getByUserId(userId).getCallerId();
			patients = patientDAO.getPatientListByCallerId(callerId);
		}

		return patients.size();
	}

	public Integer releasePatientFromCaller(Integer id) {
		List<Patient> patient = patientDAO.getPatientListByCallerId(id);
		for (Patient patients : patient) {
		/*	int patientId = patients.getId();
			System.out.println("patientid:" + patientId);
			patientDAO.releasePatientFromCaller(patientId);*/

		}
		return 1;

	}

	// Remove More than One White Space
	public String replaceWithWhiteSpacePattern(String str, String replace) {

		Pattern ptn = Pattern.compile("\\s+");
		Matcher mtch = ptn.matcher(str);
		return mtch.replaceAll(replace);
	}

	public List<PatientForm> getPatientByStatus(Integer patientStatus) {
		List<PatientForm> patientForms = new ArrayList<PatientForm>();

		List<Patient> patients = new ArrayList<Patient>();

		String role = callerService.getCurrentRole();
		if (role == InjuryConstants.INJURY_CALLER_ADMIN_ROLE) {
			patients = patientDAO.getPatientByStatus(patientStatus);
		}else if (role == InjuryConstants.INJURY_CALLER_ROLE) {
			Integer userId = callerService.getCurrentUserId();
			// get Caller Id
			Integer callerId = callerDAO.getByUserId(userId).getCallerId();
			patients = callerDAO.getPatientStatus(patientStatus, callerId);
		}

		for (Patient patient : patients) {
			// TODO: Fill the List
			patientForms.add(this.getPatientFormDetails(patient));
		}

		return patientForms;

	}

	
	public Integer activeStatusByPatientId(Integer id) {
		Integer status = 1;
		patientDAO.activeStatusByPatientId(id);
		return status;
	}

	// Get Patient List By Page Limit
	public List<PatientForm> getPatientByLimit(Integer pageNumber,
			Integer itemsPerPage, String name, String phoneNumber,
			String localReportNumber, String callerName) {
		List<Patient> patients = new ArrayList<Patient>();
		List<PatientForm> patientForms = new ArrayList<PatientForm>();
		patients = patientDAO.getPatientListByLimit(pageNumber, itemsPerPage,
				name, phoneNumber, localReportNumber, callerName);

		for (Patient patient : patients) {
			patientForms.add(this.getPatientFormDetails(patient));
		}

		return patientForms;
	}

	// Get Total Patient Count By Page Limit
	public Integer getTotalPatient(String name, String phoneNumber,
			String localReportNumber, String callerName) {
		Integer count = 0;
		if (name.equals("")&&phoneNumber.equals("")&&localReportNumber.equals("")&&callerName.equals("")) {
			count = this.getPatientList().size();
		} else {
			count = patientDAO.getTotalPatientCount(name, phoneNumber,
					localReportNumber, callerName);
		}
		return count;
	}
}
