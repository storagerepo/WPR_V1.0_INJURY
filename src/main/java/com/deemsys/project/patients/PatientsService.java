package com.deemsys.project.patients;

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

import com.deemsys.project.entity.Patients;
import com.deemsys.project.Appointments.AppointmentsDAO;
import com.deemsys.project.CallLogs.CallLogsDAO;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Map.GeoLocation;
import com.deemsys.project.Staff.StaffDAO;
import com.deemsys.project.Staff.StaffService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Clinics;
import com.deemsys.project.entity.Doctors;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;

/**
 * 
 * @author Deemsys
 * 
 *         Patients - Entity patients - Entity Object patientss - Entity List
 *         patientsDAO - Entity DAO patientsForms - EntityForm List PatientsForm
 *         - EntityForm
 * 
 */
@Service
@Transactional
public class PatientsService {

	@Autowired
	PatientsDAO patientsDAO;
	@Autowired
	CallLogsDAO callLogsDAO;
	@Autowired
	ClinicsDAO clinicsDAO;
	@Autowired
	StaffDAO staffDAO;
	@Autowired
	PDFCrashReportReader crashReportReader;

	@Autowired
	StaffService staffService;
	@Autowired
	AppointmentsDAO appointmentsDAO;

	@Autowired
	GeoLocation geoLocation;

	/*
	 * @Autowired PatientsFileRead patientsFileRead;
	 */

	// Get All Entries
	public List<PatientsForm> getPatientsList() {
		List<PatientsForm> patientsForms = new ArrayList<PatientsForm>();

		List<Patients> patientss = new ArrayList<Patients>();

		String role = staffService.getCurrentRole();
		if (role == "ROLE_ADMIN") {
			patientss = patientsDAO.getAll();
		}else if (role == InjuryConstants.INJURY_STAFF_ROLE) {
			Integer userId = staffService.getCurrentUserId();
			// get Staff Id
			Integer staffId = staffDAO.getByUserId(userId).getId();
			patientss=staffDAO.getPatientsByAccessToken(staffId);
		}else{
			patientss = patientsDAO.getAll();
		}

		for (Patients patients : patientss) {
			// TODO: Fill the List
			patientsForms.add(this.getPatientFormDetails(patients));
		}

		return patientsForms;
	}

	// Add to patients to Patient Forms
	public PatientsForm getPatientFormDetails(Patients patients){
	
		Integer staffId = 0, clinicId = 0, doctorId = 0;
		String staffName = "N/A";
		String clinicName = "N/A";
		String doctorName = "N/A";
		staffName = "N/A";
		clinicName = "N/A";
		doctorName = "N/A";
		if (patients.getStaff() != null) {
			staffId = patients.getStaff().getId();
			staffName = patients.getStaff().getFirstName() + " "
					+ patients.getStaff().getLastName();
		}
		if (patients.getClinics() != null) {
			clinicId = patients.getClinics().getClinicId();
			clinicName = patients.getClinics().getClinicName();
		}
		if (patients.getDoctors() != null) {
			doctorId = patients.getDoctors().getId();
			doctorName = patients.getDoctors().getDoctorName();
		}

		PatientsForm patientsForm = new PatientsForm(patients.getId(),
				staffId, clinicId, doctorId,
				patients.getLocalReportNumber(),
				patients.getCrashSeverity(),
				patients.getReportingAgencyName(),
				patients.getNumberOfUnits(), patients.getUnitInError(),
				patients.getCountry(), patients.getCityVillageTownship(),
				patients.getCrashDate(), patients.getTimeOfCrash()
						.toString(), patients.getUnitNumber(),
				patients.getName(), patients.getDateOfBirth(),
				patients.getGender(), patients.getAddress(),
				patients.getPhoneNumber(), patients.getInjuries(),
				patients.getEmsAgency(), patients.getMedicalFacility(),
				patients.getCrashReportFileName(),
				patients.getPatientStatus());
		patientsForm.setCallerName(staffName);
		patientsForm.setClinicName(clinicName);
		patientsForm.setDoctorName(doctorName);
		
		return patientsForm;
	}
	
