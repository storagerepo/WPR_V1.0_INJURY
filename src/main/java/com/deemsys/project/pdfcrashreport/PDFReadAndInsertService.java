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
		
		
	}
	
	
}
