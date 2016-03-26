package com.deemsys.project.Appointments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.Patient;
import com.deemsys.project.CallLogs.CallLogsDAO;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Doctors.DoctorsDAO;
import com.deemsys.project.Caller.CallerDAO;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Doctor;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.PatientDAO;
import com.deemsys.project.patient.PatientForm;

/**
 * 
 * @author Deemsys
 * 
 *         Appointments - Entity appointments - Entity Object appointmentss -
 *         Entity List appointmentsDAO - Entity DAO appointmentsForms -
 *         EntityForm List AppointmentsForm - EntityForm
 * 
 */
@Service
@Transactional
public class AppointmentsService {

	@Autowired
	AppointmentsDAO appointmentsDAO;

	@Autowired
	ClinicsDAO clinicsDAO;

	@Autowired
	PatientDAO patientDAO;

	@Autowired
	CallerService callerService;

	@Autowired
	CallLogsDAO callLogsDAO;

	@Autowired
	DoctorsDAO doctorsDAO;

	@Autowired
	CallerDAO callerDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CallerAdminService callerAdminService;

	// Get All Entries
	public List<AppointmentsForm> getAppointmentsList() {
		List<AppointmentsForm> appointmentsForms = new ArrayList<AppointmentsForm>();

		List<Appointments> appointmentss = new ArrayList<Appointments>();

		appointmentss = appointmentsDAO.getAll();

		for (Appointments appointments : appointmentss) {
			// TODO: Fill the List
		/*	AppointmentsForm appointmentsForm = new AppointmentsForm(
					appointments.getAppointmentId(), appointments.getPatient().getId(),
					appointments.getPatient().getName(),
					InjuryConstants.convertMonthFormat(appointments
							.getScheduledDate()), appointments.getNotes(),
					appointments.getStatus());
			appointmentsForms.add(appointmentsForm);*/
		}

		return appointmentsForms;
	}

	// Get Particular Entry
	public AppointmentsForm getAppointments(Long appointmentId) {
		Appointments appointments = new Appointments();

		appointments = appointmentsDAO.getAppointmentsByAppintementId(appointmentId);

		// TODO: Convert Entity to Form
		// Start
		AppointmentsForm appointmentsForm = new AppointmentsForm();
		

		// End

		return appointmentsForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeAppointments(AppointmentsForm appointmentsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Patient patient = new Patient();
		Appointments appointments = new Appointments();
		/*patient.setId(appointmentsForm.getPatientId());

		Appointments appointments = new Appointments(patient,
				InjuryConstants.convertYearFormat(appointmentsForm
						.getScheduledDate()), appointmentsForm.getNotes(),
				appointmentsForm.getStatus(), null);
		appointments.setId(appointmentsForm.getId());*/
		// Logic Ends

		appointmentsDAO.merge(appointments);
		return 1;
	}

	// Save an Entry
	public int saveAppointments(AppointmentsForm appointmentsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		//Patient patient = patientDAO.get(appointmentsForm.getPatientId());
		
		// Logic Ends
		//patientDAO.update(patient);

	//	patientDAO.updatePatientStatus(appointmentsForm.getPatientId());
		return 1;
	}


	// Update an Entry
	public int updateAppointments(AppointmentsForm appointmentsForm) {


		Appointments appointments = new Appointments();		
		appointmentsDAO.update(appointments);
		return 1;
	}

	// Delete an Entry
	public int deleteAppointments(Integer id) {
		appointmentsDAO.delete(id);
		return 1;
	}


	public AppointmentsSearchResult searchAppointments(AppointmentSearchForm appointmentSearchForm) {
		appointmentSearchForm.setCallerAdminId(callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId());
		AppointmentsSearchResult appointmentsSearchResult=appointmentsDAO.searchAppointments(appointmentSearchForm);
		return appointmentsSearchResult;

	}


	public Integer getNoOfAppointments() {
		
		Integer callerAdminId=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId();
		return appointmentsDAO.getAppointmentsCount(callerAdminId);

	}

	public List<AppointmentsForm> getAppointmentListByCallerId(Integer callerId) {
		// TODO Auto-generated method stub
		List<AppointmentsForm> appointmentsForms = new ArrayList<AppointmentsForm>();

		appointmentsForms = patientDAO.getAppointmentListByCallerId(callerId);

		return appointmentsForms;

	}

	public Integer removeAppointment(Integer appointmentId) {
		// TODO Auto-generated method stub
		Integer status = 0;
		CallLog callLogs = callLogsDAO.getCallLogsByAppointment(appointmentId);;
		callLogsDAO.update(callLogs);

		appointmentsDAO.delete(appointmentId);

		// Change Patient Status
		status = 1;

		return status;

	}
	
	// Change Appointment Status
	public void changeAppointmentStatus(Long appointmentId,Integer status){
		appointmentsDAO.changeAppointmentStatus(appointmentId, status);
	}
	
	// get Appointment Full Details By AppointmentId
	public AppointmentsForm getAppointmentsByWithFullDetails(Long appointmentId){
		return appointmentsDAO.getAppointmentsByAppointmentIdWithFullDetails(appointmentId);
	}
}
