package com.deemsys.project.Export;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;

import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.patient.PatientSearchList;
import com.deemsys.project.patient.PatientSearchResultSet;

public class ExportExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		PatientSearchResultSet patientSearchResultSet=(PatientSearchResultSet)model.get("patientSearchResultSet");
		String role=(String) model.get("role");
		List<PatientSearchList> patientSearchLists=patientSearchResultSet.getPatientSearchLists();
		
		Sheet sheet = workbook.createSheet("Patients List");
		
		//Header List
		List<String> headers=new ArrayList<String>();
		if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)||role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
			headers.add("FIRST NAME, LAST NAME");
			headers.add("STREET ADDRESS");
			headers.add("CITY");
			headers.add("STATE");
			headers.add("ZIP");
		}
		
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
		
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
			headers.add("NAME:LAST FIRST MIDDLE");
		}
		
		headers.add("DATE OF BIRTH");
		headers.add("AGE");
		headers.add("GENDER");
		
		if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
			headers.add("ADDRESS CITY STATE ZIP");
		}
		
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

			if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)||role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){

			String[] address=this.splitAddress(patient.getAddress());
			
			row.createCell(c++).setCellValue(this.changeNameFormat(patient.getName()));
			row.createCell(c++).setCellValue(address[0]);
			row.createCell(c++).setCellValue(address[1]);
			row.createCell(c++).setCellValue(address[2]);
			row.createCell(c++).setCellValue(address[3]);
			}
			
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
			
			if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
				row.createCell(c++).setCellValue(patient.getName());
			}
			
			row.createCell(c++).setCellValue(patient.getDateOfBirth());
			row.createCell(c++).setCellValue(age);
			row.createCell(c++).setCellValue(patient.getGender());
			
			if(role.equals(InjuryConstants.INJURY_CALLER_ADMIN_ROLE)||role.equals(InjuryConstants.INJURY_CALLER_ROLE)||role.equals(InjuryConstants.INJURY_SUPER_ADMIN_ROLE)){
				row.createCell(c++).setCellValue(patient.getAddress());
			}
			
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
	
	// Swap the Name Format
	public String changeNameFormat(String patientName){
		String changedPatientName="";
		if(patientName!=null){
			String[] splitName=patientName.split(",");
			if(splitName.length==2){
					// First Name Last Name
					changedPatientName=splitName[1].trim()+", "+splitName[0].trim();
			}else if(splitName.length==1){
				// First Name
				changedPatientName=splitName[0].trim();
			}else{
				// First Name Last Name
				changedPatientName=splitName[1].trim()+", "+splitName[0].trim();
			}
		}
		
		return changedPatientName;
	}
	
	// Split Address
	public String[] splitAddress(String address){
		String resultAddress[]=new String[10];
		if(address!=null){
			String[] splittedAddress=address.split(",");
			if(splittedAddress.length==1){
				resultAddress[0]=splittedAddress[0].trim();
			}
			else if(splittedAddress.length==2){
				
				if(isZipcode(splittedAddress[1].trim())){
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[3]=splittedAddress[1].trim();
				}else if(isState(splittedAddress[1].trim())){
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[2]=splittedAddress[1].trim();
				}else{
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[1]=splittedAddress[1].trim();
				}			

			}else if(splittedAddress.length==3){
				if(isZipcode(splittedAddress[2].trim())){
					resultAddress[0]=splittedAddress[0].trim();
					resultAddress[1]=splittedAddress[1].trim();
					resultAddress[3]=splittedAddress[2].trim();
				}else{
				resultAddress[0]=splittedAddress[0].trim();
				resultAddress[1]=splittedAddress[1].trim();
				resultAddress[2]=splittedAddress[2].trim();
				}
			}else if(splittedAddress.length==4){
				resultAddress[0]=splittedAddress[0].trim();
				resultAddress[1]=splittedAddress[1].trim();
				resultAddress[2]=splittedAddress[2].trim();
				resultAddress[3]=splittedAddress[3].trim();
			}else if(splittedAddress.length>4){
				
				resultAddress[3]=splittedAddress[splittedAddress.length-1].trim();
				resultAddress[2]=splittedAddress[splittedAddress.length-2].trim();
				resultAddress[1]=splittedAddress[splittedAddress.length-3].trim();
				resultAddress[0]="";
				for(int i=0;i<=splittedAddress.length-4;i++){
					if(i!=0){
						resultAddress[0]+=",";
					}
					resultAddress[0]+=splittedAddress[i].trim();
				}
				
			}
			
		}
		
		return resultAddress;
	}
	
	//Check for Zipcode
	public boolean isZipcode(String checkStr){
		if(checkStr.matches("\\d+")&&checkStr.length()==5){
			return true;
		}else{
			return false;
		}		
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
