package com.deemsys.project.login;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Staff;

public interface loginDAO extends IGenericDAO<Staff>{

	//Function to check for user name
		public Staff getByUserName(String username);
	
}
