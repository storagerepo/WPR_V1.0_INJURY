package com.deemsys.project.patients;

import java.util.List;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;
/**
 * 
 * @author Deemsys
 *
 */
public interface PatientsDAO extends IGenericDAO<Patients>{

	public List<Patients> getPatientListByStaffId(Integer staffId);

	public List<AppointmentsForm> getAppointmentListByStaffId(Integer staffId);
	public List<Patients> patientStatus(Integer patientStatus);
	
	public List<AppointmentsForm> getTodayAppointmentListByStaffId(Integer staffId);
	public List<AppointmentsForm> getParticularDayAppointmentListByStaffId(String date,Integer staffId);
	public List<Patients> patientFileRead(String fileName);
	
	public List<Appointments> getAppointmentsListByPatientsId(Integer patientId);
	public List<CallLogs> getCallLogsListByPatientsId(Integer patientId);
	public void deletePatientsByStaffId(Integer id);
	public List<Patients> getpatientsByDoctorId(Integer doctorId);

	public void removeAssignedDoctor(Integer patientId);

	public Integer activeStatusByPatientId(Integer id);

	public void updatePatientStatus(Integer patientId);

}
