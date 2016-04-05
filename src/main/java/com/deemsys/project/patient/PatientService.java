package com.deemsys.project.patient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.entity.Patient;
import com.deemsys.project.Appointments.AppointmentsDAO;
import com.deemsys.project.CallLogs.CallLogsDAO;
import com.deemsys.project.Clinics.ClinicsDAO;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyList;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.CrashReport.CrashReportDAO;
import com.deemsys.project.CrashReport.CrashReportService;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.Map.GeoLocation;
import com.deemsys.project.Caller.CallerDAO;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.Clinic;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.Doctor;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.Lawyer;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;

/**
 * 
 * @author Deemsys
 * 
 *         Patient - Entity patient - Entity Object patients - Entity List
 *         patientDAO - Entity DAO patientForms - EntityForm List PatientForm
 *         - EntityForm
 * 
 */
@Service
@Transactional
public class PatientService {

	@Autowired
	PatientDAO patientDAO;
	
	@Autowired
	CallerDAO callerDAO;
	
	@Autowired
	CallerService callerService;	

	@Autowired
	GeoLocation geoLocation;

	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	CrashReportDAO crashReportDAO;
	
	@Autowired
	CrashReportService crashReportService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	LawyersService lawyersService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CountyService countyService;
	/*
	 * @Autowired PatientFileRead patientFileRead;
	 */

	// Get All Entries
	public List<PatientForm> getPatientList() {
		List<PatientForm> patientForms = new ArrayList<PatientForm>();

		List<Patient> patients = new ArrayList<Patient>();

		String role = callerService.getCurrentRole();
		if (role == InjuryConstants.INJURY_CALLER_ADMIN_ROLE) {
			patients = patientDAO.getAll();
		}else if (role == InjuryConstants.INJURY_CALLER_ROLE) {
			Integer userId = callerService.getCurrentUserId();
			// get Caller Id
			Integer callerId = callerDAO.getByUserId(userId).getCallerId();
			patients=callerDAO.getPatientByAccessToken(callerId);
		}else{
			patients = patientDAO.getAll();
		}

		for (Patient patient : patients) {
			// TODO: Fill the List
			patientForms.add(this.getPatientForm(patient));
		}

		return patientForms;
	}

	
	
