package com.deemsys.project.pdfcrashreport;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

@Service
public class PDFCrashReportReader {
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
		PdfReader reader = new PdfReader(new URL("https://ext.dps.state.oh.us/CrashRetrieval/ViewCrashReport.aspx?redirectPage=ViewCrashReport.aspx&RequestFrom=ViewEfilePDF&CrashId="+crashId).openStream());
		List<List<String>> contentList=new ArrayList<List<String>>();
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		String pdfText="";
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i,new SimpleTextExtractionStrategy());			
			pdfText = strategy.getResultantText();
			contentList.add(Arrays.asList(pdfText.split("\n")));
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
	public List<List<String>> parsePdf(MultipartFile pdfFile) throws IOException {
		PdfReader reader = new PdfReader(pdfFile.getBytes());
		List<List<String>> contentList=new ArrayList<List<String>>();
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		String pdfText="";
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i,new SimpleTextExtractionStrategy());			
			pdfText = strategy.getResultantText();
			contentList.add(Arrays.asList(pdfText.split("\n")));
		}
		
		return contentList;
	}
	
	public PDFCrashReportJson getValuesFromPDF(List<List<String>> content) throws IOException{
		
			
		//First Page
		List<String> firstPage=content.get(0);
		ReportFirstPageForm reportFirstPageForm=new ReportFirstPageForm(firstPage.get(
				firstPage.indexOf("LOCAL REPORT NUMBER *")+1), 
				firstPage.get(firstPage.indexOf("CRASH SEVERITY HIT/SKIP")-4),
				firstPage.get(firstPage.indexOf("NUMBER OF ")-1),
				firstPage.get(firstPage.indexOf("UNIT IN ERROR")-1), 
				firstPage.get(firstPage.indexOf("COUNTY *")+1), 
				firstPage.get(firstPage.indexOf("CITY, VILLAGE, TOWNSHIP  *")+1),
				firstPage.get(firstPage.indexOf("CRASH DATE *")+1), firstPage.get(firstPage.indexOf("TIME OF  CRASH")+1));
		
		//Unit Pages
		List<ReportUnitPageForm> reportUnitPageForms=new ArrayList<ReportUnitPageForm>();		
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
	    			unitPage.get(unitPage.indexOf("OWNER NAME: LAST, FIRST, MIDDLE       ( SAME AS DRIVER )")+1),ownerPhoneNumber 
	    			, 
	    			unitPage.get(unitPage.indexOf("OWNER ADDRESS: CITY, STATE, ZIP                  SAME AS DRIVER )")+1), 
	    			unitPage.get(unitPage.indexOf("# OCCUPANTS")+1), 
	    			insuranceCompany, 
	    			policyNumber,damageScale);
	    	
	    	reportUnitPageForms.add(reportUnitPageForm);
	    }
	    
	    //Motor List Form
	    List<ReportMotoristPageForm> reportMotoristPageForms=new ArrayList<ReportMotoristPageForm>();		  
	   
    	//Unit Page Content
    	List<String> motoristPage=content.get(i);	
    	
    	ReportMotoristPageForm motoristPageForm=new ReportMotoristPageForm(motoristPage.get(motoristPage.indexOf("UNIT  NUMBER")+1),
    			motoristPage.get(motoristPage.indexOf("NAME: LAST, FIRST, MIDDLE")+1), 
    			motoristPage.get(motoristPage.indexOf("DATE  OF BIRTH")+1), 
    			motoristPage.get(motoristPage.indexOf("GENDER")+1), 
    			motoristPage.get(motoristPage.indexOf("ADDRESS, CITY, STATE, ZIP")+1), 
    			motoristPage.get(motoristPage.indexOf("CONTACT PHONE  -  INCLUDE  AREA  CODE")+1), 
    			motoristPage.get(motoristPage.indexOf("INJURIES")+1));
    	
    	reportMotoristPageForms.add(motoristPageForm);
    	
    	return new PDFCrashReportJson(reportFirstPageForm, reportUnitPageForms, reportMotoristPageForms);
    	
    	
	}
}
