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
public class PdfParserTwo {

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
				String[] propertyCoordinates = injuryProperties.getParserTwoProperty(propertyName).split(",");
				Rectangle rect = new Rectangle(Double.parseDouble(propertyCoordinates[0]),
						Double.parseDouble(propertyCoordinates[1]), Double.parseDouble(propertyCoordinates[2]),
						Double.parseDouble(propertyCoordinates[3]));
				RenderFilter[] filter = { new RegionTextRenderFilter(rect) };
				TextExtractionStrategy strategy;
				strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
				String pdfText = "";
				pdfText = PdfTextExtractor.getTextFromPage(reader, 1, strategy);
				switch (propertyName) {
				case "localReportNumber":
					reportFirstPageForm.setLocalReportNumber(this.skipUnwantedSpaces(pdfText));
					break;
				case "crashSeverity":
					reportFirstPageForm.setCrashSeverity(pdfText);
					break;
				case "reportingAgencyCode":
					reportFirstPageForm.setReportingAgencyNCIC(this.skipUnwantedSpaces(pdfText));
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
					reportFirstPageForm.setCityVillageTownship(pdfText.replaceAll("( +)", " "));
					break;
				case "crashDate":
					reportFirstPageForm.setCrashDate(this.frameDateOfBirth(pdfText));
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
			for (int i = 2; i <= reader.getNumberOfPages(); i++) {
				String[] pageCoordinates = injuryProperties.getParserTwoProperty("labelToFindPage").split(",");
				Rectangle rect = new Rectangle(Double.parseDouble(pageCoordinates[0]),
						Double.parseDouble(pageCoordinates[1]), Double.parseDouble(pageCoordinates[2]),
						Double.parseDouble(pageCoordinates[3]));
				RenderFilter[] unitPageFilter = { new RegionTextRenderFilter(rect) };
				TextExtractionStrategy unitStrategy;
				unitStrategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), unitPageFilter);
				String pdfText1 = PdfTextExtractor.getTextFromPage(reader, i, unitStrategy);
				if (this.skipUnwantedSpaces(pdfText1).equals("Unit")) {
					ReportUnitPageForm reportUnitPageForm = new ReportUnitPageForm();
					String[] UnitPropertyNames = { "driverUnitNumber", "ownerName", "ownerPhoneNumber",
							"ownerDamageScale", "ownerAddress", "licensePlateNumber", "VIN", "noOfOccupants",
							"vehicleYear", "vehcileMake", "insuranceCompany", "policyNumber", "typeOfUse" };
					for (String propertyName : UnitPropertyNames) {
						String[] unitPropertyCoordinates = injuryProperties.getParserTwoProperty(propertyName)
								.split(",");
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
							reportUnitPageForm.setOwnerName(pdfText);
							break;
						case "ownerPhoneNumber":
							reportUnitPageForm.setOwnerPhoneNumber(pdfText);
							break;
						case "ownerDamageScale":
							reportUnitPageForm.setDamageScale(pdfText.replaceAll("[^\\d]", ""));
							break;
						case "ownerAddress":
							reportUnitPageForm.setOwnerAddress(pdfText.replaceAll("( +)", " "));
							break;
						case "licensePlateNumber":
							reportUnitPageForm.setLicensePlatNumber(pdfText);
							break;
						case "VIN":
							reportUnitPageForm.setVIN(this.skipUnwantedSpaces(pdfText));
							break;
						case "noOfOccupants":
							reportUnitPageForm
									.setOccupants(this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText)));
							break;
						case "vehicleYear":
							reportUnitPageForm.setVehicleYear(this.skipUnwantedSpaces(pdfText));
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
					// Occupant/Motorist Page
				} else if (this.skipUnwantedSpaces(pdfText1).equals("Occupant/WitnessAddendum")
						|| this.skipUnwantedSpaces(pdfText1).equals("Motorist/Non-Motorist/Occupant")) {
					String[][] occupantsArray = new String[5][];

					if (this.skipUnwantedSpaces(pdfText1).equals("Motorist/Non-Motorist/Occupant")) {
						occupantsArray = InjuryConstants.MOTORIST_PAGE_COORDINATES_PARSER_TWO;
					} else if (this.skipUnwantedSpaces(pdfText1).equals("Occupant/WitnessAddendum")) {
						occupantsArray = InjuryConstants.OCCUPANT_PAGE_COORDINATES_PARSER_TWO;
					}
					for (int n = 0; n < occupantsArray.length; n++) {
						ReportMotoristPageForm reportMotoristPageForm = new ReportMotoristPageForm();
						reportMotoristPageForm = this.processOccupantDetails(i, reader, occupantsArray[n]);
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
				} else {
					continue;
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

	// Process occupant details
	public ReportMotoristPageForm processOccupantDetails(int i, PdfReader reader, String[] occupantArray)
			throws IOException {
		ReportMotoristPageForm reportMotoristPageForm = new ReportMotoristPageForm();
		String address = "";
		for (String occupantPropertyName : occupantArray) {
			String[] occupantPropertyCoordinates = injuryProperties.getParserTwoProperty(occupantPropertyName)
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
			if (occupantPropertyName.contains("PatientName")) {
				reportMotoristPageForm.setName(this.frameNameOfOccupant(pdfText));
			}
			if (occupantPropertyName.contains("Address")) {
				if (occupantPropertyName.contains("AddressDoorNo")) {
					address = pdfText;
				}
				if (occupantPropertyName.contains("AddressStreetName")) {
					address = address + "," + pdfText;
				}
				if (occupantPropertyName.contains("AddressCity")) {
					address = address + "," + pdfText;
				}
				if (occupantPropertyName.contains("AddressState")) {
					address = address + " " + pdfText;
					reportMotoristPageForm.setAdddressCityStateZip(address.replaceAll("( +)", " "));
				}

			}
			if (occupantPropertyName.contains("PhoneNumber")) {
				reportMotoristPageForm.setContactPhone(pdfText);
			}
			if (occupantPropertyName.contains("DateOfBirth")) {
				reportMotoristPageForm.setDateOfBirth(this.frameDateOfBirth(pdfText));
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
				reportMotoristPageForm.setSeatingPosition(
						this.removeZeroBeforeNumbers(this.skipUnwantedSpaces(pdfText.replaceAll("[^\\d]", ""))));
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

	// Frame Date of Birth since it comes along with "/n"
	public String frameDateOfBirth(String inputString) {
		String outputString = "";
		if (!inputString.equals("")) {
			inputString = this.skipUnwantedSpaces(inputString);
			if (inputString.contains("\n")) {
				if (inputString.indexOf("\n") > 3) {
					inputString = inputString.replace("\n", "");
				} else if (inputString.indexOf("\n") < 3) {
					inputString = inputString.substring(inputString.indexOf("\n") + 1, inputString.length())
							+ inputString.substring(0, inputString.indexOf("\n"));
				}
			}
			outputString = inputString.substring(0, 2) + "/" + inputString.substring(2, 4) + "/"
					+ inputString.substring(4, inputString.length());
		}
		return outputString;
	}

	// Process name of occupant since it comes along with "/n"
	public String frameNameOfOccupant(String inputString) {

		if (!inputString.equals("")) {
			if (inputString.contains("\n")) {
				inputString = inputString.substring(inputString.indexOf("\n") + 1, inputString.length())
						+ inputString.substring(0, inputString.indexOf("\n")).replaceAll("( +)", " ");
			}
			String[] namesArray = inputString.split(" ");
			for (int i = 0; i < namesArray.length; i++) {
				if (i == 0) {
					inputString = namesArray[i];
				} else if (i == 1) {
					inputString = inputString + ", " + namesArray[i];
				} else {
					inputString = inputString + ", " + namesArray[i];
				}
			}

		}
		return inputString;
	}
}
