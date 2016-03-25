package com.deemsys.project.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Users;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	UsersDAO usersDAO;
	
	// Get Current User Role
	public String getCurrentRole() {

		String currentRole = "";
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Object[] role = user.getAuthorities().toArray();

		if (role[0].toString().equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)) {
			currentRole = InjuryConstants.INJURY_SUPER_ADMIN_ROLE;
		} else if (role[0].toString().equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)) {
			currentRole = InjuryConstants.INJURY_CALLER_ADMIN_ROLE;
		} else if (role[0].toString().equals(
				InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)) {
			currentRole = InjuryConstants.INJURY_LAWYER_ADMIN_ROLE;
		} 
		else if (role[0].toString()
				.equals(InjuryConstants.INJURY_CALLER_ROLE)) {
			currentRole = InjuryConstants.INJURY_CALLER_ROLE;
		}
		else if (role[0].toString()
				.equals(InjuryConstants.INJURY_LAWYER_ROLE)) {
			currentRole = InjuryConstants.INJURY_LAWYER_ROLE;
		}
		return currentRole;
	}
	
	//Get User Id
	public Integer getCurrentUserID(){
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return usersDAO.getByUserName(user.getUsername()).getUserId();
	}
	
	//Check UserName Exist
	public Integer checkUsernameExist(String username) {
		Users users = new Users();

		users = usersDAO.getByUserName(username);
		// Start
		if (users != null) {
			return 0;
		} else {

			return 1;
		}
		// End
	}
}
