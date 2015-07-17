package com.deemsys.project.CallLogs;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
/**
 * 
 * @author Deemsys
 *
 */
public interface CallLogsDAO extends IGenericDAO<CallLogs>{

		public CallLogs getCallLogsByAppointment(Integer appointmentId);
}
