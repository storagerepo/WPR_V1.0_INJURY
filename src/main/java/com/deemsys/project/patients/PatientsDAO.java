package com.deemsys.project.patients;

import java.util.List;

import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Patients;
/**
 * 
 * @author Deemsys
 *
 */
public interface PatientsDAO extends IGenericDAO<Patients>{

	public List<Patients> getPatientListByStaffId(Integer staffId);

	public List<AppointmentsForm> getAppointmentListByStaffId(Integer staffId);
	


}
