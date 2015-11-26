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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.entity.Patients;
import com.deemsys.project.Appointments.AppointmentsDAO;
import com.deemsys.project.CallLogs.CallLogsDAO;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.Map.GeoLocation;
import com.deemsys.project.Staff.StaffDAO;
import com.deemsys.project.Staff.StaffForm;
import com.deemsys.project.Staff.StaffService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLogs;
import com.deemsys.project.entity.Clinics;
import com.deemsys.project.entity.Doctors;
import com.deemsys.project.entity.Staff;
import com.deemsys.project.pdfcrashreport.PDFCrashReportJson;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


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
	CallLogsDAO callLogsDAO;
	@Autowired
	ClinicsDAO clinicsDAO;
	@Autowired
	StaffDAO staffDAO;
	@Autowired
	PDFCrashReportReader crashReportReader;
	 
	@Autowired
	StaffService staffService;
	@Autowired
	AppointmentsDAO appointmentsDAO;
	
	@Autowired
	GeoLocation geoLocation;
	
	/*@Autowired
	PatientsFileRead patientsFileRead;*/
	
	//Get All Entries
	public List<PatientsForm> getPatientsList()
	{
		List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
		
		List<Patients> patientss=new ArrayList<Patients>();
		
		patientss=patientsDAO.getAll();
		
		Integer staffId=0,clinicId=0,doctorId=0;
		String staffName="N/A";
		String clinicName="N/A";
		String doctorName="N/A";
		
		
		for (Patients patients : patientss) {
			//TODO: Fill the List
			staffId=0;clinicId=0;doctorId=0;
			staffName="N/A";clinicName="N/A";doctorName="N/A";
			if(patients.getStaff()!=null)
			{
				staffId=patients.getStaff().getId();
				staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
			}
			if(patients.getClinics()!=null){
				clinicId=patients.getClinics().getClinicId();
				clinicName=patients.getClinics().getClinicName();
			}
			if(patients.getDoctors()!=null){
				doctorId=patients.getDoctors().getId();
				doctorName=patients.getDoctors().getDoctorName();
			}
				
			
			PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
			patientsForm.setCallerName(staffName);
			patientsForm.setClinicName(clinicName);
			patientsForm.setDoctorName(doctorName);
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
		Integer staffId = 0,clinicId=0,doctorId = 0;
		String staffName="N/A";
		String clinicName="N/A";
		String doctorName="N/A";
		if(patients!=null)
		{
			staffId=0;clinicId=0;doctorId=0;
		if(patients.getStaff()!=null)
		{
			staffId=patients.getStaff().getId();
			staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
		}
		if(patients.getClinics()!=null){
			clinicId=patients.getClinics().getClinicId();
			clinicName=patients.getClinics().getClinicName();
		}
		if(patients.getDoctors()!=null){
			doctorId=patients.getDoctors().getId();
			doctorName=patients.getDoctors().getDoctorName();
		}
			
		
		patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
		patientsForm.setCallerName(staffName);
		patientsForm.setClinicName(clinicName);
		patientsForm.setDoctorName(doctorName);
		
		
		}
			
		
		//End
		
		return patientsForm;
	}
	
	//Get Particular Entry
		public PatientsForm getPatientWithLatLong(Integer getId)
		{
			Patients patients=new Patients();
			
			patients=patientsDAO.get(getId);
			
			//TODO: Convert Entity to Form
			//Start
			PatientsForm patientsForm = new PatientsForm();
			Integer staffId = 0,clinicId=0,doctorId = 0;
			String staffName="N/A";
			String clinicName="N/A";
			String doctorName="N/A";
			if(patients!=null)
			{
				staffId=0;clinicId=0;doctorId=0;
			if(patients.getStaff()!=null)
			{
				staffId=patients.getStaff().getId();
				staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
			}
			if(patients.getClinics()!=null){
				clinicId=patients.getClinics().getClinicId();
				clinicName=patients.getClinics().getClinicName();
			}
			if(patients.getDoctors()!=null){
				doctorId=patients.getDoctors().getId();
				doctorName=patients.getDoctors().getDoctorName();
			}
				
			
			patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
			patientsForm.setLatitude(patients.getLatitude());
			patientsForm.setLongitude(patients.getLongitude());
			patientsForm.setCallerName(staffName);
			patientsForm.setClinicName(clinicName);
			patientsForm.setDoctorName(doctorName);
			
			
			}
				
			
			//End
			
			return patientsForm;
		}
	
	//Update an Entry
	public int updatePatients(PatientsForm patientsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		
		
		Staff staff=null;
		Clinics clinics=null;
		Doctors doctors=null;
		if(patientsForm.getCallerId()!=null)
		if(patientsForm.getCallerId()!=0)
		{
			staff=new Staff();
			staff.setId(patientsForm.getCallerId());
		}
		if(patientsForm.getClinicId()!=null)
			if(patientsForm.getClinicId()!=0)
			{
				clinics=new Clinics();
				clinics.setClinicId(patientsForm.getClinicId());
			}
		if(patientsForm.getDoctorId()!=null)
		if(patientsForm.getDoctorId()!=0)
		{
		doctors=new Doctors();
		doctors.setId(patientsForm.getDoctorId());
		}
	
		String latLong=geoLocation.getLocation(patientsForm.getAddress());
		String[] latitudeLongitude=latLong.split(",");
		
		 Patients patients=new Patients(staff,clinics,doctors,patientsForm.getLocalReportNumber(),patientsForm.getCrashSeverity(),patientsForm.getReportingAgencyName(),patientsForm.getNumberOfUnits(),patientsForm.getUnitInError(),patientsForm.getCountry(),patientsForm.getCityVillageTownship(),patientsForm.getCrashDate(),patientsForm.getTimeOfCrash().toString(),patientsForm.getUnitNumber(),patientsForm.getName(),patientsForm.getDateOfBirth(),patientsForm.getGender(),patientsForm.getAddress(),Double.parseDouble(latitudeLongitude[0]), Double.parseDouble(latitudeLongitude[1]),patientsForm.getPhoneNumber(),patientsForm.getInjuries(),patientsForm.getEmsAgency(),patientsForm.getMedicalFacility(),patientsForm.getCrashReportFileName(),patientsForm.getPatientStatus());
		
		patients.setId(patientsForm.getId());
		Integer clinicId=patientsForm.getClinicId();
		Integer doctorId=patientsForm.getDoctorId();
		
		//Logic Ends
		
		
		if(doctorId == null)
		{
			patients.setPatientStatus(1);
			patientsDAO.update(patients);
			
		}
		else if(doctorId == 0)
		{
			patients.setPatientStatus(1);
			patientsDAO.update(patients);
			
		}
		else
		{
		patients.setPatientStatus(2);
			patientsDAO.update(patients);
			
		}
		return 1;
}
	public int savePatients(PatientsForm patientsForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		
		
		
		Staff staff=null;
		Clinics clinics=null;
		Doctors doctors=null;
		if(patientsForm.getCallerId()!=null)
		if(patientsForm.getCallerId()!=0)
		{
			staff=new Staff();
			staff.setId(patientsForm.getCallerId());
		}
		if(patientsForm.getClinicId()!=null)
			if(patientsForm.getClinicId()!=0)
			{
				clinics=new Clinics();
				clinics.setClinicId(patientsForm.getClinicId());
			}
		if(patientsForm.getDoctorId()!=null)
		if(patientsForm.getDoctorId()!=0)
		{
		doctors=new Doctors();
		doctors.setId(patientsForm.getDoctorId());
		}
	
		String latLong=geoLocation.getLocation(patientsForm.getAddress());
		
		double longitude=0.0;
		double latitude=0.0;
		if(!latLong.equals("NONE")){
			String[] latitudeLongitude=latLong.split(",");
			latitude=Double.parseDouble(latitudeLongitude[0]);
			longitude=Double.parseDouble(latitudeLongitude[1]);
		}
		
		
		Patients patients=new Patients(staff,clinics,doctors,patientsForm.getLocalReportNumber(),patientsForm.getCrashSeverity(),patientsForm.getReportingAgencyName(),patientsForm.getNumberOfUnits(),patientsForm.getUnitInError(),patientsForm.getCountry(),patientsForm.getCityVillageTownship(),patientsForm.getCrashDate(),patientsForm.getTimeOfCrash().toString(),patientsForm.getUnitNumber(),patientsForm.getName(),patientsForm.getDateOfBirth(),patientsForm.getGender(),patientsForm.getAddress(),latitude,longitude,patientsForm.getPhoneNumber(),patientsForm.getInjuries(),patientsForm.getEmsAgency(),patientsForm.getMedicalFacility(),patientsForm.getCrashReportFileName(),1);
		
		if(patientsForm.getDoctorId() == null)
		{
			patients.setPatientStatus(1);
			patientsDAO.save(patients);
			
		}
		else if(patientsForm.getDoctorId() == 0)
		{
			patients.setPatientStatus(1);
			patientsDAO.save(patients);
			
		}
		else
		{
		patients.setPatientStatus(2);
		patientsDAO.save(patients);
			
		}
		
		return patients.getId();
}
	
	//Delete an Entry
	public int deletePatients(Integer id)
	{
		List<Appointments> appointments=patientsDAO.getAppointmentsListByPatientsId(id);
		List<CallLogs> callLogs=patientsDAO.getCallLogsListByPatientsId(id);
		if(appointments.size()==0 && callLogs.size()==0)
		{
			System.out.println(appointments.size());
				System.out.println(callLogs.size());
				patientsDAO.delete(id);
				
			
		}
		
			else
			{
				for(Appointments appointmentss:appointments)
				{
					Integer appointmentsId=appointmentss.getId();
					appointmentsDAO.delete(appointmentsId);
				}
				for(CallLogs callLogss:callLogs)
				{
					Integer callerId=callLogss.getId();
				callLogsDAO.delete(callerId);
				}
				patientsDAO.delete(id);
				
			}
		
		
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
                
                Integer uniqueFileId=1;
                while(serverFile.exists()){
                	serverFile=new File(dir.getAbsolutePath()
                            + File.separator + file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+'-'+uniqueFileId+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
                	uniqueFileId++;
                }
                
                
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                
                
                String fileName=serverFile.getAbsolutePath();
                String crashReportFileName=serverFile.getName();
                
                //Read the Content
                BufferedReader br = null;
        		String line = "";
        		String csvSplitBy = ",";
        		
        		
        		String[] patientArray=new String[30];
        		List<Patients> patients=new ArrayList<Patients>();
        		// try starts
       		try {
       			/*
       			File localDir = new File("E:\\CRASHREPORT_Individual\\");
       			
       			File [] files = localDir.listFiles();
       		    for (int i = 0; i < files.length; i++){
       		        if (files[i].isFile()){*/
       		          
       			br = new BufferedReader(new FileReader(fileName));
        		Integer lineNumber=1;
        				
        		while ((line = br.readLine()) != null) {
        			
        			System.out.println("note");
        			System.out.println(line);
        			
        			if(lineNumber>1){
        				
        				patientArray=line.split(csvSplitBy);
        				
        				String latLong=geoLocation.getLocation(patientArray[14].trim());
        				String[] latitudeLongitude=latLong.split(csvSplitBy);
        				// validation ends
        				patients.add(new Patients(null, null,null,patientArray[0].trim(), patientArray[1].trim(), patientArray[2].trim(),
	        							patientArray[3].trim(), patientArray[4].trim(),patientArray[5].trim(), patientArray[6].trim(), patientArray[7].trim(), 
	        							patientArray[8].trim(), patientArray[9].trim() , patientArray[10].trim(), patientArray[11].trim(), 
	        							patientArray[12].trim(), patientArray[13].trim(), Double.parseDouble(latitudeLongitude[0]), Double.parseDouble(latitudeLongitude[1]), patientArray[14].trim(), patientArray[15].trim(), 
	        							patientArray[16].trim(), patientArray[17].trim(),crashReportFileName,1));
        				
        				lineNumber++;
        				
        			}
	        		else{
	        					//patientHeader=line.split(csvSplitBy);
	        					lineNumber++;
	        				}
        				}
        		/*
        		  System.out.println(files[i]);
       		        }
       		    }
       			*/

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
               // serverFile.delete();
               
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
			
			String role=staffService.getCurrentRole();
			if(role=="ROLE_ADMIN"){
				patientss=patientsDAO.getAll();
			}
			else if(role=="ROLE_STAFF"){
				Integer staffId=staffService.getCurrentUserId();
				patientss=patientsDAO.getPatientListByStaffId(staffId);
			}
				
			Integer staffId=0,clinicId=0,doctorId=0;
			
			
			for (Patients patients : patientss) {
				//TODO: Fill the List
				staffId=0;clinicId=0;doctorId=0;
				if(patients.getStaff()!=null)
					staffId=patients.getStaff().getId();
				if(patients.getClinics()!=null)
					clinicId=patients.getClinics().getClinicId();
				
				if(patients.getDoctors()!=null)
					doctorId=patients.getDoctors().getId();
					
			
				PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
		patientsForms.add(patientsForm);
			count++;
			}

			return count;
		}

		
		
		
		
		public Integer deletePatientsByStaffId(Integer id) {
			List<Patients> patients=patientsDAO.getPatientListByStaffId(id);
			for (Patients patientss : patients) {
				int patientsId=patientss.getId();
				System.out.println("patientsid:"+patientsId);
				patientsDAO.deletePatientsByStaffId(patientsId);
				
			}
			return 1;
			
		}

		
		// Validation for patient file upload
		/*// validation
		for(int i=0;i<28;i++){
			
				if(i == 0){
					
					if(patientArray[i].equals(""))
					{
						a=a+ " = - Empty data is not allowed in LOCAL REPORT NUMBER";
			    }
				}
				
				if(i == 1){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in CRASH SEVERITY";
					}
				}
				
				if(i == 2){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in REPORTING AGENCY NAME";
					}
				}
				
				if(i == 3){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in NUMBER OF UNITS";
					}
				}
				
				if(i == 4){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in UNIT IN ERROR";
					}
				}
				
				if(i == 5){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in COUNTY ";
					}
				}
				
				if(i == 6){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in CITY VILLAGE TOWNSHIP *";
					}
				}
				
				if(i == 7){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in CRASH DATE";
					}
				}
				
				if(i == 8){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in TIME OF CRASH";
					}
				}
				
				if(i == 9){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in LOCAL REPORT NUMBER";
					}
				}
				
				if(i == 10){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in UNIT NUMBER";
					}
				}
				
				if(i == 11){
					
					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in NAME: LAST FIRST MIDDLE";
					}
				}
				
				if(i == 12){
					
					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in DATE OF BIRTH";
					}
				}
	
				if(i == 13){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in GENDER";
					}
				}
	
				if(i == 14){

					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in ADDRESS CITY STATE ZIP";
					}
				}
				
				if(i == 15){
					
					if(patientArray[i].equals("")){
						
						a=a+ " = - Empty data is not allowed in CONTACT PHONE - INCLUDE AREA CODE";
					}
				}
				
				if(i == 16){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in INJURIES";
					}
				}
				
				if(i == 17){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in EMS AGENCY";
					}
				}
				
				if(i == 18){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in MEDICAL FACILITY INJURED TAKEN TO";
					}
				}
				
				if(i == 19){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in LOCAL REPORT NUMBER";
					}
				}

				if(i ==20){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in UNIT IN ERROR";
					}
				}

				if(i == 21){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in UNIT NUMBER";
					}
				}

				if(i == 22){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in OWNER NAME";
					}
				}
				
				if(i == 23){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in OWNER PHONE NUMBER";
					}
				}
				
				if(i == 24){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in DAMAGE SCALE";
					}
				}
				
				if(i == 25){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in PROOF OF INSURANCE";
					}
				}

				if(i == 26){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in INSURANCE COMPANY";
					}
				}

				if(i == 27){

					if(patientArray[i].equals("")){
	 
						a=a+ " = - Empty data is not allowed in POLICY NUMBER";
					}
				}
				
		}
		*/
		
		//Remove More than One White Space
		 public String replaceWithWhiteSpacePattern(String str,String replace){
	         
		        Pattern ptn = Pattern.compile("\\s+");
		        Matcher mtch = ptn.matcher(str);
		        return mtch.replaceAll(replace);
		    }

		
			public List<PatientsForm> patientStatus(Integer patientStatus){
				List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
				
				List<Patients> patientss=new ArrayList<Patients>();
				
				patientss=patientsDAO.patientStatus(patientStatus);
				
				Integer staffId=0,clinicId=0,doctorId=0;
				String staffName="N/A";
				String clinicName="N/A";
				String doctorName="N/A";
				
				
				for (Patients patients : patientss) {
					//TODO: Fill the List
					staffId=0;clinicId=0;doctorId=0;
					staffName="N/A";clinicName="N/A";doctorName="N/A";
					if(patients.getStaff()!=null)
					{
						staffId=patients.getStaff().getId();
						staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
					}
					if(patients.getClinics()!=null){
						clinicId=patients.getClinics().getClinicId();
						clinicName=patients.getClinics().getClinicName();
					}
					if(patients.getDoctors()!=null){
						doctorId=patients.getDoctors().getId();
						doctorName=patients.getDoctors().getDoctorName();
					}
						
					
					PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
					patientsForm.setCallerName(staffName);
					patientsForm.setClinicName(clinicName);
					patientsForm.setDoctorName(doctorName);
					patientsForms.add(patientsForm);
				}
				
				return patientsForms;
				
			}
			public List<PatientsForm> patientStatus3(){
				List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
				List<Patients> patientss=new ArrayList<Patients>();
				
				User user = (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				String username=user.getUsername();
				System.out.println(username);
				Staff staff=staffDAO.getByUserName(username);
							Integer id=staff.getId();
							
							
							
				
				patientss=staffDAO.getPatientsByAccessToken3(id);
				Integer staffId=0,clinicId=0,doctorId=0;
				String staffName="N/A";
				String clinicName="N/A";
				String doctorName="N/A";
				
				
				for (Patients patients : patientss) {
					//TODO: Fill the List
					staffId=0;clinicId=0;doctorId=0;
					staffName="N/A";clinicName="N/A";doctorName="N/A";
					if(patients.getStaff()!=null)
					{
						staffId=patients.getStaff().getId();
						staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
					}
					if(patients.getClinics()!=null){
						clinicId=patients.getClinics().getClinicId();
						clinicName=patients.getClinics().getClinicName();
					}
					if(patients.getDoctors()!=null){
						doctorId=patients.getDoctors().getId();
						doctorName=patients.getDoctors().getDoctorName();
					}
						
					
					PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
					patientsForm.setCallerName(staffName);
					patientsForm.setClinicName(clinicName);
					patientsForm.setDoctorName(doctorName);
					patientsForms.add(patientsForm);
				}
				
				return patientsForms;
				
			}
			public List<PatientsForm> patientStatus2(){
				List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
				List<Patients> patientss=new ArrayList<Patients>();
				
				User user = (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				String username=user.getUsername();
				System.out.println(username);
				Staff staff=staffDAO.getByUserName(username);
							Integer id=staff.getId();
							
							
							
				
				patientss=staffDAO.getPatientsByAccessToken2(id);
				Integer staffId=0,clinicId=0,doctorId=0;
				String staffName="N/A";
				String clinicName="N/A";
				String doctorName="N/A";
				
				
				for (Patients patients : patientss) {
					//TODO: Fill the List
					staffId=0;clinicId=0;doctorId=0;
					staffName="N/A";clinicName="N/A";doctorName="N/A";
					if(patients.getStaff()!=null)
					{
						staffId=patients.getStaff().getId();
						staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
					}
					if(patients.getClinics()!=null){
						clinicId=patients.getClinics().getClinicId();
						clinicName=patients.getClinics().getClinicName();
					}
					if(patients.getDoctors()!=null){
						doctorId=patients.getDoctors().getId();
						doctorName=patients.getDoctors().getDoctorName();
					}
						
					
					PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
					patientsForm.setCallerName(staffName);
					patientsForm.setClinicName(clinicName);
					patientsForm.setDoctorName(doctorName);
					patientsForms.add(patientsForm);
				}
				
				return patientsForms;
				
			}
			public List<PatientsForm> patientStatus1(){
				List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
				List<Patients> patientss=new ArrayList<Patients>();
				
				User user = (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				String username=user.getUsername();
				System.out.println(username);
				Staff staff=staffDAO.getByUserName(username);
							Integer id=staff.getId();
							
							
							
				
				patientss=staffDAO.getPatientsByAccessToken1(id);
				Integer staffId=0,clinicId=0,doctorId=0;
				String staffName="N/A";
				String clinicName="N/A";
				String doctorName="N/A";
				
				
				for (Patients patients : patientss) {
					//TODO: Fill the List
					staffId=0;clinicId=0;doctorId=0;
					staffName="N/A";clinicName="N/A";doctorName="N/A";
					if(patients.getStaff()!=null)
					{
						staffId=patients.getStaff().getId();
						staffName=patients.getStaff().getFirstName()+" "+patients.getStaff().getLastName();
					}
					if(patients.getClinics()!=null){
						clinicId=patients.getClinics().getClinicId();
						clinicName=patients.getClinics().getClinicName();
					}
					if(patients.getDoctors()!=null){
						doctorId=patients.getDoctors().getId();
						doctorName=patients.getDoctors().getDoctorName();
					}
						
					
					PatientsForm patientsForm=new PatientsForm(patients.getId(),staffId,clinicId,doctorId,patients.getLocalReportNumber(),patients.getCrashSeverity(),patients.getReportingAgencyName(),patients.getNumberOfUnits(),patients.getUnitInError(),patients.getCountry(),patients.getCityVillageTownship(),patients.getCrashDate(),patients.getTimeOfCrash().toString(),patients.getUnitNumber(),patients.getName(),patients.getDateOfBirth(),patients.getGender(),patients.getAddress(),patients.getPhoneNumber(),patients.getInjuries(),patients.getEmsAgency(),patients.getMedicalFacility(),patients.getCrashReportFileName(),patients.getPatientStatus());
					patientsForm.setCallerName(staffName);
					patientsForm.setClinicName(clinicName);
					patientsForm.setDoctorName(doctorName);
					patientsForms.add(patientsForm);
				}
				
				return patientsForms;
				
			}
			
			public Integer activeStatusByPatientId(Integer id){
				Integer status=1;
				patientsDAO.activeStatusByPatientId(id);
				return status;
		    }
			

}
