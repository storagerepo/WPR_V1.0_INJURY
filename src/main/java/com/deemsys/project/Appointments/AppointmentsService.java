package com.deemsys.project.Appointments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.CallLogs.CallLogsForm;
import com.deemsys.project.Staff.StaffDAO;
import com.deemsys.project.Staff.StaffForm;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.patients.PatientsDAO;
/**
 * 
 * @author Deemsys
 *
 * Appointments 	 - Entity
 * appointments 	 - Entity Object
 * appointmentss 	 - Entity List
 * appointmentsDAO   - Entity DAO
 * appointmentsForms - EntityForm List
 * AppointmentsForm  - EntityForm
 *
 */
@Service
@Transactional
public class AppointmentsService {

	@Autowired
	AppointmentsDAO appointmentsDAO;
	
	@Autowired
	PatientsDAO patientsDAO;
	
	
	//Get All Entries
	public List<AppointmentsForm> getAppointmentsList()
	{
		List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
		
		List<Appointments> appointmentss=new ArrayList<Appointments>();
		
		appointmentss=appointmentsDAO.getAll();
		
		for (Appointments appointments : appointmentss) {
			//TODO: Fill the List
			AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getScheduledDate().toString(), appointments.getNotes(), appointments.getStatus());
			appointmentsForms.add(appointmentsForm);
		}
		
		return appointmentsForms;
	}
	
	//Get Particular Entry
	public AppointmentsForm getAppointments(Integer getId)
	{
		Appointments appointments=new Appointments();
		
		appointments=appointmentsDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		
		AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getScheduledDate().toString(), appointments.getNotes(), appointments.getStatus());
			
		//End
		
		return appointmentsForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeAppointments(AppointmentsForm appointmentsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		Patients patients = new Patients();
		patients.setId(appointmentsForm.getPatientId());
		
		Appointments appointments=new Appointments(patients,new Date(),appointmentsForm.getNotes(), appointmentsForm.getStatus(), null);
		appointments.setId(appointmentsForm.getId());
		//Logic Ends
		
		
		appointmentsDAO.merge(appointments);
		return 1;
	}
	
	//Save an Entry
	public int saveAppointments(AppointmentsForm appointmentsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Patients patients = new Patients();
		patients.setId(appointmentsForm.getPatientId());
		
		Appointments appointments=new Appointments(patients,new Date(), appointmentsForm.getNotes(), appointmentsForm.getStatus(), null);
		
		//Logic Ends
		
		appointmentsDAO.save(appointments);
		return 1;
	}
	
	public Integer updateStatu(Integer getId, Integer getStatus)
	{
		Appointments appointments=new Appointments();
		
		//Logic Starts
				AppointmentsForm appointmentsForms=new AppointmentsForm(appointments.getStatus());
					
				appointmentsDAO.updates(getId,getStatus);
				
		//End
		
		return 1;
	}
	
	
	
	//Update an Entry
	public int updateAppointments(AppointmentsForm appointmentsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Patients patients = new Patients();
		patients.setId(appointmentsForm.getPatientId());
		
		Appointments appointments=new Appointments(patients,new Date(), appointmentsForm.getNotes(), appointmentsForm.getStatus(), null);
		appointments.setId(appointmentsForm.getId());
		//Logic Ends
		
		appointmentsDAO.update(appointments);
		return 1;
	}
	
	//Delete an Entry
	public int deleteAppointments(Integer id)
	{
		appointmentsDAO.delete(id);
		return 1;
	}
	
	//Get All Comments By PaientID
			public List<AppointmentsForm> getAppointmentsListByPatient(Integer patientId)
			{
				List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
				
				List<Appointments> appointmentss=new ArrayList<Appointments>();
				
				appointmentss=appointmentsDAO.getAppointmentsListByPatient(patientId);
				
				for (Appointments appointments : appointmentss) {
					//TODO: Fill the List
					AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getScheduledDate().toString(), appointments.getNotes(), appointments.getStatus());
					appointmentsForms.add(appointmentsForm);
				}
				
				return appointmentsForms;
			}
	
}
