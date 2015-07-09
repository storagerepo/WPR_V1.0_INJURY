package com.deemsys.project.Staff;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.Staff;
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
			StaffForm staffForm=new StaffForm(staff.getId(), staff.getRole(), staff.getUsername(), staff.getPassword(), staff.getFirstName(), staff.getLastName(), staff.getPhoneNumber(), staff.getEmailAddress(), staff.getNotes(),staff.getIsEnable());
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
		
		Staff staff=new Staff(staffForm.getRole(), staffForm.getUsername(), staffForm.getPassword(), staffForm.getFirstName(), staffForm.getLastName(), staffForm.getPhoneNumber(), staffForm.getEmailAddress(), staffForm.getNotes(),staffForm.getIsEnable(),null,null,null);
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
		
		Staff staff=new Staff(staffForm.getRole(), staffForm.getUsername(), staffForm.getPassword(), staffForm.getFirstName(), staffForm.getLastName(), staffForm.getPhoneNumber(), staffForm.getEmailAddress(), staffForm.getNotes(),staffForm.getIsEnable(),null,null,null);
		//Logic Ends
		
		staffDAO.save(staff);
		return 1;
	}
	
	//Update an Entry
	public int updateStaff(StaffForm staffForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Staff staff=new Staff(staffForm.getRole(), staffForm.getUsername(), staffForm.getPassword(), staffForm.getFirstName(), staffForm.getLastName(), staffForm.getPhoneNumber(), staffForm.getEmailAddress(), staffForm.getNotes(),staffForm.getIsEnable(),null,null,null);
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
	
	
	
}
