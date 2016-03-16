package com.deemsys.project.Caller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.CallerAdmin;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.Roles;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.Users;
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


	// Get All Entries
	public List<CallerForm> getCallerList() {
		List<CallerForm> callerForms = new ArrayList<CallerForm>();

		List<Caller> callers = new ArrayList<Caller>();

		callers = callerDAO.getAll();

		for (Caller caller : callers) {
			// TODO: Fill the List
			Integer patientSize = patientsDAO.getPatientListByCallerId(caller.getCallerId()).size();
			CallerForm callerForm = new CallerForm(caller.getCallerId(), caller.getUsers()
					.getUsername(), caller.getUsers().getPassword(),
					caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(), caller.getStatus(), patientSize);
			callerForms.add(callerForm);

		}

		return callerForms;
	}

	// Get Particular Entry
	public CallerForm getCaller(Integer getId) {
		Caller caller = new Caller();

		caller = callerDAO.get(getId);
		CallerForm callerForm = new CallerForm();
		// TODO: Convert Entity to Form
		// Start
		if (caller != null) {
			callerForm = new CallerForm(caller.getCallerId(), caller.getUsers()
					.getUsername(), caller.getUsers().getPassword(),
					caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(), caller.getStatus());
		} else {
			callerForm = new CallerForm();
		}
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
		caller.setCallerId(callerForm.getId());

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
		CallerAdmin callerAdmin = new CallerAdmin();
		Users users = new Users();
		Roles role = new Roles();
		users.setUsername(callerForm.getUsername());
		users.setPassword(callerForm.getUsername());
		users.setIsEnable(1);
		Caller caller = new Caller(callerAdmin, users, callerForm.getFirstName(),
				callerForm.getLastName(), callerForm.getPhoneNumber(),
				callerForm.getEmailAddress(), callerForm.getNotes(), 1, null,null);

		callerDAO.save(caller);

		role = roleDAO.get(InjuryConstants.INJURY_CALLER_ROLE_ID);
		users.setRoles(role);
		usersDAO.save(users);
		// Logic Ends
		return 1;
	}

	// Update an Entry
	public int updateCaller(CallerForm callerForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Caller caller = callerDAO.get(callerForm.getId());
		Users users = usersDAO.get(caller.getUsers().getUserId());

		caller.setUsers(users);
		caller.setFirstName(callerForm.getFirstName());
		caller.setLastName(callerForm.getLastName());
		caller.setPhoneNumber(callerForm.getPhoneNumber());
		caller.setEmailAddress(callerForm.getEmailAddress());
		caller.setNotes(callerForm.getNotes());
		caller.setStatus(1);
		caller.setCallerId(callerForm.getId());

		callerDAO.update(caller);

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

	public Integer getNoOfCallers() {
		List<Caller> callers = new ArrayList<Caller>();
		callers = callerDAO.getAll();

		return callers.size();

	}

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

	public List<CallerForm> getCallerId() {
		List<CallerForm> callerForms = new ArrayList<CallerForm>();

		List<Caller> callers = new ArrayList<Caller>();

		callers = callerDAO.getAll();

		for (Caller caller : callers) {

			CallerForm callerForm = new CallerForm(caller.getCallerId(), caller.getUsers()
					.getUsername(), caller.getUsers().getPassword(),
					caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(), caller.getStatus());
			callerForms.add(callerForm);

		}

		return callerForms;
	}

	public Integer getCurrentUserId() {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String username = user.getUsername();
		System.out.println(username);
		Users users = usersDAO.getByUserName(username);
		return users.getUserId();
	}

	public Integer getUsername(String username) {
		Integer count = 0;

		Users users = new Users();

		users = usersDAO.getByUserName(username);
		// Start
		if (users != null) {
			System.out.println(count++);
			return count++;
		} else {

			return count;
		}
		// End
	}

	public String[] getusers() {
		String[] currentuser = new String[100];
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		currentuser[0] = user.getUsername();
		return currentuser;
	}

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
			callerForm = new CallerForm(caller.getCallerId(), caller.getUsers()
					.getUsername(), caller.getUsers().getPassword(),
					caller.getFirstName(), caller.getLastName(),
					caller.getPhoneNumber(), caller.getEmailAddress(),
					caller.getNotes(), caller.getStatus());
		} else {
			callerForm = new CallerForm();
		}
		return callerForm;
	}

	public Integer disableCaller(Integer getId) {
		Integer status = 0;
		Caller caller = new Caller();

		caller = callerDAO.get(getId);
		Users users = usersDAO.get(caller.getUsers().getUserId());

		if (caller.getStatus() == 1) {
			status = callerDAO.isDisable(getId);
			users.setIsEnable(0);
		} else if (caller.getStatus() == 0) {
			status = callerDAO.isEnable(getId);
			users.setIsEnable(1);
		}
		System.out.println(status);
		usersDAO.merge(users);
		return status;
	}

	public Integer resetPassword(Integer getId) {
		Integer status = 1;
		Caller caller = new Caller();
		caller = callerDAO.get(getId);

		status = usersDAO.resetUserPassword(caller.getUsers().getUserId());
		return status;
	}
}
