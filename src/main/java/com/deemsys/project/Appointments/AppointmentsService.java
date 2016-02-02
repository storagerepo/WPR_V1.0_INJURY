package com.deemsys.project.Appointments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.entity.Patients;
import com.deemsys.project.CallLogs.CallLogsDAO;
import com.deemsys.project.CallLogs.CallLogsForm;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Doctors.DoctorsDAO;
import com.deemsys.project.Staff.StaffDAO;
import com.deemsys.project.Staff.StaffForm;
import com.deemsys.project.Staff.StaffService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Clinics;
import com.deemsys.project.entity.Doctors;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.patients.PatientsDAO;
import com.deemsys.project.patients.PatientsForm;
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
	ClinicsDAO clinicsDAO;
	
	@Autowired
	PatientsDAO patientsDAO;
	
	@Autowired
	StaffService staffService;
	
	@Autowired
	CallLogsDAO callLogsDAO;
	
	@Autowired
	DoctorsDAO doctorsDAO ;
	
	//Get All Entries
	public List<AppointmentsForm> getAppointmentsList()
	{
		List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
		
		List<Appointments> appointmentss=new ArrayList<Appointments>();
		
		appointmentss=appointmentsDAO.getAll();
		
		for (Appointments appointments : appointmentss) {
			//TODO: Fill the List
			AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getPatients().getName(),InjuryConstants.convertMonthFormat(appointments.getScheduledDate()), appointments.getNotes(), appointments.getStatus());
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
		
		AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getPatients().getName(),InjuryConstants.convertMonthFormat(appointments.getScheduledDate()), appointments.getNotes(), appointments.getStatus());
			
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
		
		Appointments appointments=new Appointments(patients,InjuryConstants.convertYearFormat(appointmentsForm.getScheduledDate()),appointmentsForm.getNotes(), appointmentsForm.getStatus(), null);
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
		Clinics clinics=clinicsDAO.get(appointmentsForm.getClinicId());
		Doctors doctors = doctorsDAO.get(appointmentsForm.getDoctorId());
		
		Patients patients = patientsDAO.get(appointmentsForm.getPatientId());
		patients.setClinics(clinics);
		patients.setDoctors(doctors);
		
		
		Appointments appointments=new Appointments(patients,InjuryConstants.convertYearFormat(appointmentsForm.getScheduledDate()), appointmentsForm.getNotes(), 0, null);
	
		
		//Logic Ends
	    CallLogs callLogs = callLogsDAO.get(appointmentsForm.getCallLogId());
		callLogs.setAppointments(appointments);
		callLogsDAO.update(callLogs);
	
		//Logic Ends
	patientsDAO.update(patients);

	patientsDAO.updatePatientStatus(appointmentsForm.getPatientId());
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
		
		
		Patients patients = new Patients();
		patients.setId(appointmentsForm.getPatientId());
		
		Appointments appointments=new Appointments(patients,InjuryConstants.convertYearFormat(appointmentsForm.getScheduledDate()), appointmentsForm.getNotes(), appointmentsForm.getStatus(), null);
		appointments.setId(appointmentsForm.getId());
		
		
		appointmentsDAO.update(appointments);
		return 1;
	}
	
	//Delete an Entry
	public int deleteAppointments(Integer id)
	{
		appointmentsDAO.delete(id);
		return 1;
	}

	
	public PatientsForm getPatientDetails(Integer getId)
	{
		Patients patients=new Patients();
		
		patients=patientsDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		PatientsForm patientsForm = new PatientsForm();
		if(patients==null){
			
		}
		else{
			patientsForm=new PatientsForm(patients.getId(),patients.getStaff().getId(),patients.getClinics().getClinicId(),patients.getDoctors().getId(),patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate().toString(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth().toString(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());

				}
		
		//End
		
		//End
		
		return patientsForm;
	}
			
			public List<AppointmentsForm> todaysAppointment()
			{
			List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
				
				List<Appointments> appointmentss=new ArrayList<Appointments>();
				String role = staffService.getCurrentRole();
				Date monthBegin = new LocalDate().withDayOfMonth(1).toDate();
				Date monthEnd = new LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1).toDate();
				if(role=="ROLE_ADMIN"){
					appointmentss=appointmentsDAO.getAppointmentsBetweenDates(monthBegin,monthEnd);
					for (Appointments appointments : appointmentss) {
						//TODO: Fill the List
						AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getPatients().getName(),appointments.getScheduledDate().toString(), appointments.getNotes(), appointments.getStatus());
						appointmentsForms.add(appointmentsForm);
					}
				}
				else if(role=="ROLE_STAFF"){
					Integer staffId = staffService.getCurrentUserId();
					appointmentsForms=patientsDAO.getTodayAppointmentListByStaffId(staffId);
				}
				

				return appointmentsForms;
			
			
			}
			
			public List<AppointmentsForm> getByDates(String date)
			{
				List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
				
				List<Appointments> appointmentss=new ArrayList<Appointments>();
				//TODO: Convert Entity to Form
				//Start
				String role = staffService.getCurrentRole();
				if(role=="ROLE_ADMIN"){
					appointmentss=appointmentsDAO.getByDates(date);

					for (Appointments appointments : appointmentss) {
						//TODO: Fill the List
						AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getPatients().getName(),appointments.getScheduledDate().toString(), appointments.getNotes(), appointments.getStatus());
						appointmentsForms.add(appointmentsForm);
					}
				}
				else if(role=="ROLE_STAFF"){
					Integer staffId = staffService.getCurrentUserId();
					appointmentsForms=patientsDAO.getParticularDayAppointmentListByStaffId(date, staffId);
				}
				
				//End
				
				return appointmentsForms;
			}
			
			
			
			
			
			public Integer getNoOfAppointments()
			{
				Integer count=0;
				
				
				List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
				
				List<Appointments> appointmentss=new ArrayList<Appointments>();
				String role = staffService.getCurrentRole();
				if(role=="ROLE_ADMIN"){
					appointmentss=appointmentsDAO.getAll();
				}else if(role=="ROLE_STAFF"){
					Integer staffId = staffService.getCurrentUserId();
					count=patientsDAO.getAppointmentListByStaffId(staffId).size();
				}
				
				for (Appointments appointments : appointmentss) {
					//TODO: Fill the List
					AppointmentsForm appointmentsForm=new AppointmentsForm(appointments.getId(), appointments.getPatients().getId(),appointments.getPatients().getName(),appointments.getScheduledDate().toString(), appointments.getNotes(), appointments.getStatus());
					appointmentsForms.add(appointmentsForm);
					count++;
				}
				
				return count;
				
			}

			public List<AppointmentsForm> getAppointmentListByStaffId(Integer staffId){
				// TODO Auto-generated method stub
				List<AppointmentsForm> appointmentsForms=new ArrayList<AppointmentsForm>();
				
					appointmentsForms=patientsDAO.getAppointmentListByStaffId(staffId);

				return appointmentsForms;
				
			}
			
			public Integer removeAppointment(Integer appointmentId){
				// TODO Auto-generated method stub
				Integer status=0;
				CallLogs callLogs=callLogsDAO.getCallLogsByAppointment(appointmentId);
				callLogs.setAppointments(null);
				callLogsDAO.update(callLogs);
				
				appointmentsDAO.delete(appointmentId);
				
				//Change Patient Status
				Patients patients = patientsDAO.get(callLogs.getPatients().getId());
				patients.setClinics(null);
				patients.setDoctors(null);
				patients.setPatientStatus(1);
				patientsDAO.update(patients);
				status=1;

				return status;
				
			}
}
