package com.deemsys.project.patients;

import java.io.*;
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
public class PatientPDFReader {

	/**
	 * Parses a PDF to a plain text file.
	 * 
	 * @param pdf
	 *            the original PDF
	 * @param txt
	 *            the resulting text
	 * @throws IOException
	 */
	public List<List<String>> parsePdf(MultipartFile pdfFile)
			throws IOException {
		PdfReader reader = new PdfReader(pdfFile.getBytes());
		List<List<String>> contentList = new ArrayList<List<String>>();
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		String pdfText = "";
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i,
					new SimpleTextExtractionStrategy());
			pdfText = strategy.getResultantText();
			contentList.add(Arrays.asList(pdfText.split("\n")));
		}

		return contentList;
	}

	public List<List<String>> getValuesFromPDF(MultipartFile pdfFile)
			throws IOException {
		List<List<String>> content = parsePdf(pdfFile);

		List<List<String>> pageSelectedValues = new ArrayList<List<String>>();

		List<String> pageContent = new ArrayList<String>();

		// Local Report Number
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("LOCAL REPORT NUMBER *") + 1));

		// Crash Severity
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("CRASH SEVERITY HIT/SKIP") - 4));

		// Number of Units
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("NUMBER OF ") - 1));

		// Unit in Error
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("UNIT IN ERROR") - 1));

		// County
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("COUNTY *") + 1));

		// City, Village, Township
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("CITY, VILLAGE, TOWNSHIP  *") + 1));

		// Crash Date
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("CRASH DATE *") + 1));

		// Time of Crash
		pageContent.add(content.get(0).get(
				content.get(0).indexOf("TIME OF  CRASH") + 1));

		int i;
		for (i = 1; i <= Integer.parseInt(content.get(0).get(
				content.get(0).indexOf("NUMBER OF ") - 1)); i++) {
			List<String> unitPageContent = content.get(i);

			List<String> unitPage = new ArrayList<String>();

			// Unit Number
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("UNIT NUMBER") + 1));

			// Owner Name
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("OWNER NAME: LAST, FIRST, MIDDLE       ( SAME AS DRIVER )") + 1));

			// Owner Phone Number
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("OWNER PHONE NUMBER -  INC, AREA  CODE     ( SAME AS DRIVER )") + 1));

			// Owner Address
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("OWNER ADDRESS: CITY, STATE, ZIP                  SAME AS DRIVER )") + 1));

			// Occupants
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("# OCCUPANTS") + 1));

			// Insurance Company
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("INSURANCE COMPANY") + 1));

			// Policy Number
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("POLICY NUMBER") + 1));

			// Damage Scale
			unitPage.add(unitPageContent.get(unitPageContent
					.indexOf("DAMAGE SCALE") + 1));

			pageSelectedValues.add(unitPage);
		}

		// Unit Number
		pageContent.add(content.get(i).get(
				content.get(i).indexOf("UNIT  NUMBER") + 1));

		// Name
		pageContent.add(content.get(i).get(
				content.get(i).indexOf("NAME: LAST, FIRST, MIDDLE") + 1));

		// Date of Birth
		pageContent.add(content.get(i).get(
				content.get(i).indexOf("DATE  OF BIRTH") + 1));

		// Gender
		pageContent.add(content.get(i)
				.get(content.get(i).indexOf("GENDER") + 1));

		// Address, City, State, Zip
		pageContent.add(content.get(i).get(
				content.get(i).indexOf("ADDRESS, CITY, STATE, ZIP") + 1));

		// Contact Phone
		pageContent.add(content.get(i)
				.get(content.get(i).indexOf(
						"CONTACT PHONE  -  INCLUDE  AREA  CODE") + 1));

		// Injuries
		pageContent.add(content.get(i).get(
				content.get(i).indexOf("INJURIES") + 1));

		pageSelectedValues.add(pageContent);

		return pageSelectedValues;

	}

}
