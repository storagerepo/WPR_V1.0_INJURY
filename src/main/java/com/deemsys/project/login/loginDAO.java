package com.deemsys.project.login;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Users;

public interface loginDAO extends IGenericDAO<Users>{

	//Function to check for user name
		public Users getByUserName(String username);
	
}
