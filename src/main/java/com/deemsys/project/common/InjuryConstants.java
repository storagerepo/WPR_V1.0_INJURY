package com.deemsys.project.common;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.LocalDate;

public class InjuryConstants {
	
	
	public static Integer INJURY_SUPER_ADMIN_ROLE_ID=1;
	public static Integer INJURY_CALLER_ADMIN_ROLE_ID=2;
	public static Integer INJURY_LAWYER_ADMIN_ROLE_ID=3;
	public static Integer INJURY_CALLER_ROLE_ID=4;
	public static Integer INJURY_LAWYER_ROLE_ID=5;
	public static Integer INJURY_AUTO_MANAGER_ROLE_ID=6;
	public static Integer INJURY_AUTO_DEALER_ROLE_ID=7;
	public static Integer INJURY_BODY_SHOP_OWNER_ROLE_ID=8;
	public static Integer INJURY_SHOP_ROLE_ID=9;
	
	public static String INJURY_SUPER_ADMIN_ROLE="ROLE_SUPER_ADMIN";
	public static String INJURY_CALLER_ADMIN_ROLE="ROLE_CALLER_ADMIN";
	public static String INJURY_LAWYER_ADMIN_ROLE="ROLE_LAWYER_ADMIN";
	public static String INJURY_CALLER_ROLE="ROLE_CALLER";
	public static String INJURY_LAWYER_ROLE="ROLE_LAWYER";
	public static String INJURY_AUTO_MANAGER_ROLE="ROLE_AUTO_MANAGER";
	public static String INJURY_AUTO_DEALER_ROLE="ROLE_AUTO_DEALER";
	public static String INJURY_BODY_SHOP_OWNER_ROLE="ROLE_BODY_SHOP_OWNER";
	public static String INJURY_SHOP_ROLE="ROLE_SHOP";
	
	//Look up Preference Type
	public static Integer COUNTY_LOOKUP=1;
	public static Integer TIER_LOOKUP=2;
	public static Integer AGE_LOOKUP=3;
	public static Integer REPORTING_AGENCY_LOOKUP=4;
	
	// Reporting Agency Code Separator
	public static String REPORTING_AGENCY_CODE_SEPARATOR="-";
	public static String REPORTING_AGENCY_NAME_SEPARATOR="-";
	
	// Export Excel Type
	public static Integer PATIENT_EXPORT=1;
	public static Integer ACTVITY_LOG_EXPORT=2;
	
