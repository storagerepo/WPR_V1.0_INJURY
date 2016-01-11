package com.deemsys.project.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class InjuryConstants {
	
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
		  SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		SimpleDateFormat yearFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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
}
