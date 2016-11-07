package com.deemsys.project.Export;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;

import com.deemsys.project.patient.PatientSearchList;
import com.deemsys.project.patient.PatientSearchResultSet;

public class ExportExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		PatientSearchResultSet patientSearchResultSet=(PatientSearchResultSet)model.get("patientSearchResultSet");
		List<PatientSearchList> patientSearchLists=patientSearchResultSet.getPatientSearchLists();
		
		Sheet sheet = workbook.createSheet("Patients List");
		
		//Header List
		List<String> headers=new ArrayList<String>();
		headers.add("S.NO");
		headers.add("LOCAL REPORT NUMBER");
		headers.add("CRASH SEVERITY");
		headers.add("REPORTING AGENCY NAME");
		headers.add("NUMBER OF UNITS");
		headers.add("UNIT IN ERROR");
		headers.add("COUNTY");
		headers.add("CITY VILLAGE TOWNSHIP");
		headers.add("CRASH DATE");
		headers.add("TIME OF CRASH");
		headers.add("UNIT NUMBER");
		headers.add("NAME: LAST FIRST MIDDLE");
		headers.add("DATE OF BIRTH");
		headers.add("AGE");
		headers.add("GENDER");
		headers.add("ADDRESS CITY STATE ZIP");
		headers.add("CONTACT PHONE - INCLUDE AREA CODE");
		headers.add("INJURIES");
		headers.add("EMS AGENCY");
		headers.add("MEDICAL FACILITY");
		headers.add("ATFAULT INSURANCE COMPANY");
		headers.add("ATFAULT POLICY NUMBER");
		headers.add("VICTIM INSURANCE COMPANY");
		headers.add("VICTIM POLICY NUMBER");
		headers.add("TIER");
		
		Row row = null;
		Cell cell = null;
		int r = 0;
		int c = 0;
		
		//Style for header cell
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.DARK_BLUE.index);
		
		Font font=workbook.createFont();
		font.setColor(IndexedColors.WHITE.index);
		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font);
		
		//Create header cells
		row = sheet.createRow(r++);
		
		for (String header : headers) {
			cell = row.createCell(c++);
			cell.setCellStyle(style);
			cell.setCellValue(header);
		}
		
		
		
		int sno=1;
		//Create data cell
		for(PatientSearchList patient:patientSearchLists){
			
			String age="";
			if(patient.getAge()==null){
				age="";
			}else{
				age=patient.getAge().toString();
			}
			
			row = sheet.createRow(r++);
			c = 0;
			row.createCell(c++).setCellValue(sno++);
			row.createCell(c++).setCellValue(patient.getLocalReportNumber());
			row.createCell(c++).setCellValue(patient.getCrashSeverity());
			row.createCell(c++).setCellValue(patient.getReportingAgencyName());
			row.createCell(c++).setCellValue(patient.getNumberOfUnits());
			row.createCell(c++).setCellValue(patient.getUnitInError());
			row.createCell(c++).setCellValue(patient.getCounty());
			row.createCell(c++).setCellValue(patient.getCityVillageTownship());
			row.createCell(c++).setCellValue(patient.getCrashDate());
			row.createCell(c++).setCellValue(patient.getTimeOfCrash());
			row.createCell(c++).setCellValue(patient.getUnitNumber());
			row.createCell(c++).setCellValue(patient.getName());
			row.createCell(c++).setCellValue(patient.getDateOfBirth());
			row.createCell(c++).setCellValue(age);
			row.createCell(c++).setCellValue(patient.getGender());
			row.createCell(c++).setCellValue(patient.getAddress());
			row.createCell(c++).setCellValue(patient.getPhoneNumber());
			row.createCell(c++).setCellValue(patient.getInjuries());
			row.createCell(c++).setCellValue(patient.getEmsAgency());
			row.createCell(c++).setCellValue(patient.getMedicalFacility());
			row.createCell(c++).setCellValue(patient.getAtFaultInsuranceCompany());
			row.createCell(c++).setCellValue(patient.getAtFaultPolicyNumber());
			row.createCell(c++).setCellValue(patient.getVictimInsuranceCompany());
			row.createCell(c++).setCellValue(patient.getVictimPolicyNumber());
			row.createCell(c++).setCellValue("Tier "+patient.getTier());
		}
		for(int i = 0 ; i < headers.size(); i++)
			sheet.autoSizeColumn(i, true);
		
		
	}

}
