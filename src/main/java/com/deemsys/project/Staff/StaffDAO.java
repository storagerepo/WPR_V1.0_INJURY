package com.deemsys.project.Staff;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;
/**
 * 
 * @author Deemsys
 *
 */
public interface StaffDAO extends IGenericDAO<Staff>{
	public Staff getByUserName(String username);
	public List<Staff> getStaffId();
	
	
	
	public List<Patients> getPatientsByAccessToken(Integer callerId);

}
