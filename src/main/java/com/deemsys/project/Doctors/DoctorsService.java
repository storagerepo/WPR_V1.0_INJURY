package com.deemsys.project.Doctors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.Doctors;
/**
 * 
 * @author Deemsys
 *
 * Doctors 	 - Entity
 * doctors 	 - Entity Object
 * doctorss 	 - Entity List
 * doctorsDAO   - Entity DAO
 * doctorsForms - EntityForm List
 * DoctorsForm  - EntityForm
 *
 */
@Service
@Transactional
public class DoctorsService {

	@Autowired
	DoctorsDAO doctorsDAO;
	
	//Get All Entries
	public List<DoctorsForm> getDoctorsList()
	{
		List<DoctorsForm> doctorsForms=new ArrayList<DoctorsForm>();
		
		List<Doctors> doctorss=new ArrayList<Doctors>();
		
		doctorss=doctorsDAO.getAll();
		
		for (Doctors doctors : doctorss) {
			//TODO: Fill the List
			DoctorsForm doctorsForm=new DoctorsForm(doctors.getId(), doctors.getName(), doctors.getAddress(), doctors.getCity(), doctors.getCountry(), doctors.getState(), doctors.getZip(), doctors.getOfficeHours(), doctors.getNewField(), doctors.getNotes());
			doctorsForms.add(doctorsForm);
		}
		
		return doctorsForms;
	}
	
	//Get Particular Entry
	public DoctorsForm getDoctors(Integer getId)
	{
		Doctors doctors=new Doctors();
		
		doctors=doctorsDAO.get(getId);
		DoctorsForm doctorsForm=new DoctorsForm();
		//TODO: Convert Entity to Form
		//Start
		if(doctors!=null){
		doctorsForm=new DoctorsForm(doctors.getId(), doctors.getName(), doctors.getAddress(), doctors.getCity(), doctors.getCountry(), doctors.getState(), doctors.getZip(), doctors.getOfficeHours(), doctors.getNewField(), doctors.getNotes());
				}
		else{
		doctorsForm= new DoctorsForm();
		}
		//End
		
		return doctorsForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeDoctors(DoctorsForm doctorsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		Doctors doctors=new Doctors(doctorsForm.getName(), doctorsForm.getAddress(), doctorsForm.getCity(), doctorsForm.getCountry(), doctorsForm.getState(), doctorsForm.getZip(), doctorsForm.getOfficeHours(), doctorsForm.getNewField(), doctorsForm.getNotes(), null);
				doctors.setId(doctorsForm.getId());
		//Logic Ends
		
		
		doctorsDAO.merge(doctors);
		return 1;
	}
	
	//Save an Entry
	public int saveDoctors(DoctorsForm doctorsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Doctors doctors=new Doctors(doctorsForm.getName(), doctorsForm.getAddress(), doctorsForm.getCity(), doctorsForm.getCountry(), doctorsForm.getState(), doctorsForm.getZip(), doctorsForm.getOfficeHours(), doctorsForm.getNewField(), doctorsForm.getNotes(), null);	//Logic Ends
		
		doctorsDAO.save(doctors);
		return 1;
	}
	
	//Update an Entry
	public int updateDoctors(DoctorsForm doctorsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Doctors doctors=new Doctors(doctorsForm.getName(), doctorsForm.getAddress(), doctorsForm.getCity(), doctorsForm.getCountry(), doctorsForm.getState(), doctorsForm.getZip(), doctorsForm.getOfficeHours(), doctorsForm.getNewField(), doctorsForm.getNotes(), null);
		doctors.setId(doctorsForm.getId());
		//Logic Ends
		
		doctorsDAO.update(doctors);
		return 1;
	}
	
	//Delete an Entry
	public int deleteDoctors(Integer id)
	{
		doctorsDAO.delete(id);
		return 1;
	}
	
	public Integer getNoOfDoctors()
	{
		Integer count=0;
		
		List<DoctorsForm> doctorsForms=new ArrayList<DoctorsForm>();
		
		List<Doctors> doctorss=new ArrayList<Doctors>();
		
		doctorss=doctorsDAO.getAll();
		
		for (Doctors doctors : doctorss) {
			//TODO: Fill the List
			DoctorsForm doctorsForm=new DoctorsForm(doctors.getId(), doctors.getName(), doctors.getAddress(), doctors.getCity(), doctors.getCountry(), doctors.getState(), doctors.getZip(), doctors.getOfficeHours(), doctors.getNewField(), doctors.getNotes());
			doctorsForms.add(doctorsForm);
			count++;
		}
		
		
		return count;
		
	}
	
	
	
	
	public List<DoctorsForm> getDoctorId()
	{
		List<DoctorsForm> doctorsForms=new ArrayList<DoctorsForm>();
		
		List<Doctors> doctorss=new ArrayList<Doctors>();
		
		doctorss=doctorsDAO.getDoctorId();
		
		for (Doctors doctors : doctorss) {
			//TODO: Fill the List
			DoctorsForm doctorsForm=new DoctorsForm(doctors.getId(),doctors.getName());
			doctorsForms.add(doctorsForm);
		}
		
		return doctorsForms;
	}
	
}
