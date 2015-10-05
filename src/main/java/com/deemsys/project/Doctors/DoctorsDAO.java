package com.deemsys.project.Doctors;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Doctors;
/**
 * 
 * @author Deemsys
 *
 */
public interface DoctorsDAO extends IGenericDAO<Doctors>{
	
	public List<Doctors> getDoctorId();
	public List<Doctors> getDoctorsByClinicId(Integer clinicId);
	public Integer getDoctorsSizeByClinicId(Integer clinicId);
	public Integer removeClinicIdFromDoctor(Integer doctorId);
	
}
