package com.deemsys.project.patient;

import java.util.Date;
import java.util.List;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.Patient;

/**
 * 
 * @author Deemsys
 * 
 */
public interface PatientDAO extends IGenericDAO<Patient> {

	public List<Patient> getPatientListByCallerId(Integer staffId);

	public List<AppointmentsForm> getAppointmentListByCallerId(Integer staffId);

	public List<Patient> getPatientByStatus(Integer patientStatus);

	public List<AppointmentsForm> getTodayAppointmentListByCallerId(
			Integer staffId);

	public List<AppointmentsForm> getParticularDayAppointmentListByCallerId(
			String date, Integer staffId);

	public List<Patient> patientFileRead(String fileName);

	public List<Appointments> getAppointmentsListByPatientId(Integer patientId);

	public List<CallLog> getCallLogsListByPatientId(Integer patientId);

	public void releasePatientFromCaller(Integer id);

	public List<Patient> getpatientByDoctorId(Integer doctorId);

	public List<Patient> getpatientByClinicId(Integer clinicId);

	public void removeAssignedDoctor(Integer patientId);

	public void removeAssignedClinic(Integer patientId);

	public Integer activeStatusByPatientId(Integer id);

	public void updatePatientStatus(Integer patientId);

	public List<Patient> getPatientListByLimit(Integer pageNumber,
			Integer itemsPerPage, String name, String phoneNumber,
			String localReportNumber, String callerName);

	public Integer getTotalPatientCount(String localReportNumber, String county,
			String crashDate, String toDate, String recordedFromDate,
			String recordedToDate, String name);
	
	public List<Patient> searchPatients(Integer pageNumber, Integer itemsPerPage,String localReportNumber,String county, 
			String crashDate,String toDate,String recordedFromDate,String recordedToDate, String name, String customDate);

}
