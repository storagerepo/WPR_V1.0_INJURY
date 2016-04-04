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
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.CrashReportError;
import com.deemsys.project.patient.PatientForm;
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
		CrashReport crashReport=new CrashReport(crashReportError, crashReportForm.getLocalReportNumber(), crashReportForm.getCrashId(), crashReportForm.getCrashDate(), 
					county, crashReportForm.getAddedDate(), crashReportForm.getFilePath(),crashReportForm.getNumberOfPatients(),1);
		
	
		
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
				InjuryConstants.convertMonthFormat(new Date()), filePath,0, 1);
		return crashReportForm;
	}
	
	// Get Crash Report Form Details from PdfJson Form
	public CrashReportForm getCrashReportFormDetails(ReportFirstPageForm reportFirstPageForm,Integer crashId,String filePath,Integer crashReportErrorId,Integer numberOfPatients){
		CrashReportForm crashReportForm=new CrashReportForm(crashReportErrorId.toString(), reportFirstPageForm.getLocalReportNumber(), crashId.toString(), reportFirstPageForm.getCrashDate(), reportFirstPageForm.getCounty(),
				InjuryConstants.convertMonthFormat(new Date()), filePath,numberOfPatients, 1);
		return crashReportForm;
	}
	
	// Search Crash Report
	public CrashReportList searchCrashReports(CrashReportSearchForm crashReportSearchForm){
		if(!crashReportSearchForm.getNumberOfDays().equals("")){
			if(!crashReportSearchForm.getCrashFromDate().equals("")){
				crashReportSearchForm.setCrashToDate(InjuryConstants.getToDateByAddingNumberOfDays(crashReportSearchForm.getCrashFromDate(), Integer.parseInt(crashReportSearchForm.getNumberOfDays())));
			}
		}
		CrashReportList crashReportList=crashReportDAO.searchCrashReports(crashReportSearchForm.getLocalReportNumber(), crashReportSearchForm.getCrashId(), 
													crashReportSearchForm.getCrashFromDate(), crashReportSearchForm.getCrashToDate(), crashReportSearchForm.getCounty(), crashReportSearchForm.getAddedFromDate(), crashReportSearchForm.getAddedToDate(), crashReportSearchForm.getRecordsPerPage(), crashReportSearchForm.getPageNumber());
		
		return crashReportList;
	}
	
	// Split the PDF County and get Exact county Name
	public String splitCountyName(String countyName){
		return countyName.split("\\s+")[0];
	}
	
}