	// Get Particular Entry
	public PatientsForm getPatients(Integer getId) {
		Patients patients = new Patients();

		patients = patientsDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start
		PatientsForm patientsForm = new PatientsForm();
		
		patientsForm = this.getPatientFormDetails(patients);
		// End
		
		return patientsForm;
	}

	// Get Particular Entry
	public PatientsForm getPatientWithLatLong(Integer getId) {
		Patients patients = new Patients();

		patients = patientsDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start
		PatientsForm patientsForm = new PatientsForm();
		Integer staffId = 0, clinicId = 0, doctorId = 0;
		String staffName = "N/A";
		String clinicName = "N/A";
		String doctorName = "N/A";
		if (patients != null) {
			staffId = 0;
			clinicId = 0;
			doctorId = 0;
			if (patients.getStaff() != null) {
				staffId = patients.getStaff().getId();
				staffName = patients.getStaff().getFirstName() + " "
						+ patients.getStaff().getLastName();
			}
			if (patients.getClinics() != null) {
				clinicId = patients.getClinics().getClinicId();
				clinicName = patients.getClinics().getClinicName();
			}
			if (patients.getDoctors() != null) {
				doctorId = patients.getDoctors().getId();
				doctorName = patients.getDoctors().getDoctorName();
			}

			patientsForm = new PatientsForm(patients.getId(), staffId,
					clinicId, doctorId, patients.getLocalReportNumber(),
					patients.getCrashSeverity(),
					patients.getReportingAgencyName(),
					patients.getNumberOfUnits(), patients.getUnitInError(),
					patients.getCountry(), patients.getCityVillageTownship(),
					patients.getCrashDate(),
					InjuryConstants.convertAMPMTime(patients.getTimeOfCrash()
							.toString()), patients.getUnitNumber(),
					patients.getName(), patients.getDateOfBirth(),
					patients.getGender(), patients.getAddress(),
					patients.getPhoneNumber(), patients.getInjuries(),
					patients.getEmsAgency(), patients.getMedicalFacility(),
					patients.getCrashReportFileName(),
					patients.getPatientStatus());
			patientsForm.setLatitude(patients.getLatitude());
			patientsForm.setLongitude(patients.getLongitude());
			patientsForm.setCallerName(staffName);
			patientsForm.setClinicName(clinicName);
			patientsForm.setDoctorName(doctorName);

		}

		// End

		return patientsForm;
	}

	// Update an Entry
	public int updatePatients(PatientsForm patientsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Staff staff = null;
		Clinics clinics = null;
		Doctors doctors = null;
		if (patientsForm.getCallerId() != null)
			if (patientsForm.getCallerId() != 0) {
				staff = new Staff();
				staff.setId(patientsForm.getCallerId());
			}
		if (patientsForm.getClinicId() != null)
			if (patientsForm.getClinicId() != 0) {
				clinics = new Clinics();
				clinics.setClinicId(patientsForm.getClinicId());
			}
		if (patientsForm.getDoctorId() != null)
			if (patientsForm.getDoctorId() != 0) {
				doctors = new Doctors();
				doctors.setId(patientsForm.getDoctorId());
			}

		String latLong = geoLocation.getLocation(patientsForm.getAddress());
		double longitude = 0.0;
		double latitude = 0.0;
		if (!latLong.equals("NONE")) {
			String[] latitudeLongitude = latLong.split(",");
			latitude = Double.parseDouble(latitudeLongitude[0]);
			longitude = Double.parseDouble(latitudeLongitude[1]);
		}

		Patients patients = new Patients(staff, clinics, doctors,
				patientsForm.getLocalReportNumber(),
				patientsForm.getCrashSeverity(),
				patientsForm.getReportingAgencyName(),
				patientsForm.getNumberOfUnits(), patientsForm.getUnitInError(),
				patientsForm.getCountry(),
				patientsForm.getCityVillageTownship(),
				patientsForm.getCrashDate(),
				InjuryConstants.convert24HourTime(patientsForm.getTimeOfCrash()
						.toString()), patientsForm.getUnitNumber(),
				patientsForm.getName(), patientsForm.getDateOfBirth(),
				patientsForm.getGender(), patientsForm.getAddress(), latitude,
				longitude, patientsForm.getPhoneNumber(),
				patientsForm.getInjuries(), patientsForm.getEmsAgency(),
				patientsForm.getMedicalFacility(),
				patientsForm.getCrashReportFileName(),
				patientsForm.getPatientStatus());

		patients.setId(patientsForm.getId());
		Integer doctorId = patientsForm.getDoctorId();

		// Logic Ends

		if (doctorId == null) {
			patientsDAO.update(patients);

		} else if (doctorId == 0) {
			patients.setPatientStatus(1);
			patientsDAO.update(patients);

		} else {
			patients.setPatientStatus(2);
			patientsDAO.update(patients);

		}
		return 1;
	}

