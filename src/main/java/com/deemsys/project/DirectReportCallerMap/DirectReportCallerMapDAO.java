package com.deemsys.project.DirectReportCallerMap;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.DirectReportCallerAdminMap;
/**
 * 
 * @author Deemsys
 *
 */
public interface DirectReportCallerMapDAO extends IGenericDAO<DirectReportCallerAdminMap>{

	DirectReportCallerAdminMap getPatientMapsByLawyerAdminId(String crashId,Integer callerAdminId);

}
