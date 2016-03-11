package com.deemsys.project.Lawyers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.LawyerCountyMapping;
import com.deemsys.project.entity.Lawyers;
import com.deemsys.project.entity.Role;
import com.deemsys.project.entity.UserRoleMapping;
import com.deemsys.project.entity.Users;
import com.deemsys.project.patients.PatientsForm;
import com.deemsys.project.patients.PatientsService;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyForm;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.LawyerAdmin.LawyerAdminDAO;
import com.deemsys.project.LawyerCountyMapping.LawyerCountyMappingDAO;
import com.deemsys.project.LawyerCountyMapping.LawyerCountyMappingForm;
import com.deemsys.project.LawyerCountyMapping.LawyerCountyMappingService;
import com.deemsys.project.Lawyers.LawyersDAO;
import com.deemsys.project.Lawyers.LawyersForm;
import com.deemsys.project.Role.RoleDAO;
import com.deemsys.project.Staff.StaffService;
import com.deemsys.project.UserRoleMapping.UserRoleDAO;
import com.deemsys.project.Users.UsersDAO;

@Service
@Transactional
public class LawyersService {

	@Autowired
	LawyersDAO lawyersDAO;

	@Autowired
	LawyerAdminDAO lawyerAdminDAO;

	@Autowired
	UsersDAO usersDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	UserRoleDAO userRoleDAO;

	@Autowired
	StaffService staffService;

	@Autowired
	CountyDAO countyDAO;

	@Autowired
	CountyService countyService;

	@Autowired
	LawyerCountyMappingDAO lawyerCountyMappingDAO;

	@Autowired
	LawyerCountyMappingService lawyerCountyMappingService;

	@Autowired
	PatientsService patientsService;

	// Get All Entries
	public List<LawyersForm> getLawyersList() {
		List<LawyersForm> lawyersForms = new ArrayList<LawyersForm>();

		List<Lawyers> lawyerss = new ArrayList<Lawyers>();

		lawyerss = lawyersDAO.getAll();

		for (Lawyers lawyers : lawyerss) {
			// TODO: Fill the List
			LawyersForm lawyersForm = new LawyersForm(lawyers.getId(), lawyers
					.getLawyerAdmin().getId(), lawyers.getUsers().getUserId(),
					lawyers.getFirstName(), lawyers.getLastName(),
					lawyers.getMiddleName(), lawyers.getGender(),
					lawyers.getStreetAddress(), lawyers.getCity(),
					lawyers.getState(), lawyers.getZipcode(),
					lawyers.getEmailAddress(), lawyers.getPhoneNumber(),
					lawyers.getNotes(), lawyers.getStatus());
			lawyersForm.setUsername(lawyers.getUsers().getUsername());
			lawyersForms.add(lawyersForm);
		}

		return lawyersForms;
	}

	// Get All Entries
	public List<LawyersForm> getLawyersListByLawyerAdmin(Integer lawyerAdminId) {
		List<LawyersForm> lawyersForms = new ArrayList<LawyersForm>();

		List<Lawyers> lawyerss = new ArrayList<Lawyers>();

		lawyerss = lawyersDAO.getLawyersByLawyerAdmin(lawyerAdminId);

		for (Lawyers lawyers : lawyerss) {
			// TODO: Fill the List
			LawyersForm lawyersForm = new LawyersForm(lawyers.getId(), lawyers
					.getLawyerAdmin().getId(), lawyers.getUsers().getUserId(),
					lawyers.getFirstName(), lawyers.getLastName(),
					lawyers.getMiddleName(), lawyers.getGender(),
					lawyers.getStreetAddress(), lawyers.getCity(),
					lawyers.getState(), lawyers.getZipcode(),
					lawyers.getEmailAddress(), lawyers.getPhoneNumber(),
					lawyers.getNotes(), lawyers.getStatus());
			lawyersForm.setUsername(lawyers.getUsers().getUsername());
			lawyersForms.add(lawyersForm);
		}

		return lawyersForms;
	}

