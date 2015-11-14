package com.deemsys.project.pdfcrashreport;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.deemsys.project.patients.PatientsForm;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

@Service
public class PDFCrashReportReader {
	
	protected static Logger logger = LoggerFactory.getLogger("service");
	/**
	 * Parses a PDF to a plain text file.
	 * 
	 * From PDF 
	 *
	 * @param txt
	 *            the resulting text
	 * @throws IOException
	 */
	public List<List<String>> parsePdfFromURL(String crashId) throws IOException {
		List<List<String>> contentList=new ArrayList<List<String>>();
		try{
			PdfReader reader = new PdfReader(new URL("https://ext.dps.state.oh.us/CrashRetrieval/ViewCrashReport.aspx?redirectPage=ViewCrashReport.aspx&RequestFrom=ViewEfilePDF&CrashId="+crashId).openStream());
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			String pdfText="";
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,new SimpleTextExtractionStrategy());			
				pdfText = strategy.getResultantText();
				contentList.add(Arrays.asList(pdfText.split("\n")));
			}
		}
		catch(Exception ex){
			logger.error("Parse PDF From URL");
			logger.error("Crash ID:"+crashId);
			logger.error(ex.toString());
		}
		
		return contentList;
	}
	
	
	/**
	 * Parses a PDF to a plain text file.
	 * 
	 * From File
	 * 
	 * @throws IOException
	 */
	public List<List<String>> parsePdf(String fileName) throws IOException {
		List<List<String>> contentList=new ArrayList<List<String>>();
		try{
			PdfReader reader = new PdfReader(fileName);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			String pdfText="";
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,new SimpleTextExtractionStrategy());			
				pdfText = strategy.getResultantText();
				contentList.add(Arrays.asList(pdfText.split("\n")));
			}
			logger.error("Filename:"+fileName);
			reader.close();
		}catch(Exception ex){
			logger.error("parsePdf");
			logger.error("Filename:"+fileName);
			logger.error(ex.toString());
		}
		
		return contentList;
	}
	
	public PDFCrashReportJson getValuesFromPDF(List<List<String>> content) throws IOException{
		
		
		List<ReportUnitPageForm> reportUnitPageForms=new ArrayList<ReportUnitPageForm>();
	    //Motor List Form
	    List<ReportMotoristPageForm> reportMotoristPageForms=new ArrayList<ReportMotoristPageForm>();		  
	    ReportFirstPageForm reportFirstPageForm=null;
		
	    try{
		//First Page
		List<String> firstPage=content.get(0);
		reportFirstPageForm=new ReportFirstPageForm(firstPage.get(
				firstPage.indexOf("LOCAL REPORT NUMBER *")+1), 
				firstPage.get(firstPage.indexOf("CRASH SEVERITY HIT/SKIP")-4),firstPage.get(firstPage.indexOf("REPORTING AGENCY NAME *")+1),
				firstPage.get(firstPage.indexOf("NUMBER OF ")-1),
				firstPage.get(firstPage.indexOf("UNIT IN ERROR")-1), 
				firstPage.get(firstPage.indexOf("COUNTY *")+1), 
				firstPage.get(firstPage.indexOf("CITY, VILLAGE, TOWNSHIP  *")+1),
				firstPage.get(firstPage.indexOf("CRASH DATE *")+1), firstPage.get(firstPage.indexOf("TIME OF  CRASH")+1));
		
		//Unit Pages
				
	    int i;
	    for(i=1;i<=Integer.parseInt(reportFirstPageForm.getNumberOfUnits());i++){
	    	
	    	//Unit Page Content
	    	List<String> unitPage=content.get(i);
	    	
	    	String ownerPhoneNumber="",damageScale="",insuranceCompany="",policyNumber="";
	    	
	    	ownerPhoneNumber=unitPage.get(unitPage.indexOf("OWNER PHONE NUMBER -  INC, AREA  CODE     ( SAME AS DRIVER )")+1).equals("UNIT NUMBER")?"":unitPage.get(unitPage.indexOf("OWNER PHONE NUMBER -  INC, AREA  CODE     ( SAME AS DRIVER )")+1);
	    	damageScale=unitPage.get(unitPage.indexOf("DAMAGE SCALE")+1).equals("1 - NONE")?"":unitPage.get(unitPage.indexOf("DAMAGE SCALE")+1);
	    	insuranceCompany=unitPage.indexOf("INSURANCE COMPANY")==-1?"":unitPage.get(unitPage.indexOf("INSURANCE COMPANY")+1);
	    	policyNumber=unitPage.indexOf("POLICY NUMBER")==-1?"":unitPage.get(unitPage.indexOf("POLICY NUMBER")+1);
	    	
	    	ReportUnitPageForm reportUnitPageForm=new ReportUnitPageForm(unitPage.get(unitPage.indexOf("UNIT NUMBER")+1),
	    			unitPage.get(unitPage.indexOf("OWNER NAME: LAST, FIRST, MIDDLE       ( SAME AS DRIVER )")+1),ownerPhoneNumber, 
	    			unitPage.get(unitPage.indexOf("OWNER ADDRESS: CITY, STATE, ZIP                  SAME AS DRIVER )")+1), 
	    			unitPage.get(unitPage.indexOf("# OCCUPANTS")+1), 
	    			insuranceCompany, 
	    			policyNumber,damageScale);
	    	
	    	reportUnitPageForms.add(reportUnitPageForm);
	    }
	    
	
	    for(;i<content.size();i++){
	    	
    	//Motorist Page Content
    	List<String> motoristPage=content.get(i);  	
    	int index=0;
    	if(motoristPage.get(3).equals("MOTORIST/NON-MOTORIST")){
    		for (index=0;index<motoristPage.size();index++) {
    		   	
        		if(motoristPage.get(index).equals("UNIT  NUMBER")||motoristPage.get(index).equals("UNIT NUMBER")){
            		if((Integer.parseInt(motoristPage.get(index+1).trim())!=Integer.parseInt(reportFirstPageForm.getUnitInError().trim()))||Integer.parseInt(reportFirstPageForm.getNumberOfUnits().trim())==1){
            			ReportMotoristPageForm motoristPageForm=new ReportMotoristPageForm(); 
            			motoristPageForm.setUnitNumber(motoristPage.get(index+1));
            			if(motoristPage.get(index+2).equals("NAME: LAST, FIRST, MIDDLE"))
            				motoristPageForm.setName(motoristPage.get(index+3));
            			if(motoristPage.get(index+4).equals("DATE  OF BIRTH"))
            				motoristPageForm.setDateOfBirth(motoristPage.get(index+5));
            			if(motoristPage.get(index+8).equals("GENDER"))
            				motoristPageForm.setGender(motoristPage.get(index+9));
            			if(motoristPage.get(index-2).equals("ADDRESS, CITY, STATE, ZIP"))
            				motoristPageForm.setAdddressCityStateZip(motoristPage.get(index-1));
            			if(motoristPage.get(index-4).equals("CONTACT PHONE  -  INCLUDE  AREA  CODE"))
            				motoristPageForm.setContactPhone(motoristPage.get(index-3));
            			
            			if(motoristPage.get(index-21).equals("INJURIES"))
            				motoristPageForm.setInjuries(motoristPage.get(index-20));
            			else if(motoristPage.get(index-25).equals("INJURIES"))
            				motoristPageForm.setInjuries(motoristPage.get(index-24));
            			
            			if(motoristPage.get(index-17).equals("EMS AGENCY"))
            				motoristPageForm.setEmsAgency(motoristPage.get(index-16));
            			else if(motoristPage.get(index-21).equals("EMS AGENCY"))
            				motoristPageForm.setEmsAgency(motoristPage.get(index-20));
            			
            			if(motoristPage.get(index-15).equals("MEDICAL FACILITY  INJURED TAKEN TO"))
            				motoristPageForm.setMedicalFacility(motoristPage.get(index-14));
            			else if(motoristPage.get(index-19).equals("MEDICAL FACILITY  INJURED TAKEN TO"))
            				motoristPageForm.setMedicalFacility(motoristPage.get(index-18));
            			
            			reportMotoristPageForms.add(motoristPageForm);
            			}
            		
        			}        	
    			}   
    		}    	 	
	    }
	    }
	    catch(Exception ex){
	    	logger.error("Error Reading:"+reportFirstPageForm.getLocalReportNumber());
	    	logger.error(ex.toString());
	    }
    	
    	return new PDFCrashReportJson(reportFirstPageForm, reportUnitPageForms, reportMotoristPageForms);
    	
    	
	}
	
	public boolean checkStatus(PDFCrashReportJson pdfCrashReportJson){
		
		//Check for the report
		//1. Check for unit number
		try{
			Integer unitInError=Integer.parseInt(pdfCrashReportJson.getFirstPageForm().getUnitInError());
			
			if(unitInError!=99&&unitInError!=98){
				ReportUnitPageForm unitPageForm=pdfCrashReportJson.getReportUnitPageForms().get(unitInError-1);
				if(!unitPageForm.getInsuranceCompany().equals("")&&!unitPageForm.getPolicyNumber().equals("")){
					return true;
				}else{
					//Skip the form
					return false;
				}
			}else{
				return true;
			}
			
			
		}catch(Exception ex){
			System.out.println(ex.toString());
			return false;
		}
	}
	
	
	public List<PatientsForm> getPatientForm(PDFCrashReportJson pdfCrashReportJson){
		
		List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();	
		
		ReportFirstPageForm firstPageForm=pdfCrashReportJson.getFirstPageForm();
		
		for (ReportMotoristPageForm motoristPageForm : pdfCrashReportJson.getReportMotoristPageForms()) {	
			if(motoristPageForm.getAdddressCityStateZip()!=null&&motoristPageForm.getContactPhone()!=null){
				PatientsForm patientsForm=new PatientsForm();
				patientsForm.setName(motoristPageForm.getName());
				patientsForm.setUnitNumber(motoristPageForm.getUnitNumber());
				patientsForm.setDateOfBirth(motoristPageForm.getDateOfBirth());
				patientsForm.setGender(motoristPageForm.getGender());
				patientsForm.setAddress(motoristPageForm.getAdddressCityStateZip());
				patientsForm.setPhoneNumber(motoristPageForm.getContactPhone());
				patientsForm.setInjuries(motoristPageForm.getInjuries());
				patientsForm.setEmsAgency(motoristPageForm.getEmsAgency());
				patientsForm.setMedicalFacility(motoristPageForm.getMedicalFacility());			
				patientsForm.setLocalReportNumber(firstPageForm.getLocalReportNumber());
				patientsForm.setCrashSeverity(firstPageForm.getCrashSeverity());
				patientsForm.setReportingAgencyName(firstPageForm.getReportingAgencyName());
				patientsForm.setCountry(firstPageForm.getCounty());
				patientsForm.setNumberOfUnits(firstPageForm.getNumberOfUnits());
				patientsForm.setUnitInError(firstPageForm.getUnitInError());
				patientsForm.setCityVillageTownship(firstPageForm.getCityVillageTownship());
				patientsForm.setCrashDate(firstPageForm.getCrashDate());
				patientsForm.setTimeOfCrash(firstPageForm.getTimeOfCrash());		
				patientsForm.setPatientStatus(1);
				
				patientsForms.add(patientsForm);
			}
			
		}
		
		
		
		return patientsForms;
	}
}
