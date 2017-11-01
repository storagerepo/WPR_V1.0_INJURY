package com.deemsys.project.CrashReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.Caller.CallerService;
import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.CrashReportError.CrashReportErrorDAO;
import com.deemsys.project.LawyerAdmin.LawyerAdminService;
import com.deemsys.project.Lawyers.LawyersService;
import com.deemsys.project.PoliceAgency.PoliceAgencyDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.CrashReportError;
import com.deemsys.project.entity.PoliceAgency;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.PatientForm;
import com.deemsys.project.patient.PatientService;
import com.deemsys.project.pdfcrashreport.PDFCrashReportReader;
import com.deemsys.project.pdfcrashreport.ReportFirstPageForm;
/**
 * 
 * @author Deemsys
 *
 * CrashReport 	 - Entity
 * crashReport 	 - Entity Object
 * crashReports 	 - Entity List
 * crashReportDAO   - Entity DAO
 * crashReportForms - EntityForm List
 * CrashReportForm  - EntityForm
 *
 */
@Service
@Transactional
public class CrashReportService {

	@Autowired
	CrashReportDAO crashReportDAO;
	
	@Autowired
	CrashReportErrorDAO crashReportErrorDAO;
	
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	InjuryProperties injuryProperties;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	LawyersService lawyersService;
	
	@Autowired
	CallerService callerService;
	
	@Autowired
	CallerAdminService callerAdminService;
	
	@Autowired
	LawyerAdminService lawyerAdminService;
	
	@Autowired
	PDFCrashReportReader pdfCrashReportReader;
	
	@Autowired
	PoliceAgencyDAO policeAgencyDAO;
	
	//Get All Entries
	public List<CrashReportForm> getCrashReportList()
	{
		List<CrashReportForm> crashReportForms=new ArrayList<CrashReportForm>();
		return crashReportForms;
	}
	
	//Get Particular Entry
	public CrashReportForm getCrashReport(Integer getId)
	{
		//TODO: Convert Entity to Form
		//Start
		CrashReportForm crashReportForm=new CrashReportForm();
		//End
		return crashReportForm;
	}
	
	//Merge an Entry (Save or Update)
	public int mergeCrashReport(CrashReportForm crashReportForm)
	{
		//TODO: Convert Form to Entity Here
		//Logic Starts
		CrashReport crashReport=new CrashReport();
		//Logic Ends
		crashReportDAO.merge(crashReport);
		return 1;
	}
	
	//Save an Entry
	public int saveCrashReport(CrashReportForm crashReportForm)
	{
		//TODO: Convert Form to Entity Here	
		//Logic Starts
		County county= countyDAO.getCountyByName(this.splitCountyName(crashReportForm.getCounty()));
		CrashReportError crashReportError=crashReportErrorDAO.get(Integer.parseInt(crashReportForm.getCrashReportError()));
		String localReportNumber=crashReportForm.getLocalReportNumber();
		if(crashReportDAO.getCrashReportCountByLocalReportNumber(localReportNumber)>0){
			Long localReportNumberCount=crashReportDAO.getLocalReportNumberCount(localReportNumber+"(");
			localReportNumber=localReportNumber+"("+(localReportNumberCount+1)+")";
			
		}
		// Police Agency
		PoliceAgency policeAgency = policeAgencyDAO.get(crashReportForm.getReportFrom());
		
		CrashReport crashReport=new CrashReport(crashReportForm.getCrashId(), crashReportError, policeAgency, county, localReportNumber,  InjuryConstants.convertYearFormat(crashReportForm.getCrashDate()), 
					 InjuryConstants.convertYearFormat(crashReportForm.getAddedDate()), crashReportForm.getNumberOfPatients(), crashReportForm.getVehicleCount(), crashReportForm.getFilePath(), null, crashReportForm.getIsRunnerReport(),  null, 1, null, null, null);
		
		//Logic Ends
		crashReportDAO.save(crashReport);
		return 1;
	}
	
