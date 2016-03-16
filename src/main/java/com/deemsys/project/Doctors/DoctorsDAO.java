package com.deemsys.project.Doctors;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Doctor;
/**
 * 
 * @author Deemsys
 *
 */
public interface DoctorsDAO extends IGenericDAO<Doctor>{
	public List<Doctor> getDoctorsByClinic(Integer clinicId);
	
	public List<Doctor> getDoctorId();
	public List<Doctor> getDoctorsByClinicId(Integer clinicId);
	public Integer getDoctorsSizeByClinicId(Integer clinicId);
	public Integer removeClinicIdFromDoctor(Integer doctorId);
	
}
