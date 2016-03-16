package com.deemsys.project.CallLogs;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.CallLog;

/**
 * 
 * @author Deemsys
 * 
 */
public interface CallLogsDAO extends IGenericDAO<CallLog> {

	public CallLog getCallLogsByAppointment(Integer appointmentId);

	public List<CallLog> getCallLogsByPatientsId(Integer PatientId);

	public List<CallLog> getCallLogsId();
}