	// Get Particular Entry
	public LawyersForm getLawyers(Integer getId) {
		Lawyers lawyers = new Lawyers();

		lawyers = lawyersDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start

		LawyersForm lawyersForm = new LawyersForm(lawyers.getId(), lawyers
				.getLawyerAdmin().getId(), lawyers.getUsers().getUserId(),
				lawyers.getFirstName(), lawyers.getLastName(),
				lawyers.getMiddleName(), lawyers.getGender(),
				lawyers.getStreetAddress(), lawyers.getCity(),
				lawyers.getState(), lawyers.getZipcode(),
				lawyers.getEmailAddress(), lawyers.getPhoneNumber(),
				lawyers.getNotes(), lawyers.getStatus());
		lawyersForm.setUsername(lawyers.getUsers().getUsername());

		// Get County List
		lawyersForm.setCountyForms(countyService.getCountyList());

		// Get Mapped Counties
		List<LawyerCountyMappingForm> lawyerCountyMappingForms = lawyerCountyMappingService
				.getLawyerCountyMappingByLaweyerId(getId);

		int i = 0;
		Integer[] county = new Integer[lawyerCountyMappingForms.size()];
		for (LawyerCountyMappingForm lawyerCountyMappingForms1 : lawyerCountyMappingForms) {
			if (lawyerCountyMappingForms1.getCountyId() != null) {
				System.out.println("Layer County Id"
						+ lawyerCountyMappingForms1.getCountyId());
				county[i] = lawyerCountyMappingForms1.getCountyId();
			}
			i++;
		}
		lawyersForm.setCountyId(county);
		// End

		return lawyersForm;
	}

	// Merge an Entry (Save or Update)
	public int mergeLawyers(LawyersForm lawyersForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Users users = new Users();
		UserRoleMapping userRoleMapping = new UserRoleMapping();
		Role role = new Role();
		users.setUsername(lawyersForm.getUsername());
		users.setPassword(lawyersForm.getUsername());
		users.setIsEnable(1);

		Integer currentUserId = staffService.getCurrentUserId();

		LawyerAdmin lawyerAdmin = lawyerAdminDAO.getByUserId(currentUserId);
		Lawyers lawyers = new Lawyers(lawyerAdmin, users,
				lawyersForm.getFirstName(), lawyersForm.getLastName(),
				lawyersForm.getMiddleName(), lawyersForm.getGender(),
				lawyersForm.getStreetAddress(), lawyersForm.getCity(),
				lawyersForm.getState(), lawyersForm.getZipcode(),
				lawyersForm.getEmailAddress(), lawyersForm.getPhoneNumber(),
				lawyersForm.getNotes(), 1, null);
		lawyers.setId(lawyersForm.getId());

		lawyersDAO.merge(lawyers);

		role = roleDAO.get(InjuryConstants.INJURY_LAWYER_ROLE_ID);
		userRoleMapping.setRole(role);
		userRoleMapping.setUsers(users);
		userRoleDAO.save(userRoleMapping);

		// Logic Ends

		return 1;
	}

	// Save an Entry
	public int saveLawyers(LawyersForm lawyersForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Users users = new Users();
		UserRoleMapping userRoleMapping = new UserRoleMapping();
		Role role = new Role();
		users.setUsername(lawyersForm.getUsername());
		users.setPassword(lawyersForm.getUsername());
		users.setIsEnable(1);

		Integer currentUserId = staffService.getCurrentUserId();
		LawyerAdmin lawyerAdmin = lawyerAdminDAO.getByUserId(currentUserId);

		Lawyers lawyers = new Lawyers(lawyerAdmin, users,
				lawyersForm.getFirstName(), lawyersForm.getLastName(),
				lawyersForm.getMiddleName(), lawyersForm.getGender(),
				lawyersForm.getStreetAddress(), lawyersForm.getCity(),
				lawyersForm.getState(), lawyersForm.getZipcode(),
				lawyersForm.getEmailAddress(), lawyersForm.getPhoneNumber(),
				lawyersForm.getNotes(), 1, null);
		lawyersDAO.save(lawyers);

		role = roleDAO.get(InjuryConstants.INJURY_LAWYER_ROLE_ID);
		userRoleMapping.setRole(role);
		userRoleMapping.setUsers(users);
		userRoleDAO.save(userRoleMapping);

		Integer[] countyMapped = lawyersForm.getCountyId();

		// Map the County
		for (Integer countyId : countyMapped) {
			County county = countyDAO.get(countyId);
			LawyerCountyMapping lawyerCountyMapping = new LawyerCountyMapping(
					lawyers, county);
			lawyerCountyMappingDAO.save(lawyerCountyMapping);
		}

		// Logic Ends

		return 1;
	}

