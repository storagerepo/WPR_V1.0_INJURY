package com.deemsys.project.CallLogs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Appointments.AppointmentsDAO;
import com.deemsys.project.Caller.CallerDAO;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.PatientCallerAdminMap;
import com.deemsys.project.entity.PatientCallerAdminMapId;
import com.deemsys.project.entity.Users;
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
	public CallLogsForm getCallLogs(Integer getId) {
		CallLog callLogs = new CallLog();

		callLogs = callLogsDAO.get(getId);
		CallLogsForm callLogsForm = new CallLogsForm();
		// TODO: Convert Entity to Form
		// Start
		try {
			/*if (callLogs.getAppointments() == null) {
				callLogsForm = new CallLogsForm(callLogs.getId(), callLogs
						.getPatient().getId(),
						InjuryConstants.convertUSAFormatWithTime(callLogs
								.getTimeStamp()), callLogs.getResponse(),
						callLogs.getNotes());
			} else {
				callLogsForm = new CallLogsForm(callLogs.getId(), callLogs
						.getPatient().getId(), callLogs.getAppointments()
						.getId(),
						InjuryConstants.convertUSAFormatWithTime(callLogs
								.getTimeStamp()), callLogs.getResponse(),
						callLogs.getNotes());

			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		return callLogsForm;
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
		
		CallLog callLogs = new CallLog(patientCallerAdminMap,
				InjuryConstants.convertYearFormatWithTime(callLogsForm
						.getTimeStamp()), callLogsForm.getResponse(),
				callLogsForm.getNotes(),1,null);
		callLogs.setCallLogId(callLogsForm.getId());
		// Logic Ends

		callLogsDAO.merge(callLogs);
		return 1;
	}

	// Save an Entry
	public int saveCallLogs(CallLogsForm callLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Patient patient = patientDAO.get(callLogsForm.getPatientId());
		// Caller Admin
		CallerAdmin callerAdmin = new CallerAdmin();
		// Caller
		Caller caller=new Caller();
						
		PatientCallerAdminMapId patientCallerAdminMapId=new PatientCallerAdminMapId();
		PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap(patientCallerAdminMapId, callerAdmin, caller, patient, "", 0, 1, null, null);
				
		Users users = usersDAO.get(callerService.getCurrentUserId());

		CallLog callLogs = new CallLog(patientCallerAdminMap,
				InjuryConstants.convertYearFormatWithTime(callLogsForm
						.getTimeStamp()), callLogsForm.getResponse(),
				callLogsForm.getNotes(),1,null);

		Integer status = callLogsForm.getResponse();
		if (status==3) {
			patient.setPatientStatus(3);
			patientDAO.update(patient);

			callLogsDAO.save(callLogs);
		} else {
			if (patient.getPatientStatus() == 2) {
				patient.setPatientStatus(2);
			} else {
				patient.setPatientStatus(1);
			}
			patientDAO.update(patient);
			callLogsDAO.save(callLogs);
		}
		// Logic Ends

		return 1;
	}

	// Update an Entry
	public int updateCallLogs(CallLogsForm callLogsForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Patient patient = patientDAO.get(callLogsForm.getPatientId());

		Appointments appointments = new Appointments();
		CallLog callLogs = new CallLog();
		// Caller Admin
					CallerAdmin callerAdmin = new CallerAdmin();
					// Caller
					Caller caller=new Caller();
									
					PatientCallerAdminMapId patientCallerAdminMapId=new PatientCallerAdminMapId();
					PatientCallerAdminMap patientCallerAdminMap=new PatientCallerAdminMap(patientCallerAdminMapId, callerAdmin, caller, patient, "", 0, 1, null, null);
		if (callLogsForm.getAppointmentId() != null) {

			Users users = usersDAO.get(callerService.getCurrentUserId());
			callLogs = new CallLog(patientCallerAdminMap,
					InjuryConstants.convertYearFormatWithTime(callLogsForm
							.getTimeStamp()), callLogsForm.getResponse(),
					callLogsForm.getNotes(),1,null);
			callLogs.setCallLogId(callLogsForm.getId());
			Integer status = callLogsForm.getResponse();
			if (status==3) {
				patient.setPatientStatus(3);
				patientDAO.update(patient);
				callLogsDAO.update(callLogs);
			} else {
				if (patient.getPatientStatus() == 2) {
					patient.setPatientStatus(2);
				} else {
					patient.setPatientStatus(1);
				}
				patientDAO.update(patient);
				callLogsDAO.update(callLogs);
			}
		} else {
			Users users = usersDAO.get(callerService.getCurrentUserId());

			callLogs = new CallLog(patientCallerAdminMap,
					InjuryConstants.convertYearFormatWithTime(callLogsForm
							.getTimeStamp()), callLogsForm.getResponse(),
					callLogsForm.getNotes(),1,null);
			callLogs.setCallLogId(callLogsForm.getId());
			Integer status = callLogsForm.getResponse();
			if (status==3) {
				patient.setPatientStatus(3);
				patientDAO.update(patient);
				callLogsDAO.update(callLogs);
			} else {
				if (patient.getPatientStatus() == 2) {
					patient.setPatientStatus(2);
				} else {
					patient.setPatientStatus(1);
				}
				patientDAO.update(patient);
				callLogsDAO.update(callLogs);
			}
		}

		// Logic Ends
		return 1;
	}

	// Delete an Entry
	public int deleteCallLogs(Integer id) {
		callLogsDAO.delete(id);
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

	public List<CallLogsForm> getCallLogsId() {
		List<CallLogsForm> callLogsForms = new ArrayList<CallLogsForm>();

		List<CallLog> callLogss = new ArrayList<CallLog>();

		callLogss = callLogsDAO.getCallLogsId();

		for (CallLog callLogs : callLogss) {
			// TODO: Fill the List
			CallLogsForm callLogsForm = new CallLogsForm(callLogs.getCallLogId());
			callLogsForms.add(callLogsForm);
		}

		return callLogsForms;
	}

}
