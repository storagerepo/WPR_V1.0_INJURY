package com.deemsys.project.Caller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.CallerAdmin.CallerAdminDAO;
import com.deemsys.project.CallerCountyMap.CallerCountyMapDAO;
import com.deemsys.project.CallerCountyMap.CallerCountyMapService;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.PatientCallerMap.PatientCallerService;
import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.CallerAdminCountyMap;
import com.deemsys.project.entity.CallerAdminCountyMapId;
import com.deemsys.project.entity.CallerCountyMap;
import com.deemsys.project.entity.CallerCountyMapId;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.Roles;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.Users;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.PatientDAO;
import com.deemsys.project.patient.PatientForm;

/**
 * 
 * @author Deemsys
 * 
 *         Caller - Entity caller - Entity Object callers - Entity List callerDAO -
 *         Entity DAO callerForms - EntityForm List CallerForm - EntityForm
 * 
 */
@Service
@Transactional
public class CallerService {

	@Autowired
	CallerDAO callerDAO;

	@Autowired
	PatientDAO patientsDAO;

	@Autowired
	UsersDAO usersDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	CallerAdminDAO callerAdminDAO;
	
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	CallerCountyMapDAO callerCountyMapDAO;
	
	@Autowired
	CallerCountyMapService callerCountyMapService;
	
	@Autowired
	CountyService countyService;
	
	@Autowired
	PatientCallerService patientCallerService;
	
	@Autowired
	LoginService loginService;
	
	// Get All Entries
	public List<CallerForm> getCallerList() {
		List<CallerForm> callerForms = new ArrayList<CallerForm>();

		List<Caller> callers = new ArrayList<Caller>();

		callers = callerDAO.getAll();

		for (Caller caller : callers) {
			// TODO: Fill the List
			CallerForm callerForm = new CallerForm(caller.getCallerId(),caller.getCallerAdmin().getCallerAdminId(),
					caller.getUsers().getUserId(), caller.getUsers().getUsername(),
					caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(),caller.getStatus());
			callerForms.add(callerForm);

		}

		return callerForms;
	}

	// Get All Callers Under Caller Admin
	public List<CallerForm> getCallerListByCallerAdmin() {
		List<CallerForm> callerForms = new ArrayList<CallerForm>();

		List<Caller> callers = new ArrayList<Caller>();
		Integer callerAdminId=callerAdminDAO.getCallerAdminByUserId(getCurrentUserId()).getCallerAdminId();
		callers = callerDAO.getCallerByCallerAdminId(callerAdminId);

		for (Caller caller : callers) {
			// TODO: Fill the List
			CallerForm callerForm = new CallerForm(caller.getCallerId(),caller.getCallerAdmin().getCallerAdminId(),
					caller.getUsers().getUserId(), caller.getUsers().getUsername(),
					caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(),caller.getStatus());
			callerForms.add(callerForm);

		}

		return callerForms;
	}
	
