/*package com.deemsys.project.patients;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.deemsys.project.entity.Doctors;
import com.deemsys.project.entity.Patients;
import com.deemsys.project.entity.Staff;

@Service
public class PatientFileUpload {
	
	public List<Patients> processUploadFile(String fileName)
	{
			List<Patients> patients=new ArrayList<Patients>();
			
			Staff staff=new Staff();
			staff.setId(1);
			
			Doctors doctors=new Doctors();
			doctors.setId(1);
			
		  try {
	            
	            FileInputStream fis = new FileInputStream(fileName);
	            Workbook workbook = null;
	            if(fileName.toLowerCase().endsWith("xlsx")){
	                workbook = new XSSFWorkbook(fis);
	            }else if(fileName.toLowerCase().endsWith("xls")){
	               workbook = new HSSFWorkbook(fis);
	            }
	           
	            int numberOfSheets = workbook.getNumberOfSheets();            
	            
	            for(int i=0; i < numberOfSheets; i++){                 
	                
	                Sheet sheet = workbook.getSheetAt(i);                
	                
	                Iterator<Row> rowIterator = sheet.iterator();
	                
	                if(rowIterator.hasNext())
	                	rowIterator.next();
	               
	                while (rowIterator.hasNext()) 
	                {
	                	Row row = rowIterator.next();
	                	
	                	String firstName,lastName,reportNumber,phoneNumber,injury,address,otherPassenger,notes;
	                	Date dateOfCrash;

	                	firstName=row.getCell(2).getStringCellValue();
	                	lastName=row.getCell(3).getStringCellValue();
	                	reportNumber= String.valueOf(row.getCell(4).getNumericCellValue());
	                	phoneNumber=String.valueOf(row.getCell(5).getNumericCellValue());
	                	injury=row.getCell(6).getStringCellValue();
	                	address=row.getCell(7).getStringCellValue();
	                	dateOfCrash=row.getCell(8).getDateCellValue();
	                	otherPassenger=row.getCell(9).getStringCellValue();
	                	notes=row.getCell(10).getStringCellValue();
	                    patients.add(new Patients(staff, doctors, firstName,lastName ,reportNumber,phoneNumber,injury ,address,dateOfCrash,otherPassenger,notes,null,null));
	                     
	                }  
	                   
	            } 
	            fis.close();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  	
		  	return patients;
	}

}
*/