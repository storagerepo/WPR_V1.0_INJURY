package com.deemsys.project.Doctors;

import java.util.ArrayList;
import java.util.Arrays;
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
			String workingDayEntity = doctors.getWorkingDays();
			 String[] workingDaysString =  workingDayEntity.split(",");
			
			DoctorsForm doctorsForm=new DoctorsForm(doctors.getId(), doctors.getClinicName(),doctors.getDoctorName(), doctors.getAddress(), doctors.getCity(), doctors.getCountry(), doctors.getState(), doctors.getZip(), doctors.getOfficeNumber(),doctors.getFaxNumber(),workingDaysString, doctors.getOfficeHoursFromTime().toString(),doctors.getOfficeHoursToTime().toString(),doctors.getDirection(), doctors.getNotes());
			doctorsForms.add(doctorsForm);
		}
		
		return doctorsForms;
	}
	
	 public DoctorsForm getDoctors(Integer getId)
{
Doctors doctors=new Doctors();
doctors=doctorsDAO.get(getId);
	 		
	 DoctorsForm doctorsForm=new DoctorsForm();
	 String workingDayEntity = doctors.getWorkingDays();
	 String[] workingDaysString =  workingDayEntity.split(",");
	 			
if(doctors!=null){
doctorsForm=new DoctorsForm(doctors.getId(), doctors.getClinicName(),doctors.getDoctorName(), doctors.getAddress(),doctors.getCity(),doctors.getCountry(), doctors.getState(), doctors.getZip(), doctors.getOfficeNumber(),doctors.getFaxNumber(),workingDaysString, doctors.getOfficeHoursFromTime().toString(),doctors.getOfficeHoursToTime().toString(),doctors.getDirection(), doctors.getNotes());
}
else{
doctorsForm= new DoctorsForm();
 }
return doctorsForm;
   }
	 

	
	//Merge an Entry (Save or Update)
	public int mergeDoctors(DoctorsForm doctorsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		 String [] woringDays = doctorsForm.getWorkingDays();
		 String workingDaysString ="";
		 for (String string : woringDays) {
			 if(!workingDaysString.equals(""))
			 workingDaysString=workingDaysString+","+string;
			 else
				 workingDaysString=string;
		}
		Doctors doctors=new Doctors(doctorsForm.getClinicName(),doctorsForm.getDoctorName(), doctorsForm.getAddress(), doctorsForm.getCity(), doctorsForm.getCountry(), doctorsForm.getState(), doctorsForm.getZip(), doctorsForm.getOfficeNumber(), doctorsForm.getFaxNumber(),workingDaysString,new Date(),new Date(),doctorsForm.getDirection(), doctorsForm.getNotes(), null);
				doctors.setId(doctorsForm.getId());
		//Logic Ends
		
		
		doctorsDAO.merge(doctors);
		return 1;
	}
	
	//Save an Entry
	public int saveDoctors(DoctorsForm doctorsForm)
		
	{
		 String [] woringDays = doctorsForm.getWorkingDays();
		 String workingDaysString ="";
		 for (String string : woringDays) {
			 if(!workingDaysString.equals(""))
			 workingDaysString=workingDaysString+","+string;
			 else
				 workingDaysString=string;
		}
			Doctors doctors=new Doctors(doctorsForm.getClinicName(),doctorsForm.getDoctorName(), doctorsForm.getAddress(), doctorsForm.getCity(), doctorsForm.getCountry(), doctorsForm.getState(), doctorsForm.getZip(), doctorsForm.getOfficeNumber(), doctorsForm.getFaxNumber(),workingDaysString,new Date(),new Date(),doctorsForm.getDirection(), doctorsForm.getNotes(), null);
					
			doctorsDAO.save(doctors);
			return 1;
		}

	
	//Update an Entry
	public int updateDoctors(DoctorsForm doctorsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		 String [] woringDays = doctorsForm.getWorkingDays();
		 String workingDaysString ="";
		 for (String string : woringDays) {
			 if(!workingDaysString.equals(""))
			 workingDaysString=workingDaysString+","+string;
			 else
				 workingDaysString=string;
		}
		Doctors doctors=new Doctors(doctorsForm.getClinicName(),doctorsForm.getDoctorName(), doctorsForm.getAddress(), doctorsForm.getCity(), doctorsForm.getCountry(), doctorsForm.getState(), doctorsForm.getZip(), doctorsForm.getOfficeNumber(), doctorsForm.getFaxNumber(),workingDaysString,new Date(),new Date(),doctorsForm.getDirection(), doctorsForm.getNotes(), null);
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
			String workingDayEntity = doctors.getWorkingDays();
			 String[] workingDaysString =  workingDayEntity.split(",");
			
			//TODO: Fill the List
			DoctorsForm doctorsForm=new DoctorsForm(doctors.getId(), doctors.getClinicName(),doctors.getDoctorName(), doctors.getAddress(), doctors.getCity(), doctors.getCountry(), doctors.getState(), doctors.getZip(), doctors.getOfficeNumber(),doctors.getFaxNumber(),workingDaysString, doctors.getOfficeHoursFromTime().toString(),doctors.getOfficeHoursToTime().toString(),doctors.getDirection(), doctors.getNotes());
					doctorsForms.add(doctorsForm);
			count++;
		}
		System.out.println("count:"+count);
		
		return count;
		
	}
	
	
	
	
	public List<DoctorsForm> getDoctorId()
	{
		List<DoctorsForm> doctorsForms=new ArrayList<DoctorsForm>();
		
		List<Doctors> doctorss=new ArrayList<Doctors>();
		
		doctorss=doctorsDAO.getDoctorId();
		
		for (Doctors doctors : doctorss) {
			//TODO: Fill the List
			DoctorsForm doctorsForm=new DoctorsForm(doctors.getId(),doctors.getDoctorName());
			doctorsForms.add(doctorsForm);
		}
		
		return doctorsForms;
	}
	
}
