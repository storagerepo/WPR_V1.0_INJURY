package com.deemsys.project.patients;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

			
		Patients patients=new Patients(staff,doctors,patientsForm.getLocalReportNumber(),patientsForm.getCrashSeverity(),patientsForm.getReportingAgencyName(),patientsForm.getNumberOfUnits(),patientsForm.getUnitInError(),patientsForm.getCountry(),patientsForm.getCityVillageTownship(),InjuryConstants.convertYearFormat(patientsForm.getCrashDate()),patientsForm.getTimeOfCrash(),patientsForm.getLocalReportNumber1(),patientsForm.getUnitNumber(),patientsForm.getName(),InjuryConstants.convertYearFormat(patientsForm.getDateOfBirth()),patientsForm.getGender(),patientsForm.getAddress(),patientsForm.getPhoneNumber(),patientsForm.getInjuries(),patientsForm.getEmsAgency(),patientsForm.getMedicalFacility(),patientsForm.getLocalReportNumber2(),patientsForm.getUnitInError1(),patientsForm.getUnitNumber1(),patientsForm.getOwnerName(),patientsForm.getOwnerPhoneNumber(),patientsForm.getDamageScale(),patientsForm.getProofOfInsurance(),patientsForm.getInsuranceCompany(),patientsForm.getPolicyNumber());
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
	
	//Hanndling Upload File
	public int addPatientFromFile(MultipartFile file)
	{
		
		File serverFile=null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                System.out.println("Server File Location="
                        + serverFile.getAbsolutePath());
                String fileName=serverFile.getAbsolutePath();
                
                //Read the Content
                List<Patients> patients=new ArrayList<Patients>();
                patients=patientsFileRead.readPatientUsingExcel(fileName);
                
                //Now add the patients in DB
                addPatients(patients);
                
                serverFile.delete();
               
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } 
		return 1;
	}
	
	
	
	//Add Patient by File Upload
		public void addPatients(List<Patients> patients)
		{
			for (Patients patient : patients) {
				patientsDAO.save(patient);	
			}		
		}
		
		
		
		
	
}
