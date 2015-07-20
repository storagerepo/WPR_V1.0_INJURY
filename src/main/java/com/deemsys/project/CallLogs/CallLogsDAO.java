package com.deemsys.project.CallLogs;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Patients;
/**
 * 
 * @author Deemsys
 *
 */
public interface CallLogsDAO extends IGenericDAO<CallLogs>{

		public CallLogs getCallLogsByAppointment(Integer appointmentId);
		public List<CallLogs> getCallLogsByPatientsId(Integer PatientId);
}
