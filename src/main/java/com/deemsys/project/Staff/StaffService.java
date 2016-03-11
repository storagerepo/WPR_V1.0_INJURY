package com.deemsys.project.Staff;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.UserRoleMapping.UserRoleDAO;
import com.deemsys.project.Users.UsersDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Role;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.entity.UserRoleMapping;
import com.deemsys.project.entity.Users;
import com.deemsys.project.patients.PatientsDAO;
import com.deemsys.project.patients.PatientsForm;

/**
 * 
 * @author Deemsys
 * 
 *         Staff - Entity staff - Entity Object staffs - Entity List staffDAO -
 *         Entity DAO staffForms - EntityForm List StaffForm - EntityForm
 * 
 */
@Service
@Transactional
public class StaffService {

	@Autowired
	StaffDAO staffDAO;

	@Autowired
	PatientsDAO patientsDAO;

	@Autowired
	UsersDAO usersDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	UserRoleDAO userRoleDAO;

	// Get All Entries
	public List<StaffForm> getStaffList() {
		List<StaffForm> staffForms = new ArrayList<StaffForm>();

		List<Staff> staffs = new ArrayList<Staff>();

		staffs = staffDAO.getAll();

		for (Staff staff : staffs) {
			// TODO: Fill the List
			Integer patientSize = patientsDAO.getPatientListByStaffId(
					staff.getId()).size();
			StaffForm staffForm = new StaffForm(staff.getId(), staff.getUsers()
					.getUsername(), staff.getUsers().getPassword(),
					staff.getFirstName(), staff.getLastName(),
					staff.getPhoneNumber(), staff.getEmailAddress(),
					staff.getNotes(), staff.getStatus(), patientSize);
			staffForms.add(staffForm);

		}

		return staffForms;
	}

