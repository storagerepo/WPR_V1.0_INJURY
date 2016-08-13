package com.deemsys.project.pdfcrashreport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.hibernate.mapping.Array;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.CrashReport.CrashReportDAO;
import com.deemsys.project.CrashReport.CrashReportService;
import com.deemsys.project.CrashReportError.CrashReportErrorDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.CrashReportError;
import com.deemsys.project.patient.PatientForm;
import com.deemsys.project.patient.PatientService;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import java.io.*;

@Service
@Transactional
public class PDFCrashReportReader {
	
		
	@Autowired
	InjuryProperties injuryProperties;
	
	@Autowired
	AWSFileUpload awsFileUpload;
	
	@Autowired
	PatientService patientsService;
	
	@Autowired
	CrashReportService crashReportService;
	
	@Autowired
	CrashReportDAO crashReportDAO;
	
	@Autowired
	CrashReportErrorDAO crashReportErrorDAO;

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
	public List<List<String>> parsePdfFromFile(File file)
			throws IOException {
		List<List<String>> contentList = new ArrayList<List<String>>();
		try {
			FileInputStream fileInputStream=new FileInputStream(file);
			PdfReader reader = new PdfReader(fileInputStream);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			String pdfText = "";
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,
						new SimpleTextExtractionStrategy());
				pdfText = strategy.getResultantText();
				contentList.add(Arrays.asList(pdfText.split("\n")));
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return contentList;
	}

