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
	

}
