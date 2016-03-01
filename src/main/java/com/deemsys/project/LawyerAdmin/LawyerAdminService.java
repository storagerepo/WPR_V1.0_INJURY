package com.deemsys.project.LawyerAdmin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.UserRoleMapping.UserRoleDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.Role;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.entity.UserRoleMapping;
import com.deemsys.project.entity.Users;

@Service
@Transactional
public class LawyerAdminService {

	
	@Autowired
	LawyerAdminDAO lawyerAdminDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	UserRoleDAO userRoleDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	// Get All Lawyer Admin List
	public List<LawyerAdminForm> getLawyerAdminList(){
		List<LawyerAdminForm> lawyerAdminForms=new ArrayList<LawyerAdminForm>();
		List<LawyerAdmin> lawyerAdmins=new ArrayList<LawyerAdmin>();
		
		lawyerAdmins=lawyerAdminDAO.getAll();
		
		for (LawyerAdmin lawyerAdmin : lawyerAdmins) {
			LawyerAdminForm lawyerAdminForm=new LawyerAdminForm(lawyerAdmin.getId(), lawyerAdmin.getUsers().getUserId(), lawyerAdmin.getFirstName(), lawyerAdmin.getLastName(), lawyerAdmin.getMiddleName(), lawyerAdmin.getAddress(), lawyerAdmin.getEmailAddress(), lawyerAdmin.getPhoneNumber(), lawyerAdmin.getNotes(), lawyerAdmin.getStatus());
			lawyerAdminForm.setUsername(lawyerAdmin.getUsers().getUsername());
			lawyerAdminForms.add(lawyerAdminForm);
		}
		
		return lawyerAdminForms;
	}
	
	//Get Particular Entry
		public LawyerAdminForm getLawyerAdmin(Integer getId)
		{
			LawyerAdmin lawyerAdmin=new LawyerAdmin();
			
			lawyerAdmin=lawyerAdminDAO.get(getId);
			
			//TODO: Convert Entity to Form
			//Start
			
			LawyerAdminForm lawyerAdminForm=new LawyerAdminForm(lawyerAdmin.getId(), lawyerAdmin.getUsers().getUserId(), lawyerAdmin.getFirstName(), lawyerAdmin.getLastName(), lawyerAdmin.getMiddleName(), lawyerAdmin.getAddress(), lawyerAdmin.getEmailAddress(), lawyerAdmin.getPhoneNumber(), lawyerAdmin.getNotes(), lawyerAdmin.getStatus());
			lawyerAdminForm.setUsername(lawyerAdmin.getUsers().getUsername());
			//End
			
			return lawyerAdminForm;
		}
		
		//Merge an Entry (Save or Update)
		public int mergeLawyerAdmin(LawyerAdminForm lawyerAdminForm)
		{
			//TODO: Convert Form to Entity Here
			
			//Logic Starts
			Users users = new Users();
			UserRoleMapping userRoleMapping= new UserRoleMapping();
			Role role= new Role();
			users.setUsername(lawyerAdminForm.getUsername());
			users.setPassword(lawyerAdminForm.getUsername());
			users.setIsEnable(1);
			LawyerAdmin lawyerAdmin=new LawyerAdmin(users, lawyerAdminForm.getFirstName(),lawyerAdminForm.getLastName(), lawyerAdminForm.getMiddleName(), lawyerAdminForm.getAddress(), lawyerAdminForm.getEmailAddress(), lawyerAdminForm.getPhoneNumber(), lawyerAdminForm.getNotes(), lawyerAdminForm.getStatus(), null);
			lawyerAdmin.setId(lawyerAdminForm.getId());
			lawyerAdminDAO.merge(lawyerAdmin);
			
			role=roleDAO.get(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID);
			userRoleMapping.setRole(role);
			userRoleMapping.setUsers(users);
			userRoleDAO.save(userRoleMapping);
			
			//Logic Ends
			
			return 1;
		}
		
		//Save an Entry
		public int saveLawyerAdmin(LawyerAdminForm lawyerAdminForm)
		{
			//TODO: Convert Form to Entity Here	
			
			//Logic Starts
			Users users = new Users();
			UserRoleMapping userRoleMapping= new UserRoleMapping();
			Role role= new Role();
			users.setUsername(lawyerAdminForm.getUsername());
			users.setPassword(lawyerAdminForm.getUsername());
			users.setIsEnable(1);
			LawyerAdmin lawyerAdmin=new LawyerAdmin(users, lawyerAdminForm.getFirstName(),lawyerAdminForm.getLastName(), lawyerAdminForm.getMiddleName(), lawyerAdminForm.getAddress(), lawyerAdminForm.getEmailAddress(), lawyerAdminForm.getPhoneNumber(), lawyerAdminForm.getNotes(), 1, null);
			
			lawyerAdminDAO.save(lawyerAdmin);
			
			role=roleDAO.get(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID);
			userRoleMapping.setRole(role);
			userRoleMapping.setUsers(users);
			userRoleDAO.save(userRoleMapping);
			
			//Logic Ends
			
			return 1;
		}
		
		//Update an Entry
		public int updateLawyerAdmin(LawyerAdminForm lawyerAdminForm)
		{
			//TODO: Convert Form to Entity Here	
			
			//Logic Starts
			
			Users users = usersDAO.get(lawyerAdminForm.getUserId());
			LawyerAdmin lawyerAdmin=new LawyerAdmin(users, lawyerAdminForm.getFirstName(),lawyerAdminForm.getLastName(), lawyerAdminForm.getMiddleName(), lawyerAdminForm.getAddress(), lawyerAdminForm.getEmailAddress(), lawyerAdminForm.getPhoneNumber(), lawyerAdminForm.getNotes(), 1, null);
			lawyerAdmin.setId(lawyerAdminForm.getId());
			
			//Logic Ends
			
			lawyerAdminDAO.update(lawyerAdmin);
			return 1;
		}
		
		//Delete an Entry
		public int deleteLawyerAdmin(Integer id)
		{
			LawyerAdmin lawyerAdmin=lawyerAdminDAO.get(id);
			Users users=usersDAO.get(lawyerAdmin.getUsers().getUserId());
			userRoleDAO.deletebyUserId(users.getUserId());
			
			lawyerAdminDAO.delete(id);
			return 1;
		}
	
		// Get Number of Lawyer Admin Available in System
		public Integer getNumberOfLawyerAdmin(){
			Integer numberOfLawyerAdmin=0;
			
			numberOfLawyerAdmin=this.getLawyerAdminList().size();
			
			return numberOfLawyerAdmin;
		}
		
		public Integer resetLawyerAdminPassword(Integer lawyerAdminId)
		{
			Integer status=1;
			LawyerAdmin lawyerAdmin=lawyerAdminDAO.get(lawyerAdminId);
			
			status=usersDAO.resetUserPassword(lawyerAdmin.getUsers().getUserId());
			return status;
		}
		
		// Get Lawyer Admin By UserId
		public Integer getLawyerAdminIdByUserId(Integer userId){
			LawyerAdmin lawyerAdmin=lawyerAdminDAO.getByUserId(userId);
			return lawyerAdmin.getId();
		}
		
}
