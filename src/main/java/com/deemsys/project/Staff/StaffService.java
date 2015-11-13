package com.deemsys.project.Staff;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.patients.PatientsDAO;
import com.deemsys.project.patients.PatientsForm;
/**
 * 
 * @author Deemsys
 *
 * Staff 	 - Entity
 * staff 	 - Entity Object
 * staffs 	 - Entity List
 * staffDAO   - Entity DAO
 * staffForms - EntityForm List
 * StaffForm  - EntityForm
 *
 */
@Service
@Transactional
public class StaffService {

	@Autowired
	StaffDAO staffDAO;
	
	@Autowired
	PatientsDAO patientsDAO;
	
	//Get All Entries
	public List<StaffForm> getStaffList()
	{
		List<StaffForm> staffForms=new ArrayList<StaffForm>();
		
		List<Staff> staffs=new ArrayList<Staff>();
		
		staffs=staffDAO.getAll();
		
		for (Staff staff : staffs) {
			//TODO: Fill the List
			Integer patientSize=patientsDAO.getPatientListByStaffId(staff.getId()).size();
			StaffForm staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable(),patientSize);
			staffForms.add(staffForm);
			
		}
		
		return staffForms;
	}
	
	//Get Particular Entry
	public StaffForm getStaff(Integer getId)
	{
		Staff staff=new Staff();
		
		staff=staffDAO.get(getId);
		StaffForm staffForm=new StaffForm();
		//TODO: Convert Entity to Form
		//Start
		if(staff!=null){
		staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable());
		}
		else{
		staffForm= new StaffForm();
		}
		//End
		
		return staffForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeStaff(StaffForm staffForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		Staff staff=new Staff(staffForm.getRole(), staffForm.getUsername(), staffForm.getPassword(), staffForm.getFirstName(), staffForm.getLastName(), staffForm.getPhoneNumber(), staffForm.getEmailAddress(), staffForm.getNotes(),staffForm.getIsEnable(),null);
		staff.setId(staffForm.getId());
		//Logic Ends
		
		
		staffDAO.merge(staff);
		return 1;
	}
	
	//Save an Entry
	public int saveStaff(StaffForm staffForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Staff staff=new Staff("ROLE_STAFF", staffForm.getUsername(), staffForm.getUsername(), staffForm.getFirstName(), staffForm.getLastName(), staffForm.getPhoneNumber(), staffForm.getEmailAddress(), staffForm.getNotes(),1,null);
		staff.setIsEnable(1);
		//Logic Ends
		
		staffDAO.save(staff);
		return 1;
	}
	
	//Update an Entry
	public int updateStaff(StaffForm staffForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Staff staff=new Staff(staffForm.getRole(), staffForm.getUsername(), staffForm.getPassword(), staffForm.getFirstName(), staffForm.getLastName(), staffForm.getPhoneNumber(), staffForm.getEmailAddress(), staffForm.getNotes(),staffForm.getIsEnable(),null);
		staff.setId(staffForm.getId());
		//Logic Ends
		
		staffDAO.update(staff);
		return 1;
	}
	
	//Delete an Entry
	public int deleteStaff(Integer id)
	{
		List<Patients> patientss=new ArrayList<Patients>();
		int status=0;
		patientss=staffDAO.getPatientsByStaffId(id);
		if(patientss.size()==0)
		{
			
		staffDAO.delete(id);
		status=1;
		}
		else
		{
		status=0;
		}
		
		return status;
	}
	
	
	public Integer getNoOfStaffs()
	{
		Integer count=0;
		
List<StaffForm> staffForms=new ArrayList<StaffForm>();
		
		List<Staff> staffs=new ArrayList<Staff>();
		
		staffs=staffDAO.getAll();
		
		for (Staff staff : staffs) {
			//TODO: Fill the List
			StaffForm staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable());
			staffForms.add(staffForm);
			count++;
		}
		System.out.println("count:"+count);
		
		return count;
		
	}
	
	// Get Current User Role
		public String getCurrentRole(){
			
			String currentRole="";
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Object[] role = user.getAuthorities().toArray();
		
			
			if(role[0].toString().equals("ROLE_STAFF")){
				currentRole="ROLE_STAFF";
			}
			else if(role[0].toString().equals("ROLE_ADMIN")){
				currentRole="ROLE_ADMIN";
			}
			return currentRole;
		}
	
		
		
		public List<StaffForm> getStaffId()
		{
			List<StaffForm> staffForms=new ArrayList<StaffForm>();
			
			List<Staff> staffs=new ArrayList<Staff>();
			
			staffs=staffDAO.getAll();
			
			for (Staff staff : staffs) {
				
				StaffForm staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable());
				staffForms.add(staffForm);
				
			}
			
			return staffForms;
		}
		
		
		public List<PatientsForm> getPatientsByAccessToken()
		{
			
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String username=user.getUsername();
			System.out.println(username);
			Staff staff=staffDAO.getByUserName(username);
						Integer id=staff.getId();
						
						
						
			List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
			
			List<Patients> patientss=new ArrayList<Patients>();
			
			if(id!=null)
			{
			patientss=staffDAO.getPatientsByAccessToken(id);
			
			Integer staffId=0,clinicId=0,doctorId=0;
			
			
			for (Patients patients : patientss) {
				//TODO: Fill the List
				staffId=0;clinicId=0;doctorId=0;
				if(patients.getStaff()!=null)
					staffId=patients.getStaff().getId();
				String staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
if(patients.getClinics()!=null)
					clinicId=patients.getClinics().getClinicId();
				
				if(patients.getDoctors()!=null)
					doctorId=patients.getDoctors().getId();
					
				
				PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
				patientsForm.setCallerName(staffName);
				
				patientsForms.add(patientsForm);
			}
			}
			else
			{
				PatientsForm patientsForm=new  PatientsForm	();
			}
			
			return patientsForms;
		}
		
		public Integer getCurrentUserId(){
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String username=user.getUsername();
			System.out.println(username);
			Staff staff=staffDAO.getByUserName(username);
					return staff.getId();
		}
				
		
		
		
		
		
		public Integer getUsername(String username)
		{
			Integer count=0;
			
			Staff staff=new Staff();
			
			staff=staffDAO.getByUserName(username);
			StaffForm staffForm=new StaffForm();
			//TODO: Convert Entity to Form
			//Start
			if(staff!=null){
				System.out.println(count++);
				return count++;
			//staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable());
			}
			else{
			//staffForm= new StaffForm();
				return count;
			}
			//End
			}
		

