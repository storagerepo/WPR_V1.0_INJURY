package com.deemsys.project.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.deemsys.project.CallerAdmin.CallerAdminService;
import com.deemsys.project.entity.CallerAdmin;

public class InjuryConstants {
	
	
	public static Integer INJURY_SUPER_ADMIN_ROLE_ID=1;
	public static Integer INJURY_CALLER_ADMIN_ROLE_ID=2;
	public static Integer INJURY_LAWYER_ADMIN_ROLE_ID=3;
	public static Integer INJURY_CALLER_ROLE_ID=4;
	public static Integer INJURY_LAWYER_ROLE_ID=5;
	
	public static String INJURY_SUPER_ADMIN_ROLE="ROLE_SUPER_ADMIN";
	public static String INJURY_CALLER_ADMIN_ROLE="ROLE_CALLER_ADMIN";
	public static String INJURY_LAWYER_ADMIN_ROLE="ROLE_LAWYER_ADMIN";
	public static String INJURY_CALLER_ROLE="ROLE_CALLER";
	public static String INJURY_LAWYER_ROLE="ROLE_LAWYER";
	
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
		public static Date convertDatetoUSDateFormat(Date date)
		{
		SimpleDateFormat DateTimeFormat=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat DateFormat=new SimpleDateFormat("MM/dd/yyyy");

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
	
	// Convert To 24HourTime
		public static String convert24HourTime(String time)
		{
			String time24="";
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
				public static String convertAMPMTime(String time)
				{
					String time24="";
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
	public static String convertMonthFormat(Date date)
	{   SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateformat=new Date();
		try {
			dateformat =yearFormat.parse(yearFormat.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return monthFormat.format(dateformat);
	}

	// Convert To USA Month Format With Time
	public static String convertUSAFormatWithTime(Date date)
	{
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		Date dateformat=new Date();
		try {
			dateformat =yearFormat.parse(yearFormat.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return monthFormat.format(dateformat);
	}

	// Convert To Year Format With Time
	public static Date convertYearFormatWithTime(String date)
	{
		  SimpleDateFormat monthFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	
	// Convert String Time To  Date Format Time
	public static Date convertToDateFormatTime(String time)
	{
			SimpleDateFormat monthFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat yearFormat = new SimpleDateFormat("HH:mm");
			Date dateformat=new Date();
			try {
				dateformat = monthFormat.parse(time);
				dateformat=yearFormat.parse(yearFormat.format(dateformat));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return dateformat;
	}
	
	// Convert Date Time To  String Format Time
		public static String convertToStringFormatTime(Date time)
		{
				SimpleDateFormat monthFormat = new SimpleDateFormat("HH:mm");
				SimpleDateFormat yearFormat = new SimpleDateFormat("HH:mm");
				String dateformat="";
				try{
				dateformat=yearFormat.format(time);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return dateformat;
		}
	
	// Convert String Time To  Date Format Time
		public static Date convertToDateFormatTimeAMPM(String time)
		{
				SimpleDateFormat monthFormat = new SimpleDateFormat("hh:mm a");
				SimpleDateFormat yearFormat = new SimpleDateFormat("hh:mm a");
				Date dateformat=new Date();
				try {
					dateformat = monthFormat.parse(time);
					dateformat=yearFormat.parse(yearFormat.format(dateformat));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return dateformat;
		}
		
		// Convert Date Time To String Time
		public static String convertToStringFormatTimeAm(Date time) 
		{
				SimpleDateFormat monthFormat = new SimpleDateFormat("hh:mm a");
				String convertedTime="";
				try{
				convertedTime=monthFormat.format(time);
				}
				catch(Exception e){
				e.printStackTrace();
				}
				return convertedTime;
		}
		// Get To Date By adding Number Of Days to From Date
		public static String getToDateByAddingNumberOfDays(String fromDate,Integer numberOfDays){
			String toDate="";
			SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
			try {
				Date convertedDate=dateFormat.parse(fromDate);
				LocalDate fromLocalDate=new LocalDate(convertedDate);
				fromLocalDate=fromLocalDate.plusDays(numberOfDays-1);
				toDate=dateFormat.format(fromLocalDate.toDate());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return toDate;
		}
		
		public static List<String> getInvalidInsurance(){
			List<String> invalidInsurance=new ArrayList<String>();
			invalidInsurance.add("none");
			invalidInsurance.add("no insurance");
			invalidInsurance.add("n/a");
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
		
}
