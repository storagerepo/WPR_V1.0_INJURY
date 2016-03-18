package com.deemsys.project.LawyerAdmin;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.LawyerAdmin;

public interface LawyerAdminDAO extends IGenericDAO<LawyerAdmin> {

	public LawyerAdmin getByUserId(Integer userId);
	
	public Integer getUserIdByLawyerAdminId(Integer lawyerAdminId);


}
