package com.deemsys.project.pdfcrashreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
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
public class PdfParserOneTesseract {

	@Autowired
	InjuryProperties injuryProperties;
	
	@Autowired
	ScannedParser scannedParser;

	public PDFCrashReportJson parsePdfFromFile(File file) throws Exception {
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
					reportFirstPageForm.setCrashDate(this.formatDate(pdfText));
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

			//
			String fileLocation = scannedParser.convertToSearchablePDF(file);
			Map<String, List<Integer>> pageNumbers=this.getPageNumbers(fileLocation);
			Iterator<Entry<String, List<Integer>>> it = pageNumbers.entrySet().iterator();
			List<Integer> unitPages=new ArrayList<Integer>();
			List<Integer> motoristPages=new ArrayList<Integer>();
			List<Integer> occupantPages=new ArrayList<Integer>();
			while(it.hasNext()){
				Map.Entry<String, List<Integer>> pageMap= (Map.Entry<String, List<Integer>>) it.next();
				if(pageMap.getKey().equals("unit")){
					unitPages=pageMap.getValue();
				}else if(pageMap.getKey().equals("motorist")){
					motoristPages=pageMap.getValue();
				}else if(pageMap.getKey().equals("occupant")){
					occupantPages=pageMap.getValue();
				}
			}
			
			// Unit datas
			List<ReportUnitPageForm> reportUnitPageForms = new ArrayList<ReportUnitPageForm>();
			List<ReportMotoristPageForm> reportMotoristPageForms = new ArrayList<ReportMotoristPageForm>();
			for (int i = 0; i < unitPages.size(); i++) {
				ReportUnitPageForm reportUnitPageForm = new ReportUnitPageForm();
				String[] UnitPropertyNames = { "driverUnitNumber", "ownerName", "ownerPhoneNumber",
						"ownerDamageScale", "ownerAddress", "licensePlateNumber", "VIN", "noOfOccupants",
						"vehicleYear", "vehcileMake", "insuranceCompany", "policyNumber", "typeOfUse" };
				for (String propertyName : UnitPropertyNames) {
					String[] unitPropertyCoordinates = injuryProperties.getParserOneProperty(propertyName)
							.split(",");
					Rectangle rectangle = new Rectangle(Double.parseDouble(unitPropertyCoordinates[0]),
							Double.parseDouble(unitPropertyCoordinates[1]),
							Double.parseDouble(unitPropertyCoordinates[2]),
							Double.parseDouble(unitPropertyCoordinates[3]));
					RenderFilter[] unitFilter = { new RegionTextRenderFilter(rectangle) };
					TextExtractionStrategy strategy;
					strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitFilter);
					String pdfText = PdfTextExtractor.getTextFromPage(reader, unitPages.get(i), strategy);
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
			
			// Motorist Details
			for (int j = 0; j < motoristPages.size(); j++) {
			
				String[][] occupantsArray = new String[5][];
				occupantsArray = InjuryConstants.MOTORIST_PAGE_COORDINATES_PARSER_ONE;
				for (int n = 0; n < occupantsArray.length; n++) {
					ReportMotoristPageForm reportMotoristPageForm = new ReportMotoristPageForm();
					reportMotoristPageForm = this.processOccupantDetails(motoristPages.get(j), reader, occupantsArray[n]);
					if (reportMotoristPageForm != null) {
						if ((reportMotoristPageForm.getUnitNumber() != null
								&& !reportMotoristPageForm.getUnitNumber().equals(""))
								&& (reportMotoristPageForm.getName() != null && !reportMotoristPageForm.getName()
										.replaceAll(",", "").replaceAll(" ", "").equals(""))
								&& ((reportMotoristPageForm.getAdddressCityStateZip() != null && !reportMotoristPageForm
										.getAdddressCityStateZip().replaceAll(",", "").replaceAll(" ", "").equals(""))
										|| (reportMotoristPageForm.getContactPhone() != null
												&& !reportMotoristPageForm.getContactPhone().equals("")))) {
							reportMotoristPageForms.add(reportMotoristPageForm);
						}
					}
				}
			}

			// Occupants Details
			for (int j = 0; j < occupantPages.size(); j++) {
				
				String[][] occupantsArray = new String[5][];
				occupantsArray = InjuryConstants.OCCUPANT_PAGE_COORDINATES_PARSER_ONE;
				for (int n = 0; n < occupantsArray.length; n++) {
					ReportMotoristPageForm reportMotoristPageForm = new ReportMotoristPageForm();
					reportMotoristPageForm = this.processOccupantDetails(occupantPages.get(j), reader, occupantsArray[n]);
					if (reportMotoristPageForm != null) {
						if ((reportMotoristPageForm.getUnitNumber() != null
								&& !reportMotoristPageForm.getUnitNumber().equals(""))
								&& (reportMotoristPageForm.getName() != null && !reportMotoristPageForm.getName()
										.replaceAll(",", "").replaceAll(" ", "").equals(""))
								&& ((reportMotoristPageForm.getAdddressCityStateZip() != null && !reportMotoristPageForm
										.getAdddressCityStateZip().replaceAll(",", "").replaceAll(" ", "").equals(""))
										|| (reportMotoristPageForm.getContactPhone() != null
												&& !reportMotoristPageForm.getContactPhone().equals("")))) {
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
			String[] occupantPropertyCoordinates = injuryProperties.getParserOneProperty(occupantPropertyName)
					.split(",");
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
				if (pdfText.startsWith("Report #")) {
					return null;
				} else {
					reportMotoristPageForm.setName(this.formatName(pdfText));
				}
			}
			if (occupantPropertyName.contains("Address")) {
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
			if (occupantPropertyName.contains("Injury")) {
				reportMotoristPageForm.setInjuries(pdfText);
			}
			if (occupantPropertyName.contains("EMSAgency")) {
				reportMotoristPageForm.setEmsAgency(pdfText);
			}
			if (occupantPropertyName.contains("MedicalFacility")) {
				reportMotoristPageForm.setMedicalFacility(pdfText);
			}
			if (occupantPropertyName.contains("SeatingPosition")) {
				reportMotoristPageForm
						.setSeatingPosition(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
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
		String[] properties = { "noOfOccupants", "lpState", "vehicleYear","ownerDamageScale"};
		String occupants = "";
		String lpState = "";
		String vehicleYear = "";
		String damageScale = "";
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
			case "ownerDamageScale":
				damageScale = pdfText;
				break;
			default:
				break;
			}
		}
		if (this.skipUnwantedSpaces(occupants).matches("[0-9]+")
				|| (!this.skipUnwantedSpaces(lpState).matches("[0-9]+")
						&& this.skipUnwantedSpaces(lpState).length() == 2)
				|| (this.skipUnwantedSpaces(vehicleYear).matches("[0-9]+")
						&& this.skipUnwantedSpaces(vehicleYear).length() == 4)
				|| (this.skipUnwantedSpaces(damageScale).matches("[0-9]+")
								&& this.skipUnwantedSpaces(damageScale).length() == 1)) {
			return false;
		} else {
			return true;
		}

	}

	// Format Name
	public String formatName(String inputValue) {
		String[] splittedName = inputValue.split(" ");
		String outputValue = "";
		for (int j = 0; j < splittedName.length; j++) {
			// Last Name
			if (j == 0) {
				outputValue = splittedName[0];
			} // First Name
			else if (j == 1) {
				outputValue = outputValue + ", " + splittedName[1];
			} // Middle Name
			else {
				outputValue = outputValue + ", " + splittedName[j];
			}
		}
		return outputValue;
	}

	// Verify the address format
	public String formatAddress(String inputValue) {
		String outputValue = "";
		if (!inputValue.equals("")) {
			String[] addressSplitted = inputValue.replaceAll("\\s+", " ").split(" ");
			// Passing input address to find city index
			Integer cityIndex = this.findCityIndex(inputValue);
			// We will cut the array from backwards based on this value
			Integer indexToSubtract = 0;
			if (addressSplitted.length >= 3) {
				// If both state and zip code is available
				if (this.isState(addressSplitted[addressSplitted.length - 2])
						&& this.isZipcode(addressSplitted[addressSplitted.length - 1])) {
					indexToSubtract = 2;
				} else if (this.isState(addressSplitted[addressSplitted.length - 1])
						|| this.isZipcode(addressSplitted[addressSplitted.length - 1])) {
					// If either state or zipcode available
					indexToSubtract = 1;
				}
			}
			for (int i = 0; i < addressSplitted.length - indexToSubtract; i++) {
				outputValue = outputValue + " " + addressSplitted[i];
			}
			StringBuilder str = new StringBuilder(outputValue.trim().replaceAll("\\s+", " "));
			if (cityIndex != 0) {
				str.insert(cityIndex, ",");
			}
			if (indexToSubtract == 2) {
				// Merge state and zip after processing address string
				outputValue = str + ", " + addressSplitted[addressSplitted.length - 2] + ", "
						+ addressSplitted[addressSplitted.length - 1];
			} else if (indexToSubtract == 1) {
				// Merge either State or zip with address string
				outputValue = str + ", " + addressSplitted[addressSplitted.length - 1];
			} else if (indexToSubtract == 0) {
				outputValue = str.toString();
			}
		}
		return outputValue;
	}


	// Check for Zipcode
	public boolean isZipcode(String checkStr) {
		if (checkStr.matches("\\d+") && (checkStr.length() == 5 || checkStr.length() == 4)) {
			return true;
		} else {
			return false;
		}
	}

	// Find city Index
	public Integer findCityIndex(String inputValue) {
		String[] addressSplitted = inputValue.replaceAll("\\s+", " ").split(" ");
		List<String> defaultStreetForms = InjuryConstants.getDefaultStreetForms();
		List<String> matchedCount = new ArrayList<String>();
		Integer indexToUseComma = 0;
		for (int k = 0; k < addressSplitted.length; k++) {
			if (defaultStreetForms.contains(addressSplitted[k].toString().replaceAll("\\.", "").trim())) {
				if (k < addressSplitted.length - 1
						&& ((this.isState(addressSplitted[k + 1]) || this.isZipcode(addressSplitted[k + 2])))) {
					// Matches the Exact Word in String
					Pattern pattern = Pattern.compile("(?<!\\w)" + Pattern.quote(addressSplitted[k - 2]) + "(?!\\w)");
					Matcher matcher = pattern.matcher(inputValue);
					while (matcher.find()) {
						indexToUseComma = matcher.start() + addressSplitted[k - 2].length();
					}
					return indexToUseComma;
				} else if (k != addressSplitted.length - 1) {
					matchedCount.add(addressSplitted[k]);
				}
			}
		}
		// Array where we pushed matching words
		if (matchedCount.size() > 0) {
			// Fetching next value of last value from address array to process
			String nextValueInArray = addressSplitted[ArrayUtils.indexOf(addressSplitted,
					matchedCount.get(matchedCount.size() - 1)) + 1];
			// for the format--- RD 123,Wake,oh,44
			if (nextValueInArray.matches("[0-9]+") || nextValueInArray.length() <= 3) {
				// Matches the Exact Word in String
				Pattern pattern = Pattern.compile("(?<!\\w)" + Pattern.quote(nextValueInArray) + "(?!\\w)");
				Matcher matcher = pattern.matcher(inputValue);
				while (matcher.find()) {
					indexToUseComma = matcher.start() + nextValueInArray.length();
				}
			} else {
				// Matches the Exact Word in String
				Pattern pattern = Pattern
						.compile("(?<!\\w)" + Pattern.quote(matchedCount.get(matchedCount.size() - 1)) + "(?!\\w)");
				Matcher matcher = pattern.matcher(inputValue);
				while (matcher.find()) {
					indexToUseComma = matcher.start() + matchedCount.get(matchedCount.size() - 1).length();
				}
			}
		}
		return indexToUseComma;

	}
	// Check for State
	public boolean isState(String checkStr) {
		if (checkStr.matches("[a-zA-Z]+") && checkStr.length() == 2) {
			return true;
		} else {
			return false;
		}
	}

	// Format date
	public String formatDate(String date) {
		String[] dateSplitted = date.split("/");
		if (dateSplitted[2].length() == 2) {
			date = dateSplitted[0] + "/" + dateSplitted[1] + "/20" + dateSplitted[2];
		}
		return date;
	}

	// find whether motorist page
	public boolean findWhetherMotoristPage(String StateCondition1, String StateCondition2, String DriverLicence1,
			String DriverLicence2, String LocalReportNumberCondition, String localReportNumberToCompare,
			String DriverCondition1, String DriverCondition2) {
		if (!StateCondition1.equals("") && ((StateCondition1.equals("OH") || StateCondition1.equals("WA"))
				|| (!StateCondition1.matches("[0-9]+") && StateCondition1.length() == 2))) {
			return true;
		}

		if (!StateCondition2.equals("") && ((StateCondition2.equals("OH") || StateCondition2.equals("WA"))
				|| (!StateCondition2.matches("[0-9]+") && StateCondition2.length() == 2))) {
			return true;
		}
		if ((!DriverLicence1.equals("") && (DriverLicence1.matches("[0-9]+")
				&& Integer.parseInt(this.removeZeroBeforeNumbers(DriverLicence1)) <= 5))
				|| (!DriverLicence2.equals("") && (DriverLicence2.matches("[0-9]+")
						&& Integer.parseInt(this.removeZeroBeforeNumbers(DriverLicence2)) <= 5))) {
			return true;
		}
		if ((!DriverCondition1.equals("") && (DriverCondition1.matches("[0-9]+")
				&& Integer.parseInt(this.removeZeroBeforeNumbers(DriverCondition1)) <= 7))
				|| (!DriverCondition2.equals("") && (DriverCondition2.matches("[0-9]+")
						&& Integer.parseInt(this.removeZeroBeforeNumbers(DriverCondition2)) <= 7))) {
			return true;
		}
		if (!LocalReportNumberCondition.equals("")
				&& !LocalReportNumberCondition.replaceAll("\\.", "").trim().equals("F")
				&& LocalReportNumberCondition.equals(localReportNumberToCompare)) {
			return true;
		}
		
		return false;
	}
	
	// Get Page Numbers
	public Map<String, List<Integer>> getPageNumbers(String fileLocation){
		Map<String, List<Integer>> pageNumbers = new HashMap<String,List<Integer>>();
		try{
			File file = new File(fileLocation);
			FileInputStream fileInputStream = new FileInputStream(file);
			PdfReader reader = new PdfReader(fileInputStream);
			
			List<Integer> unitPageNumbers=new ArrayList<Integer>();
			List<Integer> motoristPageNumbers=new ArrayList<Integer>();
			List<Integer> occupantPageNumbers=new ArrayList<Integer>();
			for (int i = 2; i <= reader.getNumberOfPages(); i++) {
				String[] unitPageCoordinates = injuryProperties.getParserOneProperty("unitLabel").split(",");
				Rectangle rect = new Rectangle(Double.parseDouble(unitPageCoordinates[0]),
						Double.parseDouble(unitPageCoordinates[1]), Double.parseDouble(unitPageCoordinates[2]),
						Double.parseDouble(unitPageCoordinates[3]));
				RenderFilter[] filter = { new RegionTextRenderFilter(rect) };
				String pdfText = "";
				TextExtractionStrategy strategy;
				strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
				pdfText = PdfTextExtractor.getTextFromPage(reader, i, strategy);
				if(InjuryConstants.getUnitLabels().contains(pdfText)){
					unitPageNumbers.add(i);
				}else if(InjuryConstants.getMotoristLabels().contains(pdfText)){
					motoristPageNumbers.add(i);
				}else{
					unitPageCoordinates = injuryProperties.getParserOneProperty("occupantLabel").split(",");
					rect = new Rectangle(Double.parseDouble(unitPageCoordinates[0]),
							Double.parseDouble(unitPageCoordinates[1]), Double.parseDouble(unitPageCoordinates[2]),
							Double.parseDouble(unitPageCoordinates[3]));
					RenderFilter[] filter1 = { new RegionTextRenderFilter(rect) };
					strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter1);
					pdfText = PdfTextExtractor.getTextFromPage(reader, i, strategy);
					if(pdfText.equals("10 - Steerer Section oF Cas (Truck)")){
						occupantPageNumbers.add(i);
					}
				}
			}
			pageNumbers.put("unit", unitPageNumbers);
			pageNumbers.put("motorist", motoristPageNumbers);
			pageNumbers.put("occupant", occupantPageNumbers);
			return pageNumbers;
		}catch(Exception e){
			
		}
		return null;
	}
}
