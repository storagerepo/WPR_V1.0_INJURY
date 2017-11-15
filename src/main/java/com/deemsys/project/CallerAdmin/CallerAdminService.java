package com.deemsys.project.CallerAdmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Caller.CallerDAO;
import com.deemsys.project.CallerAdminCountyMapping.CallerAdminCountyMapDAO;
import com.deemsys.project.CallerAdminCountyMapping.CallerAdminCountyMapService;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.CallerAdminCountyMap;
import com.deemsys.project.entity.CallerAdminCountyMapId;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.Roles;
import com.deemsys.project.entity.Users;
/**
 * 
 * @author Deemsys
 *
 * CallerAdmin 	 - Entity
 * callerAdmin 	 - Entity Object
 * callerAdmins 	 - Entity List
 * callerAdminDAO   - Entity DAO
 * callerAdminForms - EntityForm List
 * CallerAdminForm  - EntityForm
 *
 */
@Service
@Transactional
public class CallerAdminService {

	@Autowired
	CallerAdminDAO callerAdminDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	CallerAdminCountyMapDAO callerAdminCountyMapDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	@Autowired
	CountyService countyService;
	
	@Autowired
	CallerDAO callerDAO;
	
	@Autowired
	CallerAdminCountyMapService callerAdminCountyMapService;
	
	//Get All Entries
	public List<CallerAdminForm> getCallerAdminList(Integer roleId)
	{
		List<CallerAdminForm> callerAdminForms=new ArrayList<CallerAdminForm>();
		
		callerAdminForms=callerAdminDAO.getCallerAdminListByRoleId(roleId);
		
		return callerAdminForms;
	}
	