	public int savePatients(PatientsForm patientsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Staff staff = null;
		Clinics clinics = null;
		Doctors doctors = null;
		if (patientsForm.getCallerId() != null)
			if (patientsForm.getCallerId() != 0) {
				staff = new Staff();
				staff.setId(patientsForm.getCallerId());
			}
		if (patientsForm.getClinicId() != null)
			if (patientsForm.getClinicId() != 0) {
				clinics = new Clinics();
				clinics.setClinicId(patientsForm.getClinicId());
			}
		if (patientsForm.getDoctorId() != null)
			if (patientsForm.getDoctorId() != 0) {
				doctors = new Doctors();
				doctors.setId(patientsForm.getDoctorId());
			}

		String latLong = geoLocation.getLocation(patientsForm.getAddress());

		double longitude = 0.0;
		double latitude = 0.0;
		if (!latLong.equals("NONE")) {
			String[] latitudeLongitude = latLong.split(",");
			latitude = Double.parseDouble(latitudeLongitude[0]);
			longitude = Double.parseDouble(latitudeLongitude[1]);
		}

		Patients patients = new Patients(staff, clinics, doctors,
				patientsForm.getLocalReportNumber(),
				patientsForm.getCrashSeverity(),
				patientsForm.getReportingAgencyName(),
				patientsForm.getNumberOfUnits(), patientsForm.getUnitInError(),
				patientsForm.getCountry(),
				patientsForm.getCityVillageTownship(),
				patientsForm.getCrashDate(), patientsForm.getTimeOfCrash()
						.toString(), patientsForm.getUnitNumber().trim(),
				patientsForm.getName(), patientsForm.getDateOfBirth(),
				patientsForm.getGender(), patientsForm.getAddress(), latitude,
				longitude, patientsForm.getPhoneNumber(),
				patientsForm.getInjuries(), patientsForm.getEmsAgency(),
				patientsForm.getMedicalFacility(),
				patientsForm.getCrashReportFileName(), 1);

		if (patientsForm.getDoctorId() == null) {
			patients.setPatientStatus(1);
			patientsDAO.save(patients);

		} else if (patientsForm.getDoctorId() == 0) {
			patients.setPatientStatus(1);
			patientsDAO.save(patients);

		} else {
			patients.setPatientStatus(2);
			patientsDAO.save(patients);

		}

		return patients.getId();
	}

