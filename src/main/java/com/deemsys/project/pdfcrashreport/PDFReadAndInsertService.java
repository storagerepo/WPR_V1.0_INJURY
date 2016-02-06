package com.deemsys.project.pdfcrashreport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deemsys.project.patients.PatientsForm;
import com.deemsys.project.patients.PatientsService;

@Service
public class PDFReadAndInsertService {
	
	@Autowired
	PDFCrashReportReader crashReportReader;
	
	@Autowired
	PatientsService patientsService;

	public void doReadOperation(){
		
		Logger logger = LoggerFactory.getLogger("service");
		
		File downloadFolder=new File("D://InjuryCrashReport//workspace//");
		
		for (File pdfFile : downloadFolder.listFiles()) {
			  PDFCrashReportJson pdfCrashReportJson = null;
			try {
				pdfCrashReportJson = crashReportReader.getValuesFromPDF(crashReportReader.parsePdf(pdfFile.getAbsolutePath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  Integer crashReportStatus=crashReportReader.checkStatus(pdfCrashReportJson);
			  if(crashReportStatus==1){
				  List<PatientsForm> patientsForms=new ArrayList<PatientsForm>();
				  patientsForms=crashReportReader.getPatientForm(pdfCrashReportJson);
				 String filename="";
				 File archiveFile=null; 
				 if(patientsForms.size()>0){
					  filename="D://InjuryCrashReport//Archive//"+patientsForms.get(0).getCountry()+"_CrashReport_"+patientsForms.get(0).getLocalReportNumber()+".pdf";								 
					  archiveFile=new File(filename);
					  pdfFile.renameTo(archiveFile);
				  }
				   for (PatientsForm patientsForm : patientsForms) {
					    patientsForm.setCrashReportFileName(archiveFile.getName());
					  	patientsService.savePatients(patientsForm);
				  }			 
				  	
					pdfFile.delete();
				
			  }else{
				  logger.error("Filename "+pdfFile.getAbsolutePath()+" Report not statisfied the conditions.");
				  pdfFile.delete();
			  }
		}
		
	}
	
	
}
