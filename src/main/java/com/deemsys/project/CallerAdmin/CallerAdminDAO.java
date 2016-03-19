package com.deemsys.project.CallerAdmin;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.CallerAdmin;
/**
 * 
 * @author Deemsys
 *
 */
public interface CallerAdminDAO extends IGenericDAO<CallerAdmin>{

	public Integer getUserIdByCallerAdminId(Integer callerAdminId);
	public CallerAdmin getCallerAdminByUserId(Integer userId);
}
