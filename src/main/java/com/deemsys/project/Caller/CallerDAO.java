package com.deemsys.project.Caller;

import java.util.List;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.Caller;

/**
 * 
 * @author Deemsys
 * 
 */
public interface CallerDAO extends IGenericDAO<Caller> {
	public Caller getByUserName(String username);

	public List<Caller> getCallerId();

	public Caller getByUserId(Integer userId);

	public void deleteCaller(Integer id);

	public List<Patient> getPatientByCallerId(Integer id);

	public List<Patient> getPatientByAccessToken(Integer callerId);

	public List<Patient> getPatientStatus(Integer patientStatus,
			Integer callerId);

	public Integer changePassword(String oldPassword, String userName);

	public List<Caller> checkPassword(String newPassword, String userName);

	public Caller getDetails(String userName);

	public Integer isDisable(Integer getId);

	public Integer isEnable(Integer getId);

	public Integer resetPassword(Integer id);

}
