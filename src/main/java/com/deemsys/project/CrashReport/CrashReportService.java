package com.deemsys.project.CrashReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.CrashReportError.CrashReportErrorDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.CrashReportError;
import com.deemsys.project.patient.PatientForm;
import com.deemsys.project.patient.PatientService;
import com.deemsys.project.pdfcrashreport.ReportFirstPageForm;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
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
	
	//Get All Entries
	public List<CrashReportForm> getCrashReportList()
	{
		List<CrashReportForm> crashReportForms=new ArrayList<CrashReportForm>();
		
		List<CrashReport> crashReports=new ArrayList<CrashReport>();
		
		crashReports=crashReportDAO.getAll();
		
		for (CrashReport crashReport : crashReports) {
			//TODO: Fill the List
			
		}
		
		return crashReportForms;
	}
	
	//Get Particular Entry
	public CrashReportForm getCrashReport(Integer getId)
	{
		CrashReport crashReport=new CrashReport();
		
		crashReport=crashReportDAO.get(getId);
		
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
		CrashReport crashReport=new CrashReport(crashReportError, localReportNumber, crashReportForm.getCrashId(), InjuryConstants.convertYearFormat(crashReportForm.getCrashDate()), 
					county, InjuryConstants.convertYearFormat(crashReportForm.getAddedDate()), crashReportForm.getFilePath(),crashReportForm.getNumberOfPatients(),crashReportForm.getIsRunnerReport(),  null, crashReportForm.getReportFrom(), 1);
		
	
		
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
		CrashReportForm crashReportForm=new CrashReportForm(crashReportErrorId.toString(), patientForm.getLocalReportNumber(), crashId.toString(), patientForm.getCrashDate(), patientForm.getCounty(),
				InjuryConstants.convertMonthFormat(new Date()), filePath, 0, 0, InjuryConstants.convertMonthFormat(new Date()),1);
		return crashReportForm;
	}
	
	// Get Crash Report Form Details from PdfJson Form
	public CrashReportForm getCrashReportFormDetails(ReportFirstPageForm reportFirstPageForm,String crashId,String filePath,Integer crashReportErrorId,Integer numberOfPatients){
		Integer isRunnerReport=0;
		CrashReportForm crashReportForm=new CrashReportForm(crashReportErrorId.toString(), reportFirstPageForm.getLocalReportNumber(), crashId.toString(), reportFirstPageForm.getCrashDate(), reportFirstPageForm.getCounty(),
				InjuryConstants.convertMonthFormat(new Date()), filePath,numberOfPatients, isRunnerReport, null, 1);
		return crashReportForm;
	}
	
	// Search Crash Report
	public CrashReportList searchCrashReports(CrashReportSearchForm crashReportSearchForm){
		if(!crashReportSearchForm.getNumberOfDays().equals("0")){
			if(!crashReportSearchForm.getCrashFromDate().equals("")){
				crashReportSearchForm.setCrashToDate(InjuryConstants.getToDateByAddingNumberOfDays(crashReportSearchForm.getCrashFromDate(), Integer.parseInt(crashReportSearchForm.getNumberOfDays())));
			}
		}
		CrashReportList crashReportList=crashReportDAO.searchCrashReports(crashReportSearchForm.getLocalReportNumber(), crashReportSearchForm.getCrashId(), 
													crashReportSearchForm.getCrashFromDate(), crashReportSearchForm.getCrashToDate(), crashReportSearchForm.getCountyId(), crashReportSearchForm.getAddedFromDate(), crashReportSearchForm.getAddedToDate(), crashReportSearchForm.getRecordsPerPage(), crashReportSearchForm.getPageNumber(),crashReportSearchForm.getIsRunnerReport());
		
		return crashReportList;
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
		
		CrashReport crashReport=new CrashReport(crashReportError, localReportNumber, crashId, InjuryConstants.convertYearFormat(runnerCrashReportForm.getCrashDate()), 
					county, new Date(), runnerCrashReportForm.getFilePath(), numberOfPatients, isRunnerReport, new Date(), runnerCrashReportForm.getReportFrom(),1);
		
		
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
	
	public void updateCrashReport(String runnerReportCrashId, String crashId, String fileName, Integer crashReportErrorId){
		crashReportDAO.updateCrashReportByQuery(runnerReportCrashId, crashId.toString(), crashReportErrorId, fileName, 2);
	}
	
	//Check Runner Report With ODPS
	public String checkRunnerReportWithODPSReport(RunnerCrashReportForm runnerCrashReportForm){
		Long oldReportCount=crashReportDAO.getLocalReportNumberCount(runnerCrashReportForm.getLocalReportNumber());
		Integer countyId=Integer.parseInt(runnerCrashReportForm.getCounty());
		String crashId=null;
		for (int i = 0; i <=oldReportCount; i++) {
			String localReportNumber=runnerCrashReportForm.getLocalReportNumber();
			if(i!=0){
				localReportNumber=localReportNumber+"("+i+")";
			}
			crashId=crashReportDAO.getCrashReportForChecking(localReportNumber,runnerCrashReportForm.getCrashDate(), countyId);
			if(crashId!=null&&!crashId.equals("")){
				break;
			}
		}
		
		return crashId;
	}
}