	// Delete an Entry
	public int deletePatients(Integer id) {
		List<Appointments> appointments = patientsDAO
				.getAppointmentsListByPatientsId(id);
		List<CallLogs> callLogs = patientsDAO.getCallLogsListByPatientsId(id);
		if (appointments.size() == 0 && callLogs.size() == 0) {
			System.out.println(appointments.size());
			System.out.println(callLogs.size());
			patientsDAO.delete(id);

		}

		else {
			for (Appointments appointmentss : appointments) {
				Integer appointmentsId = appointmentss.getId();
				appointmentsDAO.delete(appointmentsId);
			}
			for (CallLogs callLogss : callLogs) {
				Integer callerId = callLogss.getId();
				callLogsDAO.delete(callerId);
			}
			patientsDAO.delete(id);

		}

		return 1;
	}

	
  public Integer getNoOfPatientss() {
		List<Patients> patientss = new ArrayList<Patients>();

		String role = staffService.getCurrentRole();
		if (role == InjuryConstants.INJURY_ADMIN_ROLE) {
			patientss = patientsDAO.getAll();
		} else if (role == InjuryConstants.INJURY_STAFF_ROLE) {
			Integer userId = staffService.getCurrentUserId();
			// get Staff Id
			Integer staffId = staffDAO.getByUserId(userId).getId();
			patientss = patientsDAO.getPatientListByStaffId(staffId);
		}

		return patientss.size();
	}

	public Integer releasePatientsFromStaff(Integer id) {
		List<Patients> patients = patientsDAO.getPatientListByStaffId(id);
		for (Patients patientss : patients) {
			int patientsId = patientss.getId();
			System.out.println("patientsid:" + patientsId);
			patientsDAO.releasePatientsFromStaff(patientsId);

		}
		return 1;

	}

	// Remove More than One White Space
	public String replaceWithWhiteSpacePattern(String str, String replace) {

		Pattern ptn = Pattern.compile("\\s+");
		Matcher mtch = ptn.matcher(str);
		return mtch.replaceAll(replace);
	}

	public List<PatientsForm> getPatientsByStatus(Integer patientStatus) {
		List<PatientsForm> patientsForms = new ArrayList<PatientsForm>();

		List<Patients> patientss = new ArrayList<Patients>();

		String role = staffService.getCurrentRole();
		if (role == "ROLE_ADMIN") {
			patientss = patientsDAO.getPatientsByStatus(patientStatus);
		}else if (role == InjuryConstants.INJURY_STAFF_ROLE) {
			Integer userId = staffService.getCurrentUserId();
			// get Staff Id
			Integer staffId = staffDAO.getByUserId(userId).getId();
			patientss = staffDAO.getPatientStatus(patientStatus, staffId);
		}

		for (Patients patients : patientss) {
			// TODO: Fill the List
			patientsForms.add(this.getPatientFormDetails(patients));
		}

		return patientsForms;

	}

	
	public Integer activeStatusByPatientId(Integer id) {
		Integer status = 1;
		patientsDAO.activeStatusByPatientId(id);
		return status;
	}

	// Get Patients List By Page Limit
	public List<PatientsForm> getPatientsByLimit(Integer pageNumber,
			Integer itemsPerPage, String name, String phoneNumber,
			String localReportNumber, String callerName) {
		List<Patients> patientss = new ArrayList<Patients>();
		List<PatientsForm> patientsForms = new ArrayList<PatientsForm>();
		patientss = patientsDAO.getPatientListByLimit(pageNumber, itemsPerPage,
				name, phoneNumber, localReportNumber, callerName);

		for (Patients patients : patientss) {
			patientsForms.add(this.getPatientFormDetails(patients));
		}

		return patientsForms;
	}

	// Get Total Patients Count By Page Limit
	public Integer getTotalPatients(String name, String phoneNumber,
			String localReportNumber, String callerName) {
		Integer count = 0;
		if (name.equals("")&&phoneNumber.equals("")&&localReportNumber.equals("")&&callerName.equals("")) {
			count = this.getPatientsList().size();
		} else {
			count = patientsDAO.getTotalPatientsCount(name, phoneNumber,
					localReportNumber, callerName);
		}
		return count;
	}
}
