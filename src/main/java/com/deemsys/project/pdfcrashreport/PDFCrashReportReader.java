package com.deemsys.project.pdfcrashreport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.County.CountyDAO;
import com.deemsys.project.CrashReport.CrashReportDAO;
import com.deemsys.project.CrashReport.CrashReportService;
import com.deemsys.project.CrashReport.RunnerCrashReportForm;
import com.deemsys.project.CrashReportError.CrashReportErrorDAO;
import com.deemsys.project.PoliceAgency.PoliceAgencyDAO;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.deemsys.project.entity.County;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.CrashReportError;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.entity.PoliceAgency;
import com.deemsys.project.patient.PatientDAO;
import com.deemsys.project.patient.PatientForm;
import com.deemsys.project.patient.PatientService;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

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
	
	@Autowired
	CountyDAO countyDAO;
	
	@Autowired
	PatientDAO patientDAO;

	@Autowired
	PoliceAgencyDAO policeAgencyDAO;
	
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

	public List<List<String>> parsePdfFromWebUrl(String pdfUrl) {
		List<List<String>> contentList = new ArrayList<List<String>>();
		try {
			InputStream inputStream = new URL(pdfUrl).openStream();
			PdfReader reader = new PdfReader(inputStream);
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
	public boolean downloadPDFFile(String crashId, String addedDate) throws IOException {
			try{
					File file=getPDFFile(crashId,"",1,"");
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
								file=getPDFFile(crashId,"",1,"");
							}
						}else{
							crashId=String.valueOf(Integer.parseInt(crashId)+1);
							file=getPDFFile(crashId,"",1,"");
						}
						
					}		
					
					if(file.length()>2000){//2000 File Size refers an crash report not found exception
						//Parse the PDF
						parsePDFDocument(new File(file.getAbsoluteFile().getAbsolutePath()),crashId,addedDate);
						
						// Update Crash Id
						// Commented for Manual Upload
						//this.updateCrashId(String.valueOf(Integer.parseInt(crashId)+1));
					}else{
						System.out.println("Waiting.....");
					}
				} catch (Exception e) {
					// TODO: handle exception
					this.crashLogUpdate(crashId, e);
					// Commented for Manual Upload
					//this.updateCrashId(String.valueOf(Integer.parseInt(crashId)+1));
					CrashReportError crashReportError=crashReportErrorDAO.get(12);
					crashReportDAO.save(new CrashReport(crashId, crashReportError, null, null, "",   null, new Date() ,  0, 0, "", "", 0, null, 0, null, null, null, null, null));
					System.out.println("Failed"+e.toString());
				}
		return true;
				
	}  
	
	// Import Crash Report By Crash Id Manual Entry
	public boolean importPDFFile(String crashId, String addedDate) throws Exception {
		boolean isFileAvailable=true;
		try{
				File file=getPDFFile(crashId,"",1,"");
				
				if(file.length()>2000){//2000 File Size refers an crash report not found exception
					//Parse the PDF
					parsePDFDocument(new File(file.getAbsoluteFile().getAbsolutePath()),crashId,addedDate);
				}else{
					isFileAvailable=false;
					System.out.println("File Not Found....."+crashId);
				}
			} catch (Exception e) {
				// TODO: handle exception
				// Commented for Manual Upload
				System.out.println("Failed"+e.toString()+"-->"+crashId);
				throw e;
				
			}
	return isFileAvailable;
			
}  
	
	public boolean downloadPDFAndUploadToAWS(String crashId) throws IOException {
		try{
			if(Integer.parseInt(crashId)<6295000){
				File file=getPDFFile(crashId,"",1,"");
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
							file=getPDFFile(crashId,"",1,"");
						}
					}else{
						crashId=String.valueOf(Integer.parseInt(crashId)+1);
						file=getPDFFile(crashId,"",1,"");
					}
					
				}		
				
				if(file.length()>2000){//2000 File Size refers an crash report not found exception
					UUID randomUUID=UUID.randomUUID();
					
					String fileName="";
					String uuid="";
					
					if(Integer.parseInt(injuryProperties.getProperty("env"))==0){
						fileName=crashId + ".pdf";
						uuid=crashId.toString();
					}else{
						fileName=randomUUID+"_"+ crashId + ".pdf";
						uuid=randomUUID.toString();
					}
					//Upload To AWS S3
					awsFileUpload.uploadFileToAWSS3(file.getAbsolutePath(), fileName);
					
					// Update File Name To Crash Report
					crashReportDAO.updateCrashReportFileName(crashId, fileName);
					
					// Update Crash Id
					this.updateCrashId(String.valueOf(Integer.parseInt(crashId)+1));
				}else{
					System.out.println("Waiting.....");
				}
			}
			} catch (Exception e) {
				// TODO: handle exception
				
				System.out.println("Failed"+e.toString());
			}
	return true;
			
	}  
	
	public File getPDFFile(String crashId, String imageFileName,Integer reportType,String pdfLink) throws Exception{
		File file=null;
		HttpURLConnection httpURLConnection=null;
		try{
			String pdfAccessURL=injuryProperties.getProperty("odpsLink")+crashId;
			if(reportType==2){
				pdfAccessURL=injuryProperties.getProperty("odpsSageLink")+crashId+injuryProperties.getProperty("odpsSageImageParameter")+imageFileName;
			}else if(reportType==3){
				pdfAccessURL=pdfLink;
			}
			 
			// Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };
	 
	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	 
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	           @Override
				public boolean verify(String arg0, SSLSession arg1) {
					// TODO Auto-generated method stub
					return true;
				}
	        };
	        
			URL url=new URL(pdfAccessURL);
			httpURLConnection=(HttpURLConnection) url.openConnection();
			//httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
			
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
					firstPage.get(firstPage.indexOf("REPORTING AGENCY NCIC *") + 1),
					firstPage.get(firstPage.indexOf("REPORTING AGENCY NAME *") + 1),
					firstPage.get(firstPage.indexOf("NUMBER OF ") - 1).trim(),
					firstPage.get(firstPage.indexOf("UNIT IN ERROR") - 1).trim(),
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

				String ownerPhoneNumber = "", ownerAddress="", occupants="", damageScale = "", insuranceCompany = "", 
										policyNumber = "", vehicleMake = "", vehicleYear = "", VIN = "", licensePlateNumber="",
										typeOfUse="0";

				ownerPhoneNumber = unitPage
						.get(unitPage
								.indexOf("OWNER PHONE NUMBER -  INC, AREA  CODE     ( SAME AS DRIVER )") + 1)
						.equals("UNIT NUMBER") ? ""
						: unitPage
								.get(unitPage
										.indexOf("OWNER PHONE NUMBER -  INC, AREA  CODE     ( SAME AS DRIVER )") + 1);
				// Address
				ownerAddress=unitPage.indexOf("OWNER ADDRESS: CITY, STATE, ZIP                  SAME AS DRIVER )")==-1?""
						:unitPage.get(unitPage.indexOf("OWNER ADDRESS: CITY, STATE, ZIP                  SAME AS DRIVER )")+1);
				if(ownerAddress.equals("VEHICLE MODEL")){
					ownerAddress="";
				}
				
				// Occupants
				if(unitPage.indexOf("# OCCUPANTS")==-1){
					if(unitPage.indexOf("LICENSE PLATE NUMBER VEHICLE IDENTIFICATION NUMBER # OCCUPANTS")==-1){
						if(unitPage.indexOf("VEHICLE IDENTIFICATION NUMBER # OCCUPANTS")==-1){
							occupants="";
						}else{
							occupants=unitPage.get(unitPage.indexOf("VEHICLE IDENTIFICATION NUMBER # OCCUPANTS")+1);
						}
						
					}else{
						occupants=unitPage.get(unitPage.indexOf("LICENSE PLATE NUMBER VEHICLE IDENTIFICATION NUMBER # OCCUPANTS")+1);
					}
				}else{
					occupants=unitPage.get(unitPage.indexOf("# OCCUPANTS")+1);
				}
				
				// Damage Scale
				damageScale = unitPage
						.get(unitPage.indexOf("DAMAGE SCALE") + 1).equals(
								"1 - NONE") ? "" : unitPage.get(unitPage
						.indexOf("DAMAGE SCALE") + 1);
				
				// Insurance Company
				insuranceCompany = unitPage.indexOf("INSURANCE COMPANY") == -1 ? ""
						: unitPage
								.get(unitPage.indexOf("INSURANCE COMPANY") + 1);
				if (insuranceCompany.equals("POLICY NUMBER"))
					insuranceCompany = "";

				// Policy Number
				policyNumber = unitPage.indexOf("POLICY NUMBER") == -1 ? ""
						: unitPage.get(unitPage.indexOf("POLICY NUMBER") + 1);
				if (policyNumber.equals("TOWED BY"))
					policyNumber = "";

				// Vehicle Make
				vehicleMake=unitPage.indexOf("VEHICLE MAKE")==-1?"":unitPage.get(unitPage.indexOf("VEHICLE MAKE")+1);
				if(vehicleMake.equals("VEHICLE COLOR")){
					vehicleMake="";
				}
				
				// Vehicle Year
				vehicleYear=unitPage.indexOf("VEHICLE YEAR")==-1?"":unitPage.get(unitPage.indexOf("VEHICLE YEAR")-1);
				if(vehicleYear.equals("TOWED BY")){
					vehicleYear="";
				}else{
					if(!Pattern.matches("[0-9]{4}", vehicleYear.trim())){
						vehicleYear="";
					}
				}
				
				// VIN
				if(unitPage.indexOf("VEHICLE IDENTIFICATION NUMBER")==-1){
					if(unitPage.indexOf("LICENSE PLATE NUMBER VEHICLE IDENTIFICATION NUMBER")==-1){
						if(unitPage.indexOf("LP STATE LICENSE PLATE NUMBER VEHICLE IDENTIFICATION NUMBER")==-1){
							VIN="";
						}else{
							VIN=unitPage.get(unitPage.indexOf("LP STATE LICENSE PLATE NUMBER VEHICLE IDENTIFICATION NUMBER")+1);
						}
						
					}else{
						VIN=unitPage.get(unitPage.indexOf("LICENSE PLATE NUMBER VEHICLE IDENTIFICATION NUMBER")+1);
					}
				}else{
					VIN=unitPage.get(unitPage.indexOf("VEHICLE IDENTIFICATION NUMBER")+1);
				}
				
				// License Plate Number
				licensePlateNumber=unitPage.indexOf("LICENSE PLATE NUMBER")==-1?"":unitPage.get(unitPage.indexOf("LICENSE PLATE NUMBER")+1);
				
				// Type of Use
				if(unitPage.indexOf("TYPE OF USE")!=-1){
					if(!unitPage.get(unitPage.indexOf("TYPE OF USE")-1).equals("RESPONSE")){
						typeOfUse=unitPage.get(unitPage.indexOf("TYPE OF USE")-1);
					}
				}
				
				ReportUnitPageForm reportUnitPageForm = new ReportUnitPageForm(
						unitPage.get(unitPage.indexOf("UNIT NUMBER") + 1).trim(),
						unitPage.get(unitPage
								.indexOf("OWNER NAME: LAST, FIRST, MIDDLE       ( SAME AS DRIVER )") + 1),
						ownerPhoneNumber,
						ownerAddress,
						occupants,
						insuranceCompany, policyNumber, damageScale,vehicleMake,vehicleYear,VIN,licensePlateNumber,typeOfUse);

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
										.get(index + 1).trim());
								if (motoristPage.get(index + 2).equals(
										"NAME: LAST, FIRST, MIDDLE"))
									motoristPageForm.setName(motoristPage
											.get(index + 3));
								if (motoristPage.get(index + 4).equals(
										"DATE  OF BIRTH"))
									motoristPageForm
											.setDateOfBirth(motoristPage
													.get(index + 5));
								if(motoristPage.get(index+4).equals("GENDER")){
									if(!motoristPage.get(index + 5).equals("F - FEMALE")){
										motoristPageForm.setGender(motoristPage.get(index + 5));
									}else{
										motoristPageForm.setGender("");
									}
								}
									
								if(motoristPage.get(index+2).equals("NAME: LAST, FIRST, MIDDLE DATE  OF BIRTH AGE"))
									motoristPageForm.setAge(motoristPage.get(index + 3));
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
								if(motoristPage.get(index - 12>0?index-12:0).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 11).trim());
								}else if(motoristPage.get(index - 11>0?index-11:0).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 10));
								}else if(motoristPage.get(index - 10>0?index-10:0).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 9).trim());
								}else if(motoristPage.get(index - 9>0?index-9:0).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 8).trim());
								}else if(motoristPage.get(index - 8>0?index-8:0).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 7).trim());
								}else if(motoristPage.get(index - 7>0?index-7:0).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 6).trim());
								}else if(motoristPage.get(index - 6>0?index-6:0).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 5).trim());
								}
								
								if (motoristPage
										.get(index - 4)
										.equals("CONTACT PHONE  -  INCLUDE  AREA  CODE"))
									motoristPageForm
											.setContactPhone(motoristPage
													.get(index - 3));

								if (motoristPage.get(index - 21>0?index-21:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 20));
								if (motoristPage.get(index - 22>0?index-22:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 21));
								else if (motoristPage.get(index - 20>0?index-20:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 19));
								else if (motoristPage.get(index - 19>0?index-19:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 18));
								else if (motoristPage.get(index - 17>0?index-17:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 16));
								else if (motoristPage.get(index - 16>0?index-16:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 15));
								else if (motoristPage.get(index - 13>0?index-13:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 12));
								else if (motoristPage.get(index - 18>0?index-18:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 17));
								else if (motoristPage.get(index - 19>0?index-19:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 17));
								else if (motoristPage.get(index - 25>0?index-25:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 24));
								else if (motoristPage.get(index - 26>0?index-26:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 25));
								else if (motoristPage.get(index - 23>0?index-23:0).equals(
										"INJURIES"))
									motoristPageForm.setInjuries(motoristPage
											.get(index - 22));

								if (motoristPage.get(index - 17>0?index-17:0).equals(
										"EMS AGENCY"))
									motoristPageForm.setEmsAgency(motoristPage
											.get(index - 16));
								else if (motoristPage.get(index - 21>0?index-21:0).equals(
										"EMS AGENCY"))
									motoristPageForm.setEmsAgency(motoristPage
											.get(index - 20));
								else if (motoristPage.get(index - 22>0?index-22:0).equals(
										"EMS AGENCY"))
									motoristPageForm.setEmsAgency(motoristPage
											.get(index - 21));

								if (motoristPage.get(index - 15>0?index-15:0).equals(
										"MEDICAL FACILITY  INJURED TAKEN TO"))
									motoristPageForm
											.setMedicalFacility(motoristPage
													.get(index - 14));
								else if (motoristPage.get(index - 19>0?index - 19:0).equals(
										"MEDICAL FACILITY  INJURED TAKEN TO"))
									motoristPageForm
											.setMedicalFacility(motoristPage
													.get(index - 18));
								else if (motoristPage.get(index - 20>0?index-20:0).equals(
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
										.get(index + 1).trim());
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
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 11).trim());
								}else if(motoristPage.get(index - 11).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 10));
								}else if(motoristPage.get(index - 10).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 9).trim());
								}else if(motoristPage.get(index - 8).equals("SEATING  POSITION")){
									motoristPageForm.setSeatingPosition(motoristPage.get(index - 7).trim());
								}
								
								if (motoristPage
										.get(index - 4)
										.equals("CONTACT PHONE  -  INCLUDE  AREA  CODE"))
									motoristPageForm
											.setContactPhone(motoristPage
													.get(index - 3));

								if (motoristPage.get(index - 21>0?index - 21:0).equals(
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
								} else if (motoristPage.get(index - 20>0?index-20:0).equals(
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
								} else if (motoristPage.get(index - 22>0?index-22:0).equals(
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
								} else if (motoristPage.get(index - 26>0?index-26:0).equals(
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
								} else if (motoristPage.get(index - 18>0?index-18:0).equals(
										"INJURIES")) {
									motoristPageForm.setInjuries(motoristPage
											.get(index - 17));
								}
								else if (motoristPage.get(index - 17>0?index-17:0).equals(
										"INJURIES")) {
									motoristPageForm.setInjuries(motoristPage
											.get(index - 16));
								}
								else if (motoristPage.get(index - 23>0?index-23:0).equals(
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

						}if (motoristPage.get(index).equals("UNIT  NUMBER NAME: LAST, FIRST, MIDDLE")
								|| motoristPage.get(index)
								.equals("UNIT NUMBER NAME: LAST, FIRST, MIDDLE")) {
							
							ReportMotoristPageForm motoristPageForm = new ReportMotoristPageForm();
							
							motoristPageForm.setName(motoristPage
										.get(index + 1));
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
							if(motoristPage.get(index-5).equals("SEATING  POSITION AIR BAG USAGE EJECTION TRAPPED"))
								motoristPageForm.setSeatingPosition(null);
							// Injuires
							if(motoristPage.get(index-10).equals("INJURIES INJURED TAKEN BY EMS AGENCY MEDICAL FACILITY  INJURED TAKEN TO SAFETY EQUIPMENT USED"))
								motoristPageForm.setInjuries(null);
							
							if (motoristPage
									.get(index - 4)
									.equals("CONTACT PHONE  -  INCLUDE  AREA  CODE"))
								motoristPageForm
										.setContactPhone(motoristPage
												.get(index - 3));
							String nameChecking=motoristPageForm.getName().replaceAll(",", "").replaceAll("\\s+", "");
							if((motoristPageForm.getName()!=null&&!nameChecking.equals(""))||motoristPageForm.getContactPhone()!=null||motoristPageForm.getAdddressCityStateZip()!=null)
							  reportMotoristPageForms.add(motoristPageForm);
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
					if(!pdfCrashReportJson.getReportUnitPageForms().get(unitInError-1).getInsuranceCompany().equals("")&&!invalidInsurance.contains(pdfCrashReportJson.getReportUnitPageForms().get(unitInError-1).getInsuranceCompany().toLowerCase())){
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
			PDFCrashReportJson pdfCrashReportJson,Integer tier,String addedDate) {

		List<PatientForm> patientsForms = new ArrayList<PatientForm>();
		
		//Invalid Insurance Check conditions
		List<String> invalidInsurance=InjuryConstants.getInvalidInsurance();

		//First Page
		ReportFirstPageForm firstPageForm = pdfCrashReportJson
				.getFirstPageForm();
		
		//Units Page
		List<ReportUnitPageForm> reportUnitPageForms=pdfCrashReportJson.getReportUnitPageForms();
		
		if(tier==1){
			if(pdfCrashReportJson
					.getReportMotoristPageForms().size()>0){
				for (ReportMotoristPageForm motoristPageForm : pdfCrashReportJson
						.getReportMotoristPageForms()) {
					if(motoristPageForm.getUnitNumber()!=null){
						PatientForm patientsForm=getPatientForm(motoristPageForm, firstPageForm,reportUnitPageForms,addedDate);
						if(patientsForm!=null){
							if(patientsForm.getUnitNumber().equals(firstPageForm.getUnitInError())){
								/*
								 * Removed the Injuries Fatal Conditions
								 * if(patientsForm.getInjuries()!=null){
									if(!patientsForm.getInjuries().equals("5")){
									patientsForm.setTier(4);
									patientsForms.add(patientsForm);
									}
								}else{
									patientsForm.setTier(4);
									patientsForms.add(patientsForm);
								}*/
								patientsForm.setTier(4);
								patientsForms.add(patientsForm);
							}else{
								/*
								 * Removed the Injuries Fatal Conditions
								 * if(patientsForm.getInjuries()!=null){
									if(!patientsForm.getInjuries().equals("5")){
											patientsForm.setTier(1);
											patientsForms.add(patientsForm);
									}
								}
								else{
											patientsForm.setTier(1);
											patientsForms.add(patientsForm);
								}*/
								patientsForm.setTier(1);
								patientsForms.add(patientsForm);
							}
						}
					}else{
						// Unit Number is Not Mentioned in Motorist Page improper details in Motorist page
						PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
						patientsForm.setTier(0);
						patientsForms.add(patientsForm);
					}
					
				}
			}else{
				PatientForm patientsForm=getPatientForm(null,firstPageForm,reportUnitPageForms,addedDate);
				patientsForm.setTier(0);
				patientsForm.setIsOwner(0);
				// Occupants Or Motorist Details is Not Available
				patientsForm.setIsOccupantAvailable(0);
				patientsForms.add(patientsForm);
			}
			
		}				
		else if(tier==2){
			for (ReportUnitPageForm reportUnitPageForm : reportUnitPageForms) {
				if(reportUnitPageForm.getUnitNumber()!=firstPageForm.getUnitInError()){
					if(!reportUnitPageForm.getInsuranceCompany().equals("")&&!invalidInsurance.contains(reportUnitPageForm.getInsuranceCompany().toLowerCase())){
						for (ReportMotoristPageForm motoristPageForm : pdfCrashReportJson
								.getReportMotoristPageForms()) {
							if(motoristPageForm.getUnitNumber()!=null){
								if(motoristPageForm.getUnitNumber().equals(reportUnitPageForm.getUnitNumber())){							
									PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
									/*
									 * Removed the Injuries Fatal Conditions
									 * if(patientsForm.getInjuries()!=null){
										if(!patientsForm.getInjuries().equals("5")){
											patientsForm.setTier(2);
											patientsForms.add(patientsForm);
										}
									}else{
											patientsForm.setTier(2);
											patientsForms.add(patientsForm);
									}*/
									patientsForm.setTier(2);
									patientsForms.add(patientsForm);
								}
							}else{
								// Unit Number is Not Mentioned in Motorist Page improper details in Motorist page
								PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
								patientsForm.setTier(0);
								patientsForms.add(patientsForm);
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
				if(motoristPageForm.getUnitNumber()!=null){
					/*
					 * Removed the Injuries Fatal Conditions
					 * if(motoristPageForm.getInjuries()!=null){
						if(!motoristPageForm.getInjuries().equals("5")){
							PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
							if(patientsForm!=null){
								patientsForm.setTier(3);
								patientsForms.add(patientsForm);
							}
						}else{
							//#8 Skip the patient low injury
						}
					}else{
						PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
						if(patientsForm!=null){
							if(patientsForm.getInjuries()!=null){
								if(!patientsForm.getInjuries().equals("5")){							
								patientsForm.setTier(3);
								patientsForms.add(patientsForm);
								}
							}else{
								patientsForm.setTier(3);
								patientsForms.add(patientsForm);
							}
						}
					}*/
					PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
					if(patientsForm!=null){
						patientsForm.setTier(3);
						patientsForms.add(patientsForm);
					}
				}else{
					// Unit Number is Not Mentioned in Motorist Page improper details in Motorist page
					PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
					patientsForm.setTier(0);
					patientsForms.add(patientsForm);
				}
					
			}
		}else if(tier==4||tier==5||tier==6||tier==7||tier==8||tier==9||tier==10||tier==11){
			if(pdfCrashReportJson.getReportMotoristPageForms().size()>0){
				for (ReportMotoristPageForm motoristPageForm : pdfCrashReportJson
						.getReportMotoristPageForms()) {
					PatientForm patientsForm=getPatientForm(motoristPageForm,firstPageForm,reportUnitPageForms,addedDate);
					patientsForm.setTier(0);
					patientsForms.add(patientsForm);
				}
			}else{
				PatientForm patientsForm=getPatientForm(null,firstPageForm,reportUnitPageForms,addedDate);
				patientsForm.setTier(0);
				patientsForm.setIsOwner(0);
				patientsForm.setIsOccupantAvailable(0);
				patientsForms.add(patientsForm);
			}
		}
		return patientsForms;
	}
	
	public PatientForm getPatientForm(ReportMotoristPageForm motoristPageForm,ReportFirstPageForm firstPageForm,List<ReportUnitPageForm> reportUnitPageForms,String addedDate){
		
			PatientForm patientsForm = new PatientForm();
			if(motoristPageForm!=null){
				patientsForm.setName(motoristPageForm.getName());
				
				patientsForm.setDateOfBirth(motoristPageForm.getDateOfBirth());
				
				if(motoristPageForm.getAge()!=null)
					patientsForm.setAge(Integer.parseInt(motoristPageForm.getAge()));
				else
					patientsForm.setAge(null);
				
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
				patientsForm.setReportingAgencyNcic(firstPageForm.getReportingAgencyNCIC());
				patientsForm.setReportingAgencyName(firstPageForm
						.getReportingAgencyName());
				patientsForm.setCounty(firstPageForm.getCounty());
				patientsForm.setNumberOfUnits(firstPageForm.getNumberOfUnits());
				patientsForm.setUnitInError(firstPageForm.getUnitInError());
				patientsForm.setCityVillageTownship(firstPageForm
						.getCityVillageTownship());
				patientsForm.setCrashDate(firstPageForm.getCrashDate());
				patientsForm.setTimeOfCrash(firstPageForm.getTimeOfCrash());
				if(motoristPageForm.getUnitNumber()!=null){
					patientsForm.setUnitNumber(motoristPageForm.getUnitNumber()
							.trim());
					/*if(Integer.parseInt(firstPageForm.getNumberOfUnits())>=Integer.parseInt(motoristPageForm.getUnitNumber())){
						patientsForm.setVictimInsuranceCompany(reportUnitPageForms.get(Integer.parseInt(motoristPageForm.getUnitNumber().trim())-1).getInsuranceCompany());
						patientsForm.setVictimPolicyNumber(reportUnitPageForms.get(Integer.parseInt(motoristPageForm.getUnitNumber().trim())-1).getPolicyNumber());
						String damageScale=reportUnitPageForms.get(Integer.parseInt(motoristPageForm.getUnitNumber().trim())-1).getDamageScale();
						if(damageScale!=null&&!damageScale.equals("")){
							patientsForm.setDamageScale(Integer.parseInt(damageScale));
						}else{
							patientsForm.setDamageScale(null);
						}
					}*/
					for (ReportUnitPageForm reportUnitPageForm : reportUnitPageForms) {
						if(Integer.parseInt(reportUnitPageForm.getUnitNumber())==Integer.parseInt(motoristPageForm.getUnitNumber())){
							patientsForm.setVictimInsuranceCompany(reportUnitPageForm.getInsuranceCompany());
							patientsForm.setVictimPolicyNumber(reportUnitPageForm.getPolicyNumber());
							String damageScale=reportUnitPageForm.getDamageScale();
							if(damageScale!=null&&!damageScale.equals("")){
								patientsForm.setDamageScale(Integer.parseInt(damageScale));
							}else{
								patientsForm.setDamageScale(null);
							}
							patientsForm.setTypeOfUse(Integer.parseInt(reportUnitPageForm.getTypeOfUse()));
						}
					}
				}
				
				if(firstPageForm.getUnitInError()!=null&&!firstPageForm.getUnitInError().equals("98")&&!firstPageForm.getUnitInError().equals("99")){
					patientsForm.setAtFaultInsuranceCompany(reportUnitPageForms.get(Integer.parseInt(firstPageForm.getUnitInError().trim())-1).getInsuranceCompany());
					patientsForm.setAtFaultPolicyNumber(reportUnitPageForms.get(Integer.parseInt(firstPageForm.getUnitInError().trim())-1).getPolicyNumber());
				}
				
				patientsForm.setSeatingPosition(motoristPageForm.getSeatingPosition());
				patientsForm.setPatientStatus(1);
				patientsForm.setIsRunnerReport(0);
				
				patientsForm.setIsOwner(0);
				patientsForm.setIsOccupantAvailable(1);
			}else{	
				patientsForm.setLocalReportNumber(firstPageForm
					.getLocalReportNumber());
				patientsForm.setCrashSeverity(firstPageForm.getCrashSeverity());
				patientsForm.setReportingAgencyNcic(firstPageForm.getReportingAgencyNCIC());
				patientsForm.setReportingAgencyName(firstPageForm
						.getReportingAgencyName());
				patientsForm.setCounty(firstPageForm.getCounty());
				patientsForm.setNumberOfUnits(firstPageForm.getNumberOfUnits());
				patientsForm.setUnitInError(firstPageForm.getUnitInError());
				patientsForm.setCityVillageTownship(firstPageForm
						.getCityVillageTownship());
				patientsForm.setCrashDate(firstPageForm.getCrashDate());
				patientsForm.setTimeOfCrash(firstPageForm.getTimeOfCrash());
				patientsForm.setPatientStatus(1);
				patientsForm.setIsRunnerReport(0);
				patientsForm.setIsOwner(0);
				patientsForm.setIsOccupantAvailable(0);
			}
			// Added Date From Automation Script(Front End)
			patientsForm.setAddedDate(addedDate);
			
			return patientsForm;
		
	}
	
	// Get Vehicle and Owner Details
	public List<PatientForm> getVehicleOwnerDetails(PDFCrashReportJson pdfCrashReportJson, String addedDate){
		List<PatientForm> vehicleOwnerDetails=new ArrayList<PatientForm>();
		
		ReportFirstPageForm reportFirstPageForm = pdfCrashReportJson.getFirstPageForm();
		List<ReportUnitPageForm> reportUnitPageForms = pdfCrashReportJson.getReportUnitPageForms();
		List<ReportMotoristPageForm> reportMotoristPageForms = pdfCrashReportJson.getReportMotoristPageForms();
		String atFaultInsuranceCompany=null;
		String atFaultPolicyNumber=null;
		for (ReportUnitPageForm reportUnitPageForm : reportUnitPageForms) {
			if((reportFirstPageForm.getUnitInError()!=null&&!reportFirstPageForm.getUnitInError().equals(""))
					&&(reportUnitPageForm.getUnitNumber()!=null&&!reportUnitPageForm.getUnitNumber().equals(""))){
				if(Integer.parseInt(reportFirstPageForm.getUnitInError())==Integer.parseInt(reportUnitPageForm.getUnitNumber())){
					atFaultInsuranceCompany=reportUnitPageForm.getInsuranceCompany();
					atFaultPolicyNumber=reportUnitPageForm.getPolicyNumber();
				}
			}
		}
		
		for (ReportUnitPageForm reportUnitPageForm : reportUnitPageForms) {
			PatientForm patientForm = new PatientForm();
			
			patientForm.setLocalReportNumber(reportFirstPageForm.getLocalReportNumber());
			patientForm.setCrashSeverity(reportFirstPageForm.getCrashSeverity());
			patientForm.setReportingAgencyNcic(reportFirstPageForm.getReportingAgencyNCIC());
			patientForm.setReportingAgencyName(reportFirstPageForm
					.getReportingAgencyName());
			patientForm.setCounty(reportFirstPageForm.getCounty());
			patientForm.setNumberOfUnits(reportFirstPageForm.getNumberOfUnits());
			patientForm.setUnitInError(reportFirstPageForm.getUnitInError());
			patientForm.setCityVillageTownship(reportFirstPageForm
					.getCityVillageTownship());
			patientForm.setCrashDate(reportFirstPageForm.getCrashDate());
			patientForm.setTimeOfCrash(reportFirstPageForm.getTimeOfCrash());
			
			patientForm.setUnitNumber(reportUnitPageForm.getUnitNumber());
			patientForm.setName(reportUnitPageForm.getOwnerName());
			patientForm.setAddress(reportUnitPageForm.getOwnerAddress());
			patientForm.setPhoneNumber(reportUnitPageForm.getOwnerPhoneNumber());
			patientForm.setVehicleMake(reportUnitPageForm.getVehicleMake());
			patientForm.setVehicleYear(reportUnitPageForm.getVehicleYear());
			patientForm.setVin(reportUnitPageForm.getVIN());
			patientForm.setLicensePlateNumber(reportUnitPageForm.getLicensePlatNumber());
			patientForm.setIsOwner(1);
			if(reportUnitPageForm.getDamageScale()!=null&&!reportUnitPageForm.getDamageScale().equals("")){
				patientForm.setDamageScale(Integer.parseInt(reportUnitPageForm.getDamageScale()));
			}
			
			patientForm.setVictimInsuranceCompany(reportUnitPageForm.getInsuranceCompany());
			patientForm.setVictimPolicyNumber(reportUnitPageForm.getPolicyNumber());
			
			// At fault Policy and Insurance
			patientForm.setAtFaultInsuranceCompany(atFaultInsuranceCompany);
			patientForm.setAtFaultPolicyNumber(atFaultPolicyNumber);
			
			patientForm.setPatientStatus(1);
			patientForm.setIsRunnerReport(0);
			
			// Added Date
			patientForm.setAddedDate(addedDate);
			
			ReportMotoristPageForm motoristPageForm=this.compareUnitPageWithMotoristPage(reportUnitPageForm, reportMotoristPageForms);
			if(motoristPageForm!=null){
				if(motoristPageForm.getAge()!=null&&!motoristPageForm.equals(""))
					patientForm.setAge(Integer.parseInt(motoristPageForm.getAge()));
				
				patientForm.setGender(motoristPageForm.getGender());
				patientForm.setDateOfBirth(motoristPageForm.getDateOfBirth());
				
			}
			vehicleOwnerDetails.add(patientForm);
		}
		return vehicleOwnerDetails;
	}
	
	// Check Owner Details with Motorist Page to get Extra Details
	public ReportMotoristPageForm compareUnitPageWithMotoristPage(ReportUnitPageForm unitPageForm,List<ReportMotoristPageForm> reportMotoristPageForms){
		ReportMotoristPageForm motoristPageForm = null;
		for (ReportMotoristPageForm motoristPage : reportMotoristPageForms) {
			if(motoristPage.getName()!=null&&unitPageForm.getOwnerName()!=null&&motoristPage.getAdddressCityStateZip()!=null&&unitPageForm.getOwnerAddress()!=null){
				
				String[] ownerName=InjuryConstants.splitNameByComma(unitPageForm.getOwnerName());
				String[] motoristName=InjuryConstants.splitNameByComma(motoristPage.getName());
				
				if(motoristName[0].equals(ownerName[0])&&motoristName[1].equals(ownerName[1])&&motoristPage.getAdddressCityStateZip().equals(unitPageForm.getOwnerAddress())){
					return motoristPage;
				}
			}
		}
		return motoristPageForm;
	}
	

	
	//Main function for PDF Parsing
	public void parsePDFDocument(File file,String crashId, String addedDate) throws Exception{
		
				UUID randomUUID=UUID.randomUUID();
				String fileName="";
				String uuid="";
				
				if(Integer.parseInt(injuryProperties.getProperty("env"))==0){
					fileName="CrashReport_"+crashId+".pdf";
					uuid=crashId.toString();
				}else{
					fileName=randomUUID+"_"+ crashId + ".pdf";
					uuid=randomUUID.toString();
				}
				
				//String fileName=uuid+"_"+ crashId + ".pdf";
				//Convert PDF data to Parsed JSON
				PDFCrashReportJson pdfCrashReportJson=null;
				try {
					pdfCrashReportJson = this
							.getValuesFromPDF(this.parsePdfFromFile(file));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Vehicle Details with Owner Information
				List<PatientForm> vehicleOwnerDetails = this.getVehicleOwnerDetails(pdfCrashReportJson,addedDate);
				// Vehicle Count From Report First Page Number of Units
				Integer vehicleCount=0;
				if(pdfCrashReportJson.getFirstPageForm().getNumberOfUnits()!=null&&!pdfCrashReportJson.getFirstPageForm().getNumberOfUnits().equals(""))
					 vehicleCount=Integer.parseInt(pdfCrashReportJson.getFirstPageForm().getNumberOfUnits());
				
				
				Integer patientsCount=0;
				List<PatientForm> patientsForms = new ArrayList<PatientForm>();
 				List<PatientForm> filteredPatientsForms=new ArrayList<PatientForm>();
				//Check for report status
				Integer tierType = this.getReportType(pdfCrashReportJson);
				patientsForms=this.getTierPatientForm(pdfCrashReportJson, tierType, addedDate);
				// Check Crash Report is Runner Report
				CrashReport runnerReportCrashId =this.checkODPSReportWithRunnerReport(pdfCrashReportJson.getFirstPageForm());
				
			
				
			switch (tierType) {
				case 1:
					filteredPatientsForms=filterPatientForms(patientsForms);
					if(filteredPatientsForms.size()==0){
						patientsCount=0;
						patientsForms=this.getTierPatientForm(pdfCrashReportJson, 9, addedDate);
						for (PatientForm patientForm : patientsForms) {
							if(patientForm.getIsOccupantAvailable()!=0){
								patientsCount++;
							}
							patientForm.setTier(0);
						}
						//#9 Tier 1 Error None of the Patient are not having address and the phone number
						if(runnerReportCrashId==null){
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 9,patientsCount,vehicleCount));
						}else{
							crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 9, runnerReportCrashId.getIsRunnerReport());
						}
						
						this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
					}else{
						//Insert patients
						Integer patientAvailableTier=checkTierFourPatientsAvailable(filteredPatientsForms);
						if(patientAvailableTier==3){
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 13,filteredPatientsForms.size(),vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 13, runnerReportCrashId.getIsRunnerReport());
							}
						}else if(patientAvailableTier==1){
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 1,filteredPatientsForms.size(),vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 1, runnerReportCrashId.getIsRunnerReport());
							}
						}else if(patientAvailableTier==2){
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 14,filteredPatientsForms.size(),vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 14, runnerReportCrashId.getIsRunnerReport());
							}
						}else{
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 17,filteredPatientsForms.size(),vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 17, runnerReportCrashId.getIsRunnerReport());
							}
						}
						
						try {
							this.savePatientList(filteredPatientsForms,crashId.toString(),addedDate,runnerReportCrashId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							throw e;
						}
					}
					// Save Vehicle and Owner Details
					this.savePatientList(vehicleOwnerDetails,crashId, addedDate,runnerReportCrashId);
					break;
				case 2:
					if(patientsForms.size()==0){
						//#7 Victim units not having insurance
						patientsForms=this.getTierPatientForm(pdfCrashReportJson, 7, addedDate);
						patientsCount=0;
						for (PatientForm patientForm : patientsForms) {
							if(patientForm.getIsOccupantAvailable()!=0){
								patientsCount++;
							}
						}
						if(runnerReportCrashId==null){
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 7,patientsCount,vehicleCount));
						}else{
							System.out.println("Matched------>> Update Report");
							crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 7, runnerReportCrashId.getIsRunnerReport());
						}
						this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
					}else{
						filteredPatientsForms=filterPatientForms(patientsForms);
						if(filteredPatientsForms.size()==0){
							//#10 Tier2 No patient have address and phone number\
							patientsForms=this.getTierPatientForm(pdfCrashReportJson, 10, addedDate);
							patientsCount=0;
							for (PatientForm patientForm : patientsForms) {
								if(patientForm.getIsOccupantAvailable()!=0){
									patientsCount++;
								}
								patientForm.setTier(0);
							}
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 10,patientsCount,vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 10, runnerReportCrashId.getIsRunnerReport());
							}
							
							this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
						}else{
							Integer crashReportType=2;
							Integer count=0;
							for (PatientForm patientForm : filteredPatientsForms) {
								if(patientForm.getTier()==0){
									count++;
								}
							}
							if(filteredPatientsForms.size()==count){
								crashReportType=18;
							}
							//Insert patients
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, crashReportType,filteredPatientsForms.size(),vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, crashReportType, runnerReportCrashId.getIsRunnerReport());
							}
							try {
								this.savePatientList(filteredPatientsForms,crashId.toString(),addedDate,runnerReportCrashId);
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								throw e;
							}
							}
					}
					// Save Vehicle and Owner Details
					this.savePatientList(vehicleOwnerDetails,crashId,addedDate,runnerReportCrashId);
					break;
				case 3:
					if(patientsForms.size()==0){
						//#8 Error None of the Patient satisfy injuries scale 2 to 4
						patientsForms=this.getTierPatientForm(pdfCrashReportJson, 8, addedDate);
						patientsCount=0;
						for (PatientForm patientForm : patientsForms) {
							if(patientForm.getIsOccupantAvailable()!=0){
								patientsCount++;
							}
						}
						if(runnerReportCrashId==null){
							crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 8,patientsCount,vehicleCount));
						}else{
							System.out.println("Matched------>> Update Report");
							crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 8, runnerReportCrashId.getIsRunnerReport());
						}
						this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
					}else{
						filteredPatientsForms=filterPatientForms(patientsForms);
						if(filteredPatientsForms.size()==0){
							//#11 Tier3 Patient Not having address and phone numbers
							patientsForms=this.getTierPatientForm(pdfCrashReportJson, 11, addedDate);
							patientsCount=0;
							for (PatientForm patientForm : patientsForms) {
								if(patientForm.getIsOccupantAvailable()!=0){
									patientsCount++;
								}
								patientForm.setTier(0);
							}
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 11,patientsCount,vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 11, runnerReportCrashId.getIsRunnerReport());
							}
							
							this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
						}else{
							Integer crashReportType=3;
							Integer count=0;
							for (PatientForm patientForm : filteredPatientsForms) {
								if(patientForm.getTier()==0){
									count++;
								}
							}
							if(filteredPatientsForms.size()==count){
								crashReportType=19;
							}
							//Insert patients
							if(runnerReportCrashId==null){
								crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, crashReportType,filteredPatientsForms.size(),vehicleCount));
							}else{
								System.out.println("Matched------>> Update Report");
								crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, crashReportType, runnerReportCrashId.getIsRunnerReport());
							}
							try {
								this.savePatientList(filteredPatientsForms,crashId.toString(),addedDate,runnerReportCrashId);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								throw e;
							}
							
						}
					}
					// Save Vehicle and Owner Details
					this.savePatientList(vehicleOwnerDetails,crashId,addedDate,runnerReportCrashId);
					break;
				case 4:
					//Insert into crash report table number of units > 1 and unit in error is an animal
					patientsCount=0;
					for (PatientForm patientForm : patientsForms) {
						if(patientForm.getIsOccupantAvailable()!=0){
							patientsCount++;
						}
					}
					if(runnerReportCrashId==null){
						crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 4,patientsCount,vehicleCount));
					}else{
						System.out.println("Matched------>> Update Report");
						crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 4, runnerReportCrashId.getIsRunnerReport());
					}
					this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
					
					// Save Vehicle and Owner Details
					this.savePatientList(vehicleOwnerDetails,crashId,addedDate,runnerReportCrashId);
					break;
				case 5:
					//Insert into crash report table number of units == 1 and unit in error is an animal
					patientsCount=0;
					for (PatientForm patientForm : patientsForms) {
						if(patientForm.getIsOccupantAvailable()!=0){
							patientsCount++;
						}
					}
					if(runnerReportCrashId==null){
						crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 5,patientsCount,vehicleCount));
					}else{
						System.out.println("Matched------>> Update Report");
						crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 5, runnerReportCrashId.getIsRunnerReport());
					}
					this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
					// Save Vehicle and Owner Details
					this.savePatientList(vehicleOwnerDetails,crashId,addedDate,runnerReportCrashId);
					break;
				case 6:
					//Insert into crash report table number of units == 1 and unit in error not having insurance details
					patientsCount=0;
					for (PatientForm patientForm : patientsForms) {
						if(patientForm.getIsOccupantAvailable()!=0){
							patientsCount++;
						}
					}
					if(runnerReportCrashId==null){
						crashReportService.saveCrashReport(crashReportService.getCrashReportFormDetails(pdfCrashReportJson.getFirstPageForm(), addedDate, crashId, fileName, 6,patientsCount,vehicleCount));
					}else{
						System.out.println("Matched------>> Update Report");
						crashReportService.updateCrashReport(runnerReportCrashId.getCrashId(), crashId, fileName, 6, runnerReportCrashId.getIsRunnerReport());
					}
					this.savePatientList(patientsForms,crashId.toString(),addedDate,runnerReportCrashId);
					// Save Vehicle and Owner Details
					this.savePatientList(vehicleOwnerDetails,crashId,addedDate,runnerReportCrashId);
					break;
				default:
					break;
				}
				if(Integer.parseInt(injuryProperties.getProperty("awsUpload"))==1)				
					awsFileUpload.uploadFileToAWSS3(file.getAbsolutePath(), fileName);
				if(Integer.parseInt(injuryProperties.getProperty("env"))==1)
					file.delete();
 	}
	
	
	public void savePatientList(List<PatientForm> patientsForms,String crashId,String addedDate,CrashReport runnerReportCrashId) throws Exception{
		Integer patientCount=0;
		Integer vehicleCount=0;
		for (PatientForm patientsForm : patientsForms) {
			//patientsForm.setCrashReportFileName(uuid+"_"+crashId+".pdf");
			patientsForm.setCrashReportFileName(crashId+".pdf");
			patientsForm.setCrashId(crashId);
			try {
				if(patientsForm.getIsOwner()==1){
					patientsService.savePatient(patientsForm);
					vehicleCount++;
					System.out.println("Save Vehicle And Owner Details");
				}else{
					if(runnerReportCrashId!=null){
						Patient patient = this.checkODPSPatientsWithRunnerReportPatients(patientsForm);
						if(patient!=null){
							// Update Patient Details
							System.out.println("Matched----->>>Update Patient Details");
							patientsForm.setPatientId(patient.getPatientId());
							patientsForm.setIsRunnerReport(2);
							if(runnerReportCrashId.getIsRunnerReport()==3){
								patientsForm.setIsRunnerReport(4);
							}
							patientsForm.setStatus(1);
							patientsService.updatePatientCurrentAddedDate(patientsForm);
						}else{
							patientsService.savePatient(patientsForm);
							patientCount++;
							System.out.println("Not Matched----->>>Save Patient Details");
						}
					}else{
						patientsService.savePatient(patientsForm);
						System.out.println("No Need To Check----->>>Save Patient Details");
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
		
		// Update Patients Count
		if(runnerReportCrashId!=null){
			CrashReport crashReport = crashReportDAO.getCrashReport(crashId);
			Integer oldPatientsCount=crashReport.getNumberOfPatients();
			crashReport.setNumberOfPatients(oldPatientsCount+patientCount);
			crashReport.setVehicleCount(vehicleCount);
			crashReport.setAddedDate(InjuryConstants.convertYearFormat(addedDate));
			crashReport.setAddedDateTime(InjuryConstants.convertYearFormatWithTimeByDate(addedDate));
			crashReportDAO.update(crashReport);
		}
		
		// Update patient Added on Date To Not Matched Runner Patients. 1---> Is Runner Report Status
		List<Patient> patients = patientDAO.getRunnerReportPatients(crashId, 1);
		for (Patient patient : patients) {
			patient.setAddedDate(InjuryConstants.convertYearFormat(addedDate));
			patientDAO.update(patient);
		}
	}
	
	public List<PatientForm> filterPatientForms(List<PatientForm> patientsForms){
		List<PatientForm> filteredPatientForms=new ArrayList<PatientForm>();
		filteredPatientForms.addAll(patientsForms);
		for (PatientForm patientsForm : patientsForms) {
			if(patientsForm.getUnitNumber()!=null){
				if ((patientsForm.getAddress()==null || patientsForm.getAddress().equals(""))
						&& (patientsForm.getPhoneNumber()==null || patientsForm.getPhoneNumber().equals(""))) {
					filteredPatientForms.remove(patientsForm);
				}
			}else{
				// If Unit Number is Null
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
	
	// Check ODPS Report With Runner Report
	public CrashReport checkODPSReportWithRunnerReport(ReportFirstPageForm reportFirstPageForm){
		Long oldReportCount=crashReportDAO.getLocalReportNumberCount(reportFirstPageForm.getLocalReportNumber());
		Integer countyId=countyDAO.getCountyByName(crashReportService.splitCountyName(reportFirstPageForm.getCounty())).getCountyId();
		CrashReport crashReport=null;
		Integer isCheckAll=0;
		for (int i = 0; i <=oldReportCount; i++) {
			String localReportNumber=reportFirstPageForm.getLocalReportNumber();
			if(i!=0){
				localReportNumber=localReportNumber+"("+i+")";
			}
			crashReport=crashReportDAO.getCrashReportForChecking(localReportNumber,reportFirstPageForm.getCrashDate(), countyId, isCheckAll);
			if(crashReport!=null){
				break;
			}
		}
	
		return crashReport;
	}
	
	// Check ODPS Patients With Runner Report Patients
	public Patient checkODPSPatientsWithRunnerReportPatients(PatientForm patientForm){
		Patient patient = null;
		String patientName=this.splitPatientName(patientForm.getName());
		Integer countyId=countyDAO.getCountyByName(crashReportService.splitCountyName(patientForm.getCounty())).getCountyId();
		patient=patientDAO.checkPatientForRunnerReport(countyId, patientForm.getCrashDate(), patientName);
		return patient;
	}
	
	// Split Name
	public String splitPatientName(String patientName){
		String resultName=null;
		// Initialize as Empty
		if(patientName!=null){
			String[] splitName=patientName.split(",");
			if(splitName.length==2){
				//Last Name // First Name
				resultName=splitName[0].trim()+", "+splitName[1].trim();
			}else if(splitName.length==1){
				//Last Name
				resultName=splitName[0].trim();
			}else{
				//Last Name // First Name
				resultName=splitName[0].trim()+", "+splitName[1].trim();
			}
		}
		
		return resultName;
	}
	
	// Check Crash Report Id Availability for Manual Upload
	public boolean isCrashIdAvailable(String crashId) {
		boolean isAvailable = false;
		CrashReport crashReport = crashReportDAO.getCrashReport(crashId);
		if (crashReport != null) {
			isAvailable = true;
		}
		return isAvailable;
	}
	
	// Save Direct Runner Report (SAGE Report From ODPS) And Scheduler PD
	//Report From 0 - ODPS 1 - Police Dept 
	public int saveDirectRunnerCrashReport(RunnerCrashReportForm runnerCrashReportForm,Integer reportFrom) throws Exception
	{
		//TODO: Convert Form to Entity Here	
		int isFileAvailable=1;
		Integer reportFromMapId=0;
		try {
			
			if(!this.isDirectReportAlreadyAvailable(runnerCrashReportForm)){
				File file;
				if(reportFrom==0){
					file=getPDFFile(runnerCrashReportForm.getDocNumber(),runnerCrashReportForm.getDocImageFileName(),2,"");
				}
				else{
					file=getPDFFile(runnerCrashReportForm.getDocNumber(),runnerCrashReportForm.getDocImageFileName(),3,runnerCrashReportForm.getFilePath());
					reportFromMapId=runnerCrashReportForm.getReportFrom();
				}
					 
						
				String fileName="";
				if(file.length()>2000){//2000 File Size refers an crash report not found exception
					//Parse the PDF
					// 16 - Direct Runner Crash Reports
					if(Integer.parseInt(injuryProperties.getProperty("env"))==1){
						fileName=runnerCrashReportForm.getDocNumber() + ".pdf";
					}else{
						fileName="CrashReport_"+ runnerCrashReportForm.getDocNumber() + ".pdf";
					}
					
					// File Upload To AWS
					if(Integer.parseInt(injuryProperties.getProperty("awsUpload"))==1)				
						awsFileUpload.uploadFileToAWSS3(file.getAbsolutePath(), fileName);
					
					Integer crashReportErrorId=16;
					Integer isRunnerReport=3;
					County county= countyDAO.get(Integer.parseInt(runnerCrashReportForm.getCounty()));
					CrashReportError crashReportError=crashReportErrorDAO.get(crashReportErrorId);
					String localReportNumber=runnerCrashReportForm.getLocalReportNumber();
					if(crashReportDAO.getCrashReportCountByLocalReportNumber(localReportNumber)>0){
						Long localReportNumberCount=crashReportDAO.getLocalReportNumberCount(localReportNumber+"(");
						localReportNumber=localReportNumber+"("+(localReportNumberCount+1)+")";
					}
					
					PoliceAgency policeAgency = policeAgencyDAO.get(reportFromMapId);
					policeAgency.setLastUpdatedDate(new Date());
					policeAgency.setReportStatus(1);
					Integer numberOfPatients=0;
					Integer vehicleCount=0;
					CrashReport crashReport=new CrashReport(runnerCrashReportForm.getDocNumber(), crashReportError, policeAgency, county, localReportNumber, InjuryConstants.convertYearFormat(runnerCrashReportForm.getCrashDate()), 
							InjuryConstants.convertYearFormat(runnerCrashReportForm.getAddedDate()), numberOfPatients, vehicleCount, fileName, null, isRunnerReport, new Date(), 1, InjuryConstants.convertYearFormatWithTimeByDate(runnerCrashReportForm.getAddedDate()), InjuryConstants.convertYearFormatWithTimeByDate(runnerCrashReportForm.getAddedDate()), null, null, null);
					
					
					crashReportDAO.save(crashReport);
					// Update Last Updated Date
					policeAgencyDAO.update(policeAgency);
				}else{
					isFileAvailable=0;
				}
			}else{
				System.out.println("<<<<---- Already Exist ------>>>");
			}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isFileAvailable=2;
			throw e;
		}
	
		//Logic Ends
		
		return isFileAvailable;
	}
	
	// Save Runner Direct Report (Scanned) From Nightwatch Automation
	public int saveRunnerDirectOrScannedReport(RunnerCrashReportForm runnerCrashReportForm) throws Exception{
		int status=1;
		
		Integer crashReportErrorId=16;
		Integer isRunnerReport=3;
		UUID randomUUID = UUID.randomUUID();
		String crashId=randomUUID.toString().replaceAll("-", "");
		File file = null;
		
		try{
			boolean isSaveReport=true;
			String fileName="";
			// Police Agency
			PoliceAgency policeAgency = policeAgencyDAO.get(runnerCrashReportForm.getReportFrom());
			County county= countyDAO.get(policeAgency.getCounty().getCountyId());
			CrashReportError crashReportError=crashReportErrorDAO.get(crashReportErrorId);
			
			runnerCrashReportForm.setCounty(county.getCountyId().toString());
			if(runnerCrashReportForm.getIsNormal()==0){
				if(!this.isDirectReportAlreadyAvailable(runnerCrashReportForm)){
					file=getPDFFile(crashId,runnerCrashReportForm.getDocImageFileName(),3,runnerCrashReportForm.getFilePath());
					if(file.length()>2000){//2000 File Size refers an crash report not found exception
						//Parse the PDF
						// 16 - Direct Runner Crash Reports
						if(Integer.parseInt(injuryProperties.getProperty("env"))==1){
							fileName=crashId + ".pdf";
						}else{
							fileName="CrashReport_"+ crashId + ".pdf";
						}
						policeAgency.setLastUpdatedDate(new Date());
						policeAgency.setReportStatus(1);
					}else{
						isSaveReport=false;
						status=0;
					}
				}else{
					policeAgency.setLastUpdatedDate(new Date());
					policeAgency.setReportStatus(1);
					isSaveReport=false;
					status=2;
				}
			}else if(runnerCrashReportForm.getIsNormal()==1){
				file=getPDFFile(crashId,runnerCrashReportForm.getDocImageFileName(),3,runnerCrashReportForm.getFilePath());
				if(file.length()>2000){//2000 File Size refers an crash report not found exception
					//Parse the PDF
					// 16 - Direct Runner Crash Reports
					if(Integer.parseInt(injuryProperties.getProperty("env"))==1){
						fileName=crashId + ".pdf";
					}else{
						fileName="CrashReport_"+ crashId + ".pdf";
					}
					
					List<String> reportDetails=this.getDetailsFromPDF(this.parsePdfFromFile(file));
					runnerCrashReportForm.setLocalReportNumber(reportDetails.get(0));
					runnerCrashReportForm.setCrashDate(reportDetails.get(1));

					// Parse PDF and Upate in Runner Crash Report Form
					if(!this.isDirectReportAlreadyAvailable(runnerCrashReportForm)){
						// Save PDF FILE and Set FileName, File
						
					}else{
						isSaveReport=false;
						status=2;
					}
				}else{
					isSaveReport=false;
					status=0;
				}
				
			}else if(runnerCrashReportForm.getIsNormal()==2){
				// Values with PDF File
				if(!this.isDirectReportAlreadyAvailable(runnerCrashReportForm)){
					// Save PDF FILE and Set FileName, File
				}else{
					isSaveReport=false;
					status=2;
				}
			}else if(runnerCrashReportForm.getIsNormal()==3){
				// No Value With PDF File
				// Save PDF FILE, Parse for Value and Update in Runner Crash Report Form
				
				if(!this.isDirectReportAlreadyAvailable(runnerCrashReportForm)){
					// Save PDF FILE and Set FileName
				}else{
					isSaveReport=false;
					status=2;
				}
			}
			
			if(isSaveReport){
				// File Upload To AWS
				if(Integer.parseInt(injuryProperties.getProperty("awsUpload"))==1)				
					awsFileUpload.uploadFileToAWSS3(file.getAbsolutePath(), fileName);
				
				
				String localReportNumber=runnerCrashReportForm.getLocalReportNumber();
				if(crashReportDAO.getCrashReportCountByLocalReportNumber(localReportNumber)>0){
					Long localReportNumberCount=crashReportDAO.getLocalReportNumberCount(localReportNumber+"(");
					localReportNumber=localReportNumber+"("+(localReportNumberCount+1)+")";
				}
				
				Integer numberOfPatients=0;
				Integer vehicleCount=0;
				CrashReport crashReport=new CrashReport(crashId, crashReportError, policeAgency, county, localReportNumber, InjuryConstants.convertYearFormat(runnerCrashReportForm.getCrashDate()), 
						InjuryConstants.convertYearFormat(runnerCrashReportForm.getAddedDate()), numberOfPatients, vehicleCount, fileName, null, isRunnerReport, new Date(), 1, InjuryConstants.convertYearFormatWithTimeByDate(runnerCrashReportForm.getAddedDate()), InjuryConstants.convertYearFormatWithTimeByDate(runnerCrashReportForm.getAddedDate()), null, null, null);
				
				crashReportDAO.save(crashReport);
				
				// Update Last Updated Date
				policeAgencyDAO.update(policeAgency);
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return status;
	}
	
	// Direct Report Already Available Checking With 
	public boolean isDirectReportAlreadyAvailable(RunnerCrashReportForm runnerCrashReportForm){
		Long oldReportCount=crashReportDAO.getLocalReportNumberCount(runnerCrashReportForm.getLocalReportNumber());
		Integer countyId=Integer.parseInt(runnerCrashReportForm.getCounty());
		boolean isExist=false;
		CrashReport crashReport=null;
		Integer isCheckAll=1;
		for (int i = 0; i <=oldReportCount; i++) {
			String localReportNumber=runnerCrashReportForm.getLocalReportNumber();
			if(i!=0){
				localReportNumber=localReportNumber+"("+i+")";
			}
			crashReport=crashReportDAO.getCrashReportForChecking(localReportNumber,runnerCrashReportForm.getCrashDate(), countyId, isCheckAll);
			if(crashReport!=null){
				isExist=true;
				break;
			}
		}
			
		return isExist;
	}
	
	public List<String>  getDetailsFromPDF(List<List<String>> content)
			throws IOException {
		List<String> details = new ArrayList<String>();
		List<String> firstPageContent=content.get(0);
		String localReportNumber=firstPageContent.get(firstPageContent.indexOf("Local Information")-1);
		String crashDate=firstPageContent.get(firstPageContent.indexOf("Report Taken By (Correction or Addition to")+6);
		if(localReportNumber!=null&&!localReportNumber.equals("")){
			localReportNumber=localReportNumber.replaceAll("\\s+", "");
		}
		if(crashDate!=null&&!crashDate.equals("")){
			crashDate=crashDate.replaceAll("\\s+", "");
			crashDate=crashDate.substring(0,2)+"/"+crashDate.substring(2,4)+"/"+crashDate.substring(4,crashDate.length());
		}
		details.add(localReportNumber);
		details.add(crashDate);
		return details;
	}
	
	public Integer updateReportingAgencyForExistingReports(String fromDate, String toDate) throws IOException{
		Integer status=0;
		List<Patient> patients=patientDAO.getPatientsListByAddedOnDates(fromDate, toDate);
		for (Patient patient : patients) {
			System.out.println("Patient Crash Id"+patient.getCrashReport().getCrashId());
			String filePath=injuryProperties.getProperty("bucketURL")+patient.getCrashReport().getFilePath();
			PDFCrashReportJson pdfCrashReportJson = this.getValuesFromPDF(this.parsePdfFromWebUrl(filePath));
			System.out.println("Reporting Agency"+pdfCrashReportJson.getFirstPageForm().getReportingAgencyNCIC());
			patient.setReportingAgencyNcic(pdfCrashReportJson.getFirstPageForm().getReportingAgencyNCIC());
			patientDAO.update(patient);
		}
		return status;
	}
}
