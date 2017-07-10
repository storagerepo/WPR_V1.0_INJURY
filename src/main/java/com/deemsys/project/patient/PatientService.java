package com.deemsys.project.patient;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.County.CountyService;
import com.deemsys.project.CrashReport.CrashReportDAO;
import com.deemsys.project.CrashReport.CrashReportService;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.Map.GeoLocation;
import com.deemsys.project.ReportingAgency.ReportingAgencyDAO;
import com.deemsys.project.VehicleMakeAbbreviation.VehicleMakeAbbreviationDAO;
import com.deemsys.project.Caller.CallerDAO;
import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.entity.Appointments;
import com.deemsys.project.entity.CallLog;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.Caller;
import com.deemsys.project.entity.VehicleMakeAbbreviation;
import com.deemsys.project.login.LoginService;

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
	ReportingAgencyDAO reportingAgencyDAO;
	
	@Autowired
	VehicleMakeAbbreviationDAO vehicleMakeAbbreviationDAO;
	
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
		if(patient.getLatitude().equals(0)&&patient.getLongitude().equals(0)){
			String latLong = geoLocation.getLocation(patientForm.getAddress());
			BigDecimal longitude = new BigDecimal(0);
			BigDecimal latitude = new BigDecimal(0);
			if (!latLong.equals("NONE")) {
				String[] latitudeLongitude = latLong.split(",");
				latitude = new BigDecimal(latitudeLongitude[0]);
				longitude = new BigDecimal(latitudeLongitude[1]);
			}
			patientForm.setLatitude(InjuryConstants.convertBigDecimaltoDouble(latitude));
			patientForm.setLongitude(InjuryConstants.convertBigDecimaltoDouble(longitude));
			patient.setLatitude(latitude);
			patient.setLongitude(longitude);
			
			// Update Patient With Latitude Longitude
			patientDAO.update(patient);
		}else{
			patientForm.setLatitude(InjuryConstants.convertBigDecimaltoDouble(patient.getLatitude()));
			patientForm.setLongitude(InjuryConstants.convertBigDecimaltoDouble(patient.getLongitude()));
		}
		
		return patientForm;
		
	}

	// Update an Entry
	public int updatePatientCurrentAddedDate(PatientForm patientForm) {
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
	
	public PatientGroupedSearchResult getCurrentPatientList(CallerPatientSearchForm callerPatientSearchForm){
		
		PatientGroupedSearchResult patientGroupedSearchResult = new PatientGroupedSearchResult();
		
		String role=loginService.getCurrentRole();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)){
			callerPatientSearchForm.setCallerAdminId(callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)){
			callerPatientSearchForm.setCallerAdminId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId());
			callerPatientSearchForm.setCallerId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			callerPatientSearchForm.setLawyerAdminId(lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID()).getLawyerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
			callerPatientSearchForm.setLawyerAdminId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin().getLawyerAdminId());
			callerPatientSearchForm.setLawyerId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerId());
		}
		patientGroupedSearchResult=getFormattedSearchResult(patientDAO.searchPatientsByCAdmin(callerPatientSearchForm,false),callerPatientSearchForm.getIsArchived());
			
		return patientGroupedSearchResult;
	}	
		
		
	//Patient -> Patient Form	
	public PatientForm getPatientForm(Patient patient) {

		PatientForm patientForm = new PatientForm(patient.getPatientId(),patient.getCrashReport().getCrashId(),
				patient.getCrashReport().getLocalReportNumber(), patient.getCrashSeverity(),
				patient.getReportingAgencyNcic(),
				patient.getReportingAgencyName(), patient.getNumberOfUnits(),
				patient.getUnitInError(), patient.getCityVillageTownship(),
				InjuryConstants.convertMonthFormat(patient.getCrashDate()), InjuryConstants.convertMonthFormat(patient.getAddedDate()),
				patient.getTimeOfCrash(), patient.getUnitNumber(),
				patient.getName(), patient.getDateOfBirth(),patient.getAge(),patient.getGender(), 
				patient.getAddress(),
				InjuryConstants.convertBigDecimaltoDouble(patient.getLatitude()), InjuryConstants.convertBigDecimaltoDouble(patient.getLongitude()),
				patient.getPhoneNumber(), patient.getInjuries(),
				patient.getEmsAgency(), patient.getMedicalFacility(),
				patient.getAtFaultInsuranceCompany(),
				patient.getAtFaultPolicyNumber(),
				patient.getVictimInsuranceCompany(),
				patient.getVictimPolicyNumber(),patient.getTier(),
				patient.getVehicleMakeAbbreviation().getAbbreviation(),patient.getVehicleYear(),
				patient.getVin(),patient.getLicensePlateNumber(),patient.getIsOwner(),
				patient.getPatientStatus(),
				patient.getCrashReport().getFilePath(), patient.getStatus(),patient.getSeatingPosition(),patient.getDamageScale(),patient.getIsRunnerReport());

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
	
	VehicleMakeAbbreviation vehicleMakeAbbreviation = vehicleMakeAbbreviationDAO.getVehicleMakeAbbreviationByMake(patientForm.getVehicleMake());
	if(vehicleMakeAbbreviation==null){
		vehicleMakeAbbreviation=new VehicleMakeAbbreviation(patientForm.getVehicleMake(), patientForm.getVehicleMake(), 1, null);
		vehicleMakeAbbreviationDAO.save(vehicleMakeAbbreviation);
	}
	
	//Date 
	LocalDate addedDate=new LocalDate();
	
	//Mapping
	Patient patient = new Patient(patientForm.getPatientId(),crashReport, county,
			vehicleMakeAbbreviation,
			patientForm.getLocalReportNumber(),
			patientForm.getCrashSeverity(),
			patientForm.getReportingAgencyNcic(),
			patientForm.getReportingAgencyName(),
			patientForm.getNumberOfUnits(), patientForm.getUnitInError(),
			 patientForm.getCityVillageTownship(),
			InjuryConstants.convertYearFormat(patientForm.getCrashDate()), addedDate.toDate(),
			patientForm.getTimeOfCrash(), patientForm.getUnitNumber(),
			patientForm.getName(), patientForm.getDateOfBirth(),patientForm.getAge(),
			patientForm.getGender(), patientForm.getAddress(),
			InjuryConstants.convertDoubleBigDecimal(patientForm.getLatitude()), InjuryConstants.convertDoubleBigDecimal(patientForm.getLongitude()),
			patientForm.getPhoneNumber(), patientForm.getInjuries(),
			patientForm.getEmsAgency(), patientForm.getMedicalFacility(),
			patientForm.getAtFaultInsuranceCompany(),
			patientForm.getAtFaultPolicyNumber(),
			patientForm.getVictimInsuranceCompany(),
			patientForm.getVictimPolicyNumber(), patientForm.getSeatingPosition(), patientForm.getDamageScale(),patientForm.getTier(),
			patientForm.getVehicleYear(),patientForm.getVin(),
			patientForm.getLicensePlateNumber(), patientForm.getIsOwner(),
			patientForm.getPatientStatus(),
			patientForm.getCrashReportFileName(), patientForm.getIsRunnerReport(), patientForm.getStatus(),
			null, null);
		
		return patient;
	}
	
	
	//Patient -> Patient View Form
	public PatientViewForm getPatientViewForm(Patient patient) {

		PatientViewForm patientViewForm = new PatientViewForm(patient.getPatientId(),
				patient.getCrashReport().getLocalReportNumber(),
				InjuryConstants.convertMonthFormat(patient.getCrashDate()), patient.getCrashSeverity(),
				InjuryConstants.convertMonthFormat(patient.getAddedDate()), patient.getName(),patient.getCrashReport().getFilePath());

		// Null Exception Check
		if (patient.getCounty() != null) {
			patientViewForm.setCountyId(patient.getCounty().getCountyId());
			patientViewForm.setCounty(patient.getCounty().getName());
		}

		return patientViewForm;
	}
	
	public PatientGroupedSearchResult getFormattedSearchResult(PatientSearchResultSet patientSearchResultSet,Integer isArchived){
		
		List<PatientSearchResult> patientSearchResults = new ArrayList<PatientSearchResult>();
		PatientGroupedSearchResult patientGroupedSearchResult = new PatientGroupedSearchResult();
		if(isArchived==1){
			List<PatientSearchResultGroupByArchived> patientSearchResultGroupByArchivedList=this.getArciveDateFormattedSearchResult(patientSearchResultSet);
			String localReportNumber="";
			String archivedDate="";
			for (PatientSearchResultGroupByArchived patientSearchResultGroupByArchived : patientSearchResultGroupByArchivedList) {
				int rowCount=0;	
				PatientSearchResultGroupBy patientSearchResultGroupBy=new PatientSearchResultGroupBy();
				List<PatientSearchResultGroupBy> patientSearchResultGroupByList=new ArrayList<PatientSearchResultGroupBy>();
				for (PatientSearchList resultSet : patientSearchResultGroupByArchived.getPatientSearchLists()) {
					if(!localReportNumber.equals(resultSet.getLocalReportNumber()) || !archivedDate.equals(patientSearchResultGroupByArchived.getArchivedDate())){
						localReportNumber=resultSet.getLocalReportNumber();
						archivedDate=patientSearchResultGroupByArchived.getArchivedDate();
						if(rowCount!=0){
							patientSearchResultGroupByList.add(patientSearchResultGroupBy);
						}				
						patientSearchResultGroupBy=new PatientSearchResultGroupBy(resultSet.getLocalReportNumber(),resultSet.getUnitInError(),resultSet.getCrashDate(),resultSet.getAddedDate(),resultSet.getIsRunnerReport(),resultSet.getRunnerReportAddedDate(),resultSet.getNumberOfPatients(),resultSet.getDirectReportStatus(),resultSet.getVehicleCount(),new ArrayList<PatientSearchList>());
					}				
					//Set patient
					patientSearchResultGroupBy.getPatientSearchLists().add(resultSet);
					rowCount++;
				}
				if(rowCount>0)
				patientSearchResultGroupByList.add(patientSearchResultGroupBy);
				
				PatientSearchResult patientSearchResult=new PatientSearchResult(patientSearchResultGroupByArchived.getArchivedDate(), patientSearchResultGroupByList);
				patientSearchResults.add(patientSearchResult);
			}
			
			patientGroupedSearchResult = new PatientGroupedSearchResult(patientSearchResultSet.getTotalNoOfRecords(), patientSearchResults);
		
		}else{
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
					patientSearchResultGroupBy=new PatientSearchResultGroupBy(resultSet.getLocalReportNumber(),resultSet.getUnitInError(),resultSet.getCrashDate(),resultSet.getAddedDate(),resultSet.getIsRunnerReport(),resultSet.getRunnerReportAddedDate(),resultSet.getNumberOfPatients(),resultSet.getDirectReportStatus(),resultSet.getVehicleCount(),new ArrayList<PatientSearchList>());
				}				
				//Set patient
				patientSearchResultGroupBy.getPatientSearchLists().add(resultSet);
				rowCount++;
			}
			if(rowCount>0)
			patientSearchResultGroupByList.add(patientSearchResultGroupBy);
			
			PatientSearchResult patientSearchResult=new PatientSearchResult("", patientSearchResultGroupByList);
			patientSearchResults.add(patientSearchResult);
			// Final Grouped Result Set
			patientGroupedSearchResult = new PatientGroupedSearchResult(patientSearchResultSet.getTotalNoOfRecords(), patientSearchResults);
		}
	
		
		return patientGroupedSearchResult;
	}
	
	public PatientSearchResultSet getExportPatient(CallerPatientSearchForm callerPatientSearchForm){
		
		String role=loginService.getCurrentRole();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)){
			callerPatientSearchForm.setCallerAdminId(callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)){
			callerPatientSearchForm.setCallerAdminId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId());
			callerPatientSearchForm.setCallerId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			callerPatientSearchForm.setLawyerAdminId(lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID()).getLawyerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
			callerPatientSearchForm.setLawyerAdminId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin().getLawyerAdminId());
			callerPatientSearchForm.setLawyerId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerId());
		}
		return patientDAO.searchPatientsByCAdmin(callerPatientSearchForm,true);
		
	}
	
	// Patient Search Result Group By Archived Date
	public List<PatientSearchResultGroupByArchived> getArciveDateFormattedSearchResult(PatientSearchResultSet patientSearchResultSet){
		
		String archivedDate="";
		List<PatientSearchResultGroupByArchived> patientSearchResultGroupByArchivedList = new ArrayList<PatientSearchResultGroupByArchived>();
		PatientSearchResultGroupByArchived patientSearchResultGroupByArchived = new PatientSearchResultGroupByArchived();
		int rowCount=0;		
		for (PatientSearchList resultSet : patientSearchResultSet.getPatientSearchLists()) {
			if(!archivedDate.equals(resultSet.getArchivedDate())){
				archivedDate=resultSet.getArchivedDate();
				if(rowCount!=0){
					patientSearchResultGroupByArchivedList.add(patientSearchResultGroupByArchived);
				}				
				patientSearchResultGroupByArchived = new PatientSearchResultGroupByArchived(archivedDate, new ArrayList<PatientSearchList>());
			}				
			//Set patient
			patientSearchResultGroupByArchived.getPatientSearchLists().add(resultSet);
			rowCount++;
		}
		if(rowCount>0)
		patientSearchResultGroupByArchivedList.add(patientSearchResultGroupByArchived);
		
		return patientSearchResultGroupByArchivedList;
	}
	
}
