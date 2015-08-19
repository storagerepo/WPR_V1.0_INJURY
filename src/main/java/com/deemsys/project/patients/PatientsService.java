package com.deemsys.project.patients;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
	
	/*@Autowired
	PatientsFileRead patientsFileRead;*/
	
	//Get All Entries
	public List<PatientsForm> getPatientsList()
	{
		List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
		
		List<Patients> patientss=new ArrayList<Patients>();
		
		patientss=patientsDAO.getAll();
		
		Integer staffId=0,doctorId=0;
		
		
		for (Patients patients : patientss) {
			//TODO: Fill the List
			staffId=0;doctorId=0;
			if(patients.getStaff()!=null)
				staffId=patients.getStaff().getId();
			if(patients.getDoctors()!=null)
				doctorId=patients.getDoctors().getId();
				
			
			PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),InjuryConstants.convertMonthFormat(patients.getCrashDate()),patients.getTimeOfCrash().toString(),patients.getLocalReportNumber1(),patients.getUnitNumber(),patients.getName(),InjuryConstants.convertMonthFormat(patients.getDateOfBirth()),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getLocalReportNumber2(),patients.getUnitInError1(),patients.getUnitNumber1(),patients.getOwnerName(),patients.getOwnerPhoneNumber(),patients.getDamageScale(),patients.getProofOfInsurance(),patients.getInsuranceCompany(),patients.getPolicyNumber());
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
		Integer staffId = 0,doctorId = 0;
		
		if(patients!=null)
		{
			staffId=0;doctorId=0;
		if(patients.getStaff()!=null)
			staffId=patients.getStaff().getId();
		if(patients.getDoctors()!=null)
			doctorId=patients.getDoctors().getId();
		
		patientsForm=new PatientsForm(patients.getId(),staffId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),InjuryConstants.convertMonthFormat(patients.getCrashDate()),patients.getTimeOfCrash().toString(),patients.getLocalReportNumber1(),patients.getUnitNumber(),patients.getName(),InjuryConstants.convertMonthFormat(patients.getDateOfBirth()),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getLocalReportNumber2(),patients.getUnitInError1(),patients.getUnitNumber1(),patients.getOwnerName(),patients.getOwnerPhoneNumber(),patients.getDamageScale(),patients.getProofOfInsurance(),patients.getInsuranceCompany(),patients.getPolicyNumber());
		}
			
		
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
public String addPatientFromFile(MultipartFile file)
	{
	String a = "";
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
                BufferedReader br = null;
        		String line = "";
        		String csvSplitBy = ",";
        		
        		
        		String[] patientArray=new String[30];
       		String[] patientHeader=new String[30];
        		List<Patients> patients=new ArrayList<Patients>();
        		// try starts
       		try {

        			br = new BufferedReader(new FileReader(fileName));
        			Integer lineNumber=1;
        			while ((line = br.readLine()) != null) {
        				System.out.println("note");
        				 System.out.println(line);
        				String trimmedLastName = line.substring(line.length() - 1);
        	    	   /*if(trimmedLastName.equals(","))
        	    	      {
        	    	     a="- Empty data is not allowed in POLICY NUMBER";
        	    	      }
        	    	  */
        	    	   if(lineNumber>1)
        	    		   
        				{
        					patientArray=line.split(csvSplitBy);
        					System.out.println(patientArray[1]);
        				// validation
        					for(int i=0;i<28;i++)
        					{
        					try {
        								if(i == 7 ){
              						 SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy");
            						 Date dateformat=new Date();
        								dateformat = monthFormat.parse(patientArray[i]);
        									} 
                                               }catch (ParseException e) {
        								// TODO Auto-generated catch block
                                                                                       
                                       a=a+" = - Only Date is allowed in CRASH DATE";
        							}
                                             try {
        								if(i == 12 ){
               						 SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy");
            						 Date dateformat=new Date();
        								dateformat = monthFormat.parse(patientArray[i]);
        									} 
                                            }catch (ParseException e) {
        								// TODO Auto-generated catch block
                                                                                           
                                      a=a+ " = - Only Date is allowed in DATE OF BIRTH";
                                  
        							}

        							try
            						{
            						if(i == 1)
                					{
            					int y = Integer.parseInt(patientArray[i]);
                					}
            						}
        							catch (Exception e) {
                						System.out.println(i);
                			            a=a+ " = - Only integer is allowed in CRASH SEVERITY";
               			        }
        							
        							try
            						{
            						if(i == 3)
                					{
            					int y = Integer.parseInt(patientArray[i]);
                					}
            						}
       							catch (Exception e) {
               						System.out.println(i);
                						a=a+ " = - Only integer is allowed in NUMBER OF UNITS";
               			        }
        							
        							try
            						{
            						if(i == 4)
                					{
            					int y = Integer.parseInt(patientArray[i]);
                					}
           						}
        							catch (Exception e) {
                						System.out.println(i);
                						a=a+ " = - Only integer is allowed in UNIT IN ERROR";
                			        }
        							try
            						{
            						if(i == 10)
                					{
            					int y = Integer.parseInt(patientArray[i]);
                					}
            						}
        							catch (Exception e) {
                						System.out.println(i);
                						a=a+ " = - Only integer is allowed in UNIT NUMBER";
                			        }

        							try
            						{
            						if(i == 16)
                					{
            					int y = Integer.parseInt(patientArray[i]);
                					}
            						}
        							catch (Exception e) {
                						System.out.println(i);
                						a=a+ " = - Only integer is allowed in INJURIES";
                			        }
        							try
            						{
            						if(i == 20)
               					{
            					int y = Integer.parseInt(patientArray[i]);
               					}
            						}
        							catch (Exception e) {
                						System.out.println(i);
                						a=a+ " = - Only integer is allowed in UNIT IN ERROR";
                			        }
        							try
            						{
            						if(i == 21)
                					{
            					int y = Integer.parseInt(patientArray[i]);
                					}
            						}
        							catch (Exception e) {
                						System.out.println(i);
                						a=a+ " = - Only integer is allowed in UNIT NUMBER";
                			        }
        							try
            						{
            						if(i == 24)
                					{
            					int y = Integer.parseInt(patientArray[i]);
                					}
            						}
        							catch (Exception e) {
                						System.out.println(i);
                						a=a+ " = - Only integer is allowed in DAMAGE SCALE";
                			        }
        							
					
					
       					if(i == 0)
							   {
       						if(patientArray[i].equals(""))
       						{	a=a+ " = - Empty data is not allowed in LOCAL REPORT NUMBER";
						     
							   }
					}
					

				
				if(i == 1)
						   {
					if(patientArray[i].equals(""))
					{
						 a=a+ " = - Empty data is not allowed in CRASH SEVERITY";
						   }
						   }
				if(i == 2)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in REPORTING AGENCY NAME";
				   }
				   }
				if(i == 3)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in NUMBER OF UNITS";
				   }
				   }
				if(i == 4)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in UNIT IN ERROR";
				   }
				   }
				if(i == 5)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in COUNTY ";
				   }
				   }
				if(i == 6)
				   {
	if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in CITY VILLAGE TOWNSHIP *";
				   }
			   }
				if(i == 7)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in CRASH DATE";
				   }
				   }
				if(i == 8)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in TIME OF CRASH";
				   }
				   }
				if(i == 9)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in LOCAL REPORT NUMBER";
				   }
				   }
				if(i == 10)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in UNIT NUMBER";
				   }
				   }
				if(i == 11)
			   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in NAME: LAST FIRST MIDDLE";
			}
				   }
				if(i == 12)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in DATE OF BIRTH";
			}
				   }
				if(i == 13)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in GENDER";
				   }
				   }
				if(i == 14)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in ADDRESS CITY STATE ZIP";
				   }
				   }
				if(i == 15)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in CONTACT PHONE - INCLUDE AREA CODE";
				   }
				   }
			if(i == 16)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in INJURIES";
				   }
				   }
				if(i == 17)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in EMS AGENCY";
				   }
				   }
				if(i == 18)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in MEDICAL FACILITY INJURED TAKEN TO";
				   }
				   }
				if(i == 19)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in LOCAL REPORT NUMBER";
			   }
			   }
				if(i ==20)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in UNIT IN ERROR";
				   }
				   }
				if(i == 21)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in UNIT NUMBER";
				   }
				   }
		if(i == 22)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in OWNER NAME";
				   }
				   }
				if(i == 23)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in OWNER PHONE NUMBER";
				   }
				   }
				if(i == 24)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in DAMAGE SCALE";
				   }
				   }
				if(i == 25)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in PROOF OF INSURANCE";
				   }
				   }
				if(i == 26)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in INSURANCE COMPANY";
				   }
			   }
				if(i == 27)
				   {
			if(patientArray[i].equals(""))
			{
				 a=a+ " = - Empty data is not allowed in POLICY NUMBER";
				   }
			   }
				
				
							   }
        					// validation ends
        					patients.add(new Patients(null, null, patientArray[0], Integer.parseInt(patientArray[1]), patientArray[2],Integer.parseInt(patientArray[3]), Integer.parseInt(patientArray[4]),patientArray[5], patientArray[6], InjuryConstants.convertYearFormat(patientArray[7]), patientArray[8], patientArray[9],Integer.parseInt(patientArray[10]), patientArray[11], InjuryConstants.convertYearFormat(patientArray[12]), patientArray[13].trim(), patientArray[14], patientArray[15], Integer.parseInt(patientArray[16]), patientArray[17], patientArray[18], patientArray[19],Integer.parseInt(patientArray[20]),Integer.parseInt(patientArray[21]), patientArray[22], patientArray[23], Integer.parseInt(patientArray[24]), patientArray[25], patientArray[26], patientArray[27]));
        					
        					
        					System.out.println(line);
        				}					
        				else
        				{
        					patientHeader=line.split(csvSplitBy);
        					lineNumber++;
        				}
        			}

        		}
        		catch (NumberFormatException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		
        		finally {
        			if (br != null) {
        				try {
        					br.close();
        				} catch (IOException e) {
        					e.printStackTrace();
        				}
        			}
        		
        		}
        		//try ends
        		if(a == ""){
                //Now add the patients in DB
               addPatients(patients);
            }
                serverFile.delete();
               
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } 
       
		return a;
	}
	
	
	
	//Add Patient by File Upload
		public void addPatients(List<Patients> patients)
		{
		for (Patients patient : patients) {
				patientsDAO.save(patient);	
			}
		}

		
		public Integer getNoOfPatientss()
		{
			Integer count=0;
			List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
			
			List<Patients> patientss=new ArrayList<Patients>();
			
			patientss=patientsDAO.getAll();
		
			Integer staffId=0,doctorId=0;
			
			
			for (Patients patients : patientss) {
				//TODO: Fill the List
				staffId=0;doctorId=0;
				if(patients.getStaff()!=null)
					staffId=patients.getStaff().getId();
				if(patients.getDoctors()!=null)
					doctorId=patients.getDoctors().getId();
					
			
				PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),InjuryConstants.convertMonthFormat(patients.getCrashDate()),patients.getTimeOfCrash().toString(),patients.getLocalReportNumber1(),patients.getUnitNumber(),patients.getName(),InjuryConstants.convertMonthFormat(patients.getDateOfBirth()),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getLocalReportNumber2(),patients.getUnitInError1(),patients.getUnitNumber1(),patients.getOwnerName(),patients.getOwnerPhoneNumber(),patients.getDamageScale(),patients.getProofOfInsurance(),patients.getInsuranceCompany(),patients.getPolicyNumber());
		patientsForms.add(patientsForm);
			count++;
			}

			return count;
		}

}
