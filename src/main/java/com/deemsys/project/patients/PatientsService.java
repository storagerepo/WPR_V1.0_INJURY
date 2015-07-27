package com.deemsys.project.patients;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.deemsys.project.Appointments.AppointmentsForm;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Doctors;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;
/**
 * 
 * @author Deemsys
 *
 * Patients 	 - Entity
 * patients 	 - Entity Object
 * patientss 	 - Entity List
 * patientsDAO   - Entity DAO
 * patientsForms - EntityForm List
 * PatientsForm  - EntityForm
 *
 */
@Service
@Transactional
public class PatientsService {

	@Autowired
	PatientsDAO patientsDAO;
	
	@Autowired
	PatientFileUpload patientFileUpload;
	
	//Get All Entries
	public List<PatientsForm> getPatientsList()
	{
		List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
		
		List<Patients> patientss=new ArrayList<Patients>();
		
		patientss=patientsDAO.getAll();
		
		for (Patients patients : patientss) {
			//TODO: Fill the List
			PatientsForm patientsForm=new PatientsForm(patients.getId(),patients.getDoctors().getId(),patients.getStaff().getId(),patients.getFirstName(),patients.getLastName(),patients.getReportNumber(),patients.getPhoneNumber(),patients.getAddress(),patients.getInjury(),patients.getDateOfCrash().toString(),patients.getOtherPassengers(),patients.getNotes());
			patientsForms.add(patientsForm);
		}
		
		return patientsForms;
	}
	
	//Get Particular Entry
	public PatientsForm getPatients(Integer getId)
	{
		Patients patients=new Patients();
		
		patients=patientsDAO.get(getId);
		
		//TODO: Convert Entity to Form
		//Start
		PatientsForm patientsForm = new PatientsForm();
		if(patients==null){
			
		}
		else{
			patientsForm=new PatientsForm(patients.getId(),patients.getDoctors().getId(),patients.getStaff().getId(),patients.getFirstName(),patients.getLastName(),patients.getReportNumber(),patients.getPhoneNumber(),patients.getAddress(),patients.getInjury(),patients.getDateOfCrash().toString(),patients.getOtherPassengers(),patients.getNotes());
		}
		
		//End
		
		//End
		
		return patientsForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergePatients(PatientsForm patientsForm)
	{
		//TODO: Convert Form to Entity Here
		
		//Logic Starts
		
		
		Staff staff=new Staff();
		staff.setId(patientsForm.getCallerId());
		
		
		Doctors doctors=new Doctors();
		doctors.setId(patientsForm.getDoctorsId());
		
		
		Patients patients=new Patients(staff,doctors,patientsForm.getFirstName(),patientsForm.getLastName(),patientsForm.getReportNumber(),patientsForm.getPhoneNumber(),patientsForm.getAddress(),patientsForm.getInjury(),new Date(),patientsForm.getNotes(),patientsForm.getOtherPassengers(),null,null);
		patients.setId(patientsForm.getId());
		//Logic Ends
		
		
		patientsDAO.merge(patients);
		return 1;
	}
	
	//Save an Entry
	public int savePatients(PatientsForm patientsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		Staff staff=new Staff();
		staff.setId(patientsForm.getCallerId());
		
		
		Doctors doctors=new Doctors();
		doctors.setId(patientsForm.getDoctorsId());
		
		Patients patients=new Patients(staff,doctors,patientsForm.getFirstName(),patientsForm.getLastName(),patientsForm.getReportNumber(),patientsForm.getPhoneNumber(),patientsForm.getAddress(),patientsForm.getInjury(),new Date(),patientsForm.getNotes(),patientsForm.getOtherPassengers(),null,null);
		
		//Logic Ends
		
		patientsDAO.save(patients);
		return 1;
	}
	
	//Update an Entry
	public int updatePatients(PatientsForm patientsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Staff staff=new Staff();
		staff.setId(patientsForm.getCallerId());
		
		
		Doctors doctors=new Doctors();
		doctors.setId(patientsForm.getDoctorsId());
		
		Patients patients=new Patients(staff,doctors,patientsForm.getFirstName(),patientsForm.getLastName(),patientsForm.getReportNumber(),patientsForm.getPhoneNumber(),patientsForm.getAddress(),patientsForm.getInjury(),new Date(),patientsForm.getNotes(),patientsForm.getOtherPassengers(),null,null);
		patients.setId(patientsForm.getId());
		
		//Logic Ends
		
		patientsDAO.update(patients);
		return 1;
	}
	
	//Delete an Entry
	public int deletePatients(Integer id)
	{
		patientsDAO.delete(id);
		return 1;
	}
	
	//Add Patient by File Upload
	public int addPatientUsingExcel(String fileName)
	{
		List<Patients> patientsList=patientFileUpload.processUploadFile(fileName);
		
		if (patientsList.size()>0) {
			for (Patients patient : patientsList) {
				patientsDAO.save(patient);
			}
		}
		
		return 0;
	}
	
	
	
	public Integer getNoOfPatients()
	{
		Integer count=0;
		
List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
		
		List<Patients> patientss=new ArrayList<Patients>();
		
		patientss=patientsDAO.getAll();
		
		for (Patients patients : patientss) {
			//TODO: Fill the List
			PatientsForm patientsForm=new PatientsForm(patients.getId(),patients.getDoctors().getId(),patients.getStaff().getId(),patients.getFirstName(),patients.getLastName(),patients.getReportNumber(),patients.getPhoneNumber(),patients.getAddress(),patients.getInjury(),patients.getDateOfCrash().toString(),patients.getOtherPassengers(),patients.getNotes());
			patientsForms.add(patientsForm);
			count++;
		}
		
		return count;
		
	}

		public List<PatientsForm> getPatientListByStaffId(Integer staffId){
	
		List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
		
		List<Patients> patientss=new ArrayList<Patients>();
			
		patientss=patientsDAO.getPatientListByStaffId(staffId);
		for (Patients patients : patientss) {
			
			PatientsForm patientsForm=new PatientsForm(patients.getId(),patients.getDoctors().getId(),patients.getStaff().getId(),patients.getFirstName(),patients.getLastName(),patients.getReportNumber(),patients.getPhoneNumber(),patients.getAddress(),patients.getInjury(),patients.getDateOfCrash().toString(),patients.getOtherPassengers(),patients.getNotes());
			patientsForms.add(patientsForm);
		}

		return patientsForms;
		
	}
	
	
}