public String[] getusers() {
	String[] currentuser = new String[100];
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		currentuser[0] = user.getUsername();
		return currentuser;
	}
	

	public Integer checkPassword(String oldPassword) {
		// TODO Auto-generated method stub
		Integer status=0;
		String[] users=getusers();
		List<Staff> staff=staffDAO.checkPassword(oldPassword,users[0]);
		
		 if(staff.size()>0){
			if(oldPassword.equals(staff.get(0).getPassword())){
				status=1;
			}
			else{
				status=0;
			}
		 }
		 else{
			 status=0;
		 }
		return status;
	}

	public Integer changePassword(String newPassword) {
		// TODO Auto-generated method stub
		String[] users=getusers();
		staffDAO.changePassword(newPassword,users[0]);
		return 1;
	}
	
	public StaffForm getDetails() {
		// TODO Auto-generated method stub
		
		
			Staff staff=new Staff();
			String[] users=getusers();
				staff=staffDAO.getDetails(users[0]);

				StaffForm staffForm=new StaffForm();
			if(staff!=null){
			staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable());
			}
			else{
			staffForm= new StaffForm();
			}
			return staffForm;
		}

	public Integer disableStaff(Integer getId)
	{
		Integer status=0;
		Staff staff=new Staff();
		
		staff=staffDAO.get(getId);
		
		if(staff.getIsEnable()==1){
			status=staffDAO.isDisable(getId);
		}
		if(staff.getIsEnable()==0){
			status=staffDAO.isEnable(getId);
		}
		System.out.println(status);
		return status;
	}
	
	public Integer resetPassword(Integer getId)
	{
		Integer status=1;
		
		
		status=staffDAO.resetPassword(getId);
		return status;
	}
	}
