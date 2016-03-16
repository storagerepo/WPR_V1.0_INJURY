package com.deemsys.project.pdfcrashreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deemsys.project.patient.PatientService;

@Service
public class PDFReadAndInsertService {
	
	@Autowired
	PDFCrashReportReader crashReportReader;
	
	@Autowired
	PatientService patientsService;

	public void doReadOperation(){
		
		
	}
	
	
}
