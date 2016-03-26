package com.deemsys.project.CallLogs;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Appointments.AppointmentsDAO;
import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.Caller.CallerDAO;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminDAO;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.PatientCallerMap.PatientCallerDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.entity.PatientCallerAdminMapId;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.PatientDAO;

/**
 * 
 * @author Deemsys
 * 
 *         CallLogs - Entity callLogs - Entity Object callLogss - Entity List
 *         callLogsDAO - Entity DAO callLogsForms - EntityForm List CallLogsForm
 *         - EntityForm
 * 
 */
@Service
@Transactional
public class CallLogsService {

	@Autowired
	CallLogsDAO callLogsDAO;

	@Autowired
	CallerDAO callerDAO;

	@Autowired
	CallerService callerService;

	@Autowired
	PatientDAO patientDAO;

	@Autowired
	AppointmentsDAO appointmentsDAO;

	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	CallerAdminDAO callerAdminDAO;

	@Autowired
	LoginService loginService;
	
	@Autowired
	PatientCallerDAO patientCallerDAO;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	// Get All Entries
	public List<CallLogsForm> getCallLogsList() {
		List<CallLogsForm> callLogsForms = new ArrayList<CallLogsForm>();

		List<CallLog> callLogss = new ArrayList<CallLog>();

		callLogss = callLogsDAO.getAll();

		for (CallLog callLogs : callLogss) {
			/*// TODO: Fill the List
			CallLogsForm callLogsForm = new CallLogsForm(callLogs.getId(),
					callLogs.getPatient().getId(),
					InjuryConstants.convertUSAFormatWithTime(callLogs
							.getTimeStamp()), callLogs.getResponse(),
					callLogs.getNotes());
			callLogsForms.add(callLogsForm);*/
		}

		return callLogsForms;
	}

	// Get Particular Entry
	public CallLogsForm getCallLogsWithAppointment(Long callLogId,Long appointmentId) {
		CallLog callLogs = new CallLog();

		callLogs = callLogsDAO.getCallLogsByCallLogId(callLogId);
		Appointments appointments=appointmentsDAO.getAppointmentsByAppintementId(appointmentId);
		AppointmentsForm appointmentsForm=new AppointmentsForm();
		CallLogsForm callLogsForm = new CallLogsForm();
		// TODO: Convert Entity to Form
		// Start
		try {
			if(appointments!=null){
				appointmentsForm=new AppointmentsForm(appointments.getAppointmentId(), "", "", appointments.getScheduledDateTime(), appointments.getNotes(), appointments.getStatus(), appointments.getCallLog().getCallLogId(), appointments.getClinic().getClinicId(), appointments.getDoctorId(), "", "");
			}
			callLogsForm = new CallLogsForm(callLogs.getCallLogId(),callLogs.getPatientCallerAdminMap().getId().getPatientId(), callLogs.getPatientCallerAdminMap().getCallerAdmin().getCallerAdminId(), callLogs.getTimeStamp(), callLogs.getResponse(), callLogs.getNotes(), callLogs.getStatus(), appointmentsForm, "", "");
					
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return callLogsForm;
	}

	// Get Call Logs Only
	// Get Particular Entry
		public CallLogsForm getCallLogs(Long callLogId) {
			CallLog callLogs = new CallLog();

			callLogs = callLogsDAO.getCallLogsByCallLogId(callLogId);
			AppointmentsForm appointmentsForm=new AppointmentsForm();
			CallLogsForm callLogsForm = new CallLogsForm();
			// TODO: Convert Entity to Form
			// Start
			try {
				callLogsForm = new CallLogsForm(callLogs.getCallLogId(),callLogs.getPatientCallerAdminMap().getId().getPatientId(), callLogs.getPatientCallerAdminMap().getCallerAdmin().getCallerAdminId(), callLogs.getTimeStamp(), callLogs.getResponse(), callLogs.getNotes(), callLogs.getStatus(), appointmentsForm, "", "");
						
			
			} catch (Exception e) {
				e.printStackTrace();
			}

			return callLogsForm;
		}
	
	public List<CallLogsForm> getCallLogsFormsByUser(String patientId){
		
		String role=loginService.getCurrentRole();
		Integer callerId=0;
		Integer callerAdminId=0;
		if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			Caller caller=callerService.getCallerByUserId(loginService.getCurrentUserID());
			callerId=caller.getCallerId();
			callerAdminId=caller.getCallerAdmin().getCallerAdminId();
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			callerAdminId=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId();
		}
		List<CallLogsForm> callLogsForms=callLogsDAO.getCallLogsByPatientIdAndCallerAdminIdAndCallerId(patientId, callerAdminId, callerId);
		
		return callLogsForms;
	}
	
	// Merge an Entry (Save or Update)
	public int mergeCallLogs(CallLogsForm callLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Patient patient = new Patient();

		
		Users users = usersDAO.get(callerService.getCurrentUserId());
		// Caller Admin
		CallerAdmin callerAdmin = new CallerAdmin();
		// Caller
		Caller caller=new Caller();
				
		PatientCallerAdminMapId patientCallerAdminMapId=new PatientCallerAdminMapId();
		PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap(patientCallerAdminMapId, callerAdmin, caller, patient, "", 0, 1, null, null);
		
		CallLog callLogs = new CallLog(patientCallerAdminMap, caller,
				callLogsForm.getTimeStamp(), callLogsForm.getResponse(),
				callLogsForm.getNotes(),1,null);
		callLogs.setCallLogId(callLogsForm.getCallLogId());
		// Logic Ends

		callLogsDAO.merge(callLogs);
		return 1;
	}