	// Update an Entry
	public int updateLawyers(LawyersForm lawyersForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Lawyers lawyers = lawyersDAO.get(lawyersForm.getId());
		Users users = usersDAO.get(lawyers.getUsers().getUserId());

		Integer currentUserId = staffService.getCurrentUserId();
		LawyerAdmin lawyerAdmin = lawyerAdminDAO.getByUserId(currentUserId);

		lawyers.setId(lawyersForm.getId());
		lawyers.setFirstName(lawyersForm.getFirstName());
		lawyers.setLastName(lawyersForm.getLastName());
		lawyers.setMiddleName(lawyersForm.getMiddleName());
		lawyers.setStreetAddress(lawyersForm.getStreetAddress());
		lawyers.setCity(lawyersForm.getCity());
		lawyers.setState(lawyersForm.getState());
		lawyers.setZipcode(lawyersForm.getZipcode());
		lawyers.setGender(lawyersForm.getGender());
		lawyers.setEmailAddress(lawyersForm.getEmailAddress());
		lawyers.setPhoneNumber(lawyersForm.getPhoneNumber());
		lawyers.setNotes(lawyersForm.getNotes());
		lawyers.setStatus(1);
		lawyers.setLawyerAdmin(lawyerAdmin);
		lawyers.setUsers(users);
		lawyers.setId(lawyersForm.getId());
		lawyersDAO.update(lawyers);

		// Map the County
		// Delete the Unmatched County
		Integer[] countyMapped = lawyersForm.getCountyId();
		lawyerCountyMappingService.deleteLawyerCountyMapping(countyMapped,
				lawyersForm.getId());
		// Add a New County List
		List<Integer> newlyAddedCounty = lawyerCountyMappingService
				.getNewlyAddedCountyId(countyMapped, lawyersForm.getId());
		for (Integer countyId : newlyAddedCounty) {
			County county = countyDAO.get(countyId);
			LawyerCountyMapping lawyerCountyMapping = new LawyerCountyMapping(
					lawyers, county);
			lawyerCountyMappingDAO.save(lawyerCountyMapping);
		}
		// Logic Ends

		return 1;
	}

	// Delete an Entry
	public int deleteLawyers(Integer id) {
		Lawyers lawyers = lawyersDAO.get(id);
		Users users = usersDAO.get(lawyers.getUsers().getUserId());
		userRoleDAO.deletebyUserId(users.getUserId());

		List<LawyerCountyMapping> lawyerCountyMappings = lawyerCountyMappingDAO
				.getLaweCountyMappingsByLawyerId(id);
		for (LawyerCountyMapping lawyerCountyMapping : lawyerCountyMappings) {
			lawyerCountyMappingDAO.delete(lawyerCountyMapping.getMappingId());
		}

		lawyersDAO.delete(id);
		return 1;
	}

	// Get Number of Lawyer Admin Available in System
	public Integer getNumberOfLawyers(Integer lawyerAdminId) {
		Integer numberOfLawyerAdmin = 0;

		numberOfLawyerAdmin = this.getLawyersListByLawyerAdmin(lawyerAdminId)
				.size();

		return numberOfLawyerAdmin;
	}

	// Get Patients By Lawyer
	public List<PatientsForm> getPatientsByLawyer(Integer lawyerId) {

		List<PatientsForm> patientsForms = patientsService.getPatientsList();
		List<PatientsForm> matchedPatientForms = new ArrayList<PatientsForm>();
		List<CountyForm> countyForms = new ArrayList<CountyForm>();
		// Get Mapped Counties
		List<LawyerCountyMappingForm> lawyerCountyMappingForms = lawyerCountyMappingService
				.getLawyerCountyMappingByLaweyerId(lawyerId);
		for (LawyerCountyMappingForm lawyerCountyMappingForm : lawyerCountyMappingForms) {
			County county = countyDAO
					.get(lawyerCountyMappingForm.getCountyId());
			CountyForm countyForm = new CountyForm(county.getId(),
					county.getName(), county.getStatus());
			countyForms.add(countyForm);
		}

		for (CountyForm countyForms1 : countyForms) {
			for (PatientsForm patientsForm : patientsForms) {
				if (countyForms1.getName().equals(
						patientsForm.getCountry().split("\\s+")[0])) {
					matchedPatientForms.add(patientsForm);
				}
			}
		}

		return matchedPatientForms;
	}

	// Get Lawyers Id By User Id
	public Integer getLawyerIdByUserId(Integer userId) {

		Lawyers lawyers = lawyersDAO.getLawyersByUserId(userId);
		return lawyers.getId();
	}

	// Enable or Disable Lawyer
	public Integer enableOrDisabelLawyer(Integer lawyerId) {
		Integer status = 0;
		try {
			Lawyers lawyers = lawyersDAO.get(lawyerId);
			Users users = usersDAO.get(lawyers.getUsers().getUserId());
			if (lawyers.getStatus() == 1) {
				lawyers.setStatus(0);
				users.setIsEnable(0);
			} else if (lawyers.getStatus() == 0) {
				lawyers.setStatus(1);
				users.setIsEnable(1);
			}
			lawyersDAO.merge(lawyers);
			usersDAO.merge(users);
		} catch (Exception e) {
			status = 1;
		}

		return status;
	}

	// Reset the Password
	public Integer resetLawyerPassword(Integer lawyerId) {
		Integer status = 1;
		Lawyers lawyers = lawyersDAO.get(lawyerId);

		status = usersDAO.resetUserPassword(lawyers.getUsers().getUserId());
		return status;
	}
}