	//Get Particular Entry
	public CallerAdminForm getCallerAdmin(Integer callerAdminId)
	{
		CallerAdmin callerAdmin=new CallerAdmin();
		
		callerAdmin=callerAdminDAO.get(callerAdminId);
		
		//TODO: Convert Entity to Form
		//Start
		
		CallerAdminForm callerAdminForm=new CallerAdminForm(callerAdmin.getCallerAdminId(), callerAdmin.getUsers().getUserId(), callerAdmin.getUsers().getUsername(), callerAdmin.getFirstName(), callerAdmin.getLastName(),
				callerAdmin.getStreet(), callerAdmin.getCity(), callerAdmin.getState(), callerAdmin.getZipcode(), callerAdmin.getEmailAddress(), callerAdmin.getPhoneNumber(), 
				callerAdmin.getNotes(), callerAdmin.getIsDrivingDistance(), callerAdmin.getUsers().getIsPrivilegedUser(), callerAdmin.getStatus());
		
		callerAdminForm.setProductToken(callerAdmin.getUsers().getProductToken());
		// County List
		callerAdminForm.setCountyForms(countyService.getCountyList());
		
		// Mapped Counties
		List<Integer> countyMapped=new ArrayList<Integer>();
		List<CallerAdminCountyMap> callerAdminCountyMaps=callerAdminCountyMapDAO.getCallerAdminCountyMapByCallerAdminId(callerAdminId);
		for (CallerAdminCountyMap callerAdminCountyMap : callerAdminCountyMaps) {
			countyMapped.add(callerAdminCountyMap.getId().getCountyId());
		}
		callerAdminForm.setCounty(countyMapped);
		//End
		
		return callerAdminForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeCallerAdmin(CallerAdminForm callerAdminForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		CallerAdmin callerAdmin=new CallerAdmin();
		
		//Logic Ends
		
		
		callerAdminDAO.merge(callerAdmin);
		return 1;
	}
	
	//Save an Entry
	public int saveCallerAdmin(CallerAdminForm callerAdminForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		Users users = new Users();
		Roles roles = new Roles();
		if(callerAdminForm.getRoleId()==2){
			roles=roleDAO.get(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID);
		}else{
			roles=roleDAO.get(InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID);
		}
				
		users.setUsername(callerAdminForm.getUsername());
		users.setPassword(callerAdminForm.getUsername());
		users.setIsEnable(1);
		users.setStatus(1);
		users.setIsPasswordChanged(0);
		users.setIsDisclaimerAccepted(0);
		users.setRoles(roles);
		users.setProductToken(callerAdminForm.getProductToken());
		users.setIsPrivilegedUser(callerAdminForm.getIsPrivilegedUser());
		usersDAO.save(users);
		
		CallerAdmin callerAdmin=new CallerAdmin(users, callerAdminForm.getFirstName(), callerAdminForm.getLastName(), 
												callerAdminForm.getStreet(), callerAdminForm.getCity(), callerAdminForm.getState(), callerAdminForm.getZipcode(), callerAdminForm.getEmailAddress(), 
												callerAdminForm.getPhoneNumber(), callerAdminForm.getNotes(), callerAdminForm.getIsDrivingDistance(), 1, null, null, null, null, null);
		callerAdminDAO.save(callerAdmin);
		
		List<Integer> mappedCounty=callerAdminForm.getCounty();
		for (Integer countyId : mappedCounty) {
			County county=countyDAO.get(countyId);
			CallerAdminCountyMapId callerAdminCountyMapId=new CallerAdminCountyMapId(callerAdmin.getCallerAdminId(), countyId);
			CallerAdminCountyMap callerAdminCountyMap=new CallerAdminCountyMap(callerAdminCountyMapId, callerAdmin, county,new Date(),1);
			callerAdminCountyMapDAO.save(callerAdminCountyMap);
			
		}
		
		//Logic Ends
		
		
		return 1;
	}
	
	//Update an Entry
	public int updateCallerAdmin(CallerAdminForm callerAdminForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		CallerAdmin callerAdmin=callerAdminDAO.get(callerAdminForm.getCallerAdminId());
		Users users=usersDAO.get(callerAdmin.getUsers().getUserId());
		users.setProductToken(callerAdminForm.getProductToken());
		users.setIsPrivilegedUser(callerAdminForm.getIsPrivilegedUser());
		usersDAO.update(users);
		callerAdmin=new CallerAdmin(users, callerAdminForm.getFirstName(), callerAdminForm.getLastName(), 
				callerAdminForm.getStreet(), callerAdminForm.getCity(), callerAdminForm.getState(), callerAdminForm.getZipcode(), callerAdminForm.getEmailAddress(), 
				callerAdminForm.getPhoneNumber(), callerAdminForm.getNotes(), callerAdminForm.getIsDrivingDistance(), callerAdminForm.getStatus(), null, null, null, null, null);
		callerAdmin.setCallerAdminId(callerAdminForm.getCallerAdminId());
		callerAdminDAO.update(callerAdmin);
		
		// Delete Unmapped County
		callerAdminCountyMapService.deleteCallerAdminCountyMap(callerAdminForm.getCounty(), callerAdminForm.getCallerAdminId(),users.getUserId());
		
		// Get Newly Added County List
		List<Integer> newlyMappedCounty=callerAdminCountyMapService.getNewlyAddedCountyId(callerAdminForm.getCounty(), callerAdminForm.getCallerAdminId());
		// Save Newly Added county
		for (Integer countyId : newlyMappedCounty) {
			County county=countyDAO.get(countyId);
			CallerAdminCountyMapId callerAdminCountyMapId=new CallerAdminCountyMapId(callerAdmin.getCallerAdminId(), countyId);
			CallerAdminCountyMap callerAdminCountyMap=new CallerAdminCountyMap(callerAdminCountyMapId, callerAdmin, county,new Date(),1);
			callerAdminCountyMapDAO.save(callerAdminCountyMap);
		}
		//Logic Ends
		
		callerAdminDAO.update(callerAdmin);
		return 1;
	}
	
	//Delete an Entry
	public int deleteCallerAdmin(Integer id)
	{
		callerAdminDAO.delete(id);
		return 1;
	}
	
	
	// Get User Id By Caller Admin Id
	public Integer getUserIdByCallerAdminId(Integer callerAdminId){
		Integer userId=callerAdminDAO.getUserIdByCallerAdminId(callerAdminId);
		
		return userId;
	}
	
	//Enable or Disbale Caller Admin
	public int enableOrDisableCallerAdmin(Integer callerAdminId)
	{
		CallerAdmin callerAdmin=callerAdminDAO.get(callerAdminId);
		Users users=usersDAO.get(callerAdmin.getUsers().getUserId());
		if(users.getIsEnable()==0){
			users.setIsEnable(1);
			callerAdminDAO.enable(callerAdminId);
				
			//Enable Callers
			List<Caller> callers=callerDAO.getCallerByCallerAdminId(callerAdminId);			
			for (Caller caller : callers) {
				Users callerUser=caller.getUsers();
				if(caller.getStatus()==1){
					callerUser.setIsEnable(1);
					usersDAO.update(caller.getUsers());
				}			
			}
			
		}else if(users.getIsEnable()==1){
			users.setIsEnable(0);
			callerAdminDAO.disable(callerAdminId);
			
			//Disable Callers
			List<Caller> callers=callerDAO.getCallerByCallerAdminId(callerAdminId);			
			for (Caller caller : callers) {
				Users callerUser=caller.getUsers();
				callerUser.setIsEnable(0);
				usersDAO.update(caller.getUsers());
			}
		}
		
		usersDAO.update(users);
		return 1;
	}
	
	// Calling from Marketing App
	public int enableOrDisableCallerAdminAndCallers(Integer callerAdminId,Integer status)
	{
		CallerAdmin callerAdmin=callerAdminDAO.get(callerAdminId);
		Users users=usersDAO.get(callerAdmin.getUsers().getUserId());
		if(status==1){
			users.setIsEnable(1);
			callerAdminDAO.enable(callerAdminId);
				
			//Enable Callers
			List<Caller> callers=callerDAO.getCallerByCallerAdminId(callerAdminId);			
			for (Caller caller : callers) {
				Users callerUser=caller.getUsers();
				if(caller.getStatus()==1){
					callerUser.setIsEnable(1);
					usersDAO.update(caller.getUsers());
				}			
			}
			
		}else if(status==0){
			users.setIsEnable(0);
			callerAdminDAO.disable(callerAdminId);
			
			//Disable Callers
			List<Caller> callers=callerDAO.getCallerByCallerAdminId(callerAdminId);			
			for (Caller caller : callers) {
				Users callerUser=caller.getUsers();
				callerUser.setIsEnable(0);
				usersDAO.update(caller.getUsers());
			}
		}
		
		usersDAO.update(users);
		return 1;
	}
	
	// Reset Caller Admin Password
	public Integer resetCallerAdminPassword(Integer callerAdminId){
		Integer status=1;
		CallerAdmin callerAdmin=callerAdminDAO.get(callerAdminId);
		usersDAO.resetUserPassword(callerAdmin.getUsers().getUserId());
		return status;
	}
	
	// Get Number of Caller Admin
	public Integer getNumberOfCallerAdmins(){
		List<CallerAdminForm> callerAdminForms=callerAdminDAO.getCallerAdminListByRoleId(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID);
		return callerAdminForms.size();
	}
	
	// Get Number of Manager
	public Integer getNumberOfManagers(){
		List<CallerAdminForm> callerAdminForms=callerAdminDAO.getCallerAdminListByRoleId(InjuryConstants.INJURY_AUTO_MANAGER_ROLE_ID);
		return callerAdminForms.size();
	}
	
	//Get Caller Admin by User Id
	public CallerAdmin getCallerAdminByUserId(Integer userId){
		return callerAdminDAO.getCallerAdminByUserId(userId);
	}
	
	
}
