package com.deemsys.project.UserRoleMapping;

import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.UserRoleMapping;

public interface UserRoleDAO extends IGenericDAO<UserRoleMapping>{

	 public UserRoleMapping getbyUserId(Integer userId);
	 public void deletebyUserId(Integer userId);
	
}