	//Update an Entry
	public int updateCrashReport(CrashReportForm crashReportForm)
	{
		//TODO: Convert Form to Entity Here	
		//Logic Starts
		CrashReport crashReport=new CrashReport();
		//Logic Ends
		crashReportDAO.update(crashReport);
		return 1;
	}
	
	//Delete an Entry
	public int deleteCrashReport(Integer id)
	{
		crashReportDAO.delete(id);
		return 1;
	}
	
	// Get Crash Report Form Details from Patient Form
	public CrashReportForm getCrashReportFormDetails(PatientForm patientForm,Integer crashId,String filePath,Integer crashReportErrorId){
		Integer reportFrom=0;
		CrashReportForm crashReportForm=new CrashReportForm(crashReportErrorId.toString(), patientForm.getLocalReportNumber(), crashId.toString(), patientForm.getCrashDate(), patientForm.getCounty(),
				InjuryConstants.convertMonthFormat(new Date()), filePath, 0, 0 , 0, InjuryConstants.convertMonthFormat(new Date()),1,reportFrom);
		return crashReportForm;
	}
	
	// Get Crash Report Form Details from PdfJson Form
	public CrashReportForm getCrashReportFormDetails(ReportFirstPageForm reportFirstPageForm,String crashId,String filePath,Integer crashReportErrorId,Integer numberOfPatients,Integer vehicleCount){
		Integer isRunnerReport=0;
		Integer reportFrom=0;
		CrashReportForm crashReportForm=new CrashReportForm(crashReportErrorId.toString(), reportFirstPageForm.getLocalReportNumber(), crashId.toString(), reportFirstPageForm.getCrashDate(), reportFirstPageForm.getCounty(),
				InjuryConstants.convertMonthFormat(new Date()), filePath,numberOfPatients, vehicleCount, isRunnerReport, null, 1, reportFrom);
		return crashReportForm;
	}
	
