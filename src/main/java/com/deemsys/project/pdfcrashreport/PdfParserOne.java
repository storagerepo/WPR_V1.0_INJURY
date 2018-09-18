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

	public PDFCrashReportJson parsePdfFromFile(File file) throws IOException {
		PDFCrashReportJson pdfCrashReportJson = new PDFCrashReportJson(null, null, null);
		try {
			ReportFirstPageForm reportFirstPageForm = new ReportFirstPageForm();
			FileInputStream fileInputStream = new FileInputStream(file);
			PdfReader reader = new PdfReader(fileInputStream);
			String[] propertyNames = { "localReportNumber", "crashSeverity", "reportingAgencyCode",
					"reportingAgencyName", "numberOfUnits", "unitInError", "county", "cityVillageTownship", "crashDate",
					"crashTime" };
			for (String propertyName : propertyNames) {
				String[] propertyCoordinates = injuryProperties.getParserOneProperty(propertyName).split(",");
				Rectangle rect = new Rectangle(Double.parseDouble(propertyCoordinates[0]),
						Double.parseDouble(propertyCoordinates[1]), Double.parseDouble(propertyCoordinates[2]),
						Double.parseDouble(propertyCoordinates[3]));
				RenderFilter[] filter = { new RegionTextRenderFilter(rect) };
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
					reportFirstPageForm
							.setNumberOfUnits(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
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
			// setting first page values
			pdfCrashReportJson.setFirstPageForm(reportFirstPageForm);

			// Unit datas
			List<ReportUnitPageForm> reportUnitPageForms = new ArrayList<ReportUnitPageForm>();
			List<ReportMotoristPageForm> reportMotoristPageForms = new ArrayList<ReportMotoristPageForm>();
			Integer IncrementPage = 0;
			for (int i = 2; i <= Integer.parseInt(reportFirstPageForm.getNumberOfUnits()) + 1 + IncrementPage; i++) {
				String[] unitPageCoordinates = injuryProperties.getParserOneProperty("findUnitPage").split(",");
				Rectangle rect = new Rectangle(Double.parseDouble(unitPageCoordinates[0]),
						Double.parseDouble(unitPageCoordinates[1]), Double.parseDouble(unitPageCoordinates[2]),
						Double.parseDouble(unitPageCoordinates[3]));
				RenderFilter[] unitPageFilter = { new RegionTextRenderFilter(rect) };
				TextExtractionStrategy unitStrategy;
				unitStrategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitPageFilter);
				String pdfText1 = PdfTextExtractor.getTextFromPage(reader, i, unitStrategy);
				if (pdfText1.equals("OHIO TRAFFIC ACCIDENT - DIAGRAM/NARRITIVE CONTINUATION")
						|| this.findUnitPage(i, reader)) {
					IncrementPage = IncrementPage + 1;
					continue;
				} else {
					ReportUnitPageForm reportUnitPageForm = new ReportUnitPageForm();
					String[] UnitPropertyNames = { "driverUnitNumber", "ownerName", "ownerPhoneNumber",
							"ownerDamageScale", "ownerAddress", "licensePlateNumber", "VIN", "noOfOccupants",
							"vehicleYear", "vehcileMake", "insuranceCompany", "policyNumber", "typeOfUse" };
					for (String propertyName : UnitPropertyNames) {
						String[] unitPropertyCoordinates = injuryProperties.getParserOneProperty(propertyName).split(",");
						Rectangle rectangle = new Rectangle(Double.parseDouble(unitPropertyCoordinates[0]),
								Double.parseDouble(unitPropertyCoordinates[1]),
								Double.parseDouble(unitPropertyCoordinates[2]),
								Double.parseDouble(unitPropertyCoordinates[3]));
						RenderFilter[] unitFilter = { new RegionTextRenderFilter(rectangle) };
						TextExtractionStrategy strategy;
						strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
						String pdfText = PdfTextExtractor.getTextFromPage(reader, i, strategy);
						switch (propertyName) {
						case "driverUnitNumber":
							reportUnitPageForm
									.setUnitNumber(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
							break;
						case "ownerName":
							reportUnitPageForm.setOwnerName(this.formatName(pdfText));
							break;
						case "ownerPhoneNumber":
							reportUnitPageForm.setOwnerPhoneNumber(pdfText);
							break;
						case "ownerDamageScale":
							reportUnitPageForm.setDamageScale(pdfText);
							break;
						case "ownerAddress":
							reportUnitPageForm.setOwnerAddress(this.formatAddress(pdfText));
							break;
						case "licensePlateNumber":
							reportUnitPageForm.setLicensePlatNumber(pdfText);
							break;
						case "VIN":
							reportUnitPageForm.setVIN(pdfText);
							break;
						case "noOfOccupants":
							reportUnitPageForm
									.setOccupants(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
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

			for (int j = Integer.parseInt(reportFirstPageForm.getNumberOfUnits()) + 2 + IncrementPage; j <= reader
					.getNumberOfPages(); j++) {
				String[] findMotoristOrOccupant={"stateCoordinate1","stateCoordinate2","localReportNumberCoordinate"};
				String StateCondition1="";
				String StateCondition2="";
				String LocalReportNumber="";
				for(String property:findMotoristOrOccupant){
				String[] coordinates = injuryProperties.getParserOneProperty(property).split(",");
				Rectangle rectangle = new Rectangle(Double.parseDouble(coordinates[0]),
						Double.parseDouble(coordinates[1]), Double.parseDouble(coordinates[2]),
						Double.parseDouble(coordinates[3]));
				RenderFilter[] unitFilter = { new RegionTextRenderFilter(rectangle) };
				TextExtractionStrategy strategy;
				strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
				String pdfText = PdfTextExtractor.getTextFromPage(reader, j, strategy);
				switch (property) {
				case "stateCoordinate1":	
					StateCondition1=pdfText;
					break;
				case "stateCoordinate2":
					StateCondition2=pdfText;
					break;
				case "localReportNumberCoordinate":
					LocalReportNumber=pdfText;
					break;
				default:
					break;
				}
				}
				String[][] occupantsArray = new String[5][];
				if ((!StateCondition1.equals("") && ((StateCondition1.equals("OH") || StateCondition1.equals("WA"))
						|| (!StateCondition1.matches("[0-9]+") && StateCondition1.length() == 2)))||(!StateCondition2.equals("") && ((StateCondition2.equals("OH") || StateCondition2.equals("WA"))
								|| (!StateCondition2.matches("[0-9]+") && StateCondition2.length() == 2)))) {
					occupantsArray = InjuryConstants.MOTORIST_PAGE_COORDINATES_PARSER_ONE;
				}else if(!LocalReportNumber.equals("")&&!LocalReportNumber.equals(".F.")) {
					occupantsArray = InjuryConstants.MOTORIST_PAGE_COORDINATES_PARSER_ONE;
				}
				else {
					occupantsArray = InjuryConstants.OCCUPANT_PAGE_COORDINATES_PARSER_ONE;
				}
				for (int n = 0; n < occupantsArray.length; n++) {
					ReportMotoristPageForm reportMotoristPageForm = new ReportMotoristPageForm();
					reportMotoristPageForm = this.processOccupantDetails(j, reader, occupantsArray[n]);
					if(reportMotoristPageForm!=null){
					if((reportMotoristPageForm.getUnitNumber()!=null&&!reportMotoristPageForm.getUnitNumber().equals(""))&&(reportMotoristPageForm.getName()!=null&&!reportMotoristPageForm.getName().replaceAll(",", "").replaceAll(" ", "").equals(""))&&((reportMotoristPageForm.getAdddressCityStateZip()!=null&& !reportMotoristPageForm.getAdddressCityStateZip().replaceAll(",", "").replaceAll(" ", "").equals(""))||(reportMotoristPageForm.getContactPhone()!=null&&!reportMotoristPageForm.getContactPhone().equals(""))))
					{
						reportMotoristPageForms.add(reportMotoristPageForm);
					}
				}
				}
			}

			// setting unit data
			pdfCrashReportJson.setReportUnitPageForms(reportUnitPageForms);
			// setting occupant details
			pdfCrashReportJson.setReportMotoristPageForms(reportMotoristPageForms);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return pdfCrashReportJson;
	}

	public ReportMotoristPageForm processOccupantDetails(int i, PdfReader reader, String[] occupantArray)
			throws IOException {
		ReportMotoristPageForm reportMotoristPageForm = new ReportMotoristPageForm();
		for (String occupantPropertyName : occupantArray) {
			String[] occupantPropertyCoordinates = injuryProperties.getParserOneProperty(occupantPropertyName).split(",");
			Rectangle rectangle = new Rectangle(Double.parseDouble(occupantPropertyCoordinates[0]),
					Double.parseDouble(occupantPropertyCoordinates[1]),
					Double.parseDouble(occupantPropertyCoordinates[2]),
					Double.parseDouble(occupantPropertyCoordinates[3]));
			RenderFilter[] unitFilter = { new RegionTextRenderFilter(rectangle) };
			TextExtractionStrategy strategy;
			String pdfText = "";
			strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
			pdfText = PdfTextExtractor.getTextFromPage(reader, i, strategy);
			if (occupantPropertyName.contains("UnitNumber")) {
				reportMotoristPageForm.setUnitNumber(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
				}
			if (occupantPropertyName.contains("Name")) {
				if(pdfText.startsWith("Report #")){
					return null;
				}else{
					reportMotoristPageForm.setName(this.formatName(pdfText));
				}	
				}
			if (occupantPropertyName.contains("Address")){
					reportMotoristPageForm.setAdddressCityStateZip(this.formatAddress(pdfText));
			}
			if (occupantPropertyName.contains("PhoneNumber")) {
					reportMotoristPageForm.setContactPhone(pdfText);
			}
			if (occupantPropertyName.contains("DateOfBirth")) {
					reportMotoristPageForm.setDateOfBirth(pdfText);
			}
			if (occupantPropertyName.contains("PatientAge")) {
					reportMotoristPageForm.setAge(pdfText);
			}
			if (occupantPropertyName.contains("Gender")) {
					reportMotoristPageForm.setGender(pdfText);
			}
			if (occupantPropertyName.contains("Injury")){
					reportMotoristPageForm.setInjuries(pdfText);
			}
			if (occupantPropertyName.contains("EMSAgency")){
					reportMotoristPageForm.setEmsAgency(pdfText);
			}
			if (occupantPropertyName.contains("MedicalFacility")) {
					reportMotoristPageForm.setMedicalFacility(pdfText);
			}
			if (occupantPropertyName.contains("SeatingPosition")) {
					reportMotoristPageForm.setSeatingPosition(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
			}
		}
		return reportMotoristPageForm;

	}

	// Save File
	public File saveFile(MultipartFile inputFile) {
		File saveFileLocation = new File(injuryProperties.getProperty("tempFolder") + inputFile.getOriginalFilename());
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

	// skip unwanted spaces
	public String skipUnwantedSpaces(String inputString) {
		return inputString.replaceAll(" ", "");
	}

	// Remove Zero before numbers
	public String removeZeroBeforeNumbers(String inputString) {
		return inputString.replaceFirst("^0+(?!$)", "");
	}

	// Structure the Time of crash
	public String frameTimeOfCrash(String inputString) {

		if (inputString.lastIndexOf(":") == inputString.length() - 1) {
			inputString = inputString.substring(0, inputString.length() - 1);
		}
		if (inputString.indexOf(":") == -1) {
			inputString = inputString.substring(0, 2) + ":" + inputString.substring(2, inputString.length());
		}
		return inputString;
	}

	// Check whether unit page or not
	public boolean findUnitPage(Integer i, PdfReader reader) throws IOException {
		String[] properties = { "noOfOccupants", "lpState", "vehicleYear" };
		String occupants = "";
		String lpState = "";
		String vehicleYear = "";
		for (String propertyName : properties) {
			String[] unitPropertyCoordinates = injuryProperties.getParserOneProperty(propertyName).split(",");
			Rectangle rectangle = new Rectangle(Double.parseDouble(unitPropertyCoordinates[0]),
					Double.parseDouble(unitPropertyCoordinates[1]), Double.parseDouble(unitPropertyCoordinates[2]),
					Double.parseDouble(unitPropertyCoordinates[3]));
			RenderFilter[] unitFilter = { new RegionTextRenderFilter(rectangle) };
			TextExtractionStrategy strategy;
			strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
			String pdfText = PdfTextExtractor.getTextFromPage(reader, i, strategy);
			switch (propertyName) {
			case "noOfOccupants":
				occupants = pdfText;
				break;
			case "lpState":
				lpState = pdfText;
				break;
			case "vehicleYear":
				vehicleYear = pdfText;
				break;
			default:
				break;
			}
		}
		if (this.skipUnwantedSpaces(occupants).matches("[0-9]+")
				|| (!this.skipUnwantedSpaces(lpState).matches("[0-9]+")
						&& this.skipUnwantedSpaces(lpState).length() == 2)
				|| (this.skipUnwantedSpaces(vehicleYear).matches("[0-9]+")
						|| this.skipUnwantedSpaces(vehicleYear).length() == 4)) {
			return false;
		} else {
			return true;
		}

	}
	// Format Name
	public String formatName(String inputValue){
		String[] splittedName = inputValue.split(" ");
		String outputValue="";
		for (int j = 0; j < splittedName.length; j++) {
			// Last Name
			if(j==0){
				outputValue=splittedName[0];
			}// First Name
			else if(j==1){
				outputValue=outputValue+", "+splittedName[1];
			}// Middle Name
			else{
				outputValue=outputValue+", "+splittedName[j];
			}
		}
		return outputValue;
	}
	// Verify the address format
		public String formatAddress(String inputValue){
			String outputValue="";
			if(!inputValue.equals("")){
				String[] addressSplitted=inputValue.replaceAll("\\s+", " ").split(" ");
				Integer cityIndex=this.findCityIndex(inputValue);
				Integer indexToSubtract=0;
				if(addressSplitted.length>=3){
					if(this.isState(addressSplitted[addressSplitted.length-2])&&this.isZipcode(addressSplitted[addressSplitted.length-1])){
						indexToSubtract=2;
					}
					else if(this.isState(addressSplitted[addressSplitted.length-1])||this.isZipcode(addressSplitted[addressSplitted.length-1])){
						indexToSubtract=1;
					}
				}
				for (int i = 0; i < addressSplitted.length-indexToSubtract; i++) {
					outputValue=outputValue+" "+addressSplitted[i];
				}
				StringBuilder str=new StringBuilder(outputValue.trim());
				if(cityIndex!=0){
					str.insert(cityIndex, ",");
				}
				if(indexToSubtract==2){
					outputValue=str+", "+addressSplitted[addressSplitted.length-2]+", "+addressSplitted[addressSplitted.length-1];
				}
				else if(indexToSubtract==1){
					outputValue=str+", "+addressSplitted[addressSplitted.length-1];
				}
				else if(indexToSubtract==0){
					outputValue=str.toString();
				}
			}
			return outputValue;
		}
		
		//Check for Zipcode
		public boolean isZipcode(String checkStr){
			if(checkStr.matches("\\d+")&&(checkStr.length()==5)||(checkStr.length()==4)){
				return true;
			}else{
				return false;
			}		
		}
		//Find city Index
		public Integer findCityIndex(String inputValue){
			String[] addressSplitted=inputValue.replaceAll("\\s+", " ").split(" ");
			List<String> defaultStreetForms=InjuryConstants.getDefaultStreetForms();
			String StreetName="";
			for(int k=0;k<addressSplitted.length; k++ ){
				if(defaultStreetForms.contains(addressSplitted[k])){
					StreetName=addressSplitted[k];
					if(defaultStreetForms.contains(addressSplitted[k+1])){
						StreetName=addressSplitted[k+1];
					}
					break;
				}
			}
			Integer indexToUseComma=inputValue.indexOf(StreetName)+StreetName.length();
			return indexToUseComma;
		}
		
		//Check for State
		public boolean isState(String checkStr){
			if(checkStr.matches("[a-zA-Z]+")&&checkStr.length()==2){
				return true;
			}else{
				return false;
			}
		}
}