	// Get Particular Entry
	public CallerForm getCaller(Integer callerId) {
		Caller caller = new Caller();

		caller = callerDAO.get(callerId);
		CallerForm callerForm = new CallerForm();
		// TODO: Convert Entity to Form
		// Start
			callerForm = new CallerForm(caller.getCallerId(),caller.getCallerAdmin().getCallerAdminId(),caller.getUsers().getUserId(), 
					caller.getUsers().getUsername(),
					caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(), caller.getStatus());
			
			// County List
			callerForm.setCountyForms(countyService.getCountyList());
			
			// Mapped Counties
			List<Integer> countyMapped=new ArrayList<Integer>();
			List<CallerCountyMap> callerCountyMaps=callerCountyMapDAO.getCallerCountyMapByCallerId(callerId);
			for (CallerCountyMap callerCountyMap : callerCountyMaps) {
				countyMapped.add(callerCountyMap.getId().getCountyId());
			}
			callerForm.setCounty(countyMapped);
		
		// End

		return callerForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeCaller(CallerForm callerForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		CallerAdmin callerAdmin= new CallerAdmin();
		
		Users users = new Users();
		Roles role = new Roles();
		users.setUsername(callerForm.getUsername());
		users.setPassword(callerForm.getUsername());
		users.setIsEnable(1);
		Caller caller = new Caller(callerAdmin, users, callerForm.getFirstName(),
				callerForm.getLastName(), callerForm.getPhoneNumber(),
				callerForm.getEmailAddress(), callerForm.getNotes(), 1, null,null);
		caller.setCallerId(callerForm.getCallerId());

		callerDAO.merge(caller);

		role = roleDAO.get(InjuryConstants.INJURY_CALLER_ROLE_ID);
		users.setRoles(role);
		usersDAO.save(users);

		// Logic Ends
		return 1;
	}

	// Save an Entry
	public int saveCaller(CallerForm callerForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Users users = new Users();
		Roles role = new Roles();
		users.setUsername(callerForm.getUsername());
		users.setPassword(callerForm.getUsername());
		users.setIsEnable(1);
		users.setStatus(1);
		role = roleDAO.get(InjuryConstants.INJURY_CALLER_ROLE_ID);
		users.setRoles(role);
		usersDAO.save(users);
		
		CallerAdmin callerAdmin = callerAdminDAO.getCallerAdminByUserId(getCurrentUserId());
		Caller caller = new Caller(callerAdmin, users, callerForm.getFirstName(),
				callerForm.getLastName(), callerForm.getPhoneNumber(),
				callerForm.getEmailAddress(), callerForm.getNotes(), 1, null,null);

		callerDAO.save(caller);

		List<Integer> mappedCounty=callerForm.getCounty();
		for (Integer countyId : mappedCounty) {
			County county=countyDAO.get(countyId);
			CallerCountyMapId callerCountyMapId=new CallerCountyMapId(caller.getCallerId(), countyId);
			CallerCountyMap callerCountyMap=new CallerCountyMap(callerCountyMapId, caller, county,new Date(),1);
			callerCountyMapDAO.save(callerCountyMap);
			
		}
		
		// Logic Ends
		return 1;
	}

	// Update an Entry
	public int updateCaller(CallerForm callerForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Caller caller = callerDAO.get(callerForm.getCallerId());
		Users users = usersDAO.get(caller.getUsers().getUserId());
		CallerAdmin callerAdmin = callerAdminDAO.getCallerAdminByUserId(getCurrentUserId());
		caller.setUsers(users);
		caller.setCallerAdmin(callerAdmin);
		caller.setFirstName(callerForm.getFirstName());
		caller.setLastName(callerForm.getLastName());
		caller.setPhoneNumber(callerForm.getPhoneNumber());
		caller.setEmailAddress(callerForm.getEmailAddress());
		caller.setNotes(callerForm.getNotes());
		caller.setStatus(1);
		caller.setCallerId(callerForm.getCallerId());

		callerDAO.update(caller);
		// Delete Unmapped County
		callerCountyMapService.deleteCallerCountyMap(callerForm.getCounty(), callerForm.getCallerId());
				
		// Get Newly Added County List
		List<Integer> newlyMappedCounty=callerCountyMapService.getNewlyAddedCountyId(callerForm.getCounty(), callerForm.getCallerId());
		// Save Newly Added county
		for (Integer countyId : newlyMappedCounty) {
				County county=countyDAO.get(countyId);
				CallerCountyMapId callerCountyMapId=new CallerCountyMapId(caller.getCallerId(), countyId);
				CallerCountyMap callerCountyMap=new CallerCountyMap(callerCountyMapId, caller, county,new Date(),1);
				callerCountyMapDAO.save(callerCountyMap);
			}
		// Logic Ends
		return 1;
	}

	// Delete an Entry
	public int deleteCaller(Integer id) {
		List<Patient> patientss = new ArrayList<Patient>();
		int status = 0;
		patientss = callerDAO.getPatientByCallerId(id);
		if (patientss.size() == 0) {
			Caller caller = callerDAO.get(id);
			Users users = usersDAO.get(caller.getUsers().getUserId());
			usersDAO.delete(users.getUserId());

			callerDAO.delete(id);
			status = 1;
		} else {
			status = 0;
		}

		return status;
	}

	// Get No Of Callers Under Caller Admin
	public Integer getNoOfCallers() {
		List<Caller> callers = new ArrayList<Caller>();
		Integer callerAdminId=callerAdminDAO.getCallerAdminByUserId(getCurrentUserId()).getCallerAdminId();
		callers=callerDAO.getCallerByCallerAdminId(callerAdminId);

		return callers.size();

	}

	// Get Current User Role
	public String getCurrentRole() {
		return loginService.getCurrentRole();
	}

	public List<CallerForm> getCallerId() {
		List<CallerForm> callerForms = new ArrayList<CallerForm>();

		List<Caller> callers = new ArrayList<Caller>();

		callers = callerDAO.getAll();

		for (Caller caller : callers) {

			CallerForm callerForm = new CallerForm(caller.getCallerId(),
					caller.getCallerAdmin().getCallerAdminId(),
					caller.getUsers().getUserId(), caller.getUsers()
					.getUsername(),caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(), caller.getStatus());
			callerForms.add(callerForm);

		}

		return callerForms;
	}

	// get Current User Id
	public Integer getCurrentUserId() {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String username = user.getUsername();
		System.out.println(username);
		Users users = usersDAO.getByUserName(username);
		return users.getUserId();
	}

	// Get Current User Details
	public String[] getusers() {
		String[] currentuser = new String[100];
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		currentuser[0] = user.getUsername();
		return currentuser;
	}

	// Check Password for Caller
	public Integer checkPassword(String oldPassword) {
		// TODO Auto-generated method stub
		Integer status = 0;
		String[] users = getusers();
		List<Users> user = usersDAO.checkPassword(oldPassword, users[0]);

		if (user.size() > 0) {
			if (oldPassword.equals(user.get(0).getPassword())) {
				status = 1;
			} else {
				status = 0;
			}
		} else {
			status = 0;
		}
		return status;
	}

	// Change the Caller Password
	public Integer changePassword(String newPassword) {
		// TODO Auto-generated method stub
		String[] users = getusers();
		usersDAO.changePassword(newPassword, users[0]);
		return 1;
	}

	public CallerForm getDetails() {
		// TODO Auto-generated method stub

		Caller caller = new Caller();
		String[] users = getusers();
		caller = callerDAO.getDetails(users[0]);

		CallerForm callerForm = new CallerForm();
		if (caller != null) {
			callerForm = new CallerForm(caller.getCallerId(), caller.getCallerAdmin().getCallerAdminId(),caller.getUsers().getUserId(),
					caller.getUsers().getUsername(),caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(), caller.getStatus());
		} else {
			callerForm = new CallerForm();
		}
		return callerForm;
	}

	// Enable or Disable Caller
	public Integer enableOrDisableCaller(Integer callerId) {
		Integer status = 0;
		Caller caller = callerDAO.get(callerId);
		Users users = usersDAO.get(caller.getUsers().getUserId());

		if (users.getIsEnable() == 1) {
			callerDAO.disable(callerId);
			users.setIsEnable(0);
			status=0;
		} else if (users.getIsEnable() == 0) {
			callerDAO.enable(callerId);
			users.setIsEnable(1);
			status=1;
		}
		usersDAO.update(users);
		return status;
	}

	// Reset the Caller Password
	public Integer resetPassword(Integer callerId) {
		Integer status = 1;
		Caller caller = callerDAO.get(callerId);
		status = usersDAO.resetUserPassword(caller.getUsers().getUserId());
		return status;
	}
	
	public Caller getCallerByUserId(Integer userId){
		return callerDAO.getByUserId(userId);
	}
	
}
