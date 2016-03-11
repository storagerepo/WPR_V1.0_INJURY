package com.deemsys.project.patients;

import java.util.List;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Patients;

/**
 * 
 * @author Deemsys
 * 
 */
public interface PatientsDAO extends IGenericDAO<Patients> {

	public List<Patients> getPatientListByStaffId(Integer staffId);

	public List<AppointmentsForm> getAppointmentListByStaffId(Integer staffId);

	public List<Patients> getPatientsByStatus(Integer patientStatus);

	public List<AppointmentsForm> getTodayAppointmentListByStaffId(
			Integer staffId);

	public List<AppointmentsForm> getParticularDayAppointmentListByStaffId(
			String date, Integer staffId);

	public List<Patients> patientFileRead(String fileName);

	public List<Appointments> getAppointmentsListByPatientsId(Integer patientId);

	public List<CallLogs> getCallLogsListByPatientsId(Integer patientId);

	public void releasePatientsFromStaff(Integer id);

	public List<Patients> getpatientsByDoctorId(Integer doctorId);

	public List<Patients> getpatientsByClinicId(Integer clinicId);

	public void removeAssignedDoctor(Integer patientId);

	public void removeAssignedClinic(Integer patientId);

	public Integer activeStatusByPatientId(Integer id);

	public void updatePatientStatus(Integer patientId);

	public List<Patients> getPatientListByLimit(Integer pageNumber,
			Integer itemsPerPage, String name, String phoneNumber,
			String localReportNumber, String callerName);

	public Integer getTotalPatientsCount(String name, String phoneNumber,
			String localReportNumber, String callerName);

}
