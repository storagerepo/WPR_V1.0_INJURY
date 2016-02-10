package com.deemsys.project.CallLogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Appointments.AppointmentsDAO;
import com.deemsys.project.Staff.StaffDAO;
import com.deemsys.project.Staff.StaffForm;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.patients.PatientsDAO;
/**
 * 
 * @author Deemsys
 *
 * CallLogs 	 - Entity
 * callLogs 	 - Entity Object
 * callLogss 	 - Entity List
 * callLogsDAO   - Entity DAO
 * callLogsForms - EntityForm List
 * CallLogsForm  - EntityForm
 *
 */
@Service
@Transactional
public class CallLogsService {

	@Autowired
	CallLogsDAO callLogsDAO;
	
	@Autowired
	StaffDAO staffDAO;
	
	@Autowired
	PatientsDAO patientsDAO;
	
	@Autowired
	AppointmentsDAO appointmentsDAO;
	
	
	//Get All Entries
	public List<CallLogsForm> getCallLogsList()
	{
		List<CallLogsForm> callLogsForms=new ArrayList<CallLogsForm>();
		
		List<CallLogs> callLogss=new ArrayList<CallLogs>();
		
		callLogss=callLogsDAO.getAll();
		
		for (CallLogs callLogs : callLogss) {
			//TODO: Fill the List
			CallLogsForm callLogsForm=new CallLogsForm(callLogs.getId(),callLogs.getPatients().getId(), callLogs.getAppointments().getId(), InjuryConstants.convertUSAFormatWithTime(callLogs.getTimeStamp()), callLogs.getResponse(), callLogs.getNotes());
			callLogsForms.add(callLogsForm);
		}
		
		return callLogsForms;
	}
	
