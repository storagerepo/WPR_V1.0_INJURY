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
	
	//Get All Entries
	public List<StaffForm> getStaffList()
	{
		List<StaffForm> staffForms=new ArrayList<StaffForm>();
		
		List<Staff> staffs=new ArrayList<Staff>();
		
		staffs=staffDAO.getAll();
		
		for (Staff staff : staffs) {
			//TODO: Fill the List
			try{
			if(staff.getRole().equalsIgnoreCase("ROLE_ADMIN"))
			{
				StaffForm staffForm=new StaffForm();
				
			}
			else{
			StaffForm staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable());
			staffForms.add(staffForm);
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
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
		
		Staff staff=new Staff(staffForm.getRole(), staffForm.getUsername(), staffForm.getPassword(), staffForm.getFirstName(), staffForm.getLastName(), staffForm.getPhoneNumber(), staffForm.getEmailAddress(), staffForm.getNotes(),staffForm.getIsEnable(),null);
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
		staffDAO.delete(id);
		return 1;
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
				//TODO: Fill the List
				try{
				if(staff.getRole().equals("ROLE_STAFF"))
				{
				StaffForm staffForm=new StaffForm(staff.getId(), staff.getFirstName());
				staffForms.add(staffForm);
				}
				else
				{
					StaffForm staffForm=new StaffForm ();
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
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
			
			Integer staffId=0,doctorId=0;
			
			
			for (Patients patients : patientss) {
				//TODO: Fill the List
				staffId=0;doctorId=0;
				if(patients.getStaff()!=null)
					staffId=patients.getStaff().getId();
				if(patients.getDoctors()!=null)
					doctorId=patients.getDoctors().getId();
					
				
				PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),InjuryConstants.convertMonthFormat(patients.getCrashDate()),patients.getTimeOfCrash().toString(),patients.getLocalReportNumber1(),patients.getUnitNumber(),patients.getName(),InjuryConstants.convertMonthFormat(patients.getDateOfBirth()),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getLocalReportNumber2(),patients.getUnitInError1(),patients.getUnitNumber1(),patients.getOwnerName(),patients.getOwnerPhoneNumber(),patients.getDamageScale(),patients.getProofOfInsurance(),patients.getInsuranceCompany(),patients.getPolicyNumber());
			patientsForms.add(patientsForm);
			}
			}
			else
			{
				PatientsForm patientsForm=new  PatientsForm	();
			}
			
			return patientsForms;
		}
		
		
				
		
		
		
		
		
}
