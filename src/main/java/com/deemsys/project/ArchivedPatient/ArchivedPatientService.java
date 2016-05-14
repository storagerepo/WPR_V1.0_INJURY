package com.deemsys.project.ArchivedPatient;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsys.project.ArchivedCrashReport.ArchivedCrashReportService;
import com.deemsys.project.entity.CrashReport;
import com.deemsys.project.entity.Patient;
import com.deemsys.project.patient.PatientDAO;
import com.deemsys.project.pdfcrashreport.AWSFileUpload;

@Service
@Transactional
public class ArchivedPatientService {

	
	@Autowired
	PatientDAO patientDAO;
	
	@Autowired
	ArchivedCrashReportService archivedCrashReportService;
	
	@Autowired
	AWSFileUpload awsFileUpload;
	
	public void getSixMonthOldPatientsAndDelete(){
		
		List<Patient> patients=patientDAO.getSixMonthPatientsList();
		String crashId="";
		String crashReportFileName="";
		// Delete Crash Report with Patients
		for (Patient patient : patients) {
			CrashReport crashReport=patient.getCrashReport();
			crashId=crashReport.getCrashId();
			crashReportFileName=crashReport.getFilePath();
			// Delete Patient & Crash Reports
			patientDAO.deletePatientByPatientId(patient.getPatientId());
			archivedCrashReportService.deleteOldCrashReport(crashId);
			
			// Delete File In AWS S3
			awsFileUpload.deleteFileInAWSS3(crashReportFileName);
			
		}
		
		//Delete In Crash Report Table with 0 (Zero) Patients
		archivedCrashReportService.getSixMonthOldReportAndDelete();
	}
	
}