	// Get Particular Entry
	public PatientForm getPatient(String patientId) {
		Integer callerAdminId=0;
		String role=loginService.getCurrentRole();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			callerAdminId=callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId();
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			Caller caller=callerDAO.getByUserId(loginService.getCurrentUserID());
			callerAdminId=callerAdminService.getCallerAdmin(caller.getCallerAdmin().getCallerAdminId()).getCallerAdminId();
		}
		Patient patient=patientDAO.getPatientByPatientId(patientId);
		PatientForm patientForm=this.getPatientForm(patient);
		patientForm.setLatitude(patient.getLatitude());
		patientForm.setLongitude(patient.getLongitude());
		return patientForm;
		
	}

	// Update an Entry
	public int updatePatient(PatientForm patientForm) {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		String latLong = geoLocation.getLocation(patientForm.getAddress());
		double longitude = 0.0;
		double latitude = 0.0;
		if (!latLong.equals("NONE")) {
			String[] latitudeLongitude = latLong.split(",");
			latitude = Double.parseDouble(latitudeLongitude[0]);
			longitude = Double.parseDouble(latitudeLongitude[1]);
		}
		
		patientForm.setLatitude(latitude);
		patientForm.setLongitude(longitude);
		
		patientDAO.update(this.getPatient(patientForm));

		return 1;
	}

	//Save an Entity
	public int savePatient(PatientForm patientForm) throws Exception {
		// TODO: Convert Form to Entity Here

		// Logic Starts
		try{
			String latLong = geoLocation.getLocation(patientForm.getAddress());
	
			double longitude = 0.0;
			double latitude = 0.0;
			if (!latLong.equals("NONE")) {
				String[] latitudeLongitude = latLong.split(",");
				latitude = Double.parseDouble(latitudeLongitude[0]);
				longitude = Double.parseDouble(latitudeLongitude[1]);
			}
	
			patientForm.setLatitude(latitude);
			patientForm.setLongitude(longitude);
			try{
				patientDAO.savePatient(this.getPatient(patientForm));
			}catch(Exception exception){
				throw exception;
			}
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		return 1;
	}

	// Delete an Entry
	public int deletePatient(Integer id) {
		List<Appointments> appointments = patientDAO
				.getAppointmentsListByPatientId(id);
		List<CallLog> callLogs = patientDAO.getCallLogsListByPatientId(id);
		if (appointments.size() == 0 && callLogs.size() == 0) {
			System.out.println(appointments.size());
			System.out.println(callLogs.size());
			patientDAO.delete(id);

		}

		

		return 1;
	}

	
  public Integer getNoOfPatients() {
		List<Patient> patients = new ArrayList<Patient>();

		String role = callerService.getCurrentRole();
		if (role == InjuryConstants.INJURY_CALLER_ADMIN_ROLE) {
			patients = patientDAO.getAll();
		} else if (role == InjuryConstants.INJURY_CALLER_ROLE) {
			Integer userId = callerService.getCurrentUserId();
			// get Caller Id
			Integer callerId = callerDAO.getByUserId(userId).getCallerId();
			patients = patientDAO.getPatientListByCallerId(callerId);
		}

		return patients.size();
	}

	public Integer releasePatientFromCaller(Integer id) {
		List<Patient> patient = patientDAO.getPatientListByCallerId(id);
		for (Patient patients : patient) {
		/*	int patientId = patients.getId();
			System.out.println("patientid:" + patientId);
			patientDAO.releasePatientFromCaller(patientId);*/

		}
		return 1;

	}

	// Remove More than One White Space
	public String replaceWithWhiteSpacePattern(String str, String replace) {

		Pattern ptn = Pattern.compile("\\s+");
		Matcher mtch = ptn.matcher(str);
		return mtch.replaceAll(replace);
	}

	public List<PatientForm> getPatientByStatus(Integer patientStatus) {
		List<PatientForm> patientForms = new ArrayList<PatientForm>();

		List<Patient> patients = new ArrayList<Patient>();

		String role = callerService.getCurrentRole();
		if (role == InjuryConstants.INJURY_CALLER_ADMIN_ROLE) {
			patients = patientDAO.getPatientByStatus(patientStatus);
		}else if (role == InjuryConstants.INJURY_CALLER_ROLE) {
			Integer userId = callerService.getCurrentUserId();
			// get Caller Id
			Integer callerId = callerDAO.getByUserId(userId).getCallerId();
			patients = callerDAO.getPatientStatus(patientStatus, callerId);
		}

		for (Patient patient : patients) {
			// TODO: Fill the List
			patientForms.add(this.getPatientForm(patient));
		}

		return patientForms;

	}

	
	public Integer activeStatusByPatientId(Integer id) {
		Integer status = 1;
		patientDAO.activeStatusByPatientId(id);
		return status;
	}

	// Get Patient List By Page Limit
	public List<PatientForm> getPatientByLimit(Integer pageNumber,
			Integer itemsPerPage, String name, String phoneNumber,
			String localReportNumber, String callerName) {
		List<Patient> patients = new ArrayList<Patient>();
		List<PatientForm> patientForms = new ArrayList<PatientForm>();
		

		for (Patient patient : patients) {
			patientForms.add(this.getPatientForm(patient));
		}

		return patientForms;
	}

	// Get Total Patient Count By Page Limit
	public Integer getTotalPatient(String localReportNumber,Integer county, 
			String crashDate,Integer days,String recordedFromDate,String recordedToDate, String name,String customDate) {
		Integer count = 0;
		String toDate="";
		
		String crashToDate="";
		if(crashDate!=""){
			DateTime crashStartDate=DateTime.parse(crashDate,DateTimeFormat.forPattern("MM/dd/yyyy"));
			DateTime crashEndDate=crashStartDate.plusDays(days);
			
			if(days!=0)
				crashToDate=crashEndDate.toString("MM/dd/yyyy");
			else
				crashToDate=customDate;
		}
		
		count = patientDAO.getTotalPatientCount(localReportNumber, county, crashDate, crashToDate, recordedFromDate, recordedToDate, name);
		
		return count;
	}
	
	
	// Get Patient List By Page Limit
		public List<PatientViewForm> searchPatients(Integer pageNumber, Integer itemsPerPage,String localReportNumber,Integer county, 
				String crashDate,Integer days,String recordedFromDate,String recordedToDate, String name, String customDate) {
			List<Patient> patients = new ArrayList<Patient>();
			List<PatientViewForm> patientViewForms = new ArrayList<PatientViewForm>();
			String crashToDate="";
			if(crashDate!=""){
				DateTime crashStartDate=DateTime.parse(crashDate,DateTimeFormat.forPattern("MM/dd/yyyy"));
				DateTime crashEndDate=crashStartDate.plusDays(days);
				
				if(days!=0)
					crashToDate=crashEndDate.toString("MM/dd/yyyy");
				else
					crashToDate=customDate;
			}
			
			
			patients = patientDAO.searchPatients(pageNumber, itemsPerPage,localReportNumber,county, crashDate,crashToDate, recordedFromDate, recordedToDate, name);

			for (Patient patient : patients) {				
				patientViewForms.add(getPatientViewForm(patient));
			}

			return patientViewForms;
		}
	
	public PatientSearchResult getCurrentPatientList(CallerPatientSearchForm callerPatientSearchForm){
		
		PatientSearchResult patientSearchResult=new PatientSearchResult();
		
		String role=loginService.getCurrentRole();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)){
			callerPatientSearchForm.setCallerAdminId(callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)){
			callerPatientSearchForm.setCallerAdminId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId());
			callerPatientSearchForm.setCallerId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			callerPatientSearchForm.setLawyerAdminId(lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID()).getLawyerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
			callerPatientSearchForm.setLawyerAdminId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin().getLawyerAdminId());
			callerPatientSearchForm.setLawyerId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerId());
		}
		patientSearchResult=getFormattedSearchResult(patientDAO.searchPatientsByCAdmin(callerPatientSearchForm));
			
		return patientSearchResult;
	}	
		
		
	//Patient -> Patient Form	
	public PatientForm getPatientForm(Patient patient) {

		PatientForm patientForm = new PatientForm(patient.getPatientId(),patient.getCrashReport().getCrashId(),
				patient.getCrashReport().getLocalReportNumber(), patient.getCrashSeverity(),
				patient.getReportingAgencyName(), patient.getNumberOfUnits(),
				patient.getUnitInError(), patient.getCityVillageTownship(),
				patient.getCrashDate(), patient.getAddedDate(),
				patient.getTimeOfCrash(), patient.getUnitNumber(),
				patient.getName(), patient.getDateOfBirth(),patient.getAge(),patient.getGender(), 
				patient.getAddress(),
				patient.getLatitude(), patient.getLongitude(),
				patient.getPhoneNumber(), patient.getInjuries(),
				patient.getEmsAgency(), patient.getMedicalFacility(),
				patient.getAtFaultInsuranceCompany(),
				patient.getAtFaultPolicyNumber(),
				patient.getVictimInsuranceCompany(),
				patient.getVictimPolicyNumber(),patient.getTier(),patient.getPatientStatus(),
				patient.getCrashReport().getFilePath(), patient.getStatus());

		// Null Exception Check
		if (patient.getCounty() != null) {
			patientForm.setCountyId(patient.getCounty().getCountyId());
			patientForm.setCounty(patient.getCounty().getName());
		}

		return patientForm;
	}
	
		//Patient -> Patient Form	
	public Patient getPatient(PatientForm patientForm) {
	
	//Check Condition
	County county=new County();		
	if(patientForm.getCountyId()==null){
		try{
			county=countyDAO.getCountyByName(crashReportService.splitCountyName(patientForm.getCounty()));			
		}catch(Exception ex){
			
		}
	}else{
		county=countyDAO.get(patientForm.getCountyId());
	}
	
	//Crash Report
	CrashReport crashReport=new CrashReport();
	if(patientForm.getCrashId()!=null){
		crashReport=crashReportDAO.getCrashReport(patientForm.getCrashId());
	}
	
	
	//Date 
	LocalDate addedDate=new LocalDate();
	
	//Mapping
	Patient patient = new Patient(patientForm.getPatientId(),crashReport,
			patientForm.getLocalReportNumber(),
			patientForm.getCrashSeverity(),
			patientForm.getReportingAgencyName(),
			patientForm.getNumberOfUnits(), patientForm.getUnitInError(),
			county, patientForm.getCityVillageTownship(),
			patientForm.getCrashDate(), addedDate.toString("MM/dd/yyyy"),
			patientForm.getTimeOfCrash(), patientForm.getUnitNumber(),
			patientForm.getName(), patientForm.getDateOfBirth(),patientForm.getAge(),
			patientForm.getGender(), patientForm.getAddress(),
			patientForm.getLatitude(), patientForm.getLongitude(),
			patientForm.getPhoneNumber(), patientForm.getInjuries(),
			patientForm.getEmsAgency(), patientForm.getMedicalFacility(),
			patientForm.getAtFaultInsuranceCompany(),
			patientForm.getAtFaultPolicyNumber(),
			patientForm.getVictimInsuranceCompany(),
			patientForm.getVictimPolicyNumber(),patientForm.getTier(),
			patientForm.getPatientStatus(),
			patientForm.getCrashReportFileName(), patientForm.getStatus(),
			null, null);
		
		return patient;
	}
	
	
	//Patient -> Patient View Form
	public PatientViewForm getPatientViewForm(Patient patient) {

		PatientViewForm patientViewForm = new PatientViewForm(patient.getPatientId(),
				patient.getCrashReport().getLocalReportNumber(),
				patient.getCrashDate(), patient.getCrashSeverity(),
				patient.getAddedDate(), patient.getName(),patient.getCrashReport().getFilePath());

		// Null Exception Check
		if (patient.getCounty() != null) {
			patientViewForm.setCountyId(patient.getCounty().getCountyId());
			patientViewForm.setCounty(patient.getCounty().getName());
		}

		return patientViewForm;
	}
	
	public PatientSearchResult getFormattedSearchResult(PatientSearchResultSet patientSearchResultSet){
		
		String localReportNumber="";
		List<PatientSearchResultGroupBy> patientSearchResultGroupByList=new ArrayList<PatientSearchResultGroupBy>();
		PatientSearchResultGroupBy patientSearchResultGroupBy=new PatientSearchResultGroupBy();
	
		int rowCount=0;		
		for (PatientSearchList resultSet : patientSearchResultSet.getPatientSearchLists()) {
			if(!localReportNumber.equals(resultSet.getLocalReportNumber())){
				localReportNumber=resultSet.getLocalReportNumber();
				if(rowCount!=0){
					patientSearchResultGroupByList.add(patientSearchResultGroupBy);
				}				
				patientSearchResultGroupBy=new PatientSearchResultGroupBy(resultSet.getLocalReportNumber(),resultSet.getCrashDate(),resultSet.getAddedDate(),resultSet.getNumberOfPatients(),new ArrayList<PatientSearchList>());
			}				
			//Set patient
			patientSearchResultGroupBy.getPatientSearchLists().add(resultSet);
			rowCount++;
		}
		if(rowCount>0)
		patientSearchResultGroupByList.add(patientSearchResultGroupBy);
		
		PatientSearchResult patientSearchResult=new PatientSearchResult(patientSearchResultSet.getTotalNoOfRecords(), patientSearchResultGroupByList);
		return patientSearchResult;
	}
	
	
	
}
