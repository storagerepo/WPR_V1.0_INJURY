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
	
	public Caller getDetails(String userName);
	
	public List<Caller> getCallerByCallerAdminId(Integer callerAdminId);

	public Long getCallersByCalleradminUserId(Integer userId);

}
