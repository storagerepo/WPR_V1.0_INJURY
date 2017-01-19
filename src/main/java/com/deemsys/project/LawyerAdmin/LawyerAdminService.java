package com.deemsys.project.LawyerAdmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.LawyerAdminCountyMapping.LawyerAdminCountyMappingDAO;
import com.deemsys.project.LawyerAdminCountyMapping.LawyerAdminCountyMappingForm;
import com.deemsys.project.LawyerAdminCountyMapping.LawyerAdminCountyMappingService;
import com.deemsys.project.LawyerCountyMapping.LawyerCountyMappingForm;
import com.deemsys.project.Lawyers.LawyersDAO;
import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.Lawyer;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.LawyerAdminCountyMap;
import com.deemsys.project.entity.LawyerAdminCountyMapId;
import com.deemsys.project.entity.LawyerCountyMap;
import com.deemsys.project.entity.LawyerCountyMapId;
import com.deemsys.project.entity.Roles;
import com.deemsys.project.entity.Users;

@Service
@Transactional
public class LawyerAdminService {

	@Autowired
	LawyerAdminDAO lawyerAdminDAO;

	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	LawyerAdminCountyMappingDAO lawyerAdminCountyMappingDAO;
	
	@Autowired
	CountyService countyService;
	
	@Autowired
	LawyersDAO lawyersDAO;
	
	@Autowired
	LawyerAdminCountyMappingService lawyerAdminCountyMappingService;

	// Get All Lawyer Admin List
	public List<LawyerAdminForm> getLawyerAdminList() {
		List<LawyerAdminForm> lawyerAdminForms = new ArrayList<LawyerAdminForm>();
		List<LawyerAdmin> lawyerAdmins = new ArrayList<LawyerAdmin>();

		lawyerAdmins = lawyerAdminDAO.getAll();

		for (LawyerAdmin lawyerAdmin : lawyerAdmins) {
			LawyerAdminForm lawyerAdminForm = new LawyerAdminForm(
					lawyerAdmin.getLawyerAdminId(), lawyerAdmin.getUsers().getUserId(),
					lawyerAdmin.getFirstName(), lawyerAdmin.getLastName(),lawyerAdmin.getStreet(),
					lawyerAdmin.getCity(),
					lawyerAdmin.getState(),
					lawyerAdmin.getZipcode(),
					lawyerAdmin.getEmailAddress(),
					lawyerAdmin.getPhoneNumber(), lawyerAdmin.getNotes(),
					lawyerAdmin.getUsers().getIsPrivilegedUser(),lawyerAdmin.getStatus());
			lawyerAdminForm.setUsername(lawyerAdmin.getUsers().getUsername());
			lawyerAdminForm.setProductToken(lawyerAdmin.getUsers().getProductToken());
			lawyerAdminForms.add(lawyerAdminForm);
		}

		return lawyerAdminForms;
	}