	// Get Particular Entry
	public StaffForm getStaff(Integer getId) {
		Staff staff = new Staff();

		staff = staffDAO.get(getId);
		StaffForm staffForm = new StaffForm();
		// TODO: Convert Entity to Form
		// Start
		if (staff != null) {
			staffForm = new StaffForm(staff.getId(), staff.getUsers()
					.getUsername(), staff.getUsers().getPassword(),
					staff.getFirstName(), staff.getLastName(),
					staff.getPhoneNumber(), staff.getEmailAddress(),
					staff.getNotes(), staff.getStatus());
		} else {
			staffForm = new StaffForm();
		}
		// End

		return staffForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeStaff(StaffForm staffForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Users users = new Users();
		UserRoleMapping userRoleMapping = new UserRoleMapping();
		Role role = new Role();
		users.setUsername(staffForm.getUsername());
		users.setPassword(staffForm.getUsername());
		users.setIsEnable(1);
		Staff staff = new Staff(users, staffForm.getFirstName(),
				staffForm.getLastName(), staffForm.getPhoneNumber(),
				staffForm.getEmailAddress(), staffForm.getNotes(), 1, null);
		staff.setId(staffForm.getId());

		staffDAO.merge(staff);

		role = roleDAO.get(InjuryConstants.INJURY_STAFF_ROLE_ID);
		userRoleMapping.setRole(role);
		userRoleMapping.setUsers(users);
		userRoleDAO.save(userRoleMapping);

		// Logic Ends
		return 1;
	}

	// Save an Entry
	public int saveStaff(StaffForm staffForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Users users = new Users();
		UserRoleMapping userRoleMapping = new UserRoleMapping();
		Role role = new Role();
		users.setUsername(staffForm.getUsername());
		users.setPassword(staffForm.getUsername());
		users.setIsEnable(1);
		Staff staff = new Staff(users, staffForm.getFirstName(),
				staffForm.getLastName(), staffForm.getPhoneNumber(),
				staffForm.getEmailAddress(), staffForm.getNotes(), 1, null);

		staffDAO.save(staff);

		role = roleDAO.get(InjuryConstants.INJURY_STAFF_ROLE_ID);
		userRoleMapping.setRole(role);
		userRoleMapping.setUsers(users);
		userRoleDAO.save(userRoleMapping);
		// Logic Ends
		return 1;
	}

	// Update an Entry
	public int updateStaff(StaffForm staffForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Staff staff = staffDAO.get(staffForm.getId());
		Users users = usersDAO.get(staff.getUsers().getUserId());

		staff.setUsers(users);
		staff.setFirstName(staffForm.getFirstName());
		staff.setLastName(staffForm.getLastName());
		staff.setPhoneNumber(staffForm.getPhoneNumber());
		staff.setEmailAddress(staffForm.getEmailAddress());
		staff.setNotes(staffForm.getNotes());
		staff.setStatus(1);
		staff.setId(staffForm.getId());

		staffDAO.update(staff);

		// Logic Ends
		return 1;
	}

	// Delete an Entry
	public int deleteStaff(Integer id) {
		List<Patients> patientss = new ArrayList<Patients>();
		int status = 0;
		patientss = staffDAO.getPatientsByStaffId(id);
		if (patientss.size() == 0) {
			Staff staff = staffDAO.get(id);
			Users users = usersDAO.get(staff.getUsers().getUserId());
			userRoleDAO.deletebyUserId(users.getUserId());

			staffDAO.delete(id);
			status = 1;
		} else {
			status = 0;
		}

		return status;
	}

	public Integer getNoOfStaffs() {
		List<Staff> staffs = new ArrayList<Staff>();
		staffs = staffDAO.getAll();

		return staffs.size();

	}

	// Get Current User Role
	public String getCurrentRole() {

		String currentRole = "";
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Object[] role = user.getAuthorities().toArray();

		if (role[0].toString().equals(InjuryConstants.INJURY_ADMIN_ROLE)) {
			currentRole = InjuryConstants.INJURY_ADMIN_ROLE;
		} else if (role[0].toString().equals(InjuryConstants.INJURY_STAFF_ROLE)) {
			currentRole = InjuryConstants.INJURY_STAFF_ROLE;
		} else if (role[0].toString().equals(
				InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)) {
			currentRole = InjuryConstants.INJURY_LAWYER_ADMIN_ROLE;
		} else if (role[0].toString()
				.equals(InjuryConstants.INJURY_LAWYER_ROLE)) {
			currentRole = InjuryConstants.INJURY_LAWYER_ROLE;
		}
		return currentRole;
	}

	public List<StaffForm> getStaffId() {
		List<StaffForm> staffForms = new ArrayList<StaffForm>();

		List<Staff> staffs = new ArrayList<Staff>();

		staffs = staffDAO.getAll();

		for (Staff staff : staffs) {

			StaffForm staffForm = new StaffForm(staff.getId(), staff.getUsers()
					.getUsername(), staff.getUsers().getPassword(),
					staff.getFirstName(), staff.getLastName(),
					staff.getPhoneNumber(), staff.getEmailAddress(),
					staff.getNotes(), staff.getStatus());
			staffForms.add(staffForm);

		}

		return staffForms;
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

	public StaffForm getDetails() {
		// TODO Auto-generated method stub

		Staff staff = new Staff();
		String[] users = getusers();
		staff = staffDAO.getDetails(users[0]);

		StaffForm staffForm = new StaffForm();
		if (staff != null) {
			staffForm = new StaffForm(staff.getId(), staff.getUsers()
					.getUsername(), staff.getUsers().getPassword(),
					staff.getFirstName(), staff.getLastName(),
					staff.getPhoneNumber(), staff.getEmailAddress(),
					staff.getNotes(), staff.getStatus());
		} else {
			staffForm = new StaffForm();
		}
		return staffForm;
	}

	public Integer disableStaff(Integer getId) {
		Integer status = 0;
		Staff staff = new Staff();

		staff = staffDAO.get(getId);
		Users users = usersDAO.get(staff.getUsers().getUserId());

		if (staff.getStatus() == 1) {
			status = staffDAO.isDisable(getId);
			users.setIsEnable(0);
		} else if (staff.getStatus() == 0) {
			status = staffDAO.isEnable(getId);
			users.setIsEnable(1);
		}
		System.out.println(status);
		usersDAO.merge(users);
		return status;
	}

	public Integer resetPassword(Integer getId) {
		Integer status = 1;
		Staff staff = new Staff();
		staff = staffDAO.get(getId);

		status = usersDAO.resetUserPassword(staff.getUsers().getUserId());
		return status;
	}
}
