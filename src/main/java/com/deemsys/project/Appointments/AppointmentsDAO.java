package com.deemsys.project.Appointments;

import java.util.Date;
import java.util.List;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;

/**
 * 
 * @author Deemsys
 * 
 */
public interface AppointmentsDAO extends IGenericDAO<Appointments> {
	public Integer updates(Integer id, Integer status);

	public List<Appointments> todaysAppointment();

	public List<Appointments> getByDates(String date);

	public List<Appointments> getAppointmentsBetweenDates(Date startDate,
			Date endDate);

	public List<AppointmentsForm> getAppointmentsBetweenDatesByCallerId(
			String startDate, String endDate, Integer staffId);
	
	public Appointments getAppointmentsByAppintementId(Long appointmentId);
	
	public void deleteAppointmentsByAppointmentId(Long appointmentId);
	
	public List<AppointmentsForm> searchAppointments(AppointmentSearchForm appointmentSearchForm);
}
