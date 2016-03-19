package com.deemsys.project.Lawyers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.LawyerAdmin;
import com.deemsys.project.entity.LawyerAdminCountyMapId;
import com.deemsys.project.entity.LawyerCountyMap;
import com.deemsys.project.entity.Lawyer;
import com.deemsys.project.entity.LawyerCountyMapId;
import com.deemsys.project.entity.Roles;
import com.deemsys.project.entity.Users;
import com.deemsys.project.patient.PatientForm;
import com.deemsys.project.patient.PatientService;
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
import com.deemsys.project.Caller.CallerService;
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
	CallerService callerService;

	@Autowired
	CountyDAO countyDAO;

	@Autowired
	CountyService countyService;

	@Autowired
	LawyerCountyMappingDAO lawyerCountyMappingDAO;

	@Autowired
	LawyerCountyMappingService lawyerCountyMappingService;

	@Autowired
	PatientService patientService;

	// Get All Entries
	public List<LawyersForm> getLawyersList() {
		List<LawyersForm> lawyersForms = new ArrayList<LawyersForm>();

		List<Lawyer> lawyerss = new ArrayList<Lawyer>();

		lawyerss = lawyersDAO.getAll();

		for (Lawyer lawyers : lawyerss) {
			// TODO: Fill the List
			LawyersForm lawyersForm = new LawyersForm(lawyers.getLawyerId(), lawyers
					.getLawyerAdmin().getLawyerAdminId(), lawyers.getUsers().getUserId(),
					lawyers.getFirstName(), lawyers.getLastName(),
					lawyers.getStreet(), lawyers.getCity(),
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

		List<Lawyer> lawyerss = new ArrayList<Lawyer>();

		lawyerss = lawyersDAO.getLawyersByLawyerAdmin(lawyerAdminId);

		for (Lawyer lawyers : lawyerss) {
			// TODO: Fill the List
			LawyersForm lawyersForm = new LawyersForm(lawyers.getLawyerId(), lawyers
					.getLawyerAdmin().getLawyerAdminId(), lawyers.getUsers().getUserId(),
					lawyers.getFirstName(), lawyers.getLastName(),
					lawyers.getStreet(), lawyers.getCity(),
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
		Lawyer lawyers = new Lawyer();

		lawyers = lawyersDAO.get(getId);

		// TODO: Convert Entity to Form
		// Start

		LawyersForm lawyersForm = new LawyersForm(lawyers.getLawyerId(), lawyers
				.getLawyerAdmin().getLawyerAdminId(), lawyers.getUsers().getUserId(),
				lawyers.getFirstName(), lawyers.getLastName(),
				lawyers.getStreet(), lawyers.getCity(),
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
		Roles role = new Roles();
		users.setUsername(lawyersForm.getUsername());
		users.setPassword(lawyersForm.getUsername());
		users.setIsEnable(1);

		Integer currentUserId = callerService.getCurrentUserId();

		LawyerAdmin lawyerAdmin = lawyerAdminDAO.getByUserId(currentUserId);
		Lawyer lawyers = new Lawyer(lawyerAdmin, users,
				lawyersForm.getFirstName(), lawyersForm.getLastName(),
				lawyersForm.getStreetAddress(), lawyersForm.getCity(),
				lawyersForm.getState(), lawyersForm.getZipcode(),
				lawyersForm.getEmailAddress(), lawyersForm.getPhoneNumber(),
				lawyersForm.getNotes(), 1, null,null);
		lawyers.setLawyerId(lawyersForm.getLawyerId());

		lawyersDAO.merge(lawyers);

		role = roleDAO.get(InjuryConstants.INJURY_LAWYER_ROLE_ID);
		users.setRoles(role);
		usersDAO.save(users);

		// Logic Ends

		return 1;
	}

	// Save an Entry
	public int saveLawyers(LawyersForm lawyersForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts

		Users users = new Users();
		Roles roles = new Roles();
		users.setUsername(lawyersForm.getUsername());
		users.setPassword(lawyersForm.getUsername());
		users.setIsEnable(1);

		Integer currentUserId = callerService.getCurrentUserId();
		LawyerAdmin lawyerAdmin = lawyerAdminDAO.getByUserId(currentUserId);

		Lawyer lawyers = new Lawyer(lawyerAdmin, users,
				lawyersForm.getFirstName(), lawyersForm.getLastName(),
				lawyersForm.getStreetAddress(), lawyersForm.getCity(),
				lawyersForm.getState(), lawyersForm.getZipcode(),
				lawyersForm.getEmailAddress(), lawyersForm.getPhoneNumber(),
				lawyersForm.getNotes(), 1, null,null);
		lawyersDAO.save(lawyers);

		roles = roleDAO.get(InjuryConstants.INJURY_LAWYER_ROLE_ID);
		users.setRoles(roles);
		usersDAO.save(users);

		Integer[] countyMapped = lawyersForm.getCountyId();

		// Map the County
		for (Integer countyId : countyMapped) {
			County county = countyDAO.get(countyId);
			LawyerCountyMapId lawyerCountyMapId=new LawyerCountyMapId(lawyers.getLawyerId(), countyId);
			LawyerCountyMap lawyerCountyMapping = new LawyerCountyMap(lawyerCountyMapId,lawyers, county,1);
			lawyerCountyMappingDAO.save(lawyerCountyMapping);
		}

		// Logic Ends

		return 1;
	}

	// Update an Entry
	public int updateLawyers(LawyersForm lawyersForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		Lawyer lawyers = lawyersDAO.get(lawyersForm.getLawyerId());
		Users users = usersDAO.get(lawyers.getUsers().getUserId());

		Integer currentUserId = callerService.getCurrentUserId();
		LawyerAdmin lawyerAdmin = lawyerAdminDAO.getByUserId(currentUserId);

		lawyers.setFirstName(lawyersForm.getFirstName());
		lawyers.setLastName(lawyersForm.getLastName());
		lawyers.setStreet(lawyersForm.getStreetAddress());
		lawyers.setCity(lawyersForm.getCity());
		lawyers.setState(lawyersForm.getState());
		lawyers.setZipcode(lawyersForm.getZipcode());
		lawyers.setEmailAddress(lawyersForm.getEmailAddress());
		lawyers.setPhoneNumber(lawyersForm.getPhoneNumber());
		lawyers.setNotes(lawyersForm.getNotes());
		lawyers.setStatus(1);
		lawyers.setLawyerAdmin(lawyerAdmin);
		lawyers.setUsers(users);
		lawyers.setLawyerId(lawyersForm.getLawyerId());
		lawyersDAO.update(lawyers);

		// Map the County
		// Delete the Unmatched County
		Integer[] countyMapped = lawyersForm.getCountyId();
		lawyerCountyMappingService.deleteLawyerCountyMapping(countyMapped,
				lawyersForm.getLawyerId());
		// Add a New County List
		List<Integer> newlyAddedCounty = lawyerCountyMappingService
				.getNewlyAddedCountyId(countyMapped, lawyersForm.getLawyerId());
		for (Integer countyId : newlyAddedCounty) {
			County county = countyDAO.get(countyId);
			LawyerCountyMapId lawyerCountyMapId=new LawyerCountyMapId(lawyers.getLawyerId(), countyId);
			LawyerCountyMap lawyerCountyMapping = new LawyerCountyMap(lawyerCountyMapId,lawyers, county,1);
			lawyerCountyMappingDAO.save(lawyerCountyMapping);
		}
		// Logic Ends

		return 1;
	}

	// Delete an Entry
	public int deleteLawyers(Integer id) {
		Lawyer lawyers = lawyersDAO.get(id);
		Users users = usersDAO.get(lawyers.getUsers().getUserId());
		usersDAO.delete(users.getUserId());

		List<LawyerCountyMap> lawyerCountyMappings = lawyerCountyMappingDAO
				.getLawyerCountyMappingsByLawyerId(id);
		for (LawyerCountyMap lawyerCountyMapping : lawyerCountyMappings) {
			lawyerCountyMappingDAO.deleteLawyerCountyMappingsByLawyerIdAndCountyId(id, lawyerCountyMapping.getId().getCountyId());
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

	// Get Patient By Lawyer
	public List<PatientForm> getPatientByLawyer(Integer lawyerId) {

		List<PatientForm> patientForms = patientService.getPatientList();
		List<PatientForm> matchedPatientForms = new ArrayList<PatientForm>();
		List<CountyForm> countyForms = new ArrayList<CountyForm>();
		// Get Mapped Counties
		List<LawyerCountyMappingForm> lawyerCountyMappingForms = lawyerCountyMappingService
				.getLawyerCountyMappingByLaweyerId(lawyerId);
		for (LawyerCountyMappingForm lawyerCountyMappingForm : lawyerCountyMappingForms) {
			County county = countyDAO
					.get(lawyerCountyMappingForm.getCountyId());
			CountyForm countyForm = new CountyForm(county.getCountyId(),
					county.getName(), county.getStatus());
			countyForms.add(countyForm);
		}

		for (CountyForm countyForms1 : countyForms) {
			for (PatientForm patientForm : patientForms) {
				if (countyForms1.getName().equals(
						patientForm.getCounty().split("\\s+")[0])) {
					matchedPatientForms.add(patientForm);
				}
			}
		}

		return matchedPatientForms;
	}

	// Get Lawyers Id By User Id
	public Integer getLawyerIdByUserId(Integer userId) {

		Lawyer lawyers = lawyersDAO.getLawyersByUserId(userId);
		return lawyers.getLawyerId();
	}

	// Enable or Disable Lawyer
	public Integer enableOrDisabelLawyer(Integer lawyerId) {
		Integer status = 0;
		try {
			Lawyer lawyers = lawyersDAO.get(lawyerId);
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
		Lawyer lawyers = lawyersDAO.get(lawyerId);

		status = usersDAO.resetUserPassword(lawyers.getUsers().getUserId());
		return status;
	}
}
