package com.deemsys.project.DirectReportLawyerMap;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.DirectReportLawyerAdminMap;
/**
 * 
 * @author Deemsys
 *
 */
public interface DirectReportLaywerMapDAO extends IGenericDAO<DirectReportLawyerAdminMap>{

	public DirectReportLawyerAdminMap getPatientMapsByLawyerAdminId(String crashId,Integer lawyerAdminId);

}
