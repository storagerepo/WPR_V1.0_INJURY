package com.deemsys.project.patients;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.common.InjuryConstants;
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
	PatientsFileRead patientsFileRead;
	
	//Get All Entries
	public List<PatientsForm> getPatientsList()
	{
		List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
		
		List<Patients> patientss=new ArrayList<Patients>();
		
		patientss=patientsDAO.getAll();
		
		for (Patients patients : patientss) {
			//TODO: Fill the List
			PatientsForm patientsForm=new PatientsForm(patients.getId(),patients.getStaff().getId(),patients.getDoctors().getId(),patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),InjuryConstants.convertMonthFormat(patients.getCrashDate()),patients.getTimeOfCrash().toString(),patients.getLocalReportNumber1(),patients.getUnitNumber(),patients.getName(),InjuryConstants.convertMonthFormat(patients.getDateOfBirth()),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getLocalReportNumber2(),patients.getUnitInError1(),patients.getUnitNumber1(),patients.getOwnerName(),patients.getOwnerPhoneNumber(),patients.getDamageScale(),patients.getProofOfInsurance(),patients.getInsuranceCompany(),patients.getPolicyNumber());
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
			patientsForm=new PatientsForm(patients.getId(),patients.getStaff().getId(),patients.getDoctors().getId(),patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),InjuryConstants.convertMonthFormat(patients.getCrashDate()),patients.getTimeOfCrash().toString(),patients.getLocalReportNumber1(),patients.getUnitNumber(),patients.getName(),InjuryConstants.convertMonthFormat(patients.getDateOfBirth()),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getLocalReportNumber2(),patients.getUnitInError1(),patients.getUnitNumber1(),patients.getOwnerName(),patients.getOwnerPhoneNumber(),patients.getDamageScale(),patients.getProofOfInsurance(),patients.getInsuranceCompany(),patients.getPolicyNumber());
			
				}
		
		//End
		
		//End
		
		return patientsForm;
	}
	
	
	
	//Update an Entry
	public int updatePatients(PatientsForm patientsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		Staff staff=new Staff();
		staff.setId(patientsForm.getCallerId());
		
		
		Doctors doctors=new Doctors();
		doctors.setId(patientsForm.getDoctorId());

			
		Patients patients=new Patients(staff,doctors,patientsForm.getLocalReportNumber(),patientsForm.getCrashSeverity(),patientsForm.getReportingAgencyName(),patientsForm.getNumberOfUnits(),patientsForm.getUnitInError(),patientsForm.getCountry(),patientsForm.getCityVillageTownship(),InjuryConstants.convertYearFormat(patientsForm.getCrashDate()),new Date(),patientsForm.getLocalReportNumber1(),patientsForm.getUnitNumber(),patientsForm.getName(),InjuryConstants.convertYearFormat(patientsForm.getDateOfBirth()),patientsForm.getGender(),patientsForm.getAddress(),patientsForm.getPhoneNumber(),patientsForm.getInjuries(),patientsForm.getEmsAgency(),patientsForm.getMedicalFacility(),patientsForm.getLocalReportNumber2(),patientsForm.getUnitInError1(),patientsForm.getUnitNumber1(),patientsForm.getOwnerName(),patientsForm.getOwnerPhoneNumber(),patientsForm.getDamageScale(),patientsForm.getProofOfInsurance(),patientsForm.getInsuranceCompany(),patientsForm.getPolicyNumber(),null,null);
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
		public List<Patients> addPatientUsingExcel(String fileName)
		{
			
			String[] patientsList=patientsFileRead.readPatientUsingExcel(fileName);
			System.out.println("sun");
			List<PatientsForm> patientsForm = new ArrayList<PatientsForm>();
			PatientsForm patient = new PatientsForm();
			for(int i = 0; i < patientsList.length; i++){
				System.out.println(patientsList[i]);
				
				
				patient.setReportingAgencyName(patientsList[2]);
				patient.setCityVillageTownship(patientsList[6]);
			}
			
			patientsForm.add(patient); 
		/*
					if (patientsList.size()>0) {
			for (Patients patient : patientsList) {
				patientsDAO.save(patient);
			}

				*/
			return null;
	
		}
	
}