	//1st level scanned reports credentials
	//1st level scanned reports credentials
		public static String[][] MOTORIST_PAGE_COORDINATES_PARSER_ONE={
				{ "occupantOneUnitNumber", "occupantOneName", "occupantOneDateOfBirth",
					"occupantOnePatientAge", "occupantOneGender", "occupantOneAddress",
					"occupantOnePhoneNumber", "occupantOneInjury", "occupantOneEMSAgency",
					"occupantOneMedicalFacility", "occupantOneSeatingPosition" },
			{ "occupantTwoUnitNumber", "occupantTwoName", "occupantTwoDateOfBirth",
					"occupantTwoPatientAge", "occupantTwoGender", "occupantTwoAddress",
					"occupantTwoPhoneNumber", "occupantTwoInjury", "occupantTwoEMSAgency",
					"occupantTwoMedicalFacility", "occupantTwoSeatingPosition" },
			{ "occupantThreeUnitNumber", "occupantThreeName", "occupantThreeDateOfBirth",
					"occupantThreePatientAge", "occupantThreeGender", "occupantThreeAddress",
					"occupantThreePhoneNumber", "occupantThreeInjury", "occupantThreeEMSAgency",
					"occupantThreeMedicalFacility", "occupantThreeSeatingPosition" },
			{ "occupantFourUnitNumber", "occupantFourName", "occupantFourDateOfBirth",
					"occupantFourPatientAge", "occupantFourGender", "occupantFourAddress",
					"occupantFourPhoneNumber", "occupantFourInjury", "occupantFourEMSAgency",
					"occupantFourMedicalFacility", "occupantFourSeatingPosition" } 
					};
		public static String[][] OCCUPANT_PAGE_COORDINATES_PARSER_ONE={
				{ "occupantFiveUnitNumber", "occupantFiveName", "occupantFiveDateOfBirth",
					"occupantFivePatientAge", "occupantFiveGender", "occupantFiveAddress",
					"occupantFivePhoneNumber", "occupantFiveInjury", "occupantFiveEMSAgency",
					"occupantFiveMedicalFacility", "occupantFiveSeatingPosition" },
			{ "occupantSixUnitNumber", "occupantSixName", "occupantSixDateOfBirth",
					"occupantSixPatientAge", "occupantSixGender", "occupantSixAddress",
					"occupantSixPhoneNumber", "occupantSixInjury", "occupantSixEMSAgency",
					"occupantSixMedicalFacility", "occupantSixSeatingPosition" },
			{ "occupantSevenUnitNumber", "occupantSevenName", "occupantSevenDateOfBirth",
					"occupantSevenPatientAge", "occupantSevenGender", "occupantSevenAddress",
					"occupantSevenPhoneNumber", "occupantSevenInjury", "occupantSevenEMSAgency",
					"occupantSevenMedicalFacility", "occupantSevenSeatingPosition" },
			{ "occupantEightUnitNumber", "occupantEightName", "occupantEightDateOfBirth",
					"occupantEightPatientAge", "occupantEightGender", "occupantEightAddress",
					"occupantEightPhoneNumber", "occupantEightInjury", "occupantEightEMSAgency",
					"occupantEightMedicalFacility", "occupantEightSeatingPosition" },
			{ "occupantNineUnitNumber", "occupantNineName", "occupantNineDateOfBirth",
					"occupantNinePatientAge", "occupantNineGender", "occupantNineAddress",
					"occupantNinePhoneNumber", "occupantNineInjury", "occupantNineEMSAgency",
					"occupantNineMedicalFacility", "occupantNineSeatingPosition" },
			{ "occupantTenUnitNumber", "occupantTenName", "occupantTenDateOfBirth",
					"occupantTenPatientAge", "occupantTenGender", "occupantTenAddress",
					"occupantTenPhoneNumber", "occupantTenInjury", "occupantTenEMSAgency",
					"occupantTenMedicalFacility", "occupantTenSeatingPosition" } };
		//second level reports coordinates
	public static String[][] MOTORIST_PAGE_COORDINATES_PARSER_TWO={
			{ "occupantOneUnitNumber", "occupantOnePatientName", "occupantOneDateOfBirth",
				"occupantOnePatientAge", "occupantOneGender", "occupantOneAddressDoorNo",
				"occupantOneAddressStreetName","occupantOneAddressCity","occupantOneAddressState",
				"occupantOnePhoneNumber", "occupantOneInjury", "occupantOneEMSAgency",
				"occupantOneMedicalFacility", "occupantOneSeatingPosition" },
		{ "occupantTwoUnitNumber", "occupantTwoPatientName", "occupantTwoDateOfBirth",
				"occupantTwoPatientAge", "occupantTwoGender","occupantTwoAddressDoorNo",
				"occupantTwoAddressStreetName","occupantTwoAddressCity","occupantTwoAddressState",
				"occupantTwoPhoneNumber", "occupantTwoInjury", "occupantTwoEMSAgency",
				"occupantTwoMedicalFacility", "occupantTwoSeatingPosition" },
		{ "occupantThreeUnitNumber", "occupantThreePatientName", "occupantThreeDateOfBirth",
				"occupantThreePatientAge", "occupantThreeGender","occupantThreeAddressDoorNo",
				"occupantThreeAddressStreetName","occupantThreeAddressCity","occupantThreeAddressState",
				"occupantThreePhoneNumber", "occupantThreeInjury", "occupantThreeEMSAgency",
				"occupantThreeMedicalFacility", "occupantThreeSeatingPosition" },
		{ "occupantFourUnitNumber", "occupantFourPatientName", "occupantFourDateOfBirth",
				"occupantFourPatientAge", "occupantFourGender","occupantFourAddressDoorNo",
				"occupantFourAddressStreetName","occupantFourAddressCity","occupantFourAddressState",
				"occupantFourPhoneNumber", "occupantFourInjury", "occupantFourEMSAgency",
				"occupantFourMedicalFacility", "occupantFourSeatingPosition" } 
				};
	public static String[][] OCCUPANT_PAGE_COORDINATES_PARSER_TWO={
			{ "occupantFiveUnitNumber", "occupantFivePatientName", "occupantFiveDateOfBirth",
				"occupantFivePatientAge", "occupantFiveGender","occupantFiveAddressDoorNo",
				"occupantFiveAddressStreetName","occupantFiveAddressCity","occupantFiveAddressState",
				"occupantFivePhoneNumber", "occupantFiveInjury", "occupantFiveEMSAgency",
				"occupantFiveMedicalFacility", "occupantFiveSeatingPosition" },
		{ "occupantSixUnitNumber", "occupantSixPatientName", "occupantSixDateOfBirth",
				"occupantSixPatientAge", "occupantSixGender","occupantSixAddressDoorNo",
				"occupantSixAddressStreetName","occupantSixAddressCity","occupantSixAddressState",
				"occupantSixPhoneNumber", "occupantSixInjury", "occupantSixEMSAgency",
				"occupantSixMedicalFacility", "occupantSixSeatingPosition" },
		{ "occupantSevenUnitNumber", "occupantSevenPatientName", "occupantSevenDateOfBirth",
				"occupantSevenPatientAge", "occupantSevenGender","occupantSevenAddressDoorNo",
				"occupantSevenAddressStreetName","occupantSevenAddressCity","occupantSevenAddressState",
				"occupantSevenPhoneNumber", "occupantSevenInjury", "occupantSevenEMSAgency",
				"occupantSevenMedicalFacility", "occupantSevenSeatingPosition" },
		{ "occupantEightUnitNumber", "occupantEightPatientName", "occupantEightDateOfBirth",
				"occupantEightPatientAge", "occupantEightGender","occupantEightAddressDoorNo",
				"occupantEightAddressStreetName","occupantEightAddressCity","occupantEightAddressState",
				"occupantEightPhoneNumber", "occupantEightInjury", "occupantEightEMSAgency",
				"occupantEightMedicalFacility", "occupantEightSeatingPosition" },
		{ "occupantNineUnitNumber", "occupantNinePatientName", "occupantNineDateOfBirth",
				"occupantNinePatientAge", "occupantNineGender","occupantNineAddressDoorNo",
				"occupantNineAddressStreetName","occupantNineAddressCity","occupantNineAddressState",
				"occupantNinePhoneNumber", "occupantNineInjury", "occupantNineEMSAgency",
				"occupantNineMedicalFacility", "occupantNineSeatingPosition" },
		{ "occupantTenUnitNumber", "occupantTenPatientName", "occupantTenDateOfBirth",
				"occupantTenPatientAge", "occupantTenGender","occupantTenAddressDoorNo",
				"occupantTenAddressStreetName","occupantTenAddressCity","occupantTenAddressState",
				"occupantTenPhoneNumber", "occupantTenInjury", "occupantTenEMSAgency",
				"occupantTenMedicalFacility", "occupantTenSeatingPosition" } };
	// Convert Date To Year Format
	public static Date convertYearFormat(String date)
	{   SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateformat=new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat=yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert Date To Year Format
	public static Date convertDateFromDateAndTime(String date)
	{   SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateformat=new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat=yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert DateTime to Date Format
	public static Date convertDateTimetoDateFormat(Date date)
	{
	SimpleDateFormat DateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat DateFormat=new SimpleDateFormat("yyyy-MM-dd");

		Date dateformat=new Date();
		try {
			dateformat = DateTimeFormat.parse(DateTimeFormat.format(date));
			dateformat=DateFormat.parse(DateFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert Date to USA Date Format
	public static Date convertDatetoUSDateFormat(Date date) {
		SimpleDateFormat DateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");

		Date dateformat = new Date();
		try {
			dateformat = DateTimeFormat.parse(DateTimeFormat.format(date));
			dateformat = DateFormat.parse(DateFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Convert To 24HourTime
	public static String convert24HourTime(String time) {
		String time24 = "";
		try {

			SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
			SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
			time24 = outFormat.format(inFormat.parse(time));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time24;

	}

	// Convert To AMPMTime
	public static String convertAMPMTime(String time) {
		String time24 = "";
		try {
			SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm aa");
			time24 = outFormat.format(inFormat.parse(time));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time24;

	}

	// Convert To Month Format
	public static String convertMonthFormat(Date date) {
		String convertedDate = "";
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateformat = new Date();
		try {
			if (date != null) {
				dateformat = yearFormat.parse(yearFormat.format(date));
				convertedDate = monthFormat.format(dateformat);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;
	}

	// Convert To USA Month Format With Time
	public static String convertUSAFormatWithTime(Date date) {
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat yearFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss aa");
		Date dateformat = new Date();
		try {
			dateformat = yearFormat.parse(yearFormat.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return monthFormat.format(dateformat);
	}

	// Convert To Year Format With Time
	public static Date convertYearFormatWithTime(String date) {
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy hh:mm:ss aa");
		SimpleDateFormat yearFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}
	
	// Convert To Year Format With Time By Date
	public static Date convertYearFormatWithTimeByDate(String date) {
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat yearFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(date);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Convert To USA Month Format With Time
	public static String convertUSAFormatWithTimeAMPM(String date) {
		String convertedDateTime = "";
		SimpleDateFormat monthFormat = new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat frontMonthFormat = new SimpleDateFormat(
				"MM/dd/yyyy hh:mm:ss aa");
		Date dateformat = new Date();
		try {
			if (date != null && !date.equals("")) {
				dateformat = monthFormat.parse(date);
				convertedDateTime = frontMonthFormat.format(dateformat);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDateTime;
	}

	// Convert String Time To Date Format Time
	public static Date convertToDateFormatTime(String time) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat yearFormat = new SimpleDateFormat("HH:mm");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(time);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Convert Date Time To String Format Time
	public static String convertToStringFormatTime(Date time) {
		SimpleDateFormat yearFormat = new SimpleDateFormat("HH:mm");
		String dateformat = "";
		try {
			dateformat = yearFormat.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateformat;
	}

	// Convert String Time To Date Format Time
	public static Date convertToDateFormatTimeAMPM(String time) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat yearFormat = new SimpleDateFormat("hh:mm a");
		Date dateformat = new Date();
		try {
			dateformat = monthFormat.parse(time);
			dateformat = yearFormat.parse(yearFormat.format(dateformat));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateformat;
	}

	// Convert Date Time To String Time
	public static String convertToStringFormatTimeAm(Date time) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("hh:mm a");
		String convertedTime = "";
		try {
			convertedTime = monthFormat.format(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedTime;
	}

	// Get To Date By adding Number Of Days to From Date
	public static String getToDateByAddingNumberOfDays(String fromDate,
			Integer numberOfDays) {
		String toDate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date convertedDate = dateFormat.parse(fromDate);
			LocalDate fromLocalDate = new LocalDate(convertedDate);
			fromLocalDate = fromLocalDate.plusDays(numberOfDays - 1);
			toDate = dateFormat.format(fromLocalDate.toDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toDate;
	}

	public static List<String> getInvalidInsurance() {
		List<String> invalidInsurance = new ArrayList<String>();
		invalidInsurance.add("none");
		invalidInsurance.add("no insurance");
		invalidInsurance.add("n/a");
		invalidInsurance.add("na");
		invalidInsurance.add("not shown fra");
		invalidInsurance.add("none shown");
		invalidInsurance.add("fra not shown");
		invalidInsurance.add("none listed");
		invalidInsurance.add("fr not shown");
		invalidInsurance.add("not shown");
		invalidInsurance.add("no fr shown");
		invalidInsurance.add("unknown");
		invalidInsurance.add("-");
		return invalidInsurance;
	}

	// Generate Random String with Alpha For Password
	public String genereateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pwd = RandomStringUtils.random(15, 0, 0, false, false,
				characters.toCharArray(), new SecureRandom());
		return pwd;
	}

	public static String getRoleAsText(String currentRole) {
		// TODO Auto-generated method stub
		String roleText = "";
		if (currentRole.equals(INJURY_SUPER_ADMIN_ROLE)) {
			roleText = "CRO Super Admin";
		} else if (currentRole.equals(INJURY_CALLER_ADMIN_ROLE)) {
			roleText = "Caller Admin";
		} else if (currentRole.equals(INJURY_CALLER_ROLE)) {
			roleText = "Caller";
		} else if (currentRole.equals(INJURY_LAWYER_ADMIN_ROLE)) {
			roleText = "Lawyer Admin";
		} else if (currentRole.equals(INJURY_LAWYER_ROLE)) {
			roleText = "Lawyer";
		} else if (currentRole.equals(INJURY_AUTO_MANAGER_ROLE)) {
			roleText = "Dealer Manager";
		} else if (currentRole.equals(INJURY_AUTO_DEALER_ROLE)) {
			roleText = "Dealer";
		} else if (currentRole.equals(INJURY_BODY_SHOP_OWNER_ROLE)) {
			roleText = "Body Shop Owner";
		} else if (currentRole.equals(INJURY_SHOP_ROLE)) {
			roleText = "Shop";
		}
		
		return roleText;
	}

	// convert Bigdecimal to Double
	public static double convertBigDecimaltoDouble(BigDecimal value) {
		double convertedValue = 0;
		if (value != null) {
			convertedValue = value.doubleValue();
		}
		return convertedValue;
	}

	// Convert Double to BigDecimal
	public static BigDecimal convertDoubleBigDecimal(Double value) {
		BigDecimal convertedValue = new BigDecimal(0);
		if (value != null) {
			convertedValue = new BigDecimal(value);
		}
		return convertedValue;
	}

	// Split Name By comma
	public static String[] splitNameByComma(String inputName) {
		String[] resultName = new String[] { "", "", "" };
		if (inputName != null) {
			String[] splittedName = inputName.split(",");
			if (splittedName.length == 2) {
				// Last Name
				resultName[0] = splittedName[0].trim();
				// First Name
				resultName[1] = splittedName[1].trim();
			} else if (splittedName.length == 1) {
				// First Name
				resultName[0] = splittedName[0].trim();
			} else if (splittedName.length == 3) {
				// Last Name
				resultName[0] = splittedName[0].trim();
				// First Name
				resultName[1] = splittedName[1].trim();
				// Middle Name
				resultName[2] = splittedName[2].trim();
			}
		}
		return resultName;
	}

	// Convert String Array to Integer Array
	public static Integer[] convertStringArrayToIntegerArray(String[] inputArray) {
		Integer[] outputArray = new Integer[inputArray.length];
		for (int i = 0; i < inputArray.length; i++) {
			outputArray[i] = Integer.parseInt(inputArray[i]);
		}

		return outputArray;
	}
	
	// Substring Lat and Long
	public static String subStringLatandLang(BigDecimal inputValue){
		if(inputValue!=null){
			String subStringValue=inputValue.toString();
			if(subStringValue.length()>=8){
				return subStringValue.substring(0, subStringValue.indexOf(".")+6);
			}else{
				return subStringValue;
			}
		}else{
			return "";
		}
	}

	// Split Address
	public static String[] splitAddress(String address){
		// Index Position of Address 0 - Street Address, 1- City, 2 - State, 3 - ZipCode
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
	public static boolean isZipcode(String checkStr){
		if(checkStr.matches("\\d+")&&checkStr.length()==5){
			return true;
		}else{
			return false;
		}		
	}
	
	//Check for State
	public static boolean isState(String checkStr){
		if(checkStr.matches("[a-zA-Z]+")&&checkStr.length()==2){
			return true;
		}else{
			return false;
		}
	}
	
	// Get IP Address From Request
	public static String getRemoteAddr(HttpServletRequest request) {
		/*final String[] IP_HEADER_CANDIDATES = { 
		    "X-Forwarded-For",
		    "Proxy-Client-IP",
		    "WL-Proxy-Client-IP",
		    "HTTP_X_FORWARDED_FOR",
		    "HTTP_X_FORWARDED",
		    "HTTP_X_CLUSTER_CLIENT_IP",
		    "HTTP_CLIENT_IP",
		    "HTTP_FORWARDED_FOR",
		    "HTTP_FORWARDED",
		    "HTTP_VIA",
		    "REMOTE_ADDR" };"unknown".equalsIgnoreCase(ip)*/
	    String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
	    if (ipFromHeader != null && ipFromHeader.length() > 0) {
	        System.out.println("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
	        return ipFromHeader;
	    }
	    return request.getRemoteAddr();
	}
}
