package com.deemsys.project.Users;

import java.util.List;


import com.deemsys.project.common.IGenericDAO;
import com.deemsys.project.entity.Users;

public interface UsersDAO extends IGenericDAO<Users> {
	
	public List<Users> checkPassword(String password, String userName);
	public Integer changePassword(String newPassword,String userName);
	public Users getByUserName(String userName);
	public Integer resetUserPassword(Integer id);
	public Integer disclaimerAcceptedStatus(Integer userId);
	public Users getUserByProductToken(String productToken);
}
