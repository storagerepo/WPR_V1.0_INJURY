package com.deemsys.project.Export;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.deemsys.project.Logging.ActivityDataCount;
import com.deemsys.project.Logging.ActivityDate;
import com.deemsys.project.Logging.ActivityExportData;
import com.deemsys.project.Logging.ActivityIPAddress;
import com.deemsys.project.Logging.ActivityLogsForm;
import com.deemsys.project.Logging.ActivityLogsSearchResult;
import com.deemsys.project.Logging.ActivityLogsService;
import com.deemsys.project.common.InjuryConstants;
import com.deemsys.project.exportFields.ExportFieldsForm;
import com.deemsys.project.login.LoginService;
import com.deemsys.project.patient.PatientSearchList;
import com.deemsys.project.patient.PatientSearchResultSet;
import com.mysql.fabric.xmlrpc.base.Array;

public class ExportExcelView extends AbstractExcelView {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ActivityLogsService activityLogsService;
	
	@Autowired
	LoginService loginService;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		Integer exportType=(Integer) model.get("exportType");
		if(exportType==InjuryConstants.PATIENT_EXPORT){
			// Exclude State Option
			boolean isExcludeState = (Boolean) model.get("isExcludeState");
			PatientSearchResultSet patientSearchResultSet=(PatientSearchResultSet)model.get("patientSearchResultSet");
			System.out.println("Export Excel Total Records--->"+patientSearchResultSet.getPatientSearchLists().size());
			System.out.println("Session Id---->>"+request.getSession().getId());
			
			List<PatientSearchList> patientSearchLists=patientSearchResultSet.getPatientSearchLists();
			@SuppressWarnings("unchecked")
			List<ExportFieldsForm> exportFieldsForms = (List<ExportFieldsForm>) model.get("userExportPrefenceHeaders");
			Sheet sheet = workbook.createSheet("Patients List");
			
			/*//Header List
			List<String> headers=new ArrayList<String>();
			if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)||role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){
				headers.add("FIRST NAME LAST NAME");
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
			headers.add("TIER");*/
			
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
			
			for (ExportFieldsForm exportFieldsForm : exportFieldsForms) {
				cell = row.createCell(c++);
				cell.setCellStyle(style);
				if(exportFieldsForm.getFieldId()==12){
					if(exportFieldsForm.getFormat()==1){
						exportFieldsForm.setFieldName(exportFieldsForm.getFieldName()+": FIRST LAST");
					}else if(exportFieldsForm.getFormat()==2){
						exportFieldsForm.setFieldName(exportFieldsForm.getFieldName()+": FIRST MIDDLE LAST");
					}else if(exportFieldsForm.getFormat()==3){
						exportFieldsForm.setFieldName(exportFieldsForm.getFieldName()+": LAST FIRST");
					}else{
						exportFieldsForm.setFieldName(exportFieldsForm.getFieldName()+": LAST FIRST MIDDLE");
					}
					
				}
				cell.setCellValue(exportFieldsForm.getFieldName());
			}
			
			for(int i = 0 ; i < exportFieldsForms.size(); i++)
			  sheet.autoSizeColumn(i, true);
			
			//Create data cell
			Integer exportCount=0;
			for(PatientSearchList patient:patientSearchLists){
				if(isExcludeState){
					String[] state=this.splitAddress(patient.getAddress());
					if(state[2]!=null){
						if(state[2].equals("")){
							row = sheet.createRow(r++);
							c = 0;
							for (ExportFieldsForm exportFieldsForm : exportFieldsForms) {
								String cellValue=this.getCellValueFromPatient(patient,exportFieldsForm.getFieldId(),exportFieldsForm.getDefaultValue(),exportFieldsForm.getFormat());
								row.createCell(c++).setCellValue(cellValue==null ? "":cellValue);
							}
							exportCount++;
						}else if(state[2].equals("OH")){
							row = sheet.createRow(r++);
							c = 0;
							for (ExportFieldsForm exportFieldsForm : exportFieldsForms) {
								String cellValue=this.getCellValueFromPatient(patient,exportFieldsForm.getFieldId(),exportFieldsForm.getDefaultValue(),exportFieldsForm.getFormat());
								row.createCell(c++).setCellValue(cellValue==null ? "":cellValue);
							}
							exportCount++;
						}
					}					
				}else{
					row = sheet.createRow(r++);
					c = 0;
					for (ExportFieldsForm exportFieldsForm : exportFieldsForms) {
						String cellValue=this.getCellValueFromPatient(patient,exportFieldsForm.getFieldId(),exportFieldsForm.getDefaultValue(),exportFieldsForm.getFormat());
						row.createCell(c++).setCellValue(cellValue==null ? "":cellValue);
					}
				}
				
				/*	if(role.equals(InjuryConstants.INJURY_LAWYER_ROLE)||role.equals(InjuryConstants.INJURY_LAWYER_ADMIN_ROLE)){

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
				*/
			}
			// Activity Of Export Excel
			if(!isExcludeState){
				exportCount=patientSearchLists.size();
			}
			Integer activityId=2;
			ActivityLogsForm activityLogsForm = new ActivityLogsForm(request.getSession().getId(), "", null, loginService.getCurrentUsername(), null, "", activityId, "", null, InjuryConstants.getRemoteAddr(request), "", InjuryConstants.convertMonthFormat(new Date()), InjuryConstants.convertUSAFormatWithTime(new Date()), "", exportCount, exportCount+" Records Exported", 1);
			activityLogsService.saveActivityLogs(activityLogsForm);
			
		}else if(exportType==InjuryConstants.ACTVITY_LOG_EXPORT){
			Integer exportFormat=(Integer) model.get("exportFormat");
			if(exportFormat==1){
				@SuppressWarnings("unchecked")
				List<ActivityExportData> activityExportDatas = (List<ActivityExportData>) model.get("activityLogsForms");
				Sheet sheet = workbook.createSheet("Consolidated Activity Logs");
				
				Row row = null;
				Cell cell = null;
				int r =0;
				int c =0;
				// Header Style 
				CellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(IndexedColors.DARK_BLUE.index);
				
				Font font=workbook.createFont();
				font.setColor(IndexedColors.WHITE.index);
				
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setFont(font);
				// Header Row
				row=sheet.createRow(r++);
				List<String> consolidatedHeader=new ArrayList<String>();
				consolidatedHeader.add("Username");consolidatedHeader.add("Date");consolidatedHeader.add("IP Address");
				consolidatedHeader.add("City");consolidatedHeader.add("Region");consolidatedHeader.add("Country");
				consolidatedHeader.add("Activity");consolidatedHeader.add("No.of Times");consolidatedHeader.add("Total Records");
				for (String header : consolidatedHeader) {
					cell=row.createCell(c++);
					cell.setCellStyle(style);
					cell.setCellValue(header);
				}
				
				for (int i = 0; i < consolidatedHeader.size(); i++) {
					sheet.setColumnWidth(i,4000);
					//sheet.autoSizeColumn(i,true);
				}
				
				boolean isUserName=false,isActivityDate=false,isActivityIpAddress=false;
				for (ActivityExportData activityExportData : activityExportDatas) {
					c=0;
					row=sheet.createRow(r++);
					cell=row.createCell(c++);
					cell.setCellValue(activityExportData.getUsername());
					isUserName=true;
					for (ActivityDate activityDate : activityExportData.getActivityDates()) {
						isActivityDate=true;
						if(!isUserName){
							c=1;
							row=sheet.createRow(r++);
						}
						cell=row.createCell(c++);
						cell.setCellValue(activityDate.getAccessDate());
						for (ActivityIPAddress activityIPAddress : activityDate.getActivityIPAddresses()) {
							isActivityIpAddress=true;
							if(!isActivityDate){
								c=2;
								row=sheet.createRow(r++);
							}
							// Set the Value to 
							row.createCell(c++).setCellValue(activityIPAddress.getIpAddress());
							row.createCell(c++).setCellValue(activityIPAddress.getIpCity());
							row.createCell(c++).setCellValue(activityIPAddress.getIpRegion());
							row.createCell(c++).setCellValue(activityIPAddress.getIpCountry());
							for (ActivityDataCount activityDataCount : activityIPAddress.getActivityDataCounts()) {
								if(!isActivityIpAddress){
									c=6;
									row=sheet.createRow(r++);
								}
								row.createCell(c++).setCellValue(activityDataCount.getActivity());
								row.createCell(c++).setCellValue(activityDataCount.getAccessCount());
								row.createCell(c++).setCellValue(activityDataCount.getNoOfRecordsExported()==null?0:activityDataCount.getNoOfRecordsExported());
								isActivityIpAddress=false;
							}
							isActivityDate=false;
						}
						isUserName=false;
					}
					
				}
			}else if(exportFormat==2){
				ActivityLogsSearchResult activityLogsSearchResult = (ActivityLogsSearchResult) model.get("activityLogsForms");
				Sheet sheet = workbook.createSheet("Complete Activity Logs");
				Row row = null;
				Cell cell = null;
				int r =0;
				int c =0;
				// Header Style 
				CellStyle style = workbook.createCellStyle();
				style.setFillForegroundColor(IndexedColors.DARK_BLUE.index);
				
				Font font=workbook.createFont();
				font.setColor(IndexedColors.WHITE.index);
				
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setFont(font);
				// Header Row
				row=sheet.createRow(r++);
				List<String> completeLogHeader=new ArrayList<String>();
				completeLogHeader.add("Username");completeLogHeader.add("Activity");completeLogHeader.add("IP Address");
				completeLogHeader.add("Date");completeLogHeader.add("Start Time");completeLogHeader.add("End Time");
				completeLogHeader.add("Total Report Count");
				for (String header : completeLogHeader) {
					cell=row.createCell(c++);
					cell.setCellStyle(style);
					cell.setCellValue(header);
				}
				
				for (int i = 0; i < completeLogHeader.size(); i++) {
					sheet.setColumnWidth(i,4000);
				}
				
				for (ActivityLogsForm activityLogsForm  : activityLogsSearchResult.getActivityLogsForms()) {
					row = sheet.createRow(r++);
					c = 0;
					row.createCell(c++).setCellValue(activityLogsForm.getLoginUsername());
					row.createCell(c++).setCellValue(activityLogsForm.getActivity());
					row.createCell(c++).setCellValue(activityLogsForm.getIpAddress());
					row.createCell(c++).setCellValue(activityLogsForm.getAccessDateMonthFormat());
					row.createCell(c++).setCellValue(this.getTimeOnlyDateTime(activityLogsForm.getAccessInTime()));
					row.createCell(c++).setCellValue(this.getTimeOnlyDateTime(activityLogsForm.getAccessOutTime()));
					row.createCell(c++).setCellValue(activityLogsForm.getRecordsCount()!=null?activityLogsForm.getRecordsCount():0);
					/*for (String header : completeLogHeader) {
						row.createCell(c++).setCellValue(this.getCellValueForCompleteActivityLog(header, activityLogsForm));
					}*/
				}
			}
		}
		
		
		
	}
	
	// Swap the Name Format
	public String[] changeNameFormat(String patientName){
		String resultName[]= new String[5];
		// Initialize as Empty
		resultName[0]="";
		resultName[1]="";
		resultName[2]="";
		if(patientName!=null){
			String[] splitName=patientName.split(",");
			if(splitName.length==2){
				//Last Name
				resultName[0]=splitName[0].trim();
				// First Name
				resultName[1]=splitName[1].trim();
			}else if(splitName.length==1){
				// First Name
				resultName[0]=splitName[0].trim();
			}else{
				//Last Name
				resultName[0]=splitName[0].trim();
				// First Name
				resultName[1]=splitName[1].trim();
				// Middle Name
				resultName[2]=splitName[2].trim();
			}
		}
		
		return resultName;
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

	// Get DRIVER Details
	public String getDriverDetails(String seatingPosition){
		String isDriver="";
		if(seatingPosition!=null && seatingPosition.equals("1")){
			isDriver="Driver";
		}
		return isDriver;
	}
	
	// Get Cell Value
	public String getCellValueFromPatient(PatientSearchList patientSearchList,Integer fieldValue, String defaultValue,Integer format){
		
		String value="";
		switch (fieldValue) {
		case 1:
			value=patientSearchList.getLocalReportNumber();
			break;
		case 2:
			value=patientSearchList.getCrashSeverity();
			break;
		case 3:
			value=patientSearchList.getReportingAgencyName();
			break;
		case 4:
			value=patientSearchList.getNumberOfUnits();
			break;
		case 5:
			value=patientSearchList.getUnitInError();
			break;
		case 6:
			value=patientSearchList.getCounty();
			break;
		case 7:
			value=patientSearchList.getCityVillageTownship();
			break;
		case 8:
			value=patientSearchList.getCrashDate();
			break;
		case 9:
			value=patientSearchList.getTimeOfCrash();
			break;
		case 10:
			value=patientSearchList.getUnitNumber();
			break;
		case 11:
			value=this.getDriverDetails(patientSearchList.getSeatingPosition());
			break;
		case 12:
			value=patientSearchList.getName();
			if(format==1){
				// First Name, Last Name
				String[] firstNameLastName=this.changeNameFormat(patientSearchList.getName());
				value=firstNameLastName[1]+" "+firstNameLastName[0];
			}else if(format==2){
				// First Middle Last Name
				String[] firstMiddleLastName=this.changeNameFormat(patientSearchList.getName());
				value=firstMiddleLastName[1]+" "+firstMiddleLastName[2]+" "+firstMiddleLastName[0];
			}else if(format==3){
				// Last Name First Name
				String[] lastFirstName=this.changeNameFormat(patientSearchList.getName());
				value=lastFirstName[0]+" "+lastFirstName[1];
			}
			break;
		case 13:
			value=patientSearchList.getDateOfBirth();
			break;
		case 14:
			if(patientSearchList.getAge()!=null){
				value=patientSearchList.getAge().toString();
			}else{
				value="";
			}
			break;
		case 15:
			value=patientSearchList.getGender();
			break;
		case 16:
			value=patientSearchList.getAddress();
			break;
		case 17:
			if(patientSearchList.getPhoneNumber()!=null&&!patientSearchList.getPhoneNumber().equals("")){
				value=this.formatPhoneNumber(patientSearchList.getPhoneNumber(),format);
			}else{
				value=patientSearchList.getPhoneNumber();
			}
			break;
		case 18:
			value=patientSearchList.getInjuries();
			break;
		case 19:
			value=patientSearchList.getEmsAgency();
			break;
		case 20:
			value=patientSearchList.getMedicalFacility();
			break;
		case 21:
			value=patientSearchList.getAtFaultInsuranceCompany();
			break;
		case 22:
			value=patientSearchList.getAtFaultPolicyNumber();
			break;
		case 23:
			value=patientSearchList.getVictimInsuranceCompany();
			break;
		case 24:
			value=patientSearchList.getVictimPolicyNumber();
			break;
		case 25:
			if(patientSearchList.getTier()==null){
				value="Undetermined";
			}else{
				value="Tier "+patientSearchList.getTier().toString();
			}
			
			break;
		/*case 26:
			// First Name Last Name
			String[] firstNameLastName=this.changeNameFormat(patientSearchList.getName());
			value=firstNameLastName[1]+" "+firstNameLastName[0];
			break;*/
		case 26:
			// Street Address
			String[] address=this.splitAddress(patientSearchList.getAddress());
			value=address[0];
			break;
		case 27:
			// City
			String[] city=this.splitAddress(patientSearchList.getAddress());
			value=city[1];
			break;
		case 28:
			// State
			String[] state=this.splitAddress(patientSearchList.getAddress());
			value=state[2];
			break;
		case 29:
			// ZIP
			String[] zip=this.splitAddress(patientSearchList.getAddress());
			value=zip[3];
			break;
		/*case 30:
			// First Middle Last Name
			String[] firstMiddleLastName=this.changeNameFormat(patientSearchList.getName());
			value=firstMiddleLastName[1]+" "+firstMiddleLastName[2]+" "+firstMiddleLastName[0];
			break;
		case 32:
			// Last Name First Name
			String[] lastFirstName=this.changeNameFormat(patientSearchList.getName());
			value=lastFirstName[0]+" "+lastFirstName[1];
			break;*/
		case 30:
			//First Name
			String[] firstName=this.changeNameFormat(patientSearchList.getName());
			value=firstName[1];
			break;
		case 31:
			// Middle Name
			String[] middleName=this.changeNameFormat(patientSearchList.getName());
			value=middleName[2];
			break;
		case 32:
			// Last Name
			String[] lastName=this.changeNameFormat(patientSearchList.getName());
			value=lastName[0];
			break;
		case 33:
			// Damage Scale
			if(patientSearchList.getDamageScale()!=null){
				value=this.getDamageScaleValue(patientSearchList.getDamageScale());
			}else{
				value="";
			}
			
			break;
		case 34:
			// Salutation of Minor
			if(patientSearchList.getAge()!=null){
				if(defaultValue!=null&&patientSearchList.getAge()<18)
					value=defaultValue;
				else
					value="";
			}else{
				value="";
			}
			
			break;
		case 35:
			// Vehicle Make
			if(patientSearchList.getVehicleMake()!=null){
				value=patientSearchList.getVehicleMake();
			}else{
				value="";
			}
			
			break;
		case 36:
			// Vehicle Make
			if(patientSearchList.getVehicleYear()!=null){
				value=patientSearchList.getVehicleYear();
			}else{
				value="";
			}
			
			break;
		case 37:
			// Vehicle Make
			if(patientSearchList.getVIN()!=null){
				value=patientSearchList.getVIN();
			}else{
				value="";
			}
			
			break;
		default:
			break;
		}
		return value;
	}
	
	// Get Modified Damage Scale Value
	public String getDamageScaleValue(Integer damageScale){
		String modifiedValue="";
		if(damageScale==1){
			modifiedValue=damageScale.toString()+" - None";
		}else if(damageScale==2){
			modifiedValue=damageScale.toString()+" - Minor";
		}else if(damageScale==3){
			modifiedValue=damageScale.toString()+" - Functional";
		}else if(damageScale==4){
			modifiedValue=damageScale.toString()+" - Disabling";
		}else if(damageScale==9){
			modifiedValue=damageScale.toString()+" - Unknown";
		}
		
		return modifiedValue;
	}
	
	// Format Phone Number 
	public String formatPhoneNumber(String phoneNumber,Integer format){
		String formattedPhoneNumber="";
		String phoneNumberReplaced=phoneNumber;
	    String[] arrayPhone=new String[5];
	   if(phoneNumberReplaced.replaceAll("[-() ]", "").length()>=10){
  			if(phoneNumber.contains("-")&&phoneNumber.contains("(")){
  		  	  arrayPhone[0]=phoneNumber.substring(1,4);
  		      arrayPhone[1]=phoneNumber.substring(phoneNumber.lastIndexOf(")")+1,phoneNumber.indexOf("-"));
  		      arrayPhone[2]=phoneNumber.substring(phoneNumber.indexOf("-")+1,phoneNumber.length());
  		      if(phoneNumber.contains(" ")){
  		         arrayPhone[1]=phoneNumber.substring(phoneNumber.lastIndexOf(")")+2,phoneNumber.indexOf("-"));
  		        arrayPhone[2]=phoneNumber.substring(phoneNumber.indexOf("-")+1,phoneNumber.length());
  		      }
  		      
  		    }else if(phoneNumber.contains("-")){
  		      arrayPhone=phoneNumber.split("-");
  		   	 
  		    }else{
  		      arrayPhone[0]=phoneNumber.substring(0,3);
  		      arrayPhone[1]=phoneNumber.substring(3,6);
  		      arrayPhone[2]=phoneNumber.substring(6,10);
  		    }
  		  	
  		  	switch (format) {
  			case 1:
  				formattedPhoneNumber="+1-("+arrayPhone[0]+")-"+arrayPhone[1]+"-"+arrayPhone[2];
  				break;
  			case 2:
  				formattedPhoneNumber="+1 ("+arrayPhone[0]+") "+arrayPhone[1]+" "+arrayPhone[2];
  				break;
  			case 3:
  				formattedPhoneNumber="+1("+arrayPhone[0]+")"+arrayPhone[1]+arrayPhone[2];
  				break;
  			case 4:
  				formattedPhoneNumber="+1"+arrayPhone[0]+arrayPhone[1]+arrayPhone[2];
  				break;
  			case 5:
  				formattedPhoneNumber="1 ("+arrayPhone[0]+") "+arrayPhone[1]+" "+arrayPhone[2];
  				break;
  			case 6:
  				formattedPhoneNumber="1("+arrayPhone[0]+")"+arrayPhone[1]+""+arrayPhone[2];
  				break;
  			case 7:
  				formattedPhoneNumber="1"+arrayPhone[0]+arrayPhone[1]+arrayPhone[2];
  				break;
  			case 8:
  				formattedPhoneNumber="("+arrayPhone[0]+")-"+arrayPhone[1]+"-"+arrayPhone[2];
  				break;
  			case 9:
  				formattedPhoneNumber="("+arrayPhone[0]+") "+arrayPhone[1]+" "+arrayPhone[2];
  				break;
  			case 10:
  				formattedPhoneNumber="("+arrayPhone[0]+")"+arrayPhone[1]+arrayPhone[2];
  				break;
  			case 11:
  				formattedPhoneNumber=arrayPhone[0]+arrayPhone[1]+arrayPhone[2];
  				break;
  			default:
  				formattedPhoneNumber=phoneNumber;
  				break;
  			}
  		  	
  		  	return formattedPhoneNumber;
  		}else{
  			return phoneNumber;
  		}
	  	
	}
	
	public String getTimeOnlyDateTime(String dateTime){
		String timeValue="";
		if(dateTime!=null&&!dateTime.equals("")){
			String[] splittedValue=dateTime.split(" ");
			timeValue=splittedValue[1];
		}
		return timeValue;
	}
	
	// Get Cell Value For Complete Activity Log
	/*public String getCellValueForCompleteActivityLog(String header,ActivityLogsForm activityLogsForm){
		String cellValue="";
		switch (header) {
		case "Username":
			cellValue=activityLogsForm.getLoginUsername();
			break;
		case "Activity":
			cellValue=activityLogsForm.getActivity();
			break;
		case "IP Address":
			cellValue=activityLogsForm.getIpAddress();
			break;
		case "Date":
			cellValue=activityLogsForm.getAccessDate();
			break;
		case "Start Time":
			cellValue=activityLogsForm.getAccessInTime();
			break;
		case "End Time":
			cellValue=activityLogsForm.getAccessOutTime();
			break;
		case "Total Report Exported":
			if(activityLogsForm.getRecordsCount()!=null){
				cellValue=activityLogsForm.getRecordsCount().toString();
			}
			break;
		default:
			break;
		}
		return cellValue;
	}*/
}