	//Get Particular Entry
	public CallLogsForm getCallLogs(Integer getId)
	{
		CallLogs callLogs=new CallLogs();
		
		callLogs=callLogsDAO.get(getId);
		CallLogsForm callLogsForm=new CallLogsForm ();
		//TODO: Convert Entity to Form
		//Start
		try{
		if(callLogs.getAppointments()==null)
		{
		callLogsForm=new CallLogsForm(callLogs.getId(),callLogs.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogs.getTimeStamp()), callLogs.getResponse(), callLogs.getNotes());
		}
		else{
			callLogsForm = new CallLogsForm(callLogs.getId(),callLogs.getPatients().getId(), callLogs.getAppointments().getId(), InjuryConstants.convertUSAFormatWithTime(callLogs.getTimeStamp()), callLogs.getResponse(), callLogs.getNotes());
			
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return callLogsForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeCallLogs(CallLogsForm callLogsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		Appointments appointments = new Appointments();
		appointments.setId(callLogsForm.getAppointmentId());
		
		Patients patients = new Patients();
		patients.setId(callLogsForm.getPatientId());
		
		CallLogs callLogs=new CallLogs(patients,appointments,callLogsForm.getCallerId(),InjuryConstants.convertYearFormatWithTime(callLogsForm.getTimeStamp()), callLogsForm.getResponse(), callLogsForm.getNotes());
		callLogs.setId(callLogsForm.getId());
		//Logic Ends
		
		
		callLogsDAO.merge(callLogs);
		return 1;
	}
	
	//Save an Entry
	public int saveCallLogs(CallLogsForm callLogsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		Patients patients = patientsDAO.get(callLogsForm.getPatientId());
		Appointments appointments = new Appointments();
		
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
				String username=user.getUsername();
				Staff staff=staffDAO.getByUserName(username);
					
			CallLogs callLogs=new CallLogs(patients,null,staff.getId(),InjuryConstants.convertYearFormatWithTime(callLogsForm.getTimeStamp()), callLogsForm.getResponse(), callLogsForm.getNotes());
		
			String status=callLogsForm.getResponse();
		if(status.equals("3"))
		{
			patients.setClinics(null);
			patients.setDoctors(null);
			patients.setPatientStatus(3);
			patientsDAO.update(patients);
			
				callLogsDAO.save(callLogs);
		}
		else
		{
			if(patients.getPatientStatus()==2){
				patients.setPatientStatus(2);
			}else{
				patients.setPatientStatus(1);
			}
			patientsDAO.update(patients);
			callLogsDAO.save(callLogs);
		}
		//Logic Ends
		
		return 1;
	}
	
	//Update an Entry
	public int updateCallLogs(CallLogsForm callLogsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		Patients patients = patientsDAO.get(callLogsForm.getPatientId());
		patients.setId(callLogsForm.getPatientId());
		
		Appointments appointments=new Appointments();
		CallLogs callLogs=new CallLogs();
		if(callLogsForm.getAppointmentId()!=null){
			appointments = appointmentsDAO.get(callLogsForm.getAppointmentId());
			appointments.setPatients(patients);
			
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
					String username=user.getUsername();
					Staff staff=staffDAO.getByUserName(username);
						
			callLogs=new CallLogs(patients,appointments,staff.getId(),InjuryConstants.convertYearFormatWithTime(callLogsForm.getTimeStamp()), callLogsForm.getResponse(), callLogsForm.getNotes());
			callLogs.setId(callLogsForm.getId());
			String status=callLogsForm.getResponse();
			if(status.equals("3"))
			{
				patients.setPatientStatus(3);
				patients.setClinics(null);
				patients.setDoctors(null);
				patientsDAO.update(patients);
				callLogsDAO.update(callLogs);
			}
			else
			{
				if(patients.getPatientStatus()==2){
					patients.setPatientStatus(2);
				}else{
					patients.setPatientStatus(1);
				}
				patientsDAO.update(patients);
				callLogsDAO.update(callLogs);
			}
		}
		else{
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
					String username=user.getUsername();
					Staff staff=staffDAO.getByUserName(username);
						
			callLogs=new CallLogs(patients,null,staff.getId(),InjuryConstants.convertYearFormatWithTime(callLogsForm.getTimeStamp()), callLogsForm.getResponse(), callLogsForm.getNotes());
			callLogs.setId(callLogsForm.getId());
			String status=callLogsForm.getResponse();
			if(status.equals("3"))
			{
				patients.setPatientStatus(3);
				patients.setClinics(null);
				patients.setDoctors(null);
				patientsDAO.update(patients);
				callLogsDAO.update(callLogs);
			}
			else
			{
				if(patients.getPatientStatus()==2){
					patients.setPatientStatus(2);
				}else{
					patients.setPatientStatus(1);
				}
				patientsDAO.update(patients);
				callLogsDAO.update(callLogs);
			}
		}
		
		
		//Logic Ends
		return 1;
	}
	
	//Delete an Entry
	public int deleteCallLogs(Integer id)
	{
		callLogsDAO.delete(id);
		return 1;
	}
	
	public CallLogsForm getCallLogsByAppointment(Integer appointmentId){
		
		CallLogsForm callLogsForm= new CallLogsForm();
		CallLogs callLogs=callLogsDAO.getCallLogsByAppointment(appointmentId);
		callLogsForm = new CallLogsForm(callLogs.getId(),callLogs.getPatients().getId(), callLogs.getAppointments().getId(), InjuryConstants.convertUSAFormatWithTime(callLogs.getTimeStamp()), callLogs.getResponse(), callLogs.getNotes());
		
		return callLogsForm;
	}
	
	public List<CallLogsForm> getCallLogsByPatientsId(Integer patientId)
	{
		
		List<CallLogsForm> callLogsForm=new ArrayList<CallLogsForm>();
		List<CallLogs> callLogs=new ArrayList<CallLogs>();
		callLogs=callLogsDAO.getCallLogsByPatientsId(patientId);
		CallLogsForm callLogsForms=new CallLogsForm();
		for(CallLogs callLogss:callLogs)
		{
			try{
				Integer id=callLogss.getCallerId();
				Staff staff=new Staff();
				staff=staffDAO.get(id);
				String staffName=staff.getFirstName()+" "+staff.getLastName();
				
				if(callLogss.getAppointments()==null)
				{
						if(staffName==null){
							 callLogsForms = new CallLogsForm(callLogss.getId(),callLogss.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogss.getTimeStamp()), callLogss.getResponse(), callLogss.getNotes(),null);
						 }
						 else{
							 callLogsForms = new CallLogsForm(callLogss.getId(),callLogss.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogss.getTimeStamp()), callLogss.getResponse(), callLogss.getNotes(),staffName);
								
						 }
				}
				else
				{
					if(staffName==null){
						callLogsForms = new CallLogsForm(callLogss.getId(),callLogss.getPatients().getId(),callLogss.getAppointments().getId(), InjuryConstants.convertUSAFormatWithTime(callLogss.getTimeStamp()), callLogss.getResponse(), callLogss.getNotes(),null);
						 }
						 else{
							 callLogsForms = new CallLogsForm(callLogss.getId(),callLogss.getPatients().getId(),callLogss.getAppointments().getId(), InjuryConstants.convertUSAFormatWithTime(callLogss.getTimeStamp()), callLogss.getResponse(), callLogss.getNotes(),staffName);
								
						 }
				}
	callLogsForm.add(callLogsForms);

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return callLogsForm;
		
	}
	
	
	public List<CallLogsForm> getCallLogsId()
	{
		List<CallLogsForm> callLogsForms=new ArrayList<CallLogsForm>();
		
		List<CallLogs> callLogss=new ArrayList<CallLogs>();
		
		callLogss=callLogsDAO.getCallLogsId();
		
		for (CallLogs callLogs : callLogss) {
			//TODO: Fill the List
			CallLogsForm callLogsForm=new CallLogsForm(callLogs.getId());
			callLogsForms.add(callLogsForm);
		}
		
		return callLogsForms;
	}
	
	
}
