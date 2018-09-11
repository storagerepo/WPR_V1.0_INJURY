package com.deemsys.project.pdfcrashreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.common.InjuryProperties;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

@Service
public class PdfParserOne {

	@Autowired
	InjuryProperties injuryProperties;
	
	public PDFCrashReportJson parsePdfFromFile(File file) throws IOException{	
		PDFCrashReportJson pdfCrashReportJson=new PDFCrashReportJson(null, null, null);
		try{
			ReportFirstPageForm reportFirstPageForm=new ReportFirstPageForm();
			FileInputStream fileInputStream=new FileInputStream(file);
			PdfReader reader=new PdfReader(fileInputStream);
			String[] propertyNames={"localReportNumber","crashSeverity","reportingAgencyCode","reportingAgencyName","numberOfUnits",
					"unitInError","county","cityVillageTownship","crashDate","crashTime"};
			for(String propertyName:propertyNames){
			String[] propertyCoordinates=injuryProperties.getProperty(propertyName).split(",");
			Rectangle rect=new Rectangle(Double.parseDouble(propertyCoordinates[0]), Double.parseDouble(propertyCoordinates[1]), 
					Double.parseDouble(propertyCoordinates[2]), Double.parseDouble(propertyCoordinates[3]));
			RenderFilter[] filter = {new RegionTextRenderFilter(rect)};
			String pdfText = "";
			TextExtractionStrategy strategy;
			strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
			pdfText = PdfTextExtractor.getTextFromPage(reader, 1, strategy);
			switch (propertyName) {
			case "localReportNumber":
				reportFirstPageForm.setLocalReportNumber(this.skipUnwantedSpaces(pdfText));
				break;
			case "crashSeverity":
			reportFirstPageForm.setCrashSeverity(pdfText);
				break;
			case "reportingAgencyCode":
			reportFirstPageForm.setReportingAgencyNCIC(pdfText);
				break;
			case "reportingAgencyName":
			reportFirstPageForm.setReportingAgencyName(pdfText);
				break;
			case "numberOfUnits":
			reportFirstPageForm.setNumberOfUnits(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
				break;
			case "unitInError":
			reportFirstPageForm.setUnitInError(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
				break;
			case "county":
			reportFirstPageForm.setCounty(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
				break;
			case "cityVillageTownship":
			reportFirstPageForm.setCityVillageTownship(pdfText);
				break;
			case "crashDate":
			reportFirstPageForm.setCrashDate(pdfText);
				break;
			case "crashTime":
			reportFirstPageForm.setTimeOfCrash(this.frameTimeOfCrash(this.skipUnwantedSpaces(pdfText)));
				break;
			default:
				break;
			}
			}
			//setting first page values
			pdfCrashReportJson.setFirstPageForm(reportFirstPageForm);
			
			//Unit datas
			List<ReportUnitPageForm> reportUnitPageForms=new ArrayList<ReportUnitPageForm>();
			List<ReportMotoristPageForm> reportMotoristPageForms=new ArrayList<ReportMotoristPageForm>();
			Integer IncrementPage=0;
			for(int i = 2; i <= Integer.parseInt(reportFirstPageForm.getNumberOfUnits())+1+IncrementPage; i++){
				String[] unitPageCoordinates=injuryProperties.getProperty("findUnitPage").split(",");
				Rectangle rect=new Rectangle(Double.parseDouble(unitPageCoordinates[0]), Double.parseDouble(unitPageCoordinates[1]), 
						Double.parseDouble(unitPageCoordinates[2]), Double.parseDouble(unitPageCoordinates[3]));
				RenderFilter[] unitPageFilter = {new RegionTextRenderFilter(rect)};
				TextExtractionStrategy unitStrategy;
				unitStrategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitPageFilter);
				String pdfText1 = PdfTextExtractor.getTextFromPage(reader, i, unitStrategy);
				if(pdfText1.equals("OHIO TRAFFIC ACCIDENT - DIAGRAM/NARRITIVE CONTINUATION")){
					IncrementPage=IncrementPage+1;
					continue;
				}
				else{
					ReportUnitPageForm reportUnitPageForm=new ReportUnitPageForm();
					String[] UnitPropertyNames={"driverUnitNumber","ownerName","ownerPhoneNumber","ownerDamageScale","ownerAddress",
							"licensePlateNumber","VIN","noOfOccupants","vehicleYear","vehcileMake","insuranceCompany",
							"policyNumber","typeOfUse"};
					for(String propertyName:UnitPropertyNames){
						String[] unitPropertyCoordinates=injuryProperties.getProperty(propertyName).split(",");
						Rectangle rectangle=new Rectangle(Double.parseDouble(unitPropertyCoordinates[0]), Double.parseDouble(unitPropertyCoordinates[1]), 
								Double.parseDouble(unitPropertyCoordinates[2]), Double.parseDouble(unitPropertyCoordinates[3]));
						RenderFilter[] unitFilter = {new RegionTextRenderFilter(rectangle)};
						TextExtractionStrategy strategy;
						strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
						String pdfText = PdfTextExtractor.getTextFromPage(reader, i, strategy);
						switch (propertyName) {
						case "driverUnitNumber":
							reportUnitPageForm.setUnitNumber(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
							break;
						case "ownerName":
							reportUnitPageForm.setOwnerName(pdfText);
							break;
						case "ownerPhoneNumber":
							reportUnitPageForm.setOwnerPhoneNumber(pdfText);
							break;
						case "ownerDamageScale":
							reportUnitPageForm.setDamageScale(pdfText);
							break;
						case "ownerAddress":
							reportUnitPageForm.setOwnerAddress(pdfText);
							break;
						case "licensePlateNumber":
							reportUnitPageForm.setLicensePlatNumber(pdfText);
							break;
						case "VIN":
							reportUnitPageForm.setVIN(pdfText);
							break;
						case "noOfOccupants":
							reportUnitPageForm.setOccupants(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
							break;
						case "vehicleYear":
							reportUnitPageForm.setVehicleYear(pdfText);
							break;
						case "vehcileMake":
							reportUnitPageForm.setVehicleMake(pdfText);
							break;
						case "insuranceCompany":
							reportUnitPageForm.setInsuranceCompany(pdfText);
							break;
						case "policyNumber":
							reportUnitPageForm.setPolicyNumber(pdfText);
							break;
						case "typeOfUse":
							reportUnitPageForm.setTypeOfUse(pdfText);
							break;
						default:
							break;
						}
					}
					reportUnitPageForms.add(reportUnitPageForm);
			}
			}
				
				for(int j=Integer.parseInt(reportFirstPageForm.getNumberOfUnits())+2+IncrementPage;j<=reader.getNumberOfPages();j++){
					String[] coordinates=injuryProperties.getProperty("findOccupantOrMotorist").split(",");
					Rectangle rectangle=new Rectangle(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]), 
							Double.parseDouble(coordinates[2]), Double.parseDouble(coordinates[3]));
					RenderFilter[] unitFilter = {new RegionTextRenderFilter(rectangle)};
					TextExtractionStrategy strategy;
					strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
					String pdfText = PdfTextExtractor.getTextFromPage(reader, j, strategy);
					String[][] occupantsArray=new String[5][];
					if(!pdfText.equals("")&&(pdfText.equals("OH")||pdfText.equals("WA")&&!pdfText.matches("[0-9]+"))){
						
					occupantsArray= new String[][]{{"occupantOneUnitNumber","occupantOneName","occupantOneDateOfBirth","occupantOnePatientAge",
						"occupantOneGender","occupantOneAddress","occupantOnePhoneNumber","occupantOneInjury","occupantOneEMSAgency",
						"occupantOneMedicalFacility","occupantOneSeatingPosition"},
						{"occupantTwoUnitNumber","occupantTwoName","occupantTwoDateOfBirth","occupantTwoPatientAge",
							"occupantTwoGender","occupantTwoAddress","occupantTwoPhoneNumber","occupantTwoInjury","occupantTwoEMSAgency",
							"occupantTwoMedicalFacility","occupantTwoSeatingPosition"},
						{"occupantThreeUnitNumber","occupantThreeName","occupantThreeDateOfBirth","occupantThreePatientAge",
							"occupantThreeGender","occupantThreeAddress","occupantThreePhoneNumber","occupantThreeInjury","occupantThreeEMSAgency",
							"occupantThreeMedicalFacility","occupantThreeSeatingPosition"},
						{"occupantFourUnitNumber","occupantFourName","occupantFourDateOfBirth","occupantFourPatientAge",
							"occupantFourGender","occupantFourAddress","occupantFourPhoneNumber","occupantFourInjury","occupantFourEMSAgency",
							"occupantFourMedicalFacility","occupantFourSeatingPosition"}};
					}
					else if(!pdfText.equals("OH")||!pdfText.equals("WA")){
						
					occupantsArray=new String[][]{{"occupantFiveUnitNumber","occupantFiveName","occupantFiveDateOfBirth","occupantFivePatientAge",
						"occupantFiveGender","occupantFiveAddress","occupantFivePhoneNumber","occupantFiveInjury","occupantFiveEMSAgency",
						"occupantFiveMedicalFacility","occupantFiveSeatingPosition"},
						{"occupantSixUnitNumber","occupantSixName","occupantSixDateOfBirth","occupantSixPatientAge",
							"occupantSixGender","occupantSixAddress","occupantSixPhoneNumber","occupantSixInjury","occupantSixEMSAgency",
							"occupantSixMedicalFacility","occupantSixSeatingPosition"},
						{"occupantSevenUnitNumber","occupantSevenName","occupantSevenDateOfBirth","occupantSevenPatientAge",
							"occupantSevenGender","occupantSevenAddress","occupantSevenPhoneNumber","occupantSevenInjury","occupantSevenEMSAgency",
							"occupantSevenMedicalFacility","occupantSevenSeatingPosition"},
						{"occupantEightUnitNumber","occupantEightName","occupantEightDateOfBirth","occupantEightPatientAge",
							"occupantEightGender","occupantEightAddress","occupantEightPhoneNumber","occupantEightInjury","occupantEightEMSAgency",
							"occupantEightMedicalFacility","occupantEightSeatingPosition"},
						{"occupantNineUnitNumber","occupantNineName","occupantNineDateOfBirth","occupantNinePatientAge",
							"occupantNineGender","occupantNineAddress","occupantNinePhoneNumber","occupantNineInjury","occupantNineEMSAgency",
							"occupantNineMedicalFacility","occupantNineSeatingPosition"},
						{"occupantTenUnitNumber","occupantTenName","occupantTenDateOfBirth","occupantTenPatientAge",
							"occupantTenGender","occupantTenAddress","occupantTenPhoneNumber","occupantTenInjury","occupantTenEMSAgency",
							"occupantTenMedicalFacility","occupantTenSeatingPosition"}};
					}
					for(int n=0;n<occupantsArray.length;n++){
						ReportMotoristPageForm reportMotoristPageForm=new ReportMotoristPageForm();
						reportMotoristPageForm=this.processOccupantDetails(j,reader,occupantsArray[n]);
						if(reportMotoristPageForm!=null){
							reportMotoristPageForms.add(reportMotoristPageForm);
						}
					}
				}
		
			//setting unit data
			pdfCrashReportJson.setReportUnitPageForms(reportUnitPageForms);
			//setting occupant details
			pdfCrashReportJson.setReportMotoristPageForms(reportMotoristPageForms);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return pdfCrashReportJson;
	}
	
	public ReportMotoristPageForm processOccupantDetails(int i,PdfReader reader,String[] occupantArray)throws IOException{
		ReportMotoristPageForm reportMotoristPageForm=new ReportMotoristPageForm();
		Integer countToSkipOccupant=0;
		for(String occupantPropertyName:occupantArray){
			String[] occupantPropertyCoordinates=injuryProperties.getProperty(occupantPropertyName).split(",");
			Rectangle rectangle=new Rectangle(Double.parseDouble(occupantPropertyCoordinates[0]), Double.parseDouble(occupantPropertyCoordinates[1]), 
					Double.parseDouble(occupantPropertyCoordinates[2]), Double.parseDouble(occupantPropertyCoordinates[3]));
			RenderFilter[] unitFilter = {new RegionTextRenderFilter(rectangle)};
			TextExtractionStrategy strategy;
			String pdfText="";
			strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
			pdfText = PdfTextExtractor.getTextFromPage(reader, i, strategy);
			if(occupantPropertyName.contains("UnitNumber")){
				if(pdfText.equals("")||pdfText==null||!this.skipUnwantedSpaces(pdfText).matches("[0-9]+")){
					countToSkipOccupant=countToSkipOccupant+1;
				}
				else if(!pdfText.equals("")){
					reportMotoristPageForm.setUnitNumber(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
				}
			}
			if(occupantPropertyName.contains("Name")){
				if(pdfText.equals("")||pdfText==null){
					countToSkipOccupant=countToSkipOccupant+1;
				}
				else if(!pdfText.equals("")){
					reportMotoristPageForm.setName(pdfText);
				}
			}
			if(occupantPropertyName.contains("Address")){
				if(pdfText.equals("")||pdfText==null){
					countToSkipOccupant=countToSkipOccupant+1;
				}
				else if(!pdfText.equals("")){
					reportMotoristPageForm.setAdddressCityStateZip(pdfText);
				}
			}
			if(occupantPropertyName.contains("PhoneNumber")){
				if(pdfText.equals("")||pdfText==null){
					countToSkipOccupant=countToSkipOccupant+1;
					if(countToSkipOccupant==4){
						return null;
					}
				}
				else if(!pdfText.equals("")){
					reportMotoristPageForm.setContactPhone(pdfText);
				}
			}
			if(occupantPropertyName.contains("DateOfBirth")){
				if(!pdfText.equals("")){
					reportMotoristPageForm.setDateOfBirth(pdfText);
				}
			}
			if(occupantPropertyName.contains("PatientAge")){
				if(!pdfText.equals("")){
					reportMotoristPageForm.setAge(pdfText);
				}
			}
			if(occupantPropertyName.contains("Gender")){
				if(!pdfText.equals("")){
					reportMotoristPageForm.setGender(pdfText);
				}
			}
			if(occupantPropertyName.contains("Injury")){
				if(!pdfText.equals("")){
					reportMotoristPageForm.setInjuries(pdfText);
				}
			}
			if(occupantPropertyName.contains("EMSAgency")){
				if(!pdfText.equals("")){
					reportMotoristPageForm.setEmsAgency(pdfText);
				}
			}
			if(occupantPropertyName.contains("MedicalFacility")){
				if(!pdfText.equals("")){
					reportMotoristPageForm.setMedicalFacility(pdfText);
				}
			}
			if(occupantPropertyName.contains("SeatingPosition")){
				if(!pdfText.equals("")){
					reportMotoristPageForm.setSeatingPosition(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
				}
			}
		}
		return reportMotoristPageForm;
		
	}
	
	// Save File
		public File saveFile(MultipartFile inputFile){
			File saveFileLocation =  new File(injuryProperties.getProperty("tempFolder")+inputFile.getOriginalFilename());
			try {
				inputFile.transferTo(saveFileLocation);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return saveFileLocation;
		}
		//skip unwanted spaces
		public String skipUnwantedSpaces(String inputString){
			return inputString.replaceAll(" ", "");
		}
		//Remove Zero before numbers
		public String removeZeroBeforeNumbers(String inputString){
			return inputString.replaceFirst("^0+(?!$)", "");
		}
		//Structure the Time of crash
		public String frameTimeOfCrash(String inputString){
			
			if(inputString.lastIndexOf(":")==inputString.length()-1){
				inputString=inputString.substring(0,inputString.length()-1);	
			}
			if(inputString.indexOf(":")==-1){
				inputString=inputString.substring(0, 2)+":"+inputString.substring(2, inputString.length());
		}
			return inputString;
}}
