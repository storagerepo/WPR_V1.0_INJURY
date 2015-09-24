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
	
	public void deleteStaff(Integer id);
	public List<Patients> getPatientsByStaffId(Integer id);
	

	
	public List<Patients> getPatientsByAccessToken(Integer callerId);
	
	public Integer changePassword(String oldPassword,String userName);
	public List<Staff> checkPassword(String newPassword, String userName);
	public Staff getDetails(String userName);
	public Integer isDisable(Integer getId);
	public Integer isEnable(Integer getId);
	public Integer resetPassword(Integer id);
	

}
