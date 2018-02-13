package com.deemsys.project.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.Role.RoleForm;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Roles;
import com.deemsys.project.entity.Users;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	LawyersService lawyersService;
	
	@Autowired
	CallerService callerService;
	
	@Autowired
	RoleDAO roleDAO;
	
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
		}else if (role[0].toString()
				.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)) {
			currentRole = InjuryConstants.INJURY_AUTO_MANAGER_ROLE;
		}else if (role[0].toString()
				.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)) {
			currentRole = InjuryConstants.INJURY_AUTO_DEALER_ROLE;
		}else if (role[0].toString()
				.equals(InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE)) {
			currentRole = InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE;
		}else if (role[0].toString()
				.equals(InjuryConstants.INJURY_SHOP_ROLE)) {
			currentRole = InjuryConstants.INJURY_SHOP_ROLE;
		}
		return currentRole;
	}
	
	//Get User Id
	public Integer getCurrentUserID(){
		User user = null;
		try{
			user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return usersDAO.getByUserName(user.getUsername()).getUserId();
	}
	
	// Get Username
	public String getCurrentUsername(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
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
	
	// Check Password Changed Status
	public Integer checkPasswordChangedStatus(){
		Integer status=0;
		Users users=usersDAO.get(this.getCurrentUserID());
		if(users.getIsPasswordChanged()==1){
			status=1;
		}
		return status;
	}
	
	//Get User Preference User ID
	public Integer getPreferenceUserId(){
		
		String currentRole=this.getCurrentRole();
		
		if(currentRole.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
			return this.getCurrentUserID();
		}
		else if(currentRole.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||currentRole.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)||currentRole.equals(InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE)){
			return this.getCurrentUserID();
		}
		else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			return this.getCurrentUserID();
		}
		else if(currentRole.equals(InjuryConstants.INJURY_CALLER_ROLE)||currentRole.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)||currentRole.equals(InjuryConstants.INJURY_SHOP_ROLE)){
			return callerService.getCallerByUserId(this.getCurrentUserID()).getCallerAdmin().getUsers().getUserId();
		}
		else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ROLE)){			
			return lawyersService.getLawyerIdByUserId(this.getCurrentUserID()).getLawyerAdmin().getUsers().getUserId();
		}else{
			return 1;
		}
	}
	
	// Check Disclaimer Accepted Status
	public Integer checkDisclaimerAcceptedStatus(){
		Integer status=usersDAO.disclaimerAcceptedStatus(this.getCurrentUserID());
		return status;
	}
	
	// Update Disclaimer Status
	public Integer updateDisclaimerAcceptedStatus(){
		Users users=usersDAO.get(this.getCurrentUserID());
		users.setIsDisclaimerAccepted(1);
		usersDAO.update(users);
		return 1;
	}
	
	// Get Product Token
	public Users getProductToken(){
		Users users=usersDAO.get(this.getCurrentUserID());
		
		return users;
	}
	
	// Get User By Product Token
	public Users getUserByProductToken(String productToken){
		return usersDAO.getUserByProductToken(productToken);
	}

	public void changePrivilegedUserStatus(String customerProductToken,Integer privilegedStatus) {
		// TODO Auto-generated method stub
		Users users=usersDAO.getUserByProductToken(customerProductToken);
		users.setIsPrivilegedUser(privilegedStatus);
		usersDAO.update(users);
	}
	
  // Get Current Role Id
	public Integer getCurrentRoleId(){
		
		String currentRole=this.getCurrentRole();
		
		if(currentRole.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
			return InjuryConstants.INJURY_SUPER_ADMIN_ROLE_ID;
		}
		else if(currentRole.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			return InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID;
		}
		else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			return InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID;
		}
		else if(currentRole.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			return InjuryConstants.INJURY_CALLER_ROLE_ID;
		}
		else if(currentRole.equals(InjuryConstants.INJURY_LAWYER_ROLE)){			
			return InjuryConstants.INJURY_LAWYER_ROLE_ID;
		}else if(currentRole.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)){
			return InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID;
		}else if(currentRole.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)){
			return InjuryConstants.INJURY_AUTO_DEALER_ROLE_ID;
		}else if(currentRole.equals(InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE)){
			return InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE_ID;
		}else if(currentRole.equals(InjuryConstants.INJURY_SHOP_ROLE)){
			return InjuryConstants.INJURY_SHOP_ROLE_ID;
		}
		else{
			return 1;
		}
	}

	// Get User Id of Admins By Role and Logged In user Id
	public Integer getUserIdOfAdmin(String loginRole,Integer userId){
		
		if(loginRole.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
			return null;
		}
		else if(loginRole.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||loginRole.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)||loginRole.equals(InjuryConstants.INJURY_BODY_SHOP_OWNER_ROLE)){
			return null;
		}
		else if(loginRole.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			return null;
		}
		else if(loginRole.equals(InjuryConstants.INJURY_CALLER_ROLE)||loginRole.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)||loginRole.equals(InjuryConstants.INJURY_SHOP_ROLE)){
			return callerService.getCallerByUserId(userId).getCallerAdmin().getUsers().getUserId();
		}
		else if(loginRole.equals(InjuryConstants.INJURY_LAWYER_ROLE)){			
			return lawyersService.getLawyerIdByUserId(userId).getLawyerAdmin().getUsers().getUserId();
		}else{
			return null;
		}
	}
	
	// Get Roles
	public List<RoleForm> getRoles(){
		List<Roles> roles=roleDAO.getAll();
		List<RoleForm> roleForms=new ArrayList<RoleForm>();
		for (Roles role : roles) {
			RoleForm roleForm = new RoleForm(role.getRoleId(), InjuryConstants.getRoleAsText(role.getRole()), role.getStatus());
			roleForms.add(roleForm);
		}
		return roleForms;
	}
	
}
