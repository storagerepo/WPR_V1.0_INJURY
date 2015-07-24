package com.deemsys.project.CallLogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Staff.StaffDAO;
import com.deemsys.project.Staff.StaffForm;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;
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
	
	
	//Get All Entries
	public List<CallLogsForm> getCallLogsList()
	{
		List<CallLogsForm> callLogsForms=new ArrayList<CallLogsForm>();
		
		List<CallLogs> callLogss=new ArrayList<CallLogs>();
		
		callLogss=callLogsDAO.getAll();
		
		for (CallLogs callLogs : callLogss) {
			//TODO: Fill the List
			CallLogsForm callLogsForm=new CallLogsForm(callLogs.getId(), callLogs.getAppointments().getId(),callLogs.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogs.getTimeStamp()), callLogs.getResponse(), callLogs.getNotes());
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
			callLogsForm = new CallLogsForm(callLogs.getId(), callLogs.getAppointments().getId(),callLogs.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogs.getTimeStamp()), callLogs.getResponse(), callLogs.getNotes());
			
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
		
		CallLogs callLogs=new CallLogs(patients,appointments,InjuryConstants.convertYearFormatWithTime(callLogsForm.getTimeStamp()), callLogsForm.getResponse(), callLogsForm.getNotes());
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
		
		Appointments appointments = new Appointments();
		
		Patients patients = new Patients();
		patients.setId(callLogsForm.getPatientId());
		
		CallLogs callLogs=new CallLogs(patients,null,InjuryConstants.convertYearFormatWithTime(callLogsForm.getTimeStamp()), callLogsForm.getResponse(), callLogsForm.getNotes());
		
		//Logic Ends
		
		callLogsDAO.save(callLogs);
		return 1;
	}
	
	//Update an Entry
	public int updateCallLogs(CallLogsForm callLogsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts

		
		Appointments appointments = new Appointments();
		appointments.setId(callLogsForm.getAppointmentId());
		
		Patients patients = new Patients();
		patients.setId(callLogsForm.getPatientId());
		
		CallLogs callLogs=new CallLogs(patients,null,InjuryConstants.convertYearFormatWithTime(callLogsForm.getTimeStamp()), callLogsForm.getResponse(), callLogsForm.getNotes());
		callLogs.setId(callLogsForm.getId());
		//Logic Ends
		
		callLogsDAO.update(callLogs);
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
		callLogsForm = new CallLogsForm(callLogs.getId(), callLogs.getAppointments().getId(),callLogs.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogs.getTimeStamp()), callLogs.getResponse(), callLogs.getNotes());
		
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
			if(callLogss.getAppointments()==null)
			{

				callLogsForms = new CallLogsForm(callLogss.getId(),callLogss.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogss.getTimeStamp()), callLogss.getResponse(), callLogss.getNotes());
			}
			else{
				callLogsForms = new CallLogsForm(callLogss.getId(), callLogss.getAppointments().getId(),callLogss.getPatients().getId(), InjuryConstants.convertUSAFormatWithTime(callLogss.getTimeStamp()), callLogss.getResponse(), callLogss.getNotes());
				
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
	
}