	/**
	 * Parses a Manual PDF
	 * 
	 * From PDF
	 * 
	 * @param txt
	 *            the resulting text
	 * @throws IOException
	 */
	public List<List<String>> parsePdfFromMultipartFile(MultipartFile file)
			throws IOException {
		List<List<String>> contentList = new ArrayList<List<String>>();
		try {
			PdfReader reader = new PdfReader(file.getBytes());
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			String pdfText = "";
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,
						new SimpleTextExtractionStrategy());
				pdfText = strategy.getResultantText();
				contentList.add(Arrays.asList(pdfText.split("\n")));
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return contentList;
	}
	
	
	/**
	 * Parses a PDF to a plain text file.
	 * 
	 * From PDF 
	 *
	 * @param txt
	 *            the resulting text
	 * @throws IOException
	 */
	public boolean downloadPDFFile(String crashId) throws IOException {
			
			try{
					File file=getPDFFile(crashId);
					Integer tryCount=Integer.parseInt(injuryProperties.getProperty("reportTryTimes"));
					for (int tryCrash = 1; tryCrash < tryCount; tryCrash++) {						
						if(file!=null){
							if(file.length()>2000){//2000 File Size refers an crash report not found exception
								System.out.println("Got it!!");   
								break;
							}else{
								System.out.println(crashId+"..Failed..Lets try next.!");
								crashId=String.valueOf(Integer.parseInt(crashId)+1);
								System.out.println("Trying..."+crashId);
								file=getPDFFile(crashId);
							}
						}else{
							crashId=String.valueOf(Integer.parseInt(crashId)+1);
							file=getPDFFile(crashId);
						}
						
					}		
					
					if(file.length()>2000){//2000 File Size refers an crash report not found exception
						//Parse the PDF
						parsePDFDocument(new File(file.getAbsoluteFile().getAbsolutePath()),Integer.parseInt(crashId));
						
						// Update Crash Id
						this.updateCrashId(String.valueOf(Integer.parseInt(crashId)+1));
					}else{
						System.out.println("Waiting.....");
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					this.crashLogUpdate(crashId, e);
					this.updateCrashId(String.valueOf(Integer.parseInt(crashId)+1));
					CrashReportError crashReportError=crashReportErrorDAO.get(12);
					crashReportDAO.save(new CrashReport(crashReportError, "", crashId, null, null, new Date() , "", 0,null));
					System.out.println("Failed"+e.toString());
				}
		
			return true;
				
	}  
	
	public File getPDFFile(String crashId) throws Exception{
		File file=null;
		HttpURLConnection httpURLConnection=null;
		try{
			URL url=new URL(injuryProperties.getProperty("odpsLink")+crashId);
			httpURLConnection=(HttpURLConnection) url.openConnection();
			
			Integer tryCount=Integer.parseInt(injuryProperties.getProperty("reportAccessTry"));
			
			//Try access the same link twice to avoid loosing reports.
			for (int accessTry = 0; accessTry < tryCount; accessTry++) {
				if(httpURLConnection.getResponseCode()==200)
				{
					String filePath=injuryProperties
							.getProperty("tempFolder")
							+ "CrashReport_"
							+ crashId + ".pdf";
					try {
						InputStream in = url.openStream();	
						Files.copy(in, Paths.get(filePath),
								StandardCopyOption.REPLACE_EXISTING);
						in.close();
						file=new File(filePath);	
						if(file.length()>2000)
							break;
						else
							System.out.println("Lets hit one more time..");
						}
					catch(Exception ex){
						throw ex;
					}
					finally{
						if(httpURLConnection!=null)
						    httpURLConnection.disconnect();
					}
				}
			}			
		}catch(Exception ex){
			if(httpURLConnection!=null)
			    httpURLConnection.disconnect();
			
			throw ex;
		}
		
		return file;
	}
	
	
	/**
	 * Download PDF
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public List<List<String>> downloadPDFFromCrashIdNotepad(MultipartFile file)
			throws IOException {
		List<List<String>> contentList = new ArrayList<List<String>>();
		try {
			PdfReader reader = new PdfReader(file.getBytes());
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			String pdfText = "";
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,
						new SimpleTextExtractionStrategy());
				pdfText = strategy.getResultantText();
				contentList.add(Arrays.asList(pdfText.split("\n")));
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		return contentList;
	}

	/**
	 * Parses a PDF to a plain text file.
	 * 
	 * From PDF
	 * 
	 * @param txt
	 *            the resulting text
	 * @throws IOException
	 */
	public List<List<String>> parsePdfFromURL(String crashId)
			throws IOException {
		List<List<String>> contentList = new ArrayList<List<String>>();
		try {
			PdfReader reader = new PdfReader(
					new URL(
							"https://ext.dps.state.oh.us/CrashRetrieval/ViewCrashReport.aspx?redirectPage=ViewCrashReport.aspx&RequestFrom=ViewEfilePDF&CrashId="
									+ crashId).openStream());
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			String pdfText = "";
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,
						new SimpleTextExtractionStrategy());
				pdfText = strategy.getResultantText();
				contentList.add(Arrays.asList(pdfText.split("\n")));
			}
		} catch (Exception ex) {
			logger.error("Parse PDF From URL");
			logger.error("Crash ID:" + crashId);
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
		List<List<String>> contentList = new ArrayList<List<String>>();
		try {
			PdfReader reader = new PdfReader(fileName);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			String pdfText = "";
			TextExtractionStrategy strategy;
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
				strategy = parser.processContent(i,
						new SimpleTextExtractionStrategy());
				pdfText = strategy.getResultantText();
				contentList.add(Arrays.asList(pdfText.split("\n")));
			}
			logger.error("Filename:" + fileName);
			reader.close();
		} catch (Exception ex) {
			logger.error("parsePdf");
			logger.error("Filename:" + fileName);
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
	public List<String> getCrashIdList(MultipartFile file) throws IOException {
		List<String> crashIdList = new ArrayList<String>();

		try {
			// get the content in bytes
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					"D:\\File.txt"));
			byte[] contentInBytes = file.getBytes();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					fileOutputStream);
			bufferedOutputStream.write(contentInBytes);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();

		} catch (Exception ex) {
		}

		return crashIdList;
	}

	//Get automated crash id
	public String getCrashId(){
		
		List<String> data=new ArrayList<String>();
		try {
			System.out.println(injuryProperties.getProperty("crashIDNotepad"));
			data=FileUtils.readLines(new File(injuryProperties.getProperty("crashIDNotepad")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.get(0);		
	}
	
	//Update automated crash id
		public void updateCrashId(String crashId){			
				try {
					FileUtils.writeStringToFile(new File(injuryProperties.getProperty("crashIDNotepad")), crashId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
	
	//Crash Log Update
		public void crashLogUpdate(String crashId,Exception exe){			
				try {
					String content="Crash ID: "+crashId+" -"+exe.toString()+"/////";
					FileUtils.writeStringToFile(new File(injuryProperties.getProperty("crashIDLog")), content, true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
	
	public PDFCrashReportJson getValuesFromPDF(List<List<String>> content)
			throws IOException {

		List<ReportUnitPageForm> reportUnitPageForms = new ArrayList<ReportUnitPageForm>();
		// Motor List Form
		List<ReportMotoristPageForm> reportMotoristPageForms = new ArrayList<ReportMotoristPageForm>();
		ReportFirstPageForm reportFirstPageForm = null;

		try {
			// First Page
			List<String> firstPage = content.get(0);
			String crashDate=firstPage.get(firstPage.indexOf("CRASH DATE *") + 1);
			String cityVillageTownship=firstPage.get(firstPage.indexOf("CITY, VILLAGE, TOWNSHIP  *") + 1);
			if(firstPage.get(firstPage
							.indexOf("CITY, VILLAGE, TOWNSHIP  *") + 1).equals("ROUTE  TYPES")){
				crashDate=firstPage.get(firstPage
						.indexOf("CITY, VILLAGE, TOWNSHIP  * CRASH DATE *") + 1);
				cityVillageTownship="";
						
			}
			reportFirstPageForm = new ReportFirstPageForm(
					firstPage
							.get(firstPage.indexOf("LOCAL REPORT NUMBER *") + 1).replaceAll("\\s+", " ").trim(),
					firstPage.get(firstPage.indexOf("CRASH SEVERITY HIT/SKIP") - 4),
					firstPage.get(firstPage.indexOf("REPORTING AGENCY NAME *") + 1),
					firstPage.get(firstPage.indexOf("NUMBER OF ") - 1),
					firstPage.get(firstPage.indexOf("UNIT IN ERROR") - 1),
					firstPage.get(firstPage.indexOf("COUNTY *") + 1),
					cityVillageTownship,
					crashDate,
					firstPage.get(firstPage.indexOf("TIME OF  CRASH") + 1));

			// Unit Pages

			int i;
			for (i = 1; i <= Integer.parseInt(reportFirstPageForm
					.getNumberOfUnits()); i++) {

				// Unit Page Content
				List<String> unitPage = content.get(i);

				String ownerPhoneNumber = "", damageScale = "", insuranceCompany = "", policyNumber = "";

				ownerPhoneNumber = unitPage
						.get(unitPage
								.indexOf("OWNER PHONE NUMBER -  INC, AREA  CODE     ( SAME AS DRIVER )") + 1)
						.equals("UNIT NUMBER") ? ""
						: unitPage
								.get(unitPage
										.indexOf("OWNER PHONE NUMBER -  INC, AREA  CODE     ( SAME AS DRIVER )") + 1);
				damageScale = unitPage
						.get(unitPage.indexOf("DAMAGE SCALE") + 1).equals(
								"1 - NONE") ? "" : unitPage.get(unitPage
						.indexOf("DAMAGE SCALE") + 1);
				insuranceCompany = unitPage.indexOf("INSURANCE COMPANY") == -1 ? ""
						: unitPage
								.get(unitPage.indexOf("INSURANCE COMPANY") + 1);
				if (insuranceCompany.equals("POLICY NUMBER"))
					insuranceCompany = "";

				policyNumber = unitPage.indexOf("POLICY NUMBER") == -1 ? ""
						: unitPage.get(unitPage.indexOf("POLICY NUMBER") + 1);
				if (policyNumber.equals("TOWED BY"))
					policyNumber = "";

				ReportUnitPageForm reportUnitPageForm = new ReportUnitPageForm(
						unitPage.get(unitPage.indexOf("UNIT NUMBER") + 1),
						unitPage.get(unitPage
								.indexOf("OWNER NAME: LAST, FIRST, MIDDLE       ( SAME AS DRIVER )") + 1),
						ownerPhoneNumber,
						unitPage.get(unitPage
								.indexOf("OWNER ADDRESS: CITY, STATE, ZIP                  SAME AS DRIVER )") + 1),
						unitPage.get(unitPage.indexOf("# OCCUPANTS") + 1),
						insuranceCompany, policyNumber, damageScale);

				reportUnitPageForms.add(reportUnitPageForm);
			}

			for (; i < content.size(); i++) {

				// Motorist Page Content
				List<String> motoristPage = content.get(i);
				int index = 0;
				if (motoristPage.get(3).equals("MOTORIST/NON-MOTORIST")) {
    					for (index = 0; index < motoristPage.size(); index++) {

  						if (motoristPage.get(index).equals("UNIT  NUMBER")
    								|| motoristPage.get(index)
      										.equals("UNIT NUMBER")) {
						/*	if ((Integer.parseInt(motoristPage.get(index + 1)
									.trim()) != Integer
									.parseInt(reportFirstPageForm
											.getUnitInError().trim()))
									|| Integer.parseInt(reportFirstPageForm
											.getNumberOfUnits().trim()) == 1
									|| Integer.parseInt(reportFirstPageForm
											.getUnitInError().trim()) == 98) {*/
								ReportMotoristPageForm motoristPageForm = new ReportMotoristPageForm();
								motoristPageForm.setUnitNumber(motoristPage
										.get(index + 1));
								if (motoristPage.get(index + 2).equals(
										"NAME: LAST, FIRST, MIDDLE"))
									motoristPageForm.setName(motoristPage
											.get(index + 3));
								if (motoristPage.get(index + 4).equals(
										"DATE  OF BIRTH"))
									motoristPageForm
											.setDateOfBirth(motoristPage
													.get(index + 5));
								if (motoristPage.get(index + 6).equals(
										"AGE"))
									motoristPageForm
											.setAge(motoristPage
													.get(index + 7));
								if (motoristPage.get(index + 8)
										.equals("GENDER")){
									if(!motoristPage
											.get(index + 9).equals("F - FEMALE")){
										motoristPageForm.setGender(motoristPage
											.get(index + 9));
									}else{
										motoristPageForm.setGender("");
									}
								}
								if (motoristPage.get(index - 2).equals(
										"ADDRESS, CITY, STATE, ZIP"))
									motoristPageForm
											.setAdddressCityStateZip(motoristPage
													.get(index - 1));
								else if (motoristPage.get(index - 2).equals(
										"CONTACT PHONE  -  INCLUDE  AREA  CODE ADDRESS, CITY, STATE, ZIP"))
									motoristPageForm
											.setAdddressCityStateZip(motoristPage
													.get(index - 1));
								// Seating Position
								if(motoristPage.get(index - 12).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 11));
								}else if(motoristPage.get(index - 11).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 10));
								}
								
								if (motoristPage
										.get(index - 4)
										.equals("CONTACT PHONE  -  INCLUDE  AREA  CODE"))
									motoristPageForm
											.setContactPhone(motoristPage
													.get(index - 3));

								if (motoristPage.get(index - 21).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 20));
								if (motoristPage.get(index - 22).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 21));
								else if (motoristPage.get(index - 20).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 19));
								else if (motoristPage.get(index - 19).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 18));
								else if (motoristPage.get(index - 17).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 16));
								else if (motoristPage.get(index - 18).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 17));
								else if (motoristPage.get(index - 19).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 17));
								else if (motoristPage.get(index - 25).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 24));
								else if (motoristPage.get(index - 26).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 25));
								else if (motoristPage.get(index - 23).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 22));

								if (motoristPage.get(index - 17).equals(
										"EMS AGENCY"))
									motoristPageForm.setEmsAgency(motoristPage
											.get(index - 16));
								else if (motoristPage.get(index - 21).equals(
										"EMS AGENCY"))
									motoristPageForm.setEmsAgency(motoristPage
											.get(index - 20));
								else if (motoristPage.get(index - 22).equals(
										"EMS AGENCY"))
									motoristPageForm.setEmsAgency(motoristPage
											.get(index - 21));

								if (motoristPage.get(index - 15).equals(
										"MEDICAL FACILITY  INJURED TAKEN TO"))
									motoristPageForm
											.setMedicalFacility(motoristPage
													.get(index - 14));
								else if (motoristPage.get(index - 19).equals(
										"MEDICAL FACILITY  INJURED TAKEN TO"))
									motoristPageForm
											.setMedicalFacility(motoristPage
													.get(index - 18));
								else if (motoristPage.get(index - 20).equals(
										"MEDICAL FACILITY  INJURED TAKEN TO"))
									motoristPageForm
											.setMedicalFacility(motoristPage
													.get(index - 19));

								reportMotoristPageForms.add(motoristPageForm);
							/*}
*/
						}
					}
				} else if (motoristPage.get(0).equals("OCCUPANT")) {
					for (index = 0; index < motoristPage.size(); index++) {

						if (motoristPage.get(index).equals("UNIT  NUMBER")
								|| motoristPage.get(index)
										.equals("UNIT NUMBER")) {
							/*if ((Integer.parseInt(motoristPage.get(index + 1)
									.trim()) != Integer
									.parseInt(reportFirstPageForm
											.getUnitInError().trim()))
									|| Integer.parseInt(reportFirstPageForm
											.getNumberOfUnits().trim()) == 1) {*/
								ReportMotoristPageForm motoristPageForm = new ReportMotoristPageForm();
								motoristPageForm.setUnitNumber(motoristPage
										.get(index + 1));
								if (motoristPage.get(index + 2).equals(
										"NAME: LAST, FIRST, MIDDLE"))
									motoristPageForm.setName(motoristPage
											.get(index + 3));
								if (motoristPage.get(index + 4).equals(
										"DATE  OF BIRTH"))
									motoristPageForm
											.setDateOfBirth(motoristPage
													.get(index + 5));
								if (motoristPage.get(index + 6).equals(
										"AGE"))
									motoristPageForm
											.setAge(motoristPage
													.get(index + 7));
								if (motoristPage.get(index + 8)
										.equals("GENDER")){
									if(!motoristPage
											.get(index + 9).equals("F - FEMALE")){
										motoristPageForm.setGender(motoristPage
											.get(index + 9));
									}else{
										motoristPageForm.setGender("");
									}
								}
								if (motoristPage.get(index - 2).equals(
										"ADDRESS, CITY, STATE, ZIP"))
									motoristPageForm
											.setAdddressCityStateZip(motoristPage
													.get(index - 1));
								else if (motoristPage.get(index - 2).equals(
										"CONTACT PHONE  -  INCLUDE  AREA  CODE ADDRESS, CITY, STATE, ZIP"))
									motoristPageForm
											.setAdddressCityStateZip(motoristPage
													.get(index - 1));
								// Seating Position
								if(motoristPage.get(index - 12).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 11));
								}else if(motoristPage.get(index - 11).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 10));
								}
								
								if (motoristPage
										.get(index - 4)
										.equals("CONTACT PHONE  -  INCLUDE  AREA  CODE"))
									motoristPageForm
											.setContactPhone(motoristPage
													.get(index - 3));

								if (motoristPage.get(index - 21).equals(
										"INJURIES")) {
									motoristPageForm.setInjuries(motoristPage
											.get(index - 20));
									if (motoristPage.get(index - 17).equals(
											"EMS AGENCY")) {
										motoristPageForm
												.setEmsAgency(motoristPage
														.get(index - 16));
										if (motoristPage
												.get(index - 15)
												.equals("MEDICAL FACILITY  INJURED TAKEN TO")) {
											motoristPageForm
													.setMedicalFacility(motoristPage
															.get(index - 14));
										}
									}
								} else if (motoristPage.get(index - 20).equals(
										"INJURIES")) {
									motoristPageForm.setInjuries(motoristPage
											.get(index - 19));
									if (motoristPage.get(index - 17).equals(
											"EMS AGENCY")) {
										motoristPageForm
												.setEmsAgency(motoristPage
														.get(index - 16));
										if (motoristPage
												.get(index - 15)
												.equals("MEDICAL FACILITY  INJURED TAKEN TO")) {
											motoristPageForm
													.setMedicalFacility(motoristPage
															.get(index - 14));
										}
									}
								} else if (motoristPage.get(index - 22).equals(
										"INJURIES")) {
									motoristPageForm.setInjuries(motoristPage
											.get(index - 21));
									if (motoristPage.get(index - 17).equals(
											"EMS AGENCY")) {
										motoristPageForm
												.setEmsAgency(motoristPage
														.get(index - 16));
										if (motoristPage
												.get(index - 15)
												.equals("MEDICAL FACILITY  INJURED TAKEN TO")) {
											motoristPageForm
													.setMedicalFacility(motoristPage
															.get(index - 14));
										}
									}
								} else if (motoristPage.get(index - 26).equals(
										"INJURIES")) {
									if(motoristPage.get(index-21).length()>5){
										motoristPageForm.setInjuries(motoristPage
												.get(index - 25));
										if (motoristPage.get(index - 22).equals(
												"EMS AGENCY")) {
											motoristPageForm
													.setEmsAgency(motoristPage
															.get(index - 21));
										}
										if (motoristPage
												.get(index - 20)
												.equals("MEDICAL FACILITY  INJURED TAKEN TO")) {
											motoristPageForm
													.setMedicalFacility(motoristPage
															.get(index - 19));
										}
									}else{
									motoristPageForm.setInjuries(motoristPage
											.get(index - 21));
									if (motoristPage.get(index - 17).equals(
											"EMS AGENCY")) {
										motoristPageForm
												.setEmsAgency(motoristPage
														.get(index - 16));
										if (motoristPage
												.get(index - 15)
												.equals("MEDICAL FACILITY  INJURED TAKEN TO")) {
											motoristPageForm
													.setMedicalFacility(motoristPage
															.get(index - 14));
										}
									}
								}
								} else if (motoristPage.get(index - 18).equals(
										"INJURIES")) {
									motoristPageForm.setInjuries(motoristPage
											.get(index - 17));
								}
								else if (motoristPage.get(index - 17).equals(
										"INJURIES")) {
									motoristPageForm.setInjuries(motoristPage
											.get(index - 16));
								}
								else if (motoristPage.get(index - 23).equals(
										"INJURIES")){
									motoristPageForm.setInjuries(motoristPage
											.get(index - 22));
									if (motoristPage.get(index - 19).equals(
											"EMS AGENCY")) {
										motoristPageForm
												.setEmsAgency(motoristPage
														.get(index - 18));
									}
								}
								

								reportMotoristPageForms.add(motoristPageForm);
							/*}*/

						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Error Reading:"
					+ reportFirstPageForm.getLocalReportNumber());
			logger.error(ex.toString());
		}
		Collections.sort(reportUnitPageForms,ReportUnitPageForm.ReportUnitPageComparitor);
		return new PDFCrashReportJson(reportFirstPageForm, reportUnitPageForms,
				reportMotoristPageForms);

	}

	public Integer getReportType(PDFCrashReportJson pdfCrashReportJson) {

		// #1 Tire 1 Patient
		// #2 Tire 2 Patient
		// #3 Tire 3 Patient
		// #4 Number of units > 1 and Unit in Error is an animal
		// #5 Number of units == 1 and Unit in Error is an animal
		// #6 Number of units == 1 and Unit in Error not having Insurance
		
		List<String> invalidInsurance=InjuryConstants.getInvalidInsurance();
		
		// Check for the report
		try {

			Integer unitInError = Integer.parseInt(pdfCrashReportJson
					.getFirstPageForm().getUnitInError());
			
			Integer numberOfUnits=Integer.parseInt(pdfCrashReportJson.getFirstPageForm().getNumberOfUnits());
			
			//Check for unit > 1
			if(numberOfUnits==1){
				if(unitInError==1){					
					//Check for insurance company
					if(!pdfCrashReportJson.getReportUnitPageForms().get(0).getInsuranceCompany().equals("")&&!invalidInsurance.contains(pdfCrashReportJson.getReportUnitPageForms().get(0).getInsuranceCompany().toLowerCase())){
						// #3 Tier Patient 
						return 3;
					}else{
						//Skip the report
						// #6 Number of units == 1 and Unit in Error not having Insurance
						return 6;
					}
					
				}else{
					//Skip the Report
					// #5 Number of units == 1 and Unit in Error is an animal
					return 5;
				}
			}else{
				if(unitInError!=98&&unitInError!=99){
					//Check for unit has insurance
					//NOTE POLICY NUMBER IS NOT CHECKING
					if(!pdfCrashReportJson.getReportUnitPageForms().get(unitInError-1).getInsuranceCompany().equals("")&&!invalidInsurance.contains(pdfCrashReportJson.getReportUnitPageForms().get(0).getInsuranceCompany().toLowerCase())){
						//#1 Tier 1 patients
						return 1;
					}else{
						//Check victim have insurance
						//#2 Tier 2 patients
						return 2;
					}
					
				}else{
					//Skip the Report
					//# 4 Unit in Error is an animal
					return 4;
				}
			}	

		} catch (Exception ex) {
			System.out.println(ex.toString());
			return 0;
		}
	}

	//# Tier 1 Patient
	public List<PatientForm> getTierPatientForm(
			PDFCrashReportJson pdfCrashReportJson,Integer tier) {

		List<PatientForm> patientsForms = new ArrayList<PatientForm>();
		
		//Invalid Insurance Check conditions
		List<String> invalidInsurance=InjuryConstants.getInvalidInsurance();

		//First Page
		ReportFirstPageForm firstPageForm = pdfCrashReportJson
				.getFirstPageForm();
		
		//Units Page
		List<ReportUnitPageForm> reportUnitPageForms=pdfCrashReportJson.getReportUnitPageForms();
		
		if(tier==1){
			for (ReportMotoristPageForm motoristPageForm : pdfCrashReportJson
					.getReportMotoristPageForms()) {
				PatientForm patientsForm=getPatientForm(motoristPageForm, firstPageForm,reportUnitPageForms);
				if(patientsForm!=null){
					if(patientsForm.getUnitNumber().equals(firstPageForm.getUnitInError())){
						if(patientsForm.getInjuries()!=null){
						if(!patientsForm.getInjuries().equals("1")&&!patientsForm.getInjuries().equals("5")){
							patientsForm.setTier(4);
							patientsForms.add(patientsForm);
							}
						}else{
							patientsForm.setTier(4);
							patientsForms.add(patientsForm);
						}
					}else{
						patientsForm.setTier(1);
						patientsForms.add(patientsForm);
					}
					
				}
			}
		}				
		else if(tier==2){
			for (ReportUnitPageForm reportUnitPageForm : reportUnitPageForms) {
				if(reportUnitPageForm.getUnitNumber()!=firstPageForm.getUnitInError()){
					if(!reportUnitPageForm.getInsuranceCompany().equals("")&&!invalidInsurance.contains(reportUnitPageForm.getInsuranceCompany().toLowerCase())){
						for (ReportMotoristPageForm motoristPageForm : pdfCrashReportJson
								.getReportMotoristPageForms()) {
							if(motoristPageForm.getUnitNumber().equals(reportUnitPageForm.getUnitNumber())){
							
								PatientForm patientsForm=getPatientForm(motoristPageForm, firstPageForm,reportUnitPageForms);
								if(patientsForm!=null){
									patientsForm.setTier(2);
									patientsForms.add(patientsForm);
								}
							}							
						}
						
					}else{
						//#7 Skip the unit
						// Insert into Crash report Table.
					}
				}
			}
					
		}else if(tier==3){
			for (ReportMotoristPageForm motoristPageForm : pdfCrashReportJson
					.getReportMotoristPageForms()) {
				if(motoristPageForm.getInjuries()!=null){
					if(!motoristPageForm.getInjuries().equals("1")&&!motoristPageForm.getInjuries().equals("5")){
						PatientForm patientsForm=getPatientForm(motoristPageForm, firstPageForm,reportUnitPageForms);
						if(patientsForm!=null){
							patientsForm.setTier(3);
							patientsForms.add(patientsForm);
						}
					}else{
						//#8 Skip the patient low injury
					}
				}else{
					PatientForm patientsForm=getPatientForm(motoristPageForm, firstPageForm,reportUnitPageForms);
					if(patientsForm!=null){
						patientsForm.setTier(3);
						patientsForms.add(patientsForm);
					}
				}
				
			}
		}
		return patientsForms;
	}
	
	public PatientForm getPatientForm(ReportMotoristPageForm motoristPageForm,ReportFirstPageForm firstPageForm,List<ReportUnitPageForm> reportUnitPageForms){
		
			PatientForm patientsForm = new PatientForm();
			patientsForm.setName(motoristPageForm.getName());
			patientsForm.setUnitNumber(motoristPageForm.getUnitNumber()
					.trim());
			patientsForm.setDateOfBirth(motoristPageForm.getDateOfBirth());
			patientsForm.setAge(motoristPageForm.getAge());
			patientsForm.setGender(motoristPageForm.getGender());
			patientsForm.setAddress(motoristPageForm
					.getAdddressCityStateZip());
			patientsForm.setPhoneNumber(motoristPageForm.getContactPhone());
			patientsForm.setInjuries(motoristPageForm.getInjuries());
			patientsForm.setEmsAgency(motoristPageForm.getEmsAgency());
			patientsForm.setMedicalFacility(motoristPageForm
					.getMedicalFacility());
			patientsForm.setLocalReportNumber(firstPageForm
					.getLocalReportNumber());
			patientsForm.setCrashSeverity(firstPageForm.getCrashSeverity());
			patientsForm.setReportingAgencyName(firstPageForm
					.getReportingAgencyName());
			patientsForm.setCounty(firstPageForm.getCounty());
			patientsForm.setNumberOfUnits(firstPageForm.getNumberOfUnits());
			patientsForm.setUnitInError(firstPageForm.getUnitInError());
			patientsForm.setCityVillageTownship(firstPageForm
					.getCityVillageTownship());
			patientsForm.setCrashDate(firstPageForm.getCrashDate());
			patientsForm.setTimeOfCrash(firstPageForm.getTimeOfCrash());
			patientsForm.setVictimInsuranceCompany(reportUnitPageForms.get(Integer.parseInt(motoristPageForm.getUnitNumber().trim())-1).getInsuranceCompany());
			patientsForm.setVictimPolicyNumber(reportUnitPageForms.get(Integer.parseInt(motoristPageForm.getUnitNumber().trim())-1).getPolicyNumber());
			patientsForm.setAtFaultInsuranceCompany(reportUnitPageForms.get(Integer.parseInt(firstPageForm.getUnitInError().trim())-1).getInsuranceCompany());
			patientsForm.setAtFaultPolicyNumber(reportUnitPageForms.get(Integer.parseInt(firstPageForm.getUnitInError().trim())-1).getPolicyNumber());
			patientsForm.setSeatingPosition(motoristPageForm.getSeatingPosition());
			patientsForm.setPatientStatus(1);
			return patientsForm;
		
	}
	
	
	//Main function for PDF Parsing
	public void parsePDFDocument(File file,Integer crashId) throws Exception{
		
	
				UUID uuid=UUID.randomUUID();
		
				//String fileName=uuid+"_"+ crashId + ".pdf";
				String fileName=crashId + ".pdf";
				//String uuid=crashId.toString();
				//Convert PDF data to Parsed JSON
				PDFCrashReportJson pdfCrashReportJson=null;
				try {
					pdfCrashReportJson = this
							.getValuesFromPDF(this.parsePdfFromFile(file));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				List<PatientForm> patientsForms = new ArrayList<PatientForm>();
				//Check for report status
				Integer tierType = this.getReportType(pdfCrashReportJson);
						
				switch (tierType) {
				case 1:
					patientsForms=this.getTierPatientForm(pdfCrashReportJson, tierType);
					patientsForms=filterPatientForms(patientsForms);
					if(patientsForms.size()==0){
						//#9 Tier 1 Error None of the Patient are not having address and the phone number
						crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 9,patientsForms.size()));
					}else{
						//Insert patients
						Integer patientAvailableTier=checkTierFourPatientsAvailable(patientsForms);
						if(patientAvailableTier==3){
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 13,patientsForms.size()));
						}else if(patientAvailableTier==1){
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 1,patientsForms.size()));
						}else if(patientAvailableTier==2){
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 14,patientsForms.size()));
						}
						
						try {
							this.savePatientList(patientsForms, uuid.toString(), crashId.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							throw e;
						}
					}
					break;
				case 2:
					patientsForms=this.getTierPatientForm(pdfCrashReportJson, tierType);
					if(patientsForms.size()==0){
						//#7 Victim units not having insurance
						crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 7,patientsForms.size()));
					}else{
						patientsForms=filterPatientForms(patientsForms);
						if(patientsForms.size()==0){
							//#10 Tier2 No patient have address and phone number
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 10,patientsForms.size()));
						}else{
							//Insert patients
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 2,patientsForms.size()));
							try {
								this.savePatientList(patientsForms, uuid.toString(), crashId.toString());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								throw e;
							}
							}
					}
					
					break;
				case 3:
					patientsForms=this.getTierPatientForm(pdfCrashReportJson, tierType);
					if(patientsForms.size()==0){
						//#8 Error None of the Patient satisfy injuries scale 2 to 4
						crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 8,patientsForms.size()));
					}else{
						patientsForms=filterPatientForms(patientsForms);
						if(patientsForms.size()==0){
							//#11 Tier3 Patient Not having address and phone numbers
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 11,patientsForms.size()));
						}else{
							//Insert patients
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 3,patientsForms.size()));
							try {
								this.savePatientList(patientsForms, uuid.toString(), crashId.toString());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								throw e;
							}
							
						}
					}
					break;
				case 4:
					//Insert into crash report table number of units > 1 and unit in error is an animal
					crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 4,patientsForms.size()));
					break;
				case 5:
					//Insert into crash report table number of units == 1 and unit in error is an animal
					crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 5,patientsForms.size()));
					break;
				case 6:
					//Insert into crash report table number of units == 1 and unit in error not having insurance details
					crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), crashId, fileName, 6,patientsForms.size()));
					break;
				default:
					break;
				}
				if(Integer.parseInt(injuryProperties.getProperty("awsUpload"))==1)				
					awsFileUpload.uploadFileToAWSS3(file.getAbsolutePath(), fileName);
				//file.delete();
 	}
	
	
	public void savePatientList(List<PatientForm> patientsForms,String uuid,String crashId) throws Exception{
		for (PatientForm patientsForm : patientsForms) {
			//patientsForm.setCrashReportFileName(uuid+"_"+crashId+".pdf");
			patientsForm.setCrashReportFileName(crashId+".pdf");
			patientsForm.setCrashId(crashId);
			try {
				patientsService.savePatient(patientsForm);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}		
	}
	
	public List<PatientForm> filterPatientForms(List<PatientForm> patientsForms){
		List<PatientForm> filteredPatientForms=new ArrayList<PatientForm>();
		filteredPatientForms.addAll(patientsForms);
		for (PatientForm patientsForm : patientsForms) {
			if ((patientsForm.getAddress()==null || patientsForm.getAddress().equals(""))
						&& (patientsForm.getPhoneNumber()==null || patientsForm.getPhoneNumber().equals(""))) {
					filteredPatientForms.remove(patientsForm);
				}
			
		}
		return filteredPatientForms;
	}
	
	// Check Tier 4 Patients Available or Not
	public Integer checkTierFourPatientsAvailable(List<PatientForm> patientForms){
		
		// # 0 No Patients available Under Tier 1 and Tier 4
		// # 1 Only Tier 1 Patients available
		// # 2 Only Tier 4 Patients available
		// # 3 Both Tier 1 and Tier 4 Patients available
		
		Integer isAvailable=0;
		Integer tierFour=0;
		Integer tierOne=0;
		for (PatientForm patientsForm : patientForms) {
			if(patientsForm.getTier()==4){
				tierFour++;
			}else if(patientsForm.getTier()==1){
				tierOne++;
			}
		}
		
		if(tierOne>0&&tierFour>0){
			isAvailable=3;
		}else if(tierOne>0){
			isAvailable=1;
		}else if(tierFour>0){
			isAvailable=2;
		}
		
		return isAvailable;
		
	}
	
}
