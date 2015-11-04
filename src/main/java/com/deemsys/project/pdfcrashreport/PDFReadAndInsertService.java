package com.deemsys.project.pdfcrashreport;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deemsys.project.patients.PatientsService;

@Service
public class PDFReadAndInsertService {
	
	@Autowired
	PDFCrashReportReader crashReportReader;
	
	@Autowired
	PatientsService patientsService;

	public void doReadOperation(){
		
		File downloadFolder=new File("D://InjuryCrashReport//Download//");
		
		for (File pdfFile : downloadFolder.listFiles()) {
			  PDFCrashReportJson pdfCrashReportJson = null;
			try {
				pdfCrashReportJson = crashReportReader.getValuesFromPDF(crashReportReader.parsePdf(pdfFile.getAbsolutePath()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  boolean crashReportStatus=crashReportReader.checkStatus(pdfCrashReportJson);
			  if(crashReportStatus){
				  Integer patientId=patientsService.savePatients(crashReportReader.getPatientForm(pdfCrashReportJson));
				  File archiveFile=new File("D://InjuryCrashReport//Archive//"+patientId+"_"+pdfFile.getName());
				  pdfFile.renameTo(archiveFile);
				  pdfFile.deleteOnExit();
			  }else{
				  System.out.println("Filename "+pdfFile.getAbsolutePath()+" Report not statisfied the conditions.");
				  pdfFile.delete();
			  }
		}
		
	}
	
	
}