	// Get Particular Entry
	public LawyerAdminForm getLawyerAdmin(Integer getId) {
		LawyerAdmin lawyerAdmin = new LawyerAdmin();

		lawyerAdmin = lawyerAdminDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start

		LawyerAdminForm lawyerAdminForm = new LawyerAdminForm(
				lawyerAdmin.getLawyerAdminId(), lawyerAdmin.getUsers().getUserId(),
				lawyerAdmin.getFirstName(), lawyerAdmin.getLastName(),lawyerAdmin.getStreet(),
				lawyerAdmin.getCity(),
				lawyerAdmin.getState(),
				lawyerAdmin.getZipcode(),
				lawyerAdmin.getEmailAddress(),
				lawyerAdmin.getPhoneNumber(), lawyerAdmin.getNotes(),
				lawyerAdmin.getUsers().getIsPrivilegedUser(),lawyerAdmin.getStatus());
		lawyerAdminForm.setUsername(lawyerAdmin.getUsers().getUsername());
		lawyerAdminForm.setProductToken(lawyerAdmin.getUsers().getProductToken());
		// Get County List
				lawyerAdminForm.setCountyform(countyService.getCountyList());

				// Get Mapped Counties
				List<LawyerAdminCountyMappingForm> lawyerAdminCountyMappingForms = lawyerAdminCountyMappingService
						.getLawyerAdminCountyMappingByLawyerAdminId(getId);

				
				List<Integer> county = new ArrayList<Integer>();
				for (LawyerAdminCountyMappingForm lawyerAdminCountyMappingForms1 : lawyerAdminCountyMappingForms) {
					if (lawyerAdminCountyMappingForms1.getCountyId() != null) {
						System.out.println("Layer County Id"
								+ lawyerAdminCountyMappingForms1.getCountyId());
						county.add(lawyerAdminCountyMappingForms1.getCountyId());
					}
				}
				lawyerAdminForm.setCounty(county);
		// End

		return lawyerAdminForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeLawyerAdmin(LawyerAdminForm lawyerAdminForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Users users = new Users();
		Roles role = new Roles();
		users.setUsername(lawyerAdminForm.getUsername());
		users.setPassword(lawyerAdminForm.getUsername());
		users.setIsEnable(1);
		users.setIsPasswordChanged(0);
		LawyerAdmin lawyerAdmin = new LawyerAdmin(users,
				lawyerAdminForm.getFirstName(), lawyerAdminForm.getLastName(), 
				lawyerAdminForm.getStreet(),
				lawyerAdminForm.getCity(),
				lawyerAdminForm.getState(),
				lawyerAdminForm.getZipcode(),
				lawyerAdminForm.getEmailAddress(),
				lawyerAdminForm.getPhoneNumber(), lawyerAdminForm.getNotes(),
				lawyerAdminForm.getStatus(), null,null,null);
		lawyerAdmin.setLawyerAdminId(lawyerAdminForm.getLawyerAdminId());
		lawyerAdminDAO.merge(lawyerAdmin);

		role = roleDAO.get(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID);
		users.setRoles(role);
		users.setProductToken(lawyerAdminForm.getProductToken());
		usersDAO.save(users);
		// Logic Ends

		return 1;
	}

	// Save an Entry
	public int saveLawyerAdmin(LawyerAdminForm lawyerAdminForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Users users = new Users();
		Roles role = new Roles();
		users.setUsername(lawyerAdminForm.getUsername());
		users.setPassword(lawyerAdminForm.getUsername());
		users.setIsEnable(1);
		users.setStatus(1);
		users.setIsPasswordChanged(0);
		users.setIsDisclaimerAccepted(0);
		users.setIsPrivilegedUser(lawyerAdminForm.getIsPrivilegedUser());
		users.setProductToken(lawyerAdminForm.getProductToken());
		role = roleDAO.get(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE_ID);
		users.setRoles(role);
		users.setProductToken(lawyerAdminForm.getProductToken());
		usersDAO.save(users);
		
		LawyerAdmin lawyerAdmin = new LawyerAdmin(users,
				lawyerAdminForm.getFirstName(), lawyerAdminForm.getLastName(), 
				lawyerAdminForm.getStreet(),
				lawyerAdminForm.getCity(),
				lawyerAdminForm.getState(),
				lawyerAdminForm.getZipcode(),
				lawyerAdminForm.getEmailAddress(),
				lawyerAdminForm.getPhoneNumber(), lawyerAdminForm.getNotes(),
				1, null,null,null);
		lawyerAdminDAO.save(lawyerAdmin);

		
		
		
		List<Integer> lawyeradmincounty = lawyerAdminForm.getCounty();

		// Map the County
		for (Integer countyId : lawyeradmincounty) {
			County county = countyDAO.get(countyId);
			LawyerAdminCountyMapId lawyerAdminCountyMapId=new LawyerAdminCountyMapId(lawyerAdmin.getLawyerAdminId(), countyId);
			LawyerAdminCountyMap lawyerAdminCountyMapping = new LawyerAdminCountyMap(lawyerAdminCountyMapId,lawyerAdmin, county,new Date(),1);
			lawyerAdminCountyMappingDAO.save(lawyerAdminCountyMapping);
		}
		// Logic Ends

		return 1;
	}

	// Update an Entry
	public int updateLawyerAdmin(LawyerAdminForm lawyerAdminForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		LawyerAdmin lawyerAdmin = lawyerAdminDAO.get(lawyerAdminForm.getLawyerAdminId());
		Users users = usersDAO.get(lawyerAdmin.getUsers().getUserId());
		users.setProductToken(lawyerAdminForm.getProductToken());
		users.setIsPrivilegedUser(lawyerAdminForm.getIsPrivilegedUser());
		usersDAO.update(users);
		
		lawyerAdmin = new LawyerAdmin(users,
				lawyerAdminForm.getFirstName(), lawyerAdminForm.getLastName(), 
				lawyerAdminForm.getStreet(),
				lawyerAdminForm.getCity(),
				lawyerAdminForm.getState(),
				lawyerAdminForm.getZipcode(),
				lawyerAdminForm.getEmailAddress(),
				lawyerAdminForm.getPhoneNumber(), lawyerAdminForm.getNotes(),
				lawyerAdminForm.getStatus(), null,null,null);
		lawyerAdmin.setLawyerAdminId(lawyerAdminForm.getLawyerAdminId());
		lawyerAdminDAO.update(lawyerAdmin);
		
		List<Integer> countyMapped = lawyerAdminForm.getCounty();
		lawyerAdminCountyMappingService.deleteLawyerAdminCountyMapping(countyMapped,lawyerAdminForm.getLawyerAdminId());
		// Add a New County List
		
		List<Integer> newlyAddedCounty = lawyerAdminCountyMappingService
				.getNewlyAddedCountyId(countyMapped, lawyerAdminForm.getLawyerAdminId());
		
		
		// Map the County
				for (Integer countyId : newlyAddedCounty) {
					County county = countyDAO.get(countyId);
					LawyerAdminCountyMapId lawyerAdminCountyMapId=new LawyerAdminCountyMapId(lawyerAdmin.getLawyerAdminId(), countyId);
					LawyerAdminCountyMap lawyerAdminCountyMapping = new LawyerAdminCountyMap(lawyerAdminCountyMapId,lawyerAdmin, county,new Date(),1);
					lawyerAdminCountyMappingDAO.save(lawyerAdminCountyMapping);
				}
		
		//Logic Ends
		return 1;
	}
	
	//Enable/Disable
	public int enableOrDisableLawyerAdmin(Integer lawyerAdminId,Integer fromId){
		
		LawyerAdmin lawyerAdmin = lawyerAdminDAO.get(lawyerAdminId);
		Users users = usersDAO.get(lawyerAdmin.getUsers().getUserId());
		if(users.getIsEnable()==0){
			if(fromId==1){
				users.setIsEnable(1);
				lawyerAdminDAO.enable(lawyerAdminId);
				
				//Disable Lawyer
				List<Lawyer> lawyers=lawyersDAO.getLawyersByLawyerAdmin(lawyerAdminId);
				for (Lawyer lawyer : lawyers) {
					Users lawyerUser=lawyer.getUsers();
					if(lawyer.getStatus()==1){
						lawyerUser.setIsEnable(1);
						usersDAO.update(lawyerUser);
					}
				}
			}else{
				// Do not enable
			}
		}
		else if(users.getIsEnable()==1){
			users.setIsEnable(0);
			lawyerAdminDAO.disable(lawyerAdminId);
			
			//Disable Lawyer
			List<Lawyer> lawyers=lawyersDAO.getLawyersByLawyerAdmin(lawyerAdminId);
			for (Lawyer lawyer : lawyers) {
				Users lawyerUser=lawyer.getUsers();
				lawyerUser.setIsEnable(0);
				usersDAO.update(lawyerUser);
			}
		}
		
		usersDAO.update(users);
		
		return 1;
		
	}
	
	//Reset password
	public Integer resetLawyerAdminPassword(Integer lawyerAdminId){

		LawyerAdmin lawyerAdmin = lawyerAdminDAO.get(lawyerAdminId);
		usersDAO.resetUserPassword(lawyerAdmin.getUsers().getUserId());
		
		return 1;
	}
	

	// Delete an Entry
	public int deleteLawyerAdmin(Integer id) {
		LawyerAdmin lawyerAdmin = lawyerAdminDAO.get(id);
		Users users = usersDAO.get(lawyerAdmin.getUsers().getUserId());
		usersDAO.delete(users.getUserId());

		lawyerAdminDAO.delete(id);
		return 1;
	}

	// Get Number of Lawyer Admin Available in System
	public Integer getNumberOfLawyerAdmin() {
		Integer numberOfLawyerAdmin = 0;

		numberOfLawyerAdmin = this.getLawyerAdminList().size();

		return numberOfLawyerAdmin;
	}

	

	// Get Lawyer Admin By UserId
	public LawyerAdmin getLawyerAdminIdByUserId(Integer userId) {
		return lawyerAdminDAO.getByUserId(userId);
	}

}