	// Search Crash Report
	public DirectReportGroupResult searchCrashReports(CrashReportSearchForm crashReportSearchForm){
		
		String role=loginService.getCurrentRole();
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_MANAGER_ROLE)){
			crashReportSearchForm.setCallerAdminId(callerAdminService.getCallerAdminByUserId(loginService.getCurrentUserID()).getCallerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_AUTO_DEALER_ROLE)){
			crashReportSearchForm.setCallerAdminId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerAdmin().getCallerAdminId());
			crashReportSearchForm.setCallerId(callerService.getCallerByUserId(loginService.getCurrentUserID()).getCallerId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			crashReportSearchForm.setLawyerAdminId(lawyerAdminService.getLawyerAdminIdByUserId(loginService.getCurrentUserID()).getLawyerAdminId());
		}else if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)){
			crashReportSearchForm.setLawyerAdminId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerAdmin().getLawyerAdminId());
			crashReportSearchForm.setLawyerId(lawyersService.getLawyerIdByUserId(loginService.getCurrentUserID()).getLawyerId());
		}
		
		CrashReportList crashReportList=crashReportDAO.searchCrashReports(crashReportSearchForm);
		
		return this.groupCrashReportListByArchive(crashReportList, crashReportSearchForm.getIsArchived());
	}
	
	public DirectReportGroupResult groupCrashReportListByArchive(CrashReportList crashReportList,Integer isArchived){
		List<DirectReportGroupListByArchive> directReportGroupListByArchives = new ArrayList<DirectReportGroupListByArchive>();
		if(isArchived==1){
			Integer rowCount=0;
			String archivedDate="";
			DirectReportGroupListByArchive directReportGroupListByArchive = new DirectReportGroupListByArchive();
			for (CrashReportForm crashReportForm : crashReportList.getCrashReportForms()) {
				if(!archivedDate.equals(crashReportForm.getArchivedDate())){
					archivedDate=crashReportForm.getArchivedDate();
					if(rowCount!=0){
						directReportGroupListByArchives.add(directReportGroupListByArchive);
					}
					directReportGroupListByArchive = new DirectReportGroupListByArchive(archivedDate, new ArrayList<CrashReportForm>());
				}
				// Set Crash Report
				directReportGroupListByArchive.getCrashReportForms().add(crashReportForm);
				rowCount++;
			}
			if(rowCount>0)
				directReportGroupListByArchives.add(directReportGroupListByArchive);
		}else{
			DirectReportGroupListByArchive directReportGroupListByArchive = new DirectReportGroupListByArchive("" , crashReportList.getCrashReportForms());
			directReportGroupListByArchives.add(directReportGroupListByArchive);
		}
		
		
		DirectReportGroupResult directReportGroupResult = new DirectReportGroupResult(crashReportList.getTotalNoOfRecords(), directReportGroupListByArchives);
		
		return directReportGroupResult;
	}
	
	// Split the PDF County and get Exact county Name
	public String splitCountyName(String countyName){
		String[] countySplit=countyName.split("\\s+");
		String splittedcountyName="";
		if(countySplit.length==2){
			splittedcountyName=countyName.split("\\s+")[0];
		}else if(countySplit.length==3){
			splittedcountyName=countyName.split("\\s+")[0]+" "+countyName.split("\\s+")[1];
		}
		return splittedcountyName;
	}
	
	// Save Runner Crash Report 
	public int saveRunnerCrashReport(RunnerCrashReportForm runnerCrashReportForm)
	{
		//TODO: Convert Form to Entity Here	
		
		//Logic Starts
		// 15 - Runner Crash Reports
		Integer crashReportErrorId=15;
		Integer isRunnerReport=1;
		Integer vehicleCount=0;
		Integer numberOfPatients=runnerCrashReportForm.getPatientForms().size();
		County county= countyDAO.get(Integer.parseInt(runnerCrashReportForm.getCounty()));
		CrashReportError crashReportError=crashReportErrorDAO.get(crashReportErrorId);
		String localReportNumber=runnerCrashReportForm.getLocalReportNumber();
		if(crashReportDAO.getCrashReportCountByLocalReportNumber(localReportNumber)>0){
			Long localReportNumberCount=crashReportDAO.getLocalReportNumberCount(localReportNumber+"(");
			localReportNumber=localReportNumber+"("+(localReportNumberCount+1)+")";
		}
		
		String crashId=localReportNumber;
		if(runnerCrashReportForm.getReportFrom()==Integer.parseInt(injuryProperties.getProperty("reportFromDeemsys"))){
			crashId=injuryProperties.getProperty("deemsysCrashIdPrefix")+localReportNumber;
		}else{
			crashId=runnerCrashReportForm.getReportPrefixCode()+localReportNumber;
		}
		// Police Agency
		PoliceAgency policeAgency = policeAgencyDAO.get(runnerCrashReportForm.getReportFrom());
		CrashReport crashReport=new CrashReport(crashId, crashReportError, policeAgency, county, localReportNumber,  InjuryConstants.convertYearFormat(runnerCrashReportForm.getCrashDate()), 
					 new Date(), numberOfPatients, vehicleCount, runnerCrashReportForm.getFilePath(), null, isRunnerReport, new Date(), 1,null,null,null);
		
		
		/*String odpsCrashId=this.checkRunnerReportWithODPSReport(runnerCrashReportForm);
		 * if(odpsCrashId==null){
			crashReportDAO.save(crashReport);
		}*/
		crashReportDAO.save(crashReport);
		
		for (PatientForm patientForm : runnerCrashReportForm.getPatientForms()) {
			try {
				patientForm.setCrashId(crashId);
				patientForm.setAge(null);
				patientForm.setTier(null);
				patientForm.setCallerId(null);
				patientForm.setLatitude(null);
				patientForm.setLongitude(null);
				patientForm.setIsOwner(0);
				patientForm.setCountyId(Integer.parseInt(runnerCrashReportForm.getCounty()));
				patientService.savePatient(patientForm);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Logic Ends
		
		return 1;
	}
	
	public void updateCrashReport(String runnerReportCrashId, String crashId, String fileName, Integer crashReportErrorId, Integer isRunnerReport){
		Integer reportConverstionStatus=2;
		if(isRunnerReport==3){
			reportConverstionStatus=4;
		}
		crashReportDAO.updateCrashReportByQuery(runnerReportCrashId, crashId.toString(), crashReportErrorId, fileName, reportConverstionStatus);
	}
	
	//Check Runner Report With ODPS
	public CrashReport checkRunnerReportWithODPSReport(RunnerCrashReportForm runnerCrashReportForm){
		Long oldReportCount=crashReportDAO.getLocalReportNumberCount(runnerCrashReportForm.getLocalReportNumber());
		Integer countyId=Integer.parseInt(runnerCrashReportForm.getCounty());
		CrashReport crashReport=null;
		for (int i = 0; i <=oldReportCount; i++) {
			String localReportNumber=runnerCrashReportForm.getLocalReportNumber();
			if(i!=0){
				localReportNumber=localReportNumber+"("+i+")";
			}
			crashReport=crashReportDAO.getCrashReportForChecking(localReportNumber,runnerCrashReportForm.getCrashDate(), countyId,1);
			if(crashReport!=null&&!crashReport.equals("")){
				break;
			}
		}
		
		return crashReport;
	}
	
	//Collect Police Department Reports
	public List<PoliceDepartmentRunnerDirectReports> getPoliceDepartmentReportDetails(Integer agencyId,Integer county,String date,Integer mapId) throws Exception{
		List<PoliceDepartmentRunnerDirectReports> policeDepartmentRunnerDirectReports=new ArrayList<PoliceDepartmentRunnerDirectReports>();
    	
	//Search URL	
	String searchURL=injuryProperties.getProperty("policeReportCommonLink")+agencyId+"&"+injuryProperties.getProperty("policeReportCommonLinkDateParameter")+"="+date;	
   	
	//Document 
	Document doc = Jsoup.connect(searchURL).get();

   	 Elements tables = doc.select("table");
   	 
   	 Element table=tables.get(1);
   	 
   	 Integer rowIndex=0;
   	 for (Element row : table.select("tr")) {
   		 if(rowIndex!=0&&rowIndex%2==0){
   			Elements tds=row.select("td");
   			if(!tds.get(0).ownText().equals("No Results Returned")){
   				if(tds.get(2).ownText().equals("Accident")){
   					System.out.println("Agency ID:"+agencyId);
   					System.out.println("LocalReportNumber:"+tds.get(1).ownText());
   	   				PoliceDepartmentRunnerDirectReports policeRunnerDirectReports=new PoliceDepartmentRunnerDirectReports(tds.get(1).ownText(),tds.get(2).ownText(),county,tds.get(3).ownText(),injuryProperties.getProperty("policeReportPDFCommonLink")+tds.get(0).select("a").attr("href"),mapId);    				
   	       			policeDepartmentRunnerDirectReports.add(policeRunnerDirectReports);
   	       			savePoliceDepartmentReport(policeRunnerDirectReports);
   	   			}   
   			}else{
   				System.out.println("No Results Returned");
   			}
   		 }
   		 rowIndex++;
        }
   	 
   	 return policeDepartmentRunnerDirectReports;
	}
	
	public int savePoliceDepartmentReport(PoliceDepartmentRunnerDirectReports departmentRunnerDirectReports) throws Exception{
		
		return pdfCrashReportReader.saveDirectRunnerCrashReport(new RunnerCrashReportForm(UUID.randomUUID().toString().replaceAll("-", ""), null,departmentRunnerDirectReports.getLocalReportNumber(), departmentRunnerDirectReports.getCrashDate(), departmentRunnerDirectReports.getCountyId().toString(), departmentRunnerDirectReports.getPdfUrl(), departmentRunnerDirectReports.getMapId(), null, null),1);
	}
	
	public void backupSixMonthOldReportsDataByStoredProcedure(String date){
		crashReportDAO.backupSixMonthOldDataByStoredProcedure(date);
	}
	
}
