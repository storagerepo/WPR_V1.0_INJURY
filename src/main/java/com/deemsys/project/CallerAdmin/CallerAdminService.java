package com.deemsys.project.CallerAdmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.CallerAdminCountyMapping.CallerAdminCountyMapDAO;
import com.deemsys.project.CallerAdminCountyMapping.CallerAdminCountyMapService;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
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
	CallerAdminCountyMapService callerAdminCountyMapService;
	
	//Get All Entries
	public List<CallerAdminForm> getCallerAdminList()
	{
		List<CallerAdminForm> callerAdminForms=new ArrayList<CallerAdminForm>();
		
		List<CallerAdmin> callerAdmins=new ArrayList<CallerAdmin>();
		
		callerAdmins=callerAdminDAO.getAll();
		
		for (CallerAdmin callerAdmin : callerAdmins) {
			//TODO: Fill the List
			CallerAdminForm callerAdminForm=new CallerAdminForm(callerAdmin.getCallerAdminId(), callerAdmin.getUsers().getUserId(), callerAdmin.getUsers().getUsername(), callerAdmin.getFirstName(), callerAdmin.getLastName(),
					callerAdmin.getStreet(), callerAdmin.getCity(), callerAdmin.getState(), callerAdmin.getZipcode(), callerAdmin.getEmailAddress(), callerAdmin.getPhoneNumber(), 
					callerAdmin.getNotes(), callerAdmin.getStatus());
			callerAdminForms.add(callerAdminForm);
		}
		
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
				callerAdmin.getNotes(), callerAdmin.getStatus());
		
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
		Roles roles = roleDAO.get(InjuryConstants.INJURY_CALLER_ADMIN_ROLE_ID);
		users.setUsername(callerAdminForm.getUsername());
		users.setPassword(callerAdminForm.getUsername());
		users.setIsEnable(1);
		users.setStatus(1);
		users.setRoles(roles);
		usersDAO.save(users);
		
		CallerAdmin callerAdmin=new CallerAdmin(users, callerAdminForm.getFirstName(), callerAdminForm.getLastName(), 
												callerAdminForm.getStreet(), callerAdminForm.getCity(), callerAdminForm.getState(), callerAdminForm.getZipcode(), callerAdminForm.getEmailAddress(), 
												callerAdminForm.getPhoneNumber(), callerAdminForm.getNotes(), 1, null, null, null, null);
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
		callerAdmin=new CallerAdmin(users, callerAdminForm.getFirstName(), callerAdminForm.getLastName(), 
				callerAdminForm.getStreet(), callerAdminForm.getCity(), callerAdminForm.getState(), callerAdminForm.getZipcode(), callerAdminForm.getEmailAddress(), 
				callerAdminForm.getPhoneNumber(), callerAdminForm.getNotes(), 1, null, null, null, null);
		callerAdmin.setCallerAdminId(callerAdminForm.getCallerAdminId());
		callerAdminDAO.update(callerAdmin);
		
		// Delete Unmapped County
		callerAdminCountyMapService.deleteCallerAdminCountyMap(callerAdminForm.getCounty(), callerAdminForm.getCallerAdminId());
		
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
		}else if(users.getIsEnable()==1){
			users.setIsEnable(0);
			callerAdminDAO.disable(callerAdminId);
		}
		
		usersDAO.update(users);
		return 1;
	}
	
	// Reset Caller Admin Password
	public Integer resetCallerAdminPassword(Integer callerAdminId){
		Integer status=1;
		CallerAdmin callerAdmin=callerAdminDAO.get(callerAdminId);
		Users users=usersDAO.get(callerAdmin.getUsers().getUserId());
		users.setPassword(users.getUsername());
		usersDAO.update(users);
		return status;
	}
}