	// Save an Entry
	public int saveCallLogs(CallLogsForm callLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Patient patient = new Patient();
		patient.setPatientId(callLogsForm.getPatientId());
		// Caller
		Caller caller=callerService.getCallerByUserId(loginService.getCurrentUserID());
		// Caller Admin
		CallerAdmin callerAdmin = callerAdminDAO.get(caller.getCallerAdmin().getCallerAdminId());
		
						
		PatientCallerAdminMapId patientCallerAdminMapId=new PatientCallerAdminMapId(callLogsForm.getPatientId(), callerAdmin.getCallerAdminId());
		PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap();
		patientCallerAdminMap.setId(patientCallerAdminMapId);
		patientCallerAdminMap.setPatientStatus(callLogsForm.getResponse());
		patientCallerAdminMap.setCallerAdmin(callerAdmin);
		patientCallerAdminMap.setCaller(caller);
		patientCallerDAO.merge(patientCallerAdminMap);
		
		CallLog callLogs = new CallLog(patientCallerAdminMap,caller,callLogsForm.getTimeStamp(), callLogsForm.getResponse(),
				callLogsForm.getNotes(),1,null);

		callLogsDAO.save(callLogs);
		
		
		if(callLogsForm.getResponse()==4){
			Clinic clinic=new Clinic();
			clinic.setClinicId(callLogsForm.getAppointmentsForm().getClinicId());
			Appointments appointments=new Appointments(callLogs, InjuryConstants.convertDateFromDateAndTime(callLogsForm.getAppointmentsForm().getScheduledDateTime()),callLogsForm.getAppointmentsForm().getScheduledDateTime(), "", 1,clinic,callLogsForm.getAppointmentsForm().getDoctorId());
			appointmentsDAO.save(appointments);
		}
		
		// Logic Ends

		return 1;
	}

	// Update an Entry
	public int updateCallLogs(CallLogsForm callLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		CallLog callLogs = new CallLog();
		Patient patient = new Patient();
		patient.setPatientId(callLogsForm.getPatientId());
		// Caller
		Caller caller=callerService.getCallerByUserId(loginService.getCurrentUserID());
		// Caller Admin
		CallerAdmin callerAdmin = callerAdminDAO.get(caller.getCallerAdmin().getCallerAdminId());
							
		PatientCallerAdminMapId patientCallerAdminMapId=new PatientCallerAdminMapId(callLogsForm.getPatientId(), callerAdmin.getCallerAdminId());
		PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap();
		patientCallerAdminMap.setId(patientCallerAdminMapId);
		patientCallerAdminMap.setPatientStatus(callLogsForm.getResponse());
		patientCallerAdminMap.setCaller(caller);
		
		callLogs = new CallLog(patientCallerAdminMap, caller,callLogsForm.getTimeStamp(), callLogsForm.getResponse(),
					callLogsForm.getNotes(),1,null);
			callLogs.setCallLogId(callLogsForm.getCallLogId());
			
		 callLogsDAO.update(callLogs);
		patientCallerDAO.merge(patientCallerAdminMap);
		
		if(callLogsForm.getResponse()==4){
			Clinic clinic=new Clinic();
			clinic.setClinicId(callLogsForm.getAppointmentsForm().getClinicId());
			Appointments appointments=new Appointments(callLogs, InjuryConstants.convertDateFromDateAndTime(callLogsForm.getAppointmentsForm().getScheduledDateTime()),callLogsForm.getAppointmentsForm().getScheduledDateTime(), "", 1,clinic,callLogsForm.getAppointmentsForm().getDoctorId());
			appointments.setAppointmentId(callLogsForm.getAppointmentsForm().getId());
			appointmentsDAO.save(appointments);
		}else{
			appointmentsDAO.deleteAppointmentsByAppointmentId(callLogsForm.getAppointmentsForm().getId());
		}
		
		// Logic Ends
		return 1;
	}

	// Delete an Entry
	public int deleteCallLogs(Long callLogsId) {
		
		appointmentsDAO.deleteAppointmentsByCalllogId(callLogsId);
		callLogsDAO.deleteCallLog(callLogsId);
		return 1;
	}

	public CallLogsForm getCallLogsByAppointment(Integer appointmentId) {

		CallLogsForm callLogsForm = new CallLogsForm();
		CallLog callLogs = callLogsDAO.getCallLogsByAppointment(appointmentId);
		
		// Call Logs Form Need to add

		return callLogsForm;
	}

	public List<CallLogsForm> getCallLogsByPatientId(Integer patientId) {

		List<CallLogsForm> callLogsForm = new ArrayList<CallLogsForm>();
		List<CallLog> callLogs = new ArrayList<CallLog>();
		callLogs = callLogsDAO.getCallLogsByPatientsId(patientId);
		CallLogsForm callLogsForms = new CallLogsForm();
		for (CallLog callLogss : callLogs) {
			
			callLogsForm.add(callLogsForms);

		}
		return callLogsForm;

	}


}
