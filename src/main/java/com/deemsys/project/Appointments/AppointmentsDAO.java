package com.deemsys.project.Appointments;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;

/**
 * 
 * @author Deemsys
 * 
 */
public interface AppointmentsDAO extends IGenericDAO<Appointments> {
	
	public Appointments getAppointmentsByAppintementId(Long appointmentId);
	
	public void deleteAppointmentsByAppointmentId(Long appointmentId);
	
	public AppointmentsSearchResult searchAppointments(AppointmentSearchForm appointmentSearchForm);
	
	public Integer getAppointmentsCount(Integer callerAdminId);
	
	public Integer changeAppointmentStatus(Long appointmentId,Integer status);
	
	public AppointmentsForm getAppointmentsByAppointmentIdWithFullDetails(Long appointmentId);
	
	public void deleteAppointmentsByCalllogId(Long calllogId);
}
