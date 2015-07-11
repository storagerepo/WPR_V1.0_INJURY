package com.deemsys.project.Appointments;

import java.util.List;
import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Appointments;
/**
 * 
 * @author Deemsys
 *
 */
public interface AppointmentsDAO extends IGenericDAO<Appointments>{
	public List<Appointments> getAppointmentsListByPatient(Integer patientId);

	

	public Integer updates(Integer id,Integer status);
}
